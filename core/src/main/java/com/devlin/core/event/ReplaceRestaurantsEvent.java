package com.devlin.core.event;

import com.devlin.core.model.entities.Restaurant;

import java.util.List;

/**
 * Created by Administrator on 9/16/2016.
 */
public class ReplaceRestaurantsEvent {
    private final List<Restaurant> mRestaurants;

    public ReplaceRestaurantsEvent(List<Restaurant> restaurants) {
        mRestaurants = restaurants;
    }

    public List<Restaurant> getRestaurants() {
        return mRestaurants;
    }
}
