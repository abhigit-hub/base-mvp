package com.footinit.selfproject.ui.main;

import com.footinit.selfproject.data.db.model.Blog;
import com.footinit.selfproject.data.db.model.OpenSource;
import com.footinit.selfproject.ui.base.MvpView;

import java.util.List;

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

    void updateSwipeRefreshLayout(boolean isVisible);

    void updateBlogAdapter(List<Blog> blogList);

    void updateOpenSourceAdapter(List<OpenSource> openSourceList);

    void resetAllAdapterPositions();
}
