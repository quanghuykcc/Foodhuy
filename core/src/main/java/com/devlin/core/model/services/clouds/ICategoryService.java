package com.devlin.core.model.services.clouds;

import android.util.Log;

import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.util.QueryDate;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 8/16/2016.
 */
public interface ICategoryService {

    @GET("/foody_api_huy/api/v1/categories")
    Call<APIResponse<List<Category>>> getAll();

    @GET("/foody_api_huy/api/v1/categories")
    Call<APIResponse<List<Category>>> getSync(@Query("last_sync_timestamp") QueryDate lastSyncTimestamp);

}
