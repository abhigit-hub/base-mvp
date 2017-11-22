package com.footinit.selfproject.ui.main.blogdetails;

import com.footinit.selfproject.di.PerActivity;
import com.footinit.selfproject.ui.base.MvpPresenter;

/**
 * Created by Abhijit on 18-11-2017.
 */

@PerActivity
public interface BlogDetailsMvpPresenter<V extends BlogDetailsMvpView>
        extends MvpPresenter<V> {

    void onBlogDetailsDisplayedError();

    void onBlogFABClicked();
}
