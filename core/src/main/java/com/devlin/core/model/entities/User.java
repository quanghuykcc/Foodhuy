package com.devlin.core.model.entities;

import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 7/31/2016.
 */
public class User extends RealmObject {

    //region Properties

    @PrimaryKey
    private String mId;

    private String mUserName;

    private String mPassword;

    @Ignore
    @Expose
    private String mRetypePassword;

    private String mEmail;

    private Date mCreatedAt;

    private Date mUpdatedAt;

    private boolean mIsDeleted;

    @Expose
    private RealmList<Restaurant> mFavoriteRestaurant;

    //endregion

    //region Getter and Setter

    public RealmList<Restaurant> getFavoriteRestaurant() {
        return mFavoriteRestaurant;
    }

    public void setFavoriteRestaurant(RealmList<Restaurant> favoriteRestaurant) {
        mFavoriteRestaurant = favoriteRestaurant;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
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

    public String getRetypePassword() {
        return mRetypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        mRetypePassword = retypePassword;
    }

    //endregion

    //region Constructors

    public User() {
        super();
    }

    //endregion

    //region Override Methods

    @Override
    public String toString() {
        return "User{" +
                "mId='" + mId + '\'' +
                ", mUserName='" + mUserName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mCreatedAt=" + mCreatedAt +
                ", mUpdatedAt=" + mUpdatedAt +
                ", mIsDeleted=" + mIsDeleted +
                '}';
    }

    //endregion

}
