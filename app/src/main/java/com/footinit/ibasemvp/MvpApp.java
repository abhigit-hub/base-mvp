package com.footinit.ibasemvp;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.footinit.ibasemvp.di.component.ApplicationComponent;
import com.footinit.ibasemvp.di.component.DaggerApplicationComponent;
import com.footinit.ibasemvp.di.module.ApplicationModule;
import com.footinit.ibasemvp.utils.AppLogger;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Abhijit on 08-11-2017.
 */

public class MvpApp extends Application {

    @Inject
    CalligraphyConfig calligraphyConfig;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        //Instantiate ApplicationComponent
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        applicationComponent.inject(this);

        AppLogger.init();

        /*
        * Init Facebook SDK*/
        FacebookSdk.sdkInitialize(getApplicationContext());


        CalligraphyConfig.initDefault(calligraphyConfig);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
