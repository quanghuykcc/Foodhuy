package com.devlin.core.event;

import com.devlin.core.model.entities.Restaurant;

import java.util.List;

/**
 * Created by Administrator on 8/25/2016.
 */
public class FetchedRestaurantEvent {
    private boolean mSuccess;

    private List<Restaurant> mRestaurants;

    private String mMessage;

    public FetchedRestaurantEvent(boolean success, List<Restaurant> restaurants) {
        mSuccess = success;
        mRestaurants = restaurants;
    }

    public FetchedRestaurantEvent(boolean success, String message) {
        mSuccess = success;
        mMessage = message;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public List<Restaurant> getRestaurants() {
        return mRestaurants;
    }

    public String getMessage() {
        return mMessage;
    }
}
