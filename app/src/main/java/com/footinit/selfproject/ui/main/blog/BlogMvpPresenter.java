package com.footinit.selfproject.ui.main.blog;

import com.footinit.selfproject.di.PerActivity;
import com.footinit.selfproject.ui.base.MvpPresenter;

/**
 * Created by Abhijit on 17-11-2017.
 */

@PerActivity
public interface BlogMvpPresenter<V extends BlogMvpView> extends MvpPresenter<V> {

    void fetchBlogList();
}
