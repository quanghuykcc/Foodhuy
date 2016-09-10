package com.devlin.core.event;

import com.devlin.core.model.entities.FavoriteRestaurant;

/**
 * Created by Administrator on 9/9/2016.
 */
public class DeletedFavoriteEvent {
    private boolean mSuccess;

    private FavoriteRestaurant mFavoriteRestaurant;


    public DeletedFavoriteEvent(boolean success) {
        mSuccess = success;
    }

    public DeletedFavoriteEvent(boolean success, FavoriteRestaurant favoriteRestaurant) {
        mSuccess = success;
        mFavoriteRestaurant = favoriteRestaurant;
    }

    public FavoriteRestaurant getFavoriteRestaurant() {
        return mFavoriteRestaurant;
    }

    public boolean isSuccess() {
        return mSuccess;
    }
}
