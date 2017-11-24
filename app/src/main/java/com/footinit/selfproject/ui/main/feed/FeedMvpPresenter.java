package com.footinit.selfproject.ui.main.feed;

import com.footinit.selfproject.di.PerActivity;
import com.footinit.selfproject.ui.base.MvpPresenter;

/**
 * Created by Abhijit on 23-11-2017.
 */

@PerActivity
public interface FeedMvpPresenter<V extends FeedMvpView> extends MvpPresenter<V> {
    void onViewPrepared();
}
