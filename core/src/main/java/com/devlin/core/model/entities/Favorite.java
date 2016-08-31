package com.devlin.core.model.entities;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 8/25/2016.
 */
public class Favorite extends RealmObject {

    //region Properties

    @SerializedName("restaurant_id")
    private int mRestaurantId;

    @Index
    @SerializedName("user_id")
    private int mUserId;

    //endregion

    //region Constructors

    public Favorite(){

    }

    public Favorite(int restaurantId, int userId) {
        mRestaurantId = restaurantId;
        mUserId = userId;
    }

    //endregion
}
