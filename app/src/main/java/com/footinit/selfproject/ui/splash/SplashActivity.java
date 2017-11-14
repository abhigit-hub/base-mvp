package com.footinit.selfproject.ui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.footinit.selfproject.R;
import com.footinit.selfproject.ui.base.BaseActivity;
import com.footinit.selfproject.ui.login.LoginActivity;
import com.footinit.selfproject.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abhijit on 08-11-2017.
 */

public class SplashActivity extends BaseActivity implements SplashMvpView {


    @Inject
    SplashMvpPresenter<SplashMvpView> presenter;

    @BindView(R.id.lottie_load)
    LottieAnimationView loadLAV;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(SplashActivity.this);

        setUp();
    }

    @Override
    protected void onDestroy() {
        loadLAV.clearAnimation();
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        loadLAV.playAnimation();
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(SplashActivity.this));
        finish();
    }

    @Override
    public void openMainActivity() {
        startActivity(MainActivity.getStartIntent(SplashActivity.this));
        finish();
    }
}
