package com.footinit.ibasemvp.ui.main.opensource;

import com.footinit.ibasemvp.data.db.model.OpenSource;
import com.footinit.ibasemvp.ui.base.MvpView;

import java.util.List;

/**
 * Created by Abhijit on 17-11-2017.
 */

public interface OpenSourceMvpView extends MvpView {

    void updateOpenSourceList(List<OpenSource> list);

    void openOSDetailsActivity(OpenSource openSource);

    void onOpenSourceListReFetched();

    void onPullToRefreshEvent(boolean isVisible);
}
