package com.devlin.core.model.services.clouds;

import com.devlin.core.model.responses.ResponseRestaurant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 7/31/2016.
 */
public interface IRestaurantCloudService {

    @GET
    Call<ResponseRestaurant> getRestaurants(@Query("from") long from, @Query("to") long to, @Query("order_by") String orderBy);
}