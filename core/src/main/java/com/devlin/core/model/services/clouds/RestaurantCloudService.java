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
    public void getRestaurants(long offset, long limit, final ICallback<List<Restaurant>> callback) {
        getICloudService().getRestaurants(offset, limit).enqueue(new Callback<APIResponse<List<Restaurant>>>() {

            @Override
            public void onResponse(Call<APIResponse<List<Restaurant>>> call, Response<APIResponse<List<Restaurant>>> response) {
                APIResponse<List<Restaurant>> apiResponse = response.body();
                if (apiResponse.isSuccess()) {
                    callback.onResult(apiResponse.getData());

                }
                else {
                    Log.d("TAG", apiResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Restaurant>>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void getAllRestaurants(final ICallback<List<Restaurant>> callback) {
        /*getICloudService().getAllRestaurants().enqueue(new Callback<APIResponse<List<Restaurant>>>() {

            @Override
            public void onResponse(Call<APIResponse<List<Restaurant>>> call, Response<APIResponse<List<Restaurant>>> response) {
                APIResponse<List<Restaurant>> apiResponse = response.body();
                if (apiResponse.isSuccess()) {
                    callback.onResult(apiResponse.getData());

                    Log.d("TAG", apiResponse.getData().get(0).toString());
                }
                else {
                    Log.d("TAG", apiResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Restaurant>>> call, Throwable t) {
                callback.onFailure(t);
            }
        });*/
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
    public void getRestaurantsByCategory(Category category, long offset, long limit, final ICallback<List<Restaurant>> callback) {
        getICloudService().getRestaurantsByCategory(category.getId(), offset, limit).enqueue(new Callback<APIResponse<List<Restaurant>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Restaurant>>> call, Response<APIResponse<List<Restaurant>>> response) {
                APIResponse<List<Restaurant>> apiResponse = response.body();
                if (apiResponse.isSuccess()) {
                    callback.onResult(apiResponse.getData());
                }
                else {
                    Log.d("TAG", apiResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Restaurant>>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void addComment(Comment comment, Restaurant restaurant, ICallback<Boolean> callback) {

    }
}
