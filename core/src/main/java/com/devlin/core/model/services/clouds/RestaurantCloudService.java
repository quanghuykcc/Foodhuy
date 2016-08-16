package com.devlin.core.model.services.clouds;

import android.util.Log;

import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Comment;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.IRestaurantService;
import com.devlin.core.view.ICallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 7/31/2016.
 */
public class RestaurantCloudService extends BaseCloudService<IRestaurantCloudService> implements IRestaurantService {

    public RestaurantCloudService(IRestaurantCloudService iRestaurantCloudService) {
        mICloudService = iRestaurantCloudService;
    }

    @Override
    public void getRestaurants(long from, long to, String orderBy, ICallback<List<Restaurant>> callback) {

    }

    @Override
    public void getAllRestaurants(ICallback<List<Restaurant>> callback) {
        getICloudService().getAllRestaurants().enqueue(new Callback<APIResponse>() {

            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                APIResponse apiResponse = response.body();
                if (apiResponse.isSuccess()) {
                    Log.d("TAG", apiResponse.getData().get(0).toString());
                }
                else {
                    Log.d("TAG", apiResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                Log.e("TAG", "", t);
            }
        });
    }

    @Override
    public void saveRestaurant(Restaurant restaurant, ICallback<Boolean> callback) {

    }

    @Override
    public void saveRestaurants(List<Restaurant> restaurants, ICallback<Boolean> callback) {

    }

    @Override
    public void getRestaurantById(String id, ICallback<Restaurant> callback) {

    }

    @Override
    public void getLatestRestaurants(ICallback<List<Restaurant>> callback) {

    }

    @Override
    public void getRestaurantsByCategory(Category category, ICallback<List<Restaurant>> callback) {

    }

    @Override
    public void addComment(Comment comment, Restaurant restaurant, ICallback<Boolean> callback) {

    }
}
