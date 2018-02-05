package com.footinit.ibasemvp.ui.main.blogdetails;

import com.footinit.ibasemvp.di.PerActivity;
import com.footinit.ibasemvp.ui.base.MvpPresenter;

/**
 * Created by Abhijit on 18-11-2017.
 */

@PerActivity
public interface BlogDetailsMvpPresenter<V extends BlogDetailsMvpView>
        extends MvpPresenter<V> {

    void onBlogDetailsDisplayedError();

    void onBlogFABClicked();
}
