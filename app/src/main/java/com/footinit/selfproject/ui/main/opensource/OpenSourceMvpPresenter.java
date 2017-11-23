package com.footinit.selfproject.ui.main.opensource;

import com.footinit.selfproject.di.PerActivity;
import com.footinit.selfproject.ui.base.MvpPresenter;

/**
 * Created by Abhijit on 17-11-2017.
 */

@PerActivity
public interface OpenSourceMvpPresenter<V extends OpenSourceMvpView> extends MvpPresenter<V> {

    void fetchOpenSourceList();
}
