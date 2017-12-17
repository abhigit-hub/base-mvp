package com.footinit.selfproject.ui.main.feed;

import com.footinit.selfproject.R;
import com.footinit.selfproject.data.DataManager;
import com.footinit.selfproject.data.db.model.Blog;
import com.footinit.selfproject.data.db.model.OpenSource;
import com.footinit.selfproject.ui.base.BasePresenter;
import com.footinit.selfproject.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Abhijit on 23-11-2017.
 */

public class FeedPresenter<V extends FeedMvpView> extends BasePresenter<V>
        implements FeedMvpPresenter<V>, FeedAdapter.Callback {

    @Inject
    public FeedPresenter(SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable,
                         DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void onViewPrepared() {
        retrieveBlogList();
    }

    private void retrieveBlogList() {
        getMvpView().showLoading();
        getCompositeDisposable().add(
                getDataManager().getBlogList()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(blogList -> {
                            if (!isViewAttached())
                                return;

                            getMvpView().hideLoading();
                            if (blogList != null) {
                                ArrayList<Object> list = new ArrayList<>();
                                list.addAll(blogList);
                                retrieveOpenSourceList(list);
                            }

                        }, throwable -> {
                            if (!isViewAttached())
                                return;

                            getMvpView().hideLoading();
                            retrieveOpenSourceList(new ArrayList<Object>());
                        })
        );
    }

    private void retrieveOpenSourceList(List<Object> list) {
        getMvpView().showLoading();
        getCompositeDisposable().add(
                getDataManager().getOpenSourceList()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(openSourceList -> {
                    if (!isViewAttached())
                        return;

                    getMvpView().hideLoading();
                    if (openSourceList != null) {
                        list.addAll(openSourceList);
                        Collections.shuffle(list);
                        getMvpView().onListRetrieved(list);
                    }
                }, throwable -> {
                    if (!isViewAttached())
                        return;

                    getMvpView().hideLoading();
                    getMvpView().onError(R.string.something_went_wrong);

                    if (list != null && list.size() > 0)
                        getMvpView().onListRetrieved(list);
                })
        );
    }

    @Override
    public void onBlogItemClicked(Blog blog) {
        getMvpView().openBlogDetailsActivity(blog);
    }

    @Override
    public void onOpenSourceItemClicked(OpenSource openSource) {
        getMvpView().openOSDetailsActivity(openSource);
    }
}
