package com.devlin.core.model.services.clouds;

import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.responses.APIResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by Administrator on 7/31/2016.
 */
public interface IUserService {

    @POST("/foody_api_huy/api/v1/authenticate/sign-in")
    Call<APIResponse<User>> logIn(@Query("email") String email, @Query("password") String password);

    @POST("/foody_api_huy/api/v1/authenticate/sign-up")
    Call<APIResponse<Boolean>> signUp(@Query("email") String email, @Query("password") String password, @Query("name") String name);

    @POST("/foody_api_huy/api/v1/authenticate/re-sign-in")
    Call<APIResponse<User>> logInIfRemember(@Query("remember_token") String rememberToken);

}
