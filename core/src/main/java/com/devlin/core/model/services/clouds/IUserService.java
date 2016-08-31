package com.devlin.core.model.services.clouds;

import com.devlin.core.model.entities.User;
import com.devlin.core.model.responses.APIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by Administrator on 7/31/2016.
 */
public interface IUserService {

    @POST("/api/v1/authenticate/sign-in")
    Call<APIResponse<User>> logIn(@Query("email") String email, @Query("password") String password);

    @POST("/api/v1/authenticate/sign-up")
    Call<APIResponse<Boolean>> signUp(@Query("email") String email, @Query("password") String password, @Query("name") String name);
}
