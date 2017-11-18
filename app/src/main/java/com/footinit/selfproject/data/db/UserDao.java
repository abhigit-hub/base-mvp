package com.footinit.selfproject.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.footinit.selfproject.data.db.model.User;

/**
 * Created by Abhijit on 11-11-2017.
 */

@Dao
public interface UserDao {

    @Insert
    Long insertUser(User user);

    @Query("SELECT * FROM user")
    User getUser();

    @Query("DELETE FROM user")
    void nukeUserTable();
}
