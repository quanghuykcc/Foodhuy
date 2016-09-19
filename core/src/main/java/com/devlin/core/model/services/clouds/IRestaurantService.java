package com.devlin.core.model.services.clouds;

import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.util.QueryDate;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 7/31/2016.
 */
public interface IRestaurantService {

    @GET("/foody_api_huy/api/v1/restaurants")
    Call<APIResponse<List<Restaurant>>> getRestaurants(@Query("offset") int offset, @Query("limit") int limit);

    @GET("/foody_api_huy/api/v1/restaurants")
    Call<APIResponse<List<Restaurant>>> getNewRestaurants(@Query("last_sync_timestamp") QueryDate lastSyncTimestamp);

    @GET("/foody_api_huy/api/v1/restaurants")
    Call<APIResponse<List<Restaurant>>> getByCategory(@Query("category_id") int categoryId, @Query("offset") long offset, @Query("limit") long limit);

    @GET("/foody_api_huy/api/v1/restaurants")
    Call<APIResponse<List<Restaurant>>> getFavorite(@Query("favorite_user_id") int favoriteUserId, @Query("offset") long offset, @Query("limit") long limit);
}
