package com.footinit.selfproject.data.db;

import com.footinit.selfproject.data.db.model.Blog;
import com.footinit.selfproject.data.db.model.OpenSource;
import com.footinit.selfproject.data.db.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Abhijit on 11-11-2017.
 */

public interface DbHelper {

    //User
    Observable<Long> insertUser(User user);

    Observable<User> getCurrentUser();

    Completable wipeUserData();


    //Blog
    Observable<Long> insertBlog(Blog blog);

    Observable<List<Long>> insertBlogList(List<Blog> blogList);

    Observable<List<Blog>> getBlogList();

    Completable wipeBlogData();


    //Open Source
    Observable<Long> insertOpenSource(OpenSource openSource);

    Observable<List<Long>> insertOpenSourceList(List<OpenSource> openSourceList);

    Observable<List<OpenSource>> getOpenSourceList();

    Completable wipeOpenSourceData();

}
