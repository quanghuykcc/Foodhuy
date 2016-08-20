package com.devlin.core.model.services.clouds;

import android.util.Log;

import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.responses.APIResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 8/16/2016.
 */
public interface ICategoryCloudService {

    @GET("/api/v1/categories")
    Call<APIResponse<List<Category>>> getAllCategories();

}
