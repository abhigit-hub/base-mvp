package com.footinit.ibasemvp.ui.main;

import com.footinit.ibasemvp.di.PerActivity;
import com.footinit.ibasemvp.ui.base.MvpPresenter;

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
