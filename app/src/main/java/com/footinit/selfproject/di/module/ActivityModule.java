package com.footinit.selfproject.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.footinit.selfproject.data.db.model.Blog;
import com.footinit.selfproject.data.db.model.OpenSource;
import com.footinit.selfproject.di.ActivityContext;
import com.footinit.selfproject.di.PerActivity;
import com.footinit.selfproject.ui.login.LoginMvpPresenter;
import com.footinit.selfproject.ui.login.LoginMvpView;
import com.footinit.selfproject.ui.login.LoginPresenter;
import com.footinit.selfproject.ui.main.MainMvpPresenter;
import com.footinit.selfproject.ui.main.MainMvpView;
import com.footinit.selfproject.ui.main.MainPagerAdapter;
import com.footinit.selfproject.ui.main.MainPresenter;
import com.footinit.selfproject.ui.main.blog.BlogAdapter;
import com.footinit.selfproject.ui.main.blog.BlogMvpPresenter;
import com.footinit.selfproject.ui.main.blog.BlogMvpView;
import com.footinit.selfproject.ui.main.blog.BlogPresenter;
import com.footinit.selfproject.ui.main.blogdetails.BlogDetailsMvpPresenter;
import com.footinit.selfproject.ui.main.blogdetails.BlogDetailsMvpView;
import com.footinit.selfproject.ui.main.blogdetails.BlogDetailsPresenter;
import com.footinit.selfproject.ui.main.opensource.OpenSourceAdapter;
import com.footinit.selfproject.ui.main.opensource.OpenSourceMvpPresenter;
import com.footinit.selfproject.ui.main.opensource.OpenSourceMvpView;
import com.footinit.selfproject.ui.main.opensource.OpenSourcePresenter;
import com.footinit.selfproject.ui.main.opensourcedetails.OSDetailMvpPresenter;
import com.footinit.selfproject.ui.main.opensourcedetails.OSDetailMvpView;
import com.footinit.selfproject.ui.main.opensourcedetails.OSDetailPresenter;
import com.footinit.selfproject.ui.splash.SplashMvpPresenter;
import com.footinit.selfproject.ui.splash.SplashMvpView;
import com.footinit.selfproject.ui.splash.SplashPresenter;
import com.footinit.selfproject.utils.rx.AppSchedulerProvider;
import com.footinit.selfproject.utils.rx.SchedulerProvider;

import java.util.ArrayList;

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

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> providesMainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    BlogMvpPresenter<BlogMvpView> providesBlogPresenter(BlogPresenter<BlogMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    OpenSourceMvpPresenter<OpenSourceMvpView> providesOpenSourcePresenter(OpenSourcePresenter<OpenSourceMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    BlogDetailsMvpPresenter<BlogDetailsMvpView> providesBlogDetailsPresenter(BlogDetailsPresenter<BlogDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    OSDetailMvpPresenter<OSDetailMvpView> providesOSDetailPresenter(OSDetailPresenter<OSDetailMvpView> presenter) {
        return presenter;
    }

    @Provides
    MainPagerAdapter providesMainPagerAdapter(AppCompatActivity activity) {
        return new MainPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    @PerActivity
    BlogAdapter providesBlogAdapter() {
        return new BlogAdapter(new ArrayList<Blog>());
    }

    @Provides
    @PerActivity
    OpenSourceAdapter providesOpenSourceAdapter() {
        return new OpenSourceAdapter(new ArrayList<OpenSource>());
    }
}
