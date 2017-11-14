package com.footinit.selfproject.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.footinit.selfproject.data.db.model.User;

import javax.inject.Singleton;

/**
 * Created by Abhijit on 11-11-2017.
 */

@Database(entities = {User.class}, version = 1)
@Singleton
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
