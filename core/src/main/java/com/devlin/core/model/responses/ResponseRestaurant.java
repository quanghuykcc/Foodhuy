package com.devlin.core.model.responses;

import com.devlin.core.model.entities.Restaurant;

import java.util.List;

/**
 * Created by Administrator on 7/31/2016.
 */
public class ResponseRestaurant {

    //region Properties

    private List<Restaurant> mRestaurants;

    //endregion

    //region Getter and Setter

    public List<Restaurant> getRestaurants() {
        return mRestaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        mRestaurants = restaurants;
    }

    //endregion
}
