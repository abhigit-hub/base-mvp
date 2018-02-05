package com.footinit.ibasemvp.ui.main.blogdetails;

import com.footinit.ibasemvp.data.DataManager;
import com.footinit.ibasemvp.ui.base.BasePresenter;
import com.footinit.ibasemvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Abhijit on 18-11-2017.
 */

public class BlogDetailsPresenter<V extends BlogDetailsMvpView> extends BasePresenter<V>
        implements BlogDetailsMvpPresenter<V> {


    @Inject
    public BlogDetailsPresenter(SchedulerProvider schedulerProvider,
                                CompositeDisposable compositeDisposable,
                                DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void onBlogDetailsDisplayedError() {
        getMvpView().returnToMainActivity();
    }

    @Override
    public void onBlogFABClicked() {
        getMvpView().openInBrowser();
    }
}
