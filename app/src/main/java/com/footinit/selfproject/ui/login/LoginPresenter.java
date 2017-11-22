package com.footinit.selfproject.ui.login;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.footinit.selfproject.R;
import com.footinit.selfproject.data.DataManager;
import com.footinit.selfproject.data.db.model.User;
import com.footinit.selfproject.data.network.model.LoginRequest;
import com.footinit.selfproject.ui.base.BasePresenter;
import com.footinit.selfproject.utils.CommonUtils;
import com.footinit.selfproject.utils.rx.SchedulerProvider;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
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


    /*
    *
    * Login from Google and Facebook has negative id's for identification, Server has positive ID's
    *
    * Based on the ID's, we can deduce the form of Login
    * */
    private void onGoogleLoginSuccessful(GoogleSignInAccount account) {
        getMvpView().showMessage("Google Sign in Successful");

        insertCurrentUserIntoDb(
                new User(
                        (CommonUtils.getNegativeLong(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_GOOGLE.getType())),
                        account.getDisplayName(),
                        account.getEmail())
        );

        getDataManager().updateUserInfoInPrefs(
                CommonUtils.getNegativeLong(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_GOOGLE.getType()),
                account.getDisplayName(),
                account.getEmail(),
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_GOOGLE);
    }


    /*
    *
    * Login from Google and Facebook has negative id's for identification, Server has positive ID's
    *
    * Based on the ID's, we can deduce the form of Login
    * */
    private void onFacebookLoginSuccessful(Profile profile, JSONObject object) {
        getMvpView().showMessage("Facebook Sign in Successful");

        String email = object.optString("email");
        if (email == null) email = " ";

        insertCurrentUserIntoDb(
                new User(
                        (CommonUtils.getNegativeLong(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_FB.getType())),
                        profile.getName(),
                        email)
        );

        getDataManager().updateUserInfoInPrefs(
                CommonUtils.getNegativeLong(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_FB.getType()),
                profile.getName(),
                email,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_FB);
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
                                getMvpView().onError("Error: Cannot initiate Sign in");
                            }
                        })
        );
    }
}
