package com.footinit.selfproject.ui.main.opensource;

import com.footinit.selfproject.data.db.model.OpenSource;
import com.footinit.selfproject.ui.base.MvpView;

import java.util.List;

/**
 * Created by Abhijit on 17-11-2017.
 */

public interface OpenSourceMvpView extends MvpView {

    void updateOpenSourceList(List<OpenSource> list);
}
