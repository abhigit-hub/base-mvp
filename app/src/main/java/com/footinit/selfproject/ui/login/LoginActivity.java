package com.footinit.selfproject.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.footinit.selfproject.R;
import com.footinit.selfproject.ui.base.BaseActivity;
import com.footinit.selfproject.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Abhijit on 09-11-2017.
 */

public class LoginActivity extends BaseActivity implements LoginMvpView {


    @Inject
    LoginMvpPresenter<LoginMvpView> presenter;

    @BindView(R.id.et_email)
    EditText emailET;

    @BindView(R.id.et_password)
    EditText passwordET;

    public static Intent getStartIntent(Context context) {
      return new Intent(context, LoginActivity.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        getActivityComponent().inject(this);



        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(LoginActivity.this);
    }

    @OnClick(R.id.btn_server_login)
    void onServerLoginClicked() {
        presenter.onServerLoginClicked(emailET.getText().toString(),
                passwordET.getText().toString());
    }

    @OnClick(R.id.ib_google_login)
    void onGoogleLoginClicked() {
        presenter.onGoogleLoginClicked();
    }

    @OnClick(R.id.ib_fb_login)
    void onFacebookLoginClicked() {
        presenter.onFacebookLoginClicked();
    }


    @Override
    protected void setUp() {

    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void openMainActivity() {
        startActivity(MainActivity.getStartIntent(this));
        finish();
    }
}
