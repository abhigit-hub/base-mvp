package com.footinit.ibasemvp.di.component;

import com.footinit.ibasemvp.di.PerActivity;
import com.footinit.ibasemvp.di.module.ActivityModule;
import com.footinit.ibasemvp.ui.login.LoginActivity;
import com.footinit.ibasemvp.ui.main.MainActivity;
import com.footinit.ibasemvp.ui.main.blog.BlogFragment;
import com.footinit.ibasemvp.ui.main.blogdetails.BlogDetailsActivity;
import com.footinit.ibasemvp.ui.main.feed.FeedActivity;
import com.footinit.ibasemvp.ui.main.opensource.OpenSourceFragment;
import com.footinit.ibasemvp.ui.main.opensourcedetails.OSDetailActivity;
import com.footinit.ibasemvp.ui.splash.SplashActivity;

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

    void inject(BlogDetailsActivity activity);

    void inject(OSDetailActivity activity);

    void inject(FeedActivity activity);
}
