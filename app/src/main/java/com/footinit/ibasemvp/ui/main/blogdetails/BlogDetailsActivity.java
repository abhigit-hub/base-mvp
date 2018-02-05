package com.footinit.ibasemvp.ui.main.blogdetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.footinit.ibasemvp.R;
import com.footinit.ibasemvp.data.db.model.Blog;
import com.footinit.ibasemvp.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abhijit on 18-11-2017.
 */

public class BlogDetailsActivity extends BaseActivity
        implements BlogDetailsMvpView {

    public static final String KEY_PARCELABLE_BLOG = "KEY_PARCELABLE_BLOG";

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

        currentBlog = getIntent().getParcelableExtra(KEY_PARCELABLE_BLOG);

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
        }

        if (currentBlog != null) {
            if (currentBlog.getCoverImgUrl() != null)
                Glide.with(this)
                        .load(currentBlog.getCoverImgUrl())
                        .asBitmap()
                        .fitCenter()
                        .placeholder(R.drawable.placeholder)
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
            presenter.onBlogDetailsDisplayedError();
        }

        setUpAnimation();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentBlog != null)
                    presenter.onBlogFABClicked();
            }
        });
    }

    private void setUpAnimation() {
        Drawable drawable = fab.getDrawable();

        if (drawable != null && drawable instanceof Animatable)
            ((Animatable) drawable).start();
        else if (drawable instanceof AnimatedVectorDrawableCompat)
            ((AnimatedVectorDrawableCompat) drawable).start();
        else if (drawable instanceof AnimatedVectorDrawable)
            ((AnimatedVectorDrawable) drawable).start();
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
                    setUpAnimation();
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
