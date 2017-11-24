package com.footinit.selfproject.ui.main.opensource;

import com.footinit.selfproject.data.DataManager;
import com.footinit.selfproject.data.db.model.OpenSource;
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
            getCompositeDisposable().add(
                    getDataManager().doOpenSourceListCall()
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(new Consumer<List<OpenSource>>() {
                                @Override
                                public void accept(List<OpenSource> list) throws Exception {
                                    if (!isViewAttached())
                                        return;

                                    if (list != null) {
                                        getMvpView().updateOpenSourceList(list);
                                        clearOpenSourceListFromDb(list);
                                    }

                                    getMvpView().hideLoading();
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

    @Override
    public void onOpenSourceEmptyRetryClicked() {
        fetchOpenSourceList();
        getMvpView().onOpenSourceListReFetched();
    }

    @Override
    public void onOpenSourceItemClicked(OpenSource openSource) {
        getMvpView().openOSDetailsActivity(openSource);
    }

    private void clearOpenSourceListFromDb(List<OpenSource> openSourceList) {

        getDataManager().wipeOpenSourceData()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        addOpenSourceListToDb(openSourceList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void addOpenSourceListToDb(List<OpenSource> openSourceList) {
        getCompositeDisposable().add(
                getDataManager().insertOpenSourceList(openSourceList)
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

    private void showPersistentData() {
        getCompositeDisposable().add(
                getDataManager().getOpenSourceList()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<List<OpenSource>>() {
                            @Override
                            public void accept(List<OpenSource> openSourceList) throws Exception {
                                if (!isViewAttached())
                                    return;

                                if (openSourceList != null) {
                                    getMvpView().updateOpenSourceList(openSourceList);
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
}
