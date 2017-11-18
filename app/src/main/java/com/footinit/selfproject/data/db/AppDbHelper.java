package com.footinit.selfproject.data.db;

import com.footinit.selfproject.data.db.model.User;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

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
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return appDatabase.userDao().insertUser(user);
            }
        });
    }

    @Override
    public Observable<User> getCurrentUser() {
        return Observable.fromCallable(new Callable<User>() {
            @Override
            public User call() throws Exception {
                return appDatabase.userDao().getUser();
            }
        });
    }

    @Override
    public Completable wipeUserData() {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                appDatabase.userDao().nukeUserTable();
            }
        });
    }
}
