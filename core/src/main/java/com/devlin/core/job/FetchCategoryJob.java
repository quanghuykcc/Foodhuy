package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.event.ReplaceCategoriesEvent;
import com.devlin.core.model.entities.Category;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.clouds.ICategoryService;
import com.devlin.core.model.services.storages.CategoryModel;
import com.devlin.core.util.QueryDate;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class FetchCategoryJob extends BasicJob {

    //region Properties

    private ICategoryService mICategoryService;

    private CategoryModel mCategoryModel;

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
        List<Category> localCategories = mCategoryModel.getAll();
        post(new ReplaceCategoriesEvent(localCategories));

        Call<APIResponse<List<Category>>> call;
        Date lastSyncAt = mCategoryModel.getLastSyncAt();

        if (lastSyncAt != null) {
            call = mICategoryService.getSync(new QueryDate(lastSyncAt));
        }
        else {
            call = mICategoryService.getAll();
        }

        Response<APIResponse<List<Category>>> response = call.execute();
        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
            List<Category> fetchedCategories = response.body().getData();

            if (fetchedCategories != null && fetchedCategories.size() > 0) {
                for (Category category : fetchedCategories) {
                    if (category.isDeleted()) {
                        mCategoryModel.delete(category);
                    }
                    else {
                        mCategoryModel.addOrUpdate(category);
                    }
                }
                List<Category> syncedCategories = mCategoryModel.getAll();
                post(new ReplaceCategoriesEvent(syncedCategories));
            }
            mCategoryModel.saveLastSyncAt(response.body().getLastSyncTimestamp());
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
