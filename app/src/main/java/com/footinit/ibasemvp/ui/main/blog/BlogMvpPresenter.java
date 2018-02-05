package com.footinit.ibasemvp.ui.main.blog;

import com.footinit.ibasemvp.di.PerActivity;
import com.footinit.ibasemvp.ui.base.MvpPresenter;

/**
 * Created by Abhijit on 17-11-2017.
 */

@PerActivity
public interface BlogMvpPresenter<V extends BlogMvpView> extends MvpPresenter<V> {

    void fetchBlogList();
}
