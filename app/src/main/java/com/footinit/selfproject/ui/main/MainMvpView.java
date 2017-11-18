package com.footinit.selfproject.ui.main;

import com.footinit.selfproject.ui.base.MvpView;

/**
 * Created by Abhijit on 16-11-2017.
 */

public interface MainMvpView extends MvpView {

    void updateAppVersion();

    void updateUserName(String name);

    void updateUserEmail(String email);

    void updateUserProfilePicture(String picPath);

    void openLoginActivity();

    void openFeedActivity();

    void closeNavigationDrawer();

    void unlockDrawer();

    void lockDrawer();
}
