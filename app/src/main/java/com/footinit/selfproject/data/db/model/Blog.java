package com.footinit.selfproject.data.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Abhijit on 17-11-2017.
 */

public class Blog implements Parcelable{

    @Expose
    @SerializedName("blog_url")
    private String blogUrl;

    @Expose
    @SerializedName("img_url")
    private String coverImgUrl;

    @Expose
    private String title;

    @Expose
    private String description;

    @Expose
    private String author;

    @Expose
    @SerializedName("published_at")
    private String date;



    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(coverImgUrl);
        parcel.writeString(blogUrl);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(date);
        parcel.writeString(description);
    }

    public static final Parcelable.Creator<Blog> CREATOR = new Creator<Blog>() {
        @Override
        public Blog createFromParcel(Parcel parcel) {
            Blog blog = new Blog();

            blog.coverImgUrl = parcel.readString();
            blog.blogUrl = parcel.readString();
            blog.title = parcel.readString();
            blog.author = parcel.readString();
            blog.date = parcel.readString();
            blog.description = parcel.readString();

            return blog;
        }

        @Override
        public Blog[] newArray(int i) {
            return new Blog[i];
        }
    };
}
