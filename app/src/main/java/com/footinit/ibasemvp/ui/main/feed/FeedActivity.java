package com.footinit.ibasemvp.ui.main.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.footinit.ibasemvp.R;
import com.footinit.ibasemvp.data.db.model.Blog;
import com.footinit.ibasemvp.data.db.model.OpenSource;
import com.footinit.ibasemvp.ui.base.BaseActivity;
import com.footinit.ibasemvp.ui.main.blogdetails.BlogDetailsActivity;
import com.footinit.ibasemvp.ui.main.opensourcedetails.OSDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abhijit on 23-11-2017.
 */

public class FeedActivity extends BaseActivity
        implements FeedMvpView {

    @Inject
    FeedMvpPresenter<FeedMvpView> presenter;

    @Inject
    LinearLayoutManager layoutManager;

    @Inject
    FeedAdapter adapter;


    @BindView(R.id.rv_feed)
    RecyclerView rvFeed;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    public static Intent getStartIntent(Context context) {
        return new Intent(context, FeedActivity.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        adapter.setCallback((FeedPresenter) presenter);

        setUp();
    }


    @Override
    protected void setUp() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvFeed.setLayoutManager(layoutManager);
        rvFeed.setItemAnimator(new DefaultItemAnimator());
        rvFeed.setAdapter(adapter);

        presenter.onViewPrepared();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        adapter.removeCallback();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListRetrieved(List<Object> list) {
        adapter.addItems(list);
    }

    @Override
    public void openBlogDetailsActivity(Blog blog) {
        Intent intent = BlogDetailsActivity.getStartIntent(this);
        intent.putExtra(BlogDetailsActivity.KEY_PARCELABLE_BLOG, blog);
        startActivity(intent);
    }

    @Override
    public void openOSDetailsActivity(OpenSource openSource) {
        Intent intent = OSDetailActivity.getStartIntent(this);
        intent.putExtra(OSDetailActivity.KEY_PARCELABLE_OPEN_SOURCE, openSource);
        startActivity(intent);
    }
}
