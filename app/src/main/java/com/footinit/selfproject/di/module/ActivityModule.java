package com.footinit.selfproject.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.footinit.selfproject.di.ActivityContext;
import com.footinit.selfproject.di.PerActivity;
import com.footinit.selfproject.ui.login.LoginMvpPresenter;
import com.footinit.selfproject.ui.login.LoginMvpView;
import com.footinit.selfproject.ui.login.LoginPresenter;
import com.footinit.selfproject.ui.splash.SplashMvpPresenter;
import com.footinit.selfproject.ui.splash.SplashMvpView;
import com.footinit.selfproject.ui.splash.SplashPresenter;
import com.footinit.selfproject.utils.rx.AppSchedulerProvider;
import com.footinit.selfproject.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Abhijit on 08-11-2017.
 */

@Module
public class ActivityModule {

    private final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideScheduleProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(activity);
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }
}
