package com.devlin.core.event;

import com.devlin.core.model.entities.FavoriteRestaurant;

import java.util.List;

/**
 * Created by Administrator on 9/5/2016.
 */
public class FetchedFavoriteRestaurantEvent {
    private boolean mSuccess;

    private List<FavoriteRestaurant> mFavoriteRestaurants;

    private String mMessage;

    public FetchedFavoriteRestaurantEvent(boolean success, List<FavoriteRestaurant> favoriteRestaurants) {
        mSuccess = success;
        mFavoriteRestaurants = favoriteRestaurants;
    }

    public FetchedFavoriteRestaurantEvent(boolean success, String message) {
        mSuccess = success;
        mMessage = message;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        mSuccess = success;
    }

    public List<FavoriteRestaurant> getFavoriteRestaurants() {
        return mFavoriteRestaurants;
    }

    public void setFavoriteRestaurants(List<FavoriteRestaurant> favoriteRestaurants) {
        mFavoriteRestaurants = favoriteRestaurants;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
