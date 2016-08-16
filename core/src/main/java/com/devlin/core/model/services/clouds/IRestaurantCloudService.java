package com.devlin.core.model.services.clouds;

import com.devlin.core.model.responses.APIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 7/31/2016.
 */
public interface IRestaurantCloudService {

    @GET
    Call<APIResponse> getRestaurants(@Query("from") long from, @Query("to") long to, @Query("order_by") String orderBy);

    @GET("/api/v1/restaurants")
    Call<APIResponse> getAllRestaurants();
}
