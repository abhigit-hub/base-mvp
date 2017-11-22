package com.footinit.selfproject.ui.login;

import com.footinit.selfproject.ui.base.MvpView;

/**
 * Created by Abhijit on 09-11-2017.
 */

public interface LoginMvpView extends MvpView {

    void openMainActivity();

    void openGoogleSignInActivity();
}
