package com.devlin.core.model.services.clouds;

import com.devlin.core.model.entities.Category;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.ICategoryService;
import com.devlin.core.view.ICallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 8/16/2016.
 */
public class CategoryCloudService extends BaseCloudService<ICategoryCloudService> implements ICategoryService {

    public CategoryCloudService(ICategoryCloudService iCategoryCloudService) {
        mICloudService = iCategoryCloudService;
    }

    @Override
    public void getAllCategories(final ICallback<List<Category>> callback) {
        getICloudService().getAllCategories().enqueue(new Callback<APIResponse<List<Category>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Category>>> call, Response<APIResponse<List<Category>>> response) {
                APIResponse<List<Category>> apiResponse = response.body();

                if (apiResponse.isSuccess()) {
                    callback.onResult(apiResponse.getData());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Category>>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void saveCategories(List<Category> categories, ICallback<Boolean> callback) {

    }
}
