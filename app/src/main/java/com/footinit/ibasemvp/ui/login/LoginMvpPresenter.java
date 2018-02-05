package com.footinit.ibasemvp.ui.login;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.footinit.ibasemvp.di.PerActivity;
import com.footinit.ibasemvp.ui.base.MvpPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

/**
 * Created by Abhijit on 09-11-2017.
 */

@PerActivity
public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void onServerLoginClicked(String email, String password);

    void onGoogleLoginClicked();

    void onGoogleSignInResult(Task<GoogleSignInAccount> task);

    void onFacebookSignInResult(AccessToken accessToken, Profile currentProfile);
}
