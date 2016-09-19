package com.devlin.core.model.services.clouds;

import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.util.QueryDate;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 9/5/2016.
 */
public interface IFavoriteRestaurantService {

    @GET("/foody_api_huy/api/v1/favoriteRestaurants")
    Call<APIResponse<List<FavoriteRestaurant>>> getAll(@Query("user_id") int userId);

    @GET("/foody_api_huy/api/v1/favoriteRestaurants")
    Call<APIResponse<List<FavoriteRestaurant>>> getNew(@Query("user_id") int userId, @Query("last_sync_timestamp") QueryDate lastSyncedAt);

    @POST("/foody_api_huy/api/v1/favoriteRestaurants")
    Call<APIResponse<FavoriteRestaurant>> add(@Body FavoriteRestaurant favoriteRestaurant);

    @DELETE("/foody_api_huy/api/v1/favoriteRestaurants/{id}")
    Call<APIResponse<Integer>> delete(@Path("id") int id);
}
