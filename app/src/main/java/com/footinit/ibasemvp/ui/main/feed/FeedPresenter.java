package com.footinit.ibasemvp.ui.main.feed;

import com.footinit.ibasemvp.R;
import com.footinit.ibasemvp.data.DataManager;
import com.footinit.ibasemvp.data.db.model.Blog;
import com.footinit.ibasemvp.data.db.model.OpenSource;
import com.footinit.ibasemvp.ui.base.BasePresenter;
import com.footinit.ibasemvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
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
        retrieveAllList();
    }

    private void retrieveAllList() {
        getMvpView().showLoading();

        getCompositeDisposable().add(
                Observable.zip(getDataManager().getBlogList(),
                        getDataManager().getOpenSourceList(),
                        (t1, t2) -> {
                            List<Object> list = new ArrayList<>();

                            if (t1 != null && t1.size() > 0) list.addAll(t1);
                            if (t2 != null && t2.size() > 0) list.addAll(t2);
                            return list;
                        })
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(objectList -> {
                            if (!isViewAttached())
                                return;

                            getMvpView().hideLoading();
                            if (objectList != null && objectList.size() > 0) {
                                Collections.shuffle(objectList);
                                getMvpView().onListRetrieved(objectList);
                            }
                        }, throwable -> {
                            if (!isViewAttached())
                                return;

                            getMvpView().hideLoading();
                            getMvpView().onError(R.string.something_went_wrong);
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
