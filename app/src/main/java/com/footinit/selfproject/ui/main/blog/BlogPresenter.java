package com.footinit.selfproject.ui.main.blog;

import com.footinit.selfproject.data.DataManager;
import com.footinit.selfproject.data.db.model.Blog;
import com.footinit.selfproject.ui.base.BasePresenter;
import com.footinit.selfproject.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Abhijit on 17-11-2017.
 */

public class BlogPresenter<V extends BlogMvpView> extends BasePresenter<V>
        implements BlogMvpPresenter<V>, BlogAdapter.Callback {


    @Inject
    public BlogPresenter(SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable,
                         DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void fetchBlogList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getCompositeDisposable().add(
                    getDataManager().doBlogListApiCall()
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(new Consumer<List<Blog>>() {
                                @Override
                                public void accept(List<Blog> blogList) throws Exception {
                                    if (!isViewAttached())
                                        return;

                                    getMvpView().hideLoading();
                                    if (blogList != null) {
                                        getMvpView().updateBlogList(blogList);
                                        clearBlogListFromDb(blogList);
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    if (!isViewAttached())
                                        return;

                                    getMvpView().hideLoading();
                                    getMvpView().onError("Could not fetch items");
                                    showPersistentData();
                                }
                            })
            );
        } else {
            showPersistentData();
        }
    }

    private void showPersistentData() {
        getCompositeDisposable().add(
                getDataManager().getBlogList()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Blog>>() {
                    @Override
                    public void accept(List<Blog> blogList) throws Exception {
                        if (!isViewAttached())
                            return;

                        if (blogList != null) {
                            getMvpView().updateBlogList(blogList);
                            getMvpView().onError("Showing Stale Items");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (!isViewAttached())
                            return;

                        getMvpView().onError("Could not show items");
                    }
                })
        );
    }

    private void clearBlogListFromDb(List<Blog> blogList) {

        getDataManager().wipeBlogData()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        addBlogListToDb(blogList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void addBlogListToDb(List<Blog> blogList) {
        getCompositeDisposable().add(
                getDataManager().insertBlogList(blogList)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<List<Long>>() {
                            @Override
                            public void accept(List<Long> longs) throws Exception {
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        })
        );
    }

    @Override
    public void onBlogEmptyRetryClicked() {
        fetchBlogList();
        getMvpView().onBlogListReFetched();
    }

    @Override
    public void onBlogItemClicked(Blog blog) {
        getMvpView().openBlogDetailActivity(blog);
    }
}
