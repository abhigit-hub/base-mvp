package com.footinit.ibasemvp.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.footinit.ibasemvp.data.db.model.Blog;

import java.util.List;

/**
 * Created by Abhijit on 23-11-2017.
 */

@Dao
public interface BlogDao {

    @Insert
    Long insertBlog(Blog blog);

    @Insert
    List<Long> insertBlogList(List<Blog> blogList);

    @Query("SELECT * FROM blog")
    List<Blog> getBlogList();

    @Query("SELECT COUNT(id) FROM blog")
    Long getRecordsCount();

    @Query("DELETE FROM blog")
    void nukeBlogTable();
}
