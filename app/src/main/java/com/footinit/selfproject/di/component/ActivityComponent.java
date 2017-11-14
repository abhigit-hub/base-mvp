package com.footinit.selfproject.di.component;

import com.footinit.selfproject.di.PerActivity;
import com.footinit.selfproject.di.module.ActivityModule;
import com.footinit.selfproject.ui.login.LoginActivity;
import com.footinit.selfproject.ui.splash.SplashActivity;

import dagger.Component;

/**
 * Created by Abhijit on 08-11-2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity activity);

    void inject(LoginActivity activity);
}
