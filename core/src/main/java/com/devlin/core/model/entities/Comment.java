package com.devlin.core.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 08-Aug-16.
 */
public class Comment extends RealmObject {

    //region Properties

    @Expose(serialize = false)
    @SerializedName("id")
    @PrimaryKey
    private int mId;

    @SerializedName("content")
    private String mContent;

    @SerializedName("created_at")
    private Date mCreatedAt;

    @SerializedName("updated_at")
    private Date mUpdatedAt;

    @SerializedName("deleted_at")
    private Date mDeletedAt;

    @SerializedName("commenter_id")
    private int mCommenterId;

    @SerializedName("restaurant_id")
    private int mRestaurantId;

    @Expose(serialize = false)
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

    public Date getDeletedAt() {
        return mDeletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        mDeletedAt = deletedAt;
    }

    public User getCommenter() {
        return mCommenter;
    }

    public void setCommenter(User commenter) {
        mCommenter = commenter;
    }

    public int getCommenterId() {
        return mCommenterId;
    }

    public void setCommenterId(int commenterId) {
        mCommenterId = commenterId;
    }

    public int getRestaurantId() {
        return mRestaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        mRestaurantId = restaurantId;
    }

    //endregion


    @Override
    public String toString() {
        return "Comment{" +
                "mId=" + mId +
                ", mContent='" + mContent + '\'' +
                ", mCreatedAt=" + mCreatedAt +
                ", mUpdatedAt=" + mUpdatedAt +
                ", mDeletedAt=" + mDeletedAt +
                ", mCommenter=" + mCommenter +
                '}';
    }
}
