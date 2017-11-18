package com.footinit.selfproject.ui.main.blog;

import com.footinit.selfproject.data.DataManager;
import com.footinit.selfproject.data.db.model.Blog;
import com.footinit.selfproject.ui.base.BasePresenter;
import com.footinit.selfproject.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
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
    public void onViewPrepared() {
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
                                if (blogList != null)
                                    getMvpView().updateBlogList(blogList);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (!isViewAttached())
                                    return;

                                getMvpView().hideLoading();
                                getMvpView().onError("Could not fetch items");
                            }
                        })
        );
    }

    @Override
    public void onBlogEmptyRetryClicked() {
        getMvpView().onBlogEmptyRetryClicked();
    }

    @Override
    public void onBlogItemClicked(Blog blog) {
        getMvpView().openBlogDetailActivity(blog);
    }
}
