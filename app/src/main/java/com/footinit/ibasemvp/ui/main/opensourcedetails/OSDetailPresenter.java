package com.footinit.ibasemvp.ui.main.opensourcedetails;

import com.footinit.ibasemvp.data.DataManager;
import com.footinit.ibasemvp.ui.base.BasePresenter;
import com.footinit.ibasemvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Abhijit on 18-11-2017.
 */

public class OSDetailPresenter<V extends OSDetailMvpView> extends BasePresenter<V>
        implements OSDetailMvpPresenter<V> {

    @Inject
    public OSDetailPresenter(SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable,
                             DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void onOSDetailsDisplayedError() {
        getMvpView().returnToMainActivity();
    }

    @Override
    public void onOpenSourceFABClicked() {
            getMvpView().openInBrowser();
    }
}
