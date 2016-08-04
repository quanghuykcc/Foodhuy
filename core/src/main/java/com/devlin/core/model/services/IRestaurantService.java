package com.devlin.core.model.services;

import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.view.ICallback;

import java.util.List;
import java.util.StringTokenizer;

import io.realm.RealmResults;

/**
 * Created by Administrator on 7/25/2016.
 */
public interface IRestaurantService {

    void getRestaurants(long from, long to, String orderBy, ICallback<List<Restaurant>> callback);

    void getAllRestaurants(ICallback<List<Restaurant>> callback);

    void saveRestaurant(Restaurant restaurant, ICallback<Boolean> callback);

    void saveRestaurants(List<Restaurant> restaurants, ICallback<Boolean> callback);

    void getRestaurantById(String id, ICallback<Restaurant> callback);

    void getLatestRestaurants(ICallback<List<Restaurant>> callback);

    void getRestaurantsByCategory(Category category, ICallback<List<Restaurant>> callback);

}
