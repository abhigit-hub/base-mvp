package com.footinit.selfproject.ui.main.blog;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.footinit.selfproject.R;
import com.footinit.selfproject.data.db.model.Blog;
import com.footinit.selfproject.di.PerActivity;
import com.footinit.selfproject.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Abhijit on 17-11-2017.
 */

@PerActivity
public class BlogAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_EMPTY = 1;


    private Callback callback;
    private List<Blog> blogList;

    public BlogAdapter(List<Blog> blogList) {
        this.blogList = blogList;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog_view, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {

        if (blogList != null && blogList.size() > 0)
            return VIEW_TYPE_NORMAL;
        else return VIEW_TYPE_EMPTY;
    }

    @Override
    public int getItemCount() {

        if (blogList != null && blogList.size() > 0)
            return blogList.size();
        else return 1;
    }


    /*
    * This add method is called only on first time list fetch.
    * Is called from the Fragment, where the first network call for blog list is done here*/
    void addItems(List<Blog> list) {
        this.blogList.addAll(list);
        notifyDataSetChanged();
    }

    /*
    * This update method is called every time apart from the first time list fetch.
    * Is called from the MainActivity. Previous List must be cleared to store new list, to avoid duplicates*/
    public void updateListItems(List<Blog> blogList) {
        this.blogList.clear();
        this.blogList.addAll(blogList);
        notifyDataSetChanged();
    }

    void removeCallback() {
        callback = null;
    }


    public interface Callback {

        void onBlogEmptyRetryClicked();

        void onBlogItemClicked(Blog blog);
    }


    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.cover_image_view)
        ImageView ivCover;

        @BindView(R.id.title_text_view)
        TextView tvTitle;

        @BindView(R.id.author_text_view)
        TextView tvAuthor;

        @BindView(R.id.date_text_view)
        TextView tvDate;

        @BindView(R.id.description_text_view)
        TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {
            ivCover.setImageDrawable(null);
            tvTitle.setText("");
            tvAuthor.setText("");
            tvDate.setText("");
            tvDescription.setText("");
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            final Blog blog = blogList.get(position);

            if (blog.getCoverImgUrl() != null)
                Glide.with(itemView.getContext())
                        .load(blog.getCoverImgUrl())
                        .asBitmap()
                        .fitCenter()
                        .into(ivCover);

            if (blog.getTitle() != null)
                tvTitle.setText(blog.getTitle());

            if (blog.getAuthor() != null)
                tvAuthor.setText(blog.getAuthor());

            if (blog.getDate() != null)
                tvDate.setText(blog.getDate());

            if (blog.getDescription() != null)
                tvDescription.setText(blog.getDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (blog != null && callback != null)
                        callback.onBlogItemClicked(blog);
                }
            });
        }
    }


    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.btn_retry)
        Button retryButton;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        @OnClick(R.id.btn_retry)
        void onRetryClicked() {
            if (callback != null)
                callback.onBlogEmptyRetryClicked();
        }
    }
}
