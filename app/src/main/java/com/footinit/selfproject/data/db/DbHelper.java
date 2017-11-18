package com.footinit.selfproject.data.db;

import com.footinit.selfproject.data.db.model.User;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Abhijit on 11-11-2017.
 */

public interface DbHelper {

    Observable<Long> insertUser(User user);

    Observable<User> getCurrentUser();

    Completable wipeUserData();
}
