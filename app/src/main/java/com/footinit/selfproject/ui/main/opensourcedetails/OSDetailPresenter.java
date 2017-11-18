package com.footinit.selfproject.ui.main.opensourcedetails;

import com.footinit.selfproject.data.DataManager;
import com.footinit.selfproject.data.db.model.OpenSource;
import com.footinit.selfproject.ui.base.BasePresenter;
import com.footinit.selfproject.utils.rx.SchedulerProvider;

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
