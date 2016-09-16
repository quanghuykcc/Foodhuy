package com.devlin.core.model.services.clouds;

import com.devlin.core.model.entities.Comment;
import com.devlin.core.model.responses.APIResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 9/16/2016.
 */
public interface ICommentService {

    @POST("/foody_api_huy/api/v1/comments")
    Call<APIResponse<Comment>> add(@Body Comment comment);

    @GET("/foody_api_huy/api/v1/comments")
    Call<APIResponse<Comment>> getByRestaurants(@Query("restaurant_id") int restaurantId, @Query("offset") long offset, @Query("limit") long limit);

}
