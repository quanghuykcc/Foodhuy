package com.devlin.core.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 8/25/2016.
 */
public class FavoriteRestaurant extends RealmObject {

    //region Properties
    @PrimaryKey
    @SerializedName("id")
    private int mId;

    @SerializedName("restaurant_id")
    private int mRestaurantId;

    @SerializedName("user_id")
    private int mUserId;

    @SerializedName("created_at")
    private Date mCreatedAt;

    @SerializedName("updated_at")
    private Date mUpdatedAt;

    @SerializedName("deleted_at")
    private Date mDeletedAt;

    //endregion

    //region Getter and Setter

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getRestaurantId() {
        return mRestaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        mRestaurantId = restaurantId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
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

    public boolean isDeleted() {
        return mDeletedAt != null;
    }

    //endregion

    //region Constructors

    public FavoriteRestaurant(int restaurantId, int userId) {
        super();
        mRestaurantId = restaurantId;
        mUserId = userId;
    }

    public FavoriteRestaurant() {
        super();
    }

    //endregion


    @Override
    public String toString() {
        return "FavoriteRestaurant{" +
                "mId=" + mId +
                ", mRestaurantId=" + mRestaurantId +
                ", mUserId=" + mUserId +
                ", mCreatedAt=" + mCreatedAt +
                ", mUpdatedAt=" + mUpdatedAt +
                ", mDeletedAt=" + mDeletedAt +
                '}';
    }
}
