package com.footinit.selfproject.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.footinit.selfproject.data.AppDataManager;
import com.footinit.selfproject.data.DataManager;
import com.footinit.selfproject.data.db.AppDatabase;
import com.footinit.selfproject.data.db.AppDbHelper;
import com.footinit.selfproject.data.db.DbHelper;
import com.footinit.selfproject.data.network.ApiCall;
import com.footinit.selfproject.data.network.ApiHelper;
import com.footinit.selfproject.data.network.AppApiHelper;
import com.footinit.selfproject.data.pref.AppPreferenceHelper;
import com.footinit.selfproject.data.pref.PreferenceHelper;
import com.footinit.selfproject.di.ApplicationContext;
import com.footinit.selfproject.di.DatabaseInfo;
import com.footinit.selfproject.di.PreferenceInfo;
import com.footinit.selfproject.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Abhijit on 08-11-2017.
 */

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context providesContext() {
        return application;
    }

    @Provides
    Application providesApplication() {
        return application;
    }

    @Provides
    @DatabaseInfo
    String providesDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @DatabaseInfo
    Integer providesDatabaseVersion() {
        return AppConstants.DB_VERSION;
    }

    @Provides
    @PreferenceInfo
    String providesSharedPrefName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager providesDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper providesDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    ApiHelper providesApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    PreferenceHelper providesPreferenceHelper(AppPreferenceHelper appPreferenceHelper) {
        return appPreferenceHelper;
    }

    @Provides
    @Singleton
    ApiCall providesApiCall() {
        return ApiCall.Factory.create();
    }

    @Provides
    @Singleton
    AppDatabase providesAppDatabase(@ApplicationContext Context context,
                                    @DatabaseInfo String dbName) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                dbName).build();
    }

}
