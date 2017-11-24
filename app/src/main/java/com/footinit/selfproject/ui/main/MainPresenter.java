package com.footinit.selfproject.ui.main;

import com.facebook.login.LoginManager;
import com.footinit.selfproject.data.DataManager;
import com.footinit.selfproject.data.db.model.Blog;
import com.footinit.selfproject.data.db.model.OpenSource;
import com.footinit.selfproject.ui.base.BasePresenter;
import com.footinit.selfproject.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Abhijit on 16-11-2017.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {


    @Inject
    public MainPresenter(SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable,
                         DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void onNavMenuCreated() {
        if (!isViewAttached())
            return;

        String stringHolder;

        stringHolder = getDataManager().getCurrentUserName();
        if (stringHolder != null && !stringHolder.isEmpty())
            getMvpView().updateUserName(stringHolder);

        stringHolder = getDataManager().getCurrentUserEmail();
        if (stringHolder != null && !stringHolder.isEmpty())
            getMvpView().updateUserEmail(stringHolder);
    }

    @Override
    public void onDrawerOptionFeedClicked() {
        getMvpView().closeNavigationDrawer();
        checkFeedAvailableInDb();
    }

    private void checkFeedAvailableInDb() {
        getMvpView().showLoading();

        getCompositeDisposable().add(
                getDataManager().getBlogRecordCount()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                if (!isViewAttached())
                                    return;

                                if (aLong > 0) {
                                    getMvpView().hideLoading();
                                    getMvpView().openFeedActivity();
                                } else {
                                    getMvpView().hideLoading();
                                    checkFeedAvailableInOpenSourceDb();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (!isViewAttached())
                                    return;

                                getMvpView().hideLoading();
                                checkFeedAvailableInOpenSourceDb();
                            }
                        })
        );
    }

    private void checkFeedAvailableInOpenSourceDb() {
        getMvpView().showLoading();
        getCompositeDisposable().add(
                getDataManager().getOpenSourceRecordCount()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe((Consumer<Long>) aLong -> {
                            if (!isViewAttached())
                                return;

                            if (aLong > 0) {
                                getMvpView().hideLoading();
                                getMvpView().openFeedActivity();
                            } else {
                                getMvpView().hideLoading();
                                if (getMvpView().isNetworkConnected())
                                    getMvpView().onError("Something went wrong");
                            }
                        }, throwable -> {
                            if (!isViewAttached())
                                return;

                            getMvpView().hideLoading();
                            if (getMvpView().isNetworkConnected())
                                getMvpView().onError("Something went wrong");
                        })
        );
    }

    @Override
    public void onDrawerOptionLogoutClicked() {
        getMvpView().showLoading();

    /*
    * Logout from the Facebook's LoginManager Instance
    * */
        LoginManager.getInstance().logOut();


    /*
    * Clearing Shared Preferences
    * */
        getDataManager().setCurrentUserLoggedOut();

    /*
    * Clearing/Wiping all data from the User Table
    * And if successful, logs User out
    * */

        getDataManager().wipeUserData()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        getMvpView().hideLoading();
                        getMvpView().showMessage("Logging you out");
                        getMvpView().openLoginActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideLoading();
                        getMvpView().showMessage("There was an error while logging you out");
                    }
                });
    }

    @Override
    public void onViewInitialized() {

    }

    @Override
    public void onRefreshNetworkCall() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().updateSwipeRefreshLayout(true);
            getCompositeDisposable().add(
                    getDataManager().doBlogListApiCall()
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(new Consumer<List<Blog>>() {
                                @Override
                                public void accept(List<Blog> blogList) throws Exception {
                                    if (!isViewAttached())
                                        return;

                                    if (blogList != null) {
                                        getMvpView().updateBlogAdapter(blogList);
                                        clearBlogListFromDb(blogList);
                                    }
                                    onOpenSourceNetworkCall();
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    if (!isViewAttached())
                                        return;

                                    getMvpView().updateSwipeRefreshLayout(false);
                                    getMvpView().onError("Could not fetch items");
                                }
                            })
            );
        } else {
            getMvpView().updateSwipeRefreshLayout(false);
        }
    }

    private void onOpenSourceNetworkCall() {
        getMvpView().updateSwipeRefreshLayout(true);
        getCompositeDisposable().add(
                getDataManager().doOpenSourceListCall()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<List<OpenSource>>() {
                            @Override
                            public void accept(List<OpenSource> list) throws Exception {
                                if (!isViewAttached())
                                    return;

                                getMvpView().updateSwipeRefreshLayout(false);
                                if (list != null) {
                                    getMvpView().updateOpenSourceAdapter(list);
                                    clearOpenSourceListFromDb(list);
                                }
                                getMvpView().showMessage("Updated items");
                                getMvpView().resetAllAdapterPositions();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (!isViewAttached())
                                    return;

                                getMvpView().updateSwipeRefreshLayout(false);
                                getMvpView().onError("Could not fetch items");
                            }
                        })
        );
    }

    private void clearBlogListFromDb(List<Blog> blogList) {

        getDataManager().wipeBlogData()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        addBlogListToDb(blogList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void addBlogListToDb(List<Blog> blogList) {
        getCompositeDisposable().add(
                getDataManager().insertBlogList(blogList)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<List<Long>>() {
                            @Override
                            public void accept(List<Long> longs) throws Exception {

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        })
        );
    }

    private void clearOpenSourceListFromDb(List<OpenSource> openSourceList) {

        getDataManager().wipeOpenSourceData()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        addOpenSourceListToDb(openSourceList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void addOpenSourceListToDb(List<OpenSource> openSourceList) {
        getCompositeDisposable().add(
                getDataManager().insertOpenSourceList(openSourceList)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<List<Long>>() {
                            @Override
                            public void accept(List<Long> longs) throws Exception {

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        })
        );
    }
}
