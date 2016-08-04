package com.devlin.core.model.entities;

import android.databinding.ObservableArrayList;

import java.util.List;

/**
 * Created by Administrator on 7/27/2016.
 */
public class RestaurantList {
    //region Properties

    private ObservableArrayList<Restaurant> mRestaurantList;

    private int mTotalCount;

    //endregion

    //region Contructors

    public RestaurantList() {
        mRestaurantList = new ObservableArrayList<Restaurant>();
    }

    //endregion

    //region Public methods


    public ObservableArrayList<Restaurant> getRestaurants() {
        return mRestaurantList;
    }

    public void add(Restaurant restaurant) {
        mRestaurantList.add(restaurant);
    }
    public void addAll(List<Restaurant> restaurants) {
        mRestaurantList.addAll(restaurants);
    }
    //endregion
}
