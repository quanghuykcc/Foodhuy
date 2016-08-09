package com.devlin.core.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 08-Aug-16.
 */
public class Comment extends RealmObject {

    //region Properties

    @SerializedName("id")
    @PrimaryKey
    private int mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("content")
    private String mContent;

    @SerializedName("created_at")
    private Date mCreatedAt;

    @SerializedName("updated_at")
    private Date mUpdatedAt;

    @SerializedName("is_deleted")
    private boolean mIsDeleted;

    @SerializedName("commenter")
    private User mCommenter;

    //endregion

    //region Getter and Setter

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }

    public Date getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return mIsDeleted;
    }

    public void setDeleted(boolean deleted) {
        mIsDeleted = deleted;
    }

    public User getCommenter() {
        return mCommenter;
    }

    public void setCommenter(User commenter) {
        mCommenter = commenter;
    }


    //endregion
}
