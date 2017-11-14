package com.footinit.selfproject;

import android.app.Application;

import com.footinit.selfproject.di.component.ApplicationComponent;
import com.footinit.selfproject.di.component.DaggerApplicationComponent;
import com.footinit.selfproject.di.module.ApplicationModule;
import com.footinit.selfproject.utils.AppLogger;

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

        AppLogger.init();

        CalligraphyConfig.initDefault(calligraphyConfig);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
