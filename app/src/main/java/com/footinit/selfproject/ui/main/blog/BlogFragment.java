package com.footinit.selfproject.ui.main.blog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.footinit.selfproject.R;
import com.footinit.selfproject.data.db.model.Blog;
import com.footinit.selfproject.di.component.ActivityComponent;
import com.footinit.selfproject.ui.base.BaseFragment;
import com.footinit.selfproject.ui.main.blogdetails.BlogDetailsActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abhijit on 17-11-2017.
 */

public class BlogFragment extends BaseFragment
        implements BlogMvpView {

    public static final String KEY_PARCELABLE_BLOG = "BLOG_PARCELABLE_KEY";

    @Inject
    BlogMvpPresenter<BlogMvpView> presenter;

    @Inject
    BlogAdapter blogAdapter;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.rv_blog)
    RecyclerView rvBlog;


    public static BlogFragment newInstance() {
        Bundle args = new Bundle();
        BlogFragment fragment = new BlogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_blog, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);

            setUnBinder(ButterKnife.bind(this, view));

            presenter.onAttach(this);

            blogAdapter.setCallback((BlogPresenter) presenter);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvBlog.setLayoutManager(linearLayoutManager);
        rvBlog.setItemAnimator(new DefaultItemAnimator());
        rvBlog.setAdapter(blogAdapter);

        presenter.fetchBlogList();
    }

    @Override
    public void updateBlogList(List<Blog> blogList) {
        blogAdapter.addItems(blogList);
    }

    @Override
    public void onBlogEmptyRetryClicked() {
        presenter.fetchBlogList();
    }

    @Override
    public void openBlogDetailActivity(Blog blog) {
        Intent intent = BlogDetailsActivity.getStartIntent(getContext());
        intent.putExtra(KEY_PARCELABLE_BLOG, blog);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
