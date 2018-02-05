package com.footinit.ibasemvp.ui.main.feed;

import com.footinit.ibasemvp.data.db.model.Blog;
import com.footinit.ibasemvp.data.db.model.OpenSource;
import com.footinit.ibasemvp.ui.base.MvpView;

import java.util.List;

/**
 * Created by Abhijit on 23-11-2017.
 */

public interface FeedMvpView extends MvpView {
    void onListRetrieved(List<Object> list);

    void openBlogDetailsActivity(Blog blog);

    void openOSDetailsActivity(OpenSource openSource);
}
