package com.footinit.selfproject.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.footinit.selfproject.data.db.model.Blog;

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

    @Query("DELETE FROM blog")
    void nukeBlogTable();
}
