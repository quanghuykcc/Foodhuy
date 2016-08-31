package com.devlin.core.model.services.clouds;

import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.view.ICallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 7/31/2016.
 */
public class UserCloudService extends BaseCloudService<IUserService>  {

    public UserCloudService(IUserService iUserService) {
        mICloudService = iUserService;
    }

    public void logIn(User user, final ICallback<User> callback) {
        getICloudService().logIn(user.getEmail(), user.getPassword()).enqueue(new Callback<APIResponse<User>>() {
            @Override
            public void onResponse(Call<APIResponse<User>> call, Response<APIResponse<User>> response) {
                APIResponse<User> apiResponse = response.body();
                if (apiResponse != null) {
                    if (apiResponse.isSuccess()) {
                        callback.onResult(apiResponse.getData());
                    }
                    else {
                        callback.onFailure(new Throwable(apiResponse.getMessage()));
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponse<User>> call, Throwable t) {
                callback.onFailure(t);
            }
        });

    }

    public void register(User user, final ICallback<Boolean> callback) {
        getICloudService().signUp(user.getEmail(), user.getPassword(), user.getUserName()).equals(new Callback<APIResponse<Boolean>>() {
            @Override
            public void onResponse(Call<APIResponse<Boolean>> call, Response<APIResponse<Boolean>> response) {
                APIResponse<Boolean> apiResponse = response.body();
                if (apiResponse != null && apiResponse.isSuccess()) {
                    callback.onResult(apiResponse.getData());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<Boolean>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void addFavoriteRestaurant(User user, Restaurant restaurant, ICallback<Boolean> callback) {

    }

    public void removeFavoriteRestaurant(User user, Restaurant restaurant, ICallback<Boolean> callback) {

    }

    public void loadFavoriteRestaurants(User user, ICallback<List<Restaurant>> callback) {

    }
}
