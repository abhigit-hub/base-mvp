package com.footinit.ibasemvp.di.component;

import android.app.Application;
import android.content.Context;

import com.facebook.CallbackManager;
import com.footinit.ibasemvp.MvpApp;
import com.footinit.ibasemvp.data.DataManager;
import com.footinit.ibasemvp.di.ApplicationContext;
import com.footinit.ibasemvp.di.module.ApplicationModule;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Abhijit on 08-11-2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();

    GoogleSignInClient getGoogleSignInClient();

    CallbackManager getCallbackManager();


    void inject(MvpApp mvpApp);
}
