package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.event.ChangeFavoriteStatusEvent;
import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.clouds.IFavoriteRestaurantService;
import com.devlin.core.model.services.storages.FavoriteRestaurantModel;

import retrofit2.Response;

/**
 * Created by Administrator on 9/9/2016.
 */
public class DeleteFavoriteJob extends BasicJob {

    private static final String GROUP = "DeleteFavoriteJob";
    private FavoriteRestaurantModel mFavoriteRestaurantModel;
    private IFavoriteRestaurantService mFavoriteRestaurantService;
    private FavoriteRestaurant mFavoriteRestaurant;

    public DeleteFavoriteJob(@BasicJob.Priority int priority, FavoriteRestaurantModel favoriteRestaurantModel, IFavoriteRestaurantService favoriteRestaurantService, FavoriteRestaurant favoriteRestaurant) {
        super(new Params(priority).requireNetwork().groupBy(GROUP));
        mFavoriteRestaurant = favoriteRestaurant;
        mFavoriteRestaurantModel = favoriteRestaurantModel;
        mFavoriteRestaurantService = favoriteRestaurantService;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        mFavoriteRestaurant = mFavoriteRestaurantModel.find(mFavoriteRestaurant.getUserId(), mFavoriteRestaurant.getRestaurantId());

        mFavoriteRestaurantModel.delete(mFavoriteRestaurant);

        Response<APIResponse<Integer>> response = mFavoriteRestaurantService.delete(mFavoriteRestaurant.getId())
                .execute();

        if (response.isSuccessful() && response.body() != null &&  response.body().isSuccess()) {
            return;
        } else {
            mFavoriteRestaurantModel.add(mFavoriteRestaurant);
            post(new ChangeFavoriteStatusEvent());
        }
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        mFavoriteRestaurantModel.add(mFavoriteRestaurant);
        post(new ChangeFavoriteStatusEvent());
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
}
