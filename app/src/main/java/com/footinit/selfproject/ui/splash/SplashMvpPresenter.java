package com.footinit.selfproject.ui.splash;

import com.footinit.selfproject.di.PerActivity;
import com.footinit.selfproject.ui.base.MvpPresenter;

/**
 * Created by Abhijit on 08-11-2017.
 */

@PerActivity
public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {
}
