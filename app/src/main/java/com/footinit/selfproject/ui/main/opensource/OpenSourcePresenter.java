package com.footinit.selfproject.ui.main.opensource;

import com.footinit.selfproject.data.DataManager;
import com.footinit.selfproject.data.db.model.OpenSource;
import com.footinit.selfproject.ui.base.BasePresenter;
import com.footinit.selfproject.ui.base.MvpPresenter;
import com.footinit.selfproject.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Abhijit on 17-11-2017.
 */

public class OpenSourcePresenter<V extends OpenSourceMvpView> extends BasePresenter<V>
        implements OpenSourceMvpPresenter<V> {


    @Inject
    public OpenSourcePresenter(SchedulerProvider schedulerProvider,
                               CompositeDisposable compositeDisposable,
                               DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void onViewPrepared() {
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

                                if (list != null)
                                    getMvpView().updateOpenSourceList(list);

                                getMvpView().hideLoading();
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
}
