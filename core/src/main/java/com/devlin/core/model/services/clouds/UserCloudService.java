package com.devlin.core.model.services.clouds;

import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.services.IUserService;
import com.devlin.core.view.ICallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 7/31/2016.
 */
public class UserCloudService extends BaseCloudService<IUserCloudService> implements IUserService {

    @Override
    public void logIn(User user, final ICallback<User> callback) {
        getICloudService().logIn(user.getUserName(), user.getPassword()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user != null) {
                    callback.onResult(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onFailure(t);
            }
        });

    }

    @Override
    public void register(User user, ICallback<Boolean> callback) {

    }

    @Override
    public void addFavoriteRestaurant(User user, Restaurant restaurant, ICallback<Boolean> callback) {

    }

    @Override
    public void removeFavoriteRestaurant(User user, Restaurant restaurant, ICallback<Boolean> callback) {

    }

    @Override
    public void loadFavoriteRestaurants(User user, ICallback<List<Restaurant>> callback) {

    }
}
