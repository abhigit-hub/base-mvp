package com.footinit.ibasemvp.ui.main.blog;

import com.footinit.ibasemvp.data.db.model.Blog;
import com.footinit.ibasemvp.ui.base.MvpView;

import java.util.List;

/**
 * Created by Abhijit on 17-11-2017.
 */

public interface BlogMvpView extends MvpView {

    void updateBlogList(List<Blog> blogList);

    void openBlogDetailActivity(Blog blog);

    void onBlogListReFetched();

    void onPullToRefreshEvent(boolean isVisible);
}
