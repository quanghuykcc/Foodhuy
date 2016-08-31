package com.devlin.core.model.services.clouds;

import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.SyncHistory;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.view.ICallback;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 8/16/2016.
 */
public class CategoryCloudService extends BaseCloudService<ICategoryService> {

    public CategoryCloudService(ICategoryService iCategoryService) {
        mICloudService = iCategoryService;
    }

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

    public void saveCategories(List<Category> categories, ICallback<Boolean> callback) {

    }

    public void getNewCategories(final ICallback<List<Category>> callback) {
        final Realm realm = Realm.getDefaultInstance();

        final SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", "categories").findFirst();

        if (syncHistory != null) {
            getICloudService().getNewCategories(syncHistory.getLastSyncTimestamp()).enqueue(new Callback<APIResponse<List<Category>>>() {
                @Override
                public void onResponse(Call<APIResponse<List<Category>>> call, Response<APIResponse<List<Category>>> response) {
                    APIResponse<List<Category>> apiResponse = response.body();

                    if (apiResponse.isSuccess()) {
                        callback.onResult(apiResponse.getData());
                        realm.beginTransaction();
                        syncHistory.setLastSyncTimestamp(apiResponse.getLastSyncTimestamp());
                        realm.commitTransaction();
                    }
                }

                @Override
                public void onFailure(Call<APIResponse<List<Category>>> call, Throwable t) {
                    callback.onFailure(t);
                }
            });
        }

        else {
            getICloudService().getAllCategories().enqueue(new Callback<APIResponse<List<Category>>>() {
                @Override
                public void onResponse(Call<APIResponse<List<Category>>> call, Response<APIResponse<List<Category>>> response) {
                    APIResponse<List<Category>> apiResponse = response.body();

                    if (apiResponse.isSuccess()) {
                        callback.onResult(apiResponse.getData());

                        realm.beginTransaction();

                        SyncHistory newSyncHistory = realm.createObject(SyncHistory.class);
                        newSyncHistory.setNameTable("categories");
                        newSyncHistory.setLastSyncTimestamp(apiResponse.getLastSyncTimestamp());

                        realm.commitTransaction();
                    }
                }

                @Override
                public void onFailure(Call<APIResponse<List<Category>>> call, Throwable t) {
                    callback.onFailure(t);
                }
            });
        }


    }
}
