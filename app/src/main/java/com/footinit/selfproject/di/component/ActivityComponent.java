package com.footinit.selfproject.di.component;

import com.footinit.selfproject.di.PerActivity;
import com.footinit.selfproject.di.module.ActivityModule;
import com.footinit.selfproject.ui.login.LoginActivity;
import com.footinit.selfproject.ui.main.MainActivity;
import com.footinit.selfproject.ui.main.blog.BlogFragment;
import com.footinit.selfproject.ui.main.opensource.OpenSourceFragment;
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

    void inject(MainActivity activity);

    void inject(BlogFragment fragment);

    void inject(OpenSourceFragment fragment);
}
