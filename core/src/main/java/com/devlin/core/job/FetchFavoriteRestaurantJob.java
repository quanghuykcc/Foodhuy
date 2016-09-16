package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.clouds.IFavoriteRestaurantService;
import com.devlin.core.model.services.storages.FavoriteRestaurantModel;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 9/5/2016.
 */
public class FetchFavoriteRestaurantJob extends BasicJob {

    //region Properties

    private static final String GROUP = "FetchFavoriteRestaurantJob";

    private static final String TAG = "FetchFavoriteJob";

    private FavoriteRestaurantModel mFavoriteRestaurantModel;

    private IFavoriteRestaurantService mIFavoriteRestaurantService;

    private User mLoggedInUser;

    //endregion

    //region Constructors;

    public FetchFavoriteRestaurantJob(@BasicJob.Priority int priority, FavoriteRestaurantModel favoriteRestaurantModel, IFavoriteRestaurantService iFavoriteRestaurantService, User loggedInUser) {
        super(new Params(priority).groupBy(GROUP).requireNetwork());

        mLoggedInUser = loggedInUser;

        mFavoriteRestaurantModel = favoriteRestaurantModel;

        mIFavoriteRestaurantService = iFavoriteRestaurantService;
    }

    //endregion

    //region Override Methods

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Call<APIResponse<List<FavoriteRestaurant>>> callFavoriteRestaurants;

        Date latestSynchronizeTimestamp = mFavoriteRestaurantModel.getLatestSynchronizeTimestamp();

        if(latestSynchronizeTimestamp != null) {
            callFavoriteRestaurants = mIFavoriteRestaurantService.getNewFavoriteRestaurants(mLoggedInUser.getId(), latestSynchronizeTimestamp);
        }
        else {
            callFavoriteRestaurants = mIFavoriteRestaurantService.getFavoriteRestaurants(mLoggedInUser.getId());
        }
        Response<APIResponse<List<FavoriteRestaurant>>> response = callFavoriteRestaurants.execute();
        if (response.isSuccessful()) {
            APIResponse<List<FavoriteRestaurant>> apiResponse = response.body();

            if (apiResponse.isSuccess()) {
                if (apiResponse.getData().size() > 0) {
                    mFavoriteRestaurantModel.cacheFavoriteRestaurants(apiResponse.getData());

                    mFavoriteRestaurantModel.updateLatestSynchronizeTimestamp(apiResponse.getLastSyncTimestamp());
                    return;
                }
                else {
                    Log.d(TAG, "There is nothing changed");
                    return;
                }

            }
            else {
                Log.d(TAG, apiResponse.getMessage());
                return;
            }
        }
        else {
            Log.d(TAG, "Error fetched favorites: " + response.raw().message());
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
        return 5;
    }

    //endregion

}
