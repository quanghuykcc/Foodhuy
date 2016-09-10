package com.devlin.foodhuy.adapters.binder;

import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.viewmodel.LatestRestaurantViewModel;

/**
 * Created by Administrator on 9/9/2016.
 */
public class RestaurantBinder extends ConditionalDataBinder<Restaurant> {
    public RestaurantBinder(int bindingVariable, int layoutId) {
        super(bindingVariable, layoutId);
    }

    @Override
    public boolean canHandle(Restaurant model) {
        return true;
    }
}
