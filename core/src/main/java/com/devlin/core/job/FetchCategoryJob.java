package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.event.FetchedCategoryEvent;
import com.devlin.core.model.entities.Category;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.clouds.ICategoryService;
import com.devlin.core.model.services.storages.CategoryModel;
import com.devlin.core.util.QueryDate;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Response;

public class FetchCategoryJob extends BasicJob {

    //region Properties
    private ICategoryService mICategoryService;

    private CategoryModel mCategoryModel;

    private static final String TAG = "FetchCategoryJob";

    private static final String GROUP = "FetchCategoryJob";

    //endregion

    //region Constructors

    public FetchCategoryJob(@BasicJob.Priority int priority, ICategoryService categoryService, CategoryModel categoryModel) {
        super(new Params(priority).groupBy(GROUP).requireNetwork());

        mICategoryService = categoryService;

        mCategoryModel = categoryModel;

    }

    //endregion

    //region Override Methods

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Call<APIResponse<List<Category>>> call;

        Date latestSynchronizeTimestamp = mCategoryModel.getLatestSynchronize();

        if (latestSynchronizeTimestamp != null) {
            call = mICategoryService.getNewCategories(new QueryDate(latestSynchronizeTimestamp));
        }
        else {
            call = mICategoryService.getAllCategories();
        }

        Response<APIResponse<List<Category>>> response = call.execute();
        if (response != null && response.isSuccessful()) {
            APIResponse<List<Category>> apiResponse = response.body();

            if (apiResponse != null && apiResponse.isSuccess()) {
                if (apiResponse.getData().size() > 0) {
                    for (Category category : apiResponse.getData()) {
                        if (category.isDeleted()) {
                            mCategoryModel.delete(category);
                        }
                        else {
                            mCategoryModel.addOrUpdate(category);
                        }
                    }
                }
                mCategoryModel.saveLatestSynchronize(apiResponse.getLastSyncTimestamp());
            }
        }
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        if (shouldRetry(throwable)) {
            return RetryConstraint.RETRY;
        }
        return RetryConstraint.CANCEL;
    }

    @Override
    protected int getRetryLimit() {
        return 2;
    }

    //endregion
}
