package com.footinit.ibasemvp.ui.main.blog;

import com.footinit.ibasemvp.R;
import com.footinit.ibasemvp.data.DataManager;
import com.footinit.ibasemvp.data.db.model.Blog;
import com.footinit.ibasemvp.ui.base.BasePresenter;
import com.footinit.ibasemvp.utils.AppLogger;
import com.footinit.ibasemvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
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
            getMvpView().onPullToRefreshEvent(true);

            getCompositeDisposable().add(
                    getDataManager().doBlogListApiCall()
                            .flatMap(blogs -> Observable.concat(
                                    getDataManager().wipeBlogData().subscribeOn(getSchedulerProvider().io()).toObservable(),
                                    getDataManager().insertBlogList(blogs).subscribeOn(getSchedulerProvider().io()))
                                    .doOnError(throwable -> AppLogger.e(throwable, BlogPresenter.class.getSimpleName()))
                                    .ignoreElements()
                                    .andThen(Observable.just(blogs)))
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(blogs -> {
                                if (!isViewAttached())
                                    return;

                                getMvpView().hideLoading();
                                getMvpView().onPullToRefreshEvent(false);

                                if (blogs != null && blogs.size() > 0)
                                    getMvpView().updateBlogList(blogs);
                            }, throwable -> {
                                if (!isViewAttached())
                                    return;

                                getMvpView().hideLoading();
                                getMvpView().onPullToRefreshEvent(false);
                                getMvpView().onError(R.string.could_not_fetch_items);
                                showPersistentData();
                            })
            );

        } else {
            getMvpView().onPullToRefreshEvent(false);
            showPersistentData();
        }
    }

    private void showPersistentData() {
        getCompositeDisposable().add(
                getDataManager().getBlogList()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(blogList -> {
                            if (!isViewAttached())
                                return;

                            if (blogList != null) {
                                getMvpView().updateBlogList(blogList);
                                getMvpView().onError(R.string.no_internet);
                            }
                        }, throwable -> {
                            if (!isViewAttached())
                                return;

                            getMvpView().onError(R.string.could_not_show_items);
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
