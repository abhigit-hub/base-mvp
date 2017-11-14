package com.footinit.selfproject.data.db;

import com.footinit.selfproject.data.db.model.User;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Abhijit on 11-11-2017.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private AppDatabase appDatabase;

    @Inject
    AppDbHelper(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public Observable<Long> insertUser(final User user) {
        return Observable.fromCallable(() -> appDatabase.userDao().insertUser(user));
    }

    @Override
    public Observable<User> getCurrentUser() {
        return Observable.fromCallable(() -> appDatabase.userDao().getUser());
    }
}
