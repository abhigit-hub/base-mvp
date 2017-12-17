package com.footinit.selfproject.ui.main;

import com.footinit.selfproject.di.PerActivity;
import com.footinit.selfproject.ui.base.MvpPresenter;

/**
 * Created by Abhijit on 16-11-2017.
 */

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    void onNavMenuCreated();

    void onDrawerOptionFeedClicked();

    void onDrawerOptionLogoutClicked();

    void onViewInitialized();
}
