package com.footinit.selfproject.ui.main.blogdetails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.footinit.selfproject.R;
import com.footinit.selfproject.data.db.model.Blog;
import com.footinit.selfproject.ui.base.BaseActivity;
import com.footinit.selfproject.ui.main.MainActivity;
import com.footinit.selfproject.ui.main.blog.BlogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abhijit on 18-11-2017.
 */

public class BlogDetailsActivity extends BaseActivity
        implements BlogDetailsMvpView {

    @Inject
    BlogDetailsMvpPresenter<BlogDetailsMvpView> presenter;

    private Blog currentBlog;

    @BindView(R.id.iv_cover_img)
    ImageView ivCover;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_author)
    TextView tvAuthor;

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.tvDescription)
    TextView tvDescription;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ctl_blog_detail)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.fab_blog)
    FloatingActionButton fab;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, BlogDetailsActivity.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);

        currentBlog = getIntent().getParcelableExtra(BlogFragment.KEY_PARCELABLE_BLOG);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {

        setUpCollapsingToolbarLayout();
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.blog_details));
        }

        if (currentBlog != null) {
            if (currentBlog.getCoverImgUrl() != null)
                Glide.with(this)
                        .load(currentBlog.getCoverImgUrl())
                        .asBitmap()
                        .fitCenter()
                        .into(ivCover);

            if (currentBlog.getTitle() != null)
                tvTitle.setText(currentBlog.getTitle());

            if (currentBlog.getAuthor() != null)
                tvAuthor.setText(currentBlog.getAuthor());

            if (currentBlog.getDate() != null)
                tvDate.setText(currentBlog.getDate());

            if (currentBlog.getDescription() != null)
                tvDescription.setText(currentBlog.getDescription());
        } else {
            onError("There was an error displaying Blog Details");
            presenter.onBlogDetailsDisplayError();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBlogFABClicked();
            }
        });
    }

    private void setUpCollapsingToolbarLayout() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.blog_details));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
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
    public void onBackPressed() {

        Intent upIntent = NavUtils.getParentActivityIntent(this);
        assert upIntent != null;
        upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (!NavUtils.shouldUpRecreateTask(this, upIntent)) {
            NavUtils.navigateUpTo(this, upIntent);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void returnToMainActivity() {
        onBackPressed();
    }

    @Override
    public void openInBrowser() {
        if (currentBlog != null && currentBlog.getBlogUrl() != null) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(currentBlog.getBlogUrl()));
            startActivity(intent);
        }
    }
}
