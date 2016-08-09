package com.devlin.core.model.services;

import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.responses.ResponseRestaurant;
import com.devlin.core.view.ICallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 7/25/2016.
 */
public interface IUserService {

    void logIn(User user, ICallback<User> callback);

    void register(User user, ICallback<Boolean> callback);

    void addFavoriteRestaurant(User user, Restaurant restaurant, ICallback<Boolean> callback);

    void removeFavoriteRestaurant(User user, Restaurant restaurant, ICallback<Boolean> callback);

    void loadFavoriteRestaurants(User user, ICallback<List<Restaurant>> callback);
}
