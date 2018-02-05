package com.footinit.ibasemvp.ui.main.opensourcedetails;

import com.footinit.ibasemvp.di.PerActivity;
import com.footinit.ibasemvp.ui.base.MvpPresenter;

/**
 * Created by Abhijit on 18-11-2017.
 */

@PerActivity
public interface OSDetailMvpPresenter<V extends OSDetailMvpView> extends MvpPresenter<V> {

    void onOSDetailsDisplayedError();

    void onOpenSourceFABClicked();
}
