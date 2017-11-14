package com.footinit.selfproject.di.component;

import android.app.Application;
import android.content.Context;

import com.footinit.selfproject.data.DataManager;
import com.footinit.selfproject.di.ApplicationContext;
import com.footinit.selfproject.di.module.ApplicationModule;

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
}
