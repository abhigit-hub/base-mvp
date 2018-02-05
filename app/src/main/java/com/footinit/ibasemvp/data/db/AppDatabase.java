package com.footinit.ibasemvp.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.footinit.ibasemvp.data.db.model.Blog;
import com.footinit.ibasemvp.data.db.model.OpenSource;
import com.footinit.ibasemvp.data.db.model.User;

import javax.inject.Singleton;

/**
 * Created by Abhijit on 11-11-2017.
 */

@Database(entities = {User.class, Blog.class, OpenSource.class}, version = 1)
@Singleton
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract BlogDao blogDao();

    public abstract OpenSourceDao openSourceDao();
}
