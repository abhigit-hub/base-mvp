package com.footinit.selfproject.ui.login;

import com.footinit.selfproject.di.PerActivity;
import com.footinit.selfproject.ui.base.MvpPresenter;

/**
 * Created by Abhijit on 09-11-2017.
 */

@PerActivity
public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void onServerLoginClicked(String email, String password);

    void onGoogleLoginClicked();

    void onFacebookLoginClicked();
}
