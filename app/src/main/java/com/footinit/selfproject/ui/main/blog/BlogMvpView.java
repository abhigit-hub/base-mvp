package com.footinit.selfproject.ui.main.blog;

import com.footinit.selfproject.data.db.model.Blog;
import com.footinit.selfproject.ui.base.MvpView;

import java.util.List;

/**
 * Created by Abhijit on 17-11-2017.
 */

public interface BlogMvpView extends MvpView {

    void updateBlogList(List<Blog> blogList);

    void openBlogDetailActivity(Blog blog);

    void onBlogListReFetched();
}
