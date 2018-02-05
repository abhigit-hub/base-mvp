package com.footinit.ibasemvp.ui.splash;

import com.footinit.ibasemvp.data.DataManager;
import com.footinit.ibasemvp.ui.base.BasePresenter;
import com.footinit.ibasemvp.utils.rx.SchedulerProvider;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Abhijit on 08-11-2017.
 */

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    @Inject
    SplashPresenter(SchedulerProvider schedulerProvider,
                    CompositeDisposable compositeDisposable,
                    DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

        startActivityWithDelay();
    }

    private void startActivityWithDelay() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                decideNextActivity();
            }
        }, 2000);
    }

    private void decideNextActivity() {
        if (getDataManager().getCurrentUserLoggedInMode() ==
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType())
            getMvpView().openLoginActivity();
        else getMvpView().openMainActivity();
    }
}
