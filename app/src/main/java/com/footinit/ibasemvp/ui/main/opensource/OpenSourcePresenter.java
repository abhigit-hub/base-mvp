package com.footinit.ibasemvp.ui.main.opensource;

import com.footinit.ibasemvp.R;
import com.footinit.ibasemvp.data.DataManager;
import com.footinit.ibasemvp.data.db.model.OpenSource;
import com.footinit.ibasemvp.ui.base.BasePresenter;
import com.footinit.ibasemvp.utils.AppLogger;
import com.footinit.ibasemvp.utils.rx.SchedulerProvider;

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

public class OpenSourcePresenter<V extends OpenSourceMvpView> extends BasePresenter<V>
        implements OpenSourceMvpPresenter<V>, OpenSourceAdapter.Callback {


    @Inject
    public OpenSourcePresenter(SchedulerProvider schedulerProvider,
                               CompositeDisposable compositeDisposable,
                               DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void fetchOpenSourceList() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getMvpView().onPullToRefreshEvent(true);

            getCompositeDisposable().add(
                    getDataManager().doOpenSourceListCall()
                            .flatMap(openSources -> Observable.concat(
                                    getDataManager().wipeOpenSourceData().subscribeOn(getSchedulerProvider().io()).toObservable(),
                                    getDataManager().insertOpenSourceList(openSources).subscribeOn(getSchedulerProvider().io()))
                                    .doOnError(throwable -> AppLogger.e(throwable, OpenSourcePresenter.class.getSimpleName()))
                                    .ignoreElements()
                                    .andThen(Observable.just(openSources)))
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(openSources -> {
                                if (!isViewAttached())
                                    return;

                                getMvpView().hideLoading();
                                getMvpView().onPullToRefreshEvent(false);

                                if (openSources != null && openSources.size() > 0)
                                    getMvpView().updateOpenSourceList(openSources);
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

    @Override
    public void onOpenSourceEmptyRetryClicked() {
        fetchOpenSourceList();
        getMvpView().onOpenSourceListReFetched();
    }

    @Override
    public void onOpenSourceItemClicked(OpenSource openSource) {
        getMvpView().openOSDetailsActivity(openSource);
    }

    private void showPersistentData() {
        getCompositeDisposable().add(
                getDataManager().getOpenSourceList()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(openSourceList -> {
                            if (!isViewAttached())
                                return;

                            if (openSourceList != null) {
                                getMvpView().updateOpenSourceList(openSourceList);
                                getMvpView().onError(R.string.no_internet);
                            }
                        }, throwable -> {
                            if (!isViewAttached())
                                return;

                            getMvpView().onError(R.string.could_not_show_items);
                        })
        );
    }
}
