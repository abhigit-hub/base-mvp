package com.footinit.selfproject.ui.main.opensourcedetails;

import com.footinit.selfproject.di.PerActivity;
import com.footinit.selfproject.ui.base.MvpPresenter;

/**
 * Created by Abhijit on 18-11-2017.
 */

@PerActivity
public interface OSDetailMvpPresenter<V extends OSDetailMvpView> extends MvpPresenter<V> {

    void onOSDetailsDisplayedError();

    void onOpenSourceFABClicked();
}
