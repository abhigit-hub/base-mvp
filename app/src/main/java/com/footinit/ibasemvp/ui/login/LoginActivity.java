package com.footinit.ibasemvp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.footinit.ibasemvp.MvpApp;
import com.footinit.ibasemvp.R;
import com.footinit.ibasemvp.ui.base.BaseActivity;
import com.footinit.ibasemvp.ui.main.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Abhijit on 09-11-2017.
 */

public class LoginActivity extends BaseActivity implements LoginMvpView {


    private static final int RC_GOOGLE_SIGN_IN = 1;
    private static final String EMAIL = "email";

    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;


    @Inject
    LoginMvpPresenter<LoginMvpView> presenter;


    @BindView(R.id.et_email)
    EditText emailET;

    @BindView(R.id.et_password)
    EditText passwordET;

    @BindView(R.id.btn_fb_login)
    LoginButton btnFBLogin;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        callbackManager = ((MvpApp) getApplication()).getApplicationComponent().getCallbackManager();

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(LoginActivity.this);

        setUp();
    }

    @OnClick(R.id.btn_server_login)
    void onServerLoginClicked() {
        presenter.onServerLoginClicked(emailET.getText().toString(),
                passwordET.getText().toString());
    }

    @OnClick(R.id.btn_google_login)
    void onGoogleLoginClicked() {
        presenter.onGoogleLoginClicked();
    }


    @Override
    protected void setUp() {

        btnFBLogin.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        final AccessToken accessToken = loginResult.getAccessToken();

                        /*
                        * Check if Profile is available, if not register for profile information
                        * */
                        if (Profile.getCurrentProfile() == null) {
                            profileTracker = new ProfileTracker() {
                                @Override
                                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                                    presenter.onFacebookSignInResult(accessToken,
                                            Profile.getCurrentProfile());
                                    profileTracker.stopTracking();
                                }
                            };
                        } else {
                            presenter.onFacebookSignInResult(accessToken,
                                    Profile.getCurrentProfile());
                        }

                    }

                    @Override
                    public void onCancel() {
                        showMessage("Facebook Sign in Cancelled");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        showMessage("Facebook Sign in Failed");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        btnFBLogin.registerCallback(callbackManager, null);
        if (profileTracker != null)
            profileTracker.stopTracking();
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void openMainActivity() {
        startActivity(MainActivity.getStartIntent(this));
        finish();
    }

    @Override
    public void openGoogleSignInActivity() {
        Intent intent = ((MvpApp) getApplication()).getApplicationComponent().getGoogleSignInClient().getSignInIntent();
        startActivityForResult(intent, RC_GOOGLE_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            presenter.onGoogleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
