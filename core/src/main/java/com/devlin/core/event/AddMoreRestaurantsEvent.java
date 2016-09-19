package com.devlin.core.event;

import com.devlin.core.model.entities.Restaurant;

import java.util.List;

/**
 * Created by Administrator on 9/16/2016.
 */
public class AddMoreRestaurantsEvent {
    private final List<Restaurant> mRestaurants;

    public AddMoreRestaurantsEvent(List<Restaurant> restaurants) {
        mRestaurants = restaurants;
    }

    public List<Restaurant> getRestaurants() {
        return mRestaurants;
    }
}
