package com.footinit.selfproject.ui.login;

import com.footinit.selfproject.R;
import com.footinit.selfproject.data.DataManager;
import com.footinit.selfproject.data.db.model.User;
import com.footinit.selfproject.data.network.model.LoginRequest;
import com.footinit.selfproject.ui.base.BasePresenter;
import com.footinit.selfproject.utils.CommonUtils;
import com.footinit.selfproject.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Abhijit on 09-11-2017.
 */

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {

    @Inject
    LoginPresenter(SchedulerProvider schedulerProvider,
                   CompositeDisposable compositeDisposable,
                   DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void onServerLoginClicked(String email, String password) {
        if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
            return;
        }
        if (!CommonUtils.isEmailValid(email)) {
            getMvpView().onError(R.string.invalid_email);
            return;
        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        }

        getMvpView().showLoading();

        getCompositeDisposable().add(
                getDataManager().doServerLoginApiCall(new LoginRequest.ServerLoginRequest(email, password))
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<User>() {
                            @Override
                            public void accept(User user) throws Exception {
                                insertCurrentUserIntoDb(user);
                                getDataManager().updateUserInfoInPrefs(user.getUserID(),
                                        user.getUserName(),
                                        user.getEmail(),
                                        DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_SERVER);

                                if (!isViewAttached())
                                    return;

                                getMvpView().hideLoading();
                                getMvpView().showMessage("Signing in");
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (!isViewAttached())
                                    return;

                                getMvpView().hideLoading();
                                getMvpView().onError("Server Sign In Failed");
                            }
                        })
        );
    }

    @Override
    public void onGoogleLoginClicked() {
        getMvpView().showLoading();

        getCompositeDisposable().add(
                getDataManager().doGoogleLoginApiCall(new LoginRequest.GoogleLoginRequest("test1", "test1"))
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<User>() {
                            @Override
                            public void accept(User user) throws Exception {
                                insertCurrentUserIntoDb(user);
                                getDataManager().updateUserInfoInPrefs(user.getUserID(),
                                        user.getUserName(),
                                        user.getEmail(),
                                        DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_GOOGLE);

                                if (!isViewAttached())
                                    return;

                                getMvpView().showMessage("Signing in with Google");
                                getMvpView().hideLoading();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (!isViewAttached())
                                    return;

                                getMvpView().hideLoading();
                                getMvpView().onError("Google Sign In Failed");
                            }
                        })
        );
    }

    @Override
    public void onFacebookLoginClicked() {
        getMvpView().showLoading();

        getCompositeDisposable().add(
                getDataManager().doFacebookLoginApiCall(new LoginRequest.FacebookLoginRequest("test3", "test4"))
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<User>() {
                            @Override
                            public void accept(User user) throws Exception {
                                insertCurrentUserIntoDb(user);
                                getDataManager().updateUserInfoInPrefs(user.getUserID(),
                                        user.getUserName(),
                                        user.getEmail(),
                                        DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_FB);

                                if (!isViewAttached())
                                    return;

                                getMvpView().hideLoading();
                                getMvpView().showMessage("Signing in with Facebook");
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (!isViewAttached())
                                    return;

                                getMvpView().hideLoading();
                                getMvpView().showMessage("Facebook Sign In Failed");
                            }
                        })
        );
    }

    private void insertCurrentUserIntoDb(User user) {
        getMvpView().showLoading();

        getCompositeDisposable().add(
                getDataManager().insertUser(user)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                if (!isViewAttached())
                                    return;

                                getMvpView().hideLoading();
                                getMvpView().openMainActivity();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (!isViewAttached())
                                    return;

                                getMvpView().hideLoading();
                                getMvpView().onError("Error: Cannot initiate login");
                            }
                        })
        );
    }
}
