package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.event.FetchedCategoryEvent;
import com.devlin.core.event.FetchedRestaurantEvent;
import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.Configuration;
import com.devlin.core.model.services.clouds.IRestaurantService;
import com.devlin.core.model.services.storages.RestaurantModel;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 8/25/2016.
 */
public class FetchRestaurantJob extends BasicJob {

    //region Properties

    private static final String GROUP = "FetchRestaurantJob";

    private static final String TAG = "FetchRestaurantJob";

    private IRestaurantService mIRestaurantService;

    private RestaurantModel mRestaurantModel;

    //endregion

    //region Constructors

    public FetchRestaurantJob(@BasicJob.Priority int priority, IRestaurantService restaurantService, RestaurantModel restaurantModel) {
        super(new Params(priority).groupBy(GROUP).requireNetwork());

        mIRestaurantService = restaurantService;

        mRestaurantModel = restaurantModel;
    }

    //endregion

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Call<APIResponse<List<Restaurant>>> call;

        Date latestSynchronizeTimestamp = mRestaurantModel.getLatestSynchronizeTimestamp();

        if (latestSynchronizeTimestamp != null) {
            call = mIRestaurantService.getNewRestaurants(latestSynchronizeTimestamp);
        }
        else {
            call = mIRestaurantService.getRestaurants(0, Configuration.NUMBER_CACHE_RESTAURANTS);
        }

        Response<APIResponse<List<Restaurant>>> response = call.execute();
        if (response.isSuccessful()) {
            APIResponse<List<Restaurant>> apiResponse = response.body();

            if (apiResponse.isSuccess()) {
                if (apiResponse.getData().size() > 0) {
                    mRestaurantModel.handleFetchedRestaurants(apiResponse.getData(), apiResponse.getLastSyncTimestamp());

                    getEventBus().post(new FetchedRestaurantEvent(true));
                    return;
                }
                else {
                    Log.d(TAG, "There is nothing changed");
                }

            }
            else {
                getEventBus().post(new FetchedRestaurantEvent(false));
                return;
            }
        }
        else {
            getEventBus().post(new FetchedRestaurantEvent(false));
            return;
        }
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        getEventBus().post(new FetchedRestaurantEvent(false));
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
        return 5;
    }
}
