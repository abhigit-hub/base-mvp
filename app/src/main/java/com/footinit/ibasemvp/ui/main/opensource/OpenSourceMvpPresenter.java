package com.footinit.ibasemvp.ui.main.opensource;

import com.footinit.ibasemvp.di.PerActivity;
import com.footinit.ibasemvp.ui.base.MvpPresenter;

/**
 * Created by Abhijit on 17-11-2017.
 */

@PerActivity
public interface OpenSourceMvpPresenter<V extends OpenSourceMvpView> extends MvpPresenter<V> {

    void fetchOpenSourceList();
}
