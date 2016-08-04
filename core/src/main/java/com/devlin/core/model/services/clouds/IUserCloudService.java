package com.devlin.core.model.services.clouds;

import com.devlin.core.model.entities.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by Administrator on 7/31/2016.
 */
public interface IUserCloudService {

    @GET("/logIn")
    Call<User> logIn(@Query("user_name") String userName, @Query("password") String password);

}
