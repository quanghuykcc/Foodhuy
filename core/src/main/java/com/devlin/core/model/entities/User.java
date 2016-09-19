package com.devlin.core.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    @SerializedName("id")
    @PrimaryKey
    private int mId;

    @SerializedName("name")
    private String mName;

    @Ignore
    @SerializedName("password")
    private String mPassword;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("remember_token")
    private String mRemmemberToken;

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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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

    public Date getDeletedAt() {
        return mDeletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        mDeletedAt = deletedAt;
    }

    public String getRemmemberToken() {
        return mRemmemberToken;
    }

    public void setRemmemberToken(String remmemberToken) {
        mRemmemberToken = remmemberToken;
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
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mRemmemberToken='" + mRemmemberToken + '\'' +
                '}';
    }

    //endregion

}
