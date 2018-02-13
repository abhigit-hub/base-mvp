package com.footinit.ibasemvp.ui.login;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.footinit.ibasemvp.R;
import com.footinit.ibasemvp.data.DataManager;
import com.footinit.ibasemvp.data.db.model.User;
import com.footinit.ibasemvp.data.network.model.LoginRequest;
import com.footinit.ibasemvp.ui.base.BasePresenter;
import com.footinit.ibasemvp.utils.CommonUtils;
import com.footinit.ibasemvp.utils.rx.SchedulerProvider;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

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


    /*
    *
    * Login from Google and Facebook has negative id's for identification, Server has positive ID's
    *
    * Based on the ID's, we can deduce the form of Login
    * */
    @Override
    public void onServerLoginClicked(String email, String password) {
        if (getMvpView().isNetworkConnected()) {
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
                            .concatMap(user -> getDataManager().saveUser(user)
                                    .ignoreElements()
                                    .andThen(Observable.just(user)))
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(user -> {
                                getDataManager().updateUserInfoInPrefs(user.getUserID(),
                                        user.getUserName(),
                                        user.getEmail(),
                                        DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_SERVER);

                                if (!isViewAttached())
                                    return;

                                getMvpView().hideLoading();
                                getMvpView().showMessage(R.string.signing_in);
                                getMvpView().openMainActivity();
                            }, throwable -> {
                                if (!isViewAttached())
                                    return;

                                getMvpView().hideLoading();
                                getMvpView().onError(R.string.server_sign_in_failed);
                            })
            );

        }
    }

    @Override
    public void onGoogleLoginClicked() {
        if (getMvpView().isNetworkConnected())
            getMvpView().openGoogleSignInActivity();
    }

    @Override
    public void onGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            onGoogleLoginSuccessful(account);
        } catch (ApiException e) {
            e.printStackTrace();
            Timber.d("signInResult:failed code=" + e.getStatusCode());
            getMvpView().onError("Google Sign in Failed");
        }
    }


    /*
    *  Uses AccessToken to make another request to Facebook's Graph API
    *  in order to retrieve User's email id
    * */
    @Override
    public void onFacebookSignInResult(AccessToken accessToken, final Profile currentProfile) {

        if (getMvpView().isNetworkConnected()) {
            GraphRequest request = GraphRequest.newMeRequest(accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            onFacebookLoginSuccessful(currentProfile, object);
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }


    /*
    *
    * Login from Google and Facebook has negative id's for identification, Server has positive ID's
    *
    * Based on the ID's, we can deduce the form of Login
    * */

    private void onGoogleLoginSuccessful(GoogleSignInAccount account) {
        getMvpView().showMessage(R.string.google_sign_in_successful);

        long id = CommonUtils.getNegativeLong(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_GOOGLE.getType());
        String name = account.getDisplayName();
        String email = account.getEmail();

        insertCurrentUserIntoDb(new User(id,name, email));

        getDataManager().updateUserInfoInPrefs(
                id,
                name,
                email,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_GOOGLE);
    }


    /*
    *
    * Login from Google and Facebook has negative id's for identification, Server has positive ID's
    *
    * Based on the ID's, we can deduce the form of Login
    * */
    private void onFacebookLoginSuccessful(Profile profile, JSONObject object) {
        getMvpView().showMessage(R.string.facebook_sign_in_successful);

        String email = object.optString("email");
        if (email == null) email = " ";
        long id = CommonUtils.getNegativeLong(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_FB.getType());
        String name = profile.getName();

        insertCurrentUserIntoDb(new User(id,name, email));

        getDataManager().updateUserInfoInPrefs(
                id,
                name,
                email,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_FB);
    }

    private void insertCurrentUserIntoDb(User user) {
        getCompositeDisposable().add(
                getDataManager().saveUser(user)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(aLong -> {
                            if (!isViewAttached())
                                return;

                            getMvpView().hideLoading();
                            getMvpView().openMainActivity();
                        }, throwable -> {
                            getMvpView().hideLoading();
                            getMvpView().onError(R.string.cannnot_initiate_sign_in);
                        })
        );
    }
}
