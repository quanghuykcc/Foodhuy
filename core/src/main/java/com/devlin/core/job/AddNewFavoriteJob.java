package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.event.AddedNewFavoriteEvent;
import com.devlin.core.event.ChangeFavoriteStatusEvent;
import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.clouds.IFavoriteRestaurantService;
import com.devlin.core.model.services.storages.FavoriteRestaurantModel;

import retrofit2.Response;

/**
 * Created by Administrator on 9/6/2016.
 */
public class AddNewFavoriteJob extends BasicJob {

    //region Properties

    private FavoriteRestaurantModel mFavoriteRestaurantModel;

    private IFavoriteRestaurantService mIFavoriteRestaurantService;

    private FavoriteRestaurant mFavoriteRestaurant;

    private static final String GROUP = "AddNewFavoriteJob";

    //endregion

    //region Constructors

    public AddNewFavoriteJob(@BasicJob.Priority int priority, FavoriteRestaurantModel favoriteRestaurantModel, IFavoriteRestaurantService iFavoriteRestaurantService, FavoriteRestaurant favoriteRestaurant) {
        super(new Params(priority).groupBy(GROUP).requireNetwork());

        mFavoriteRestaurantModel = favoriteRestaurantModel;

        mIFavoriteRestaurantService = iFavoriteRestaurantService;

        mFavoriteRestaurant = favoriteRestaurant;
    }


    //endregion

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        mFavoriteRestaurantModel.add(mFavoriteRestaurant);

        Response<APIResponse<FavoriteRestaurant>> response = mIFavoriteRestaurantService.add(mFavoriteRestaurant)
                                                                                        .execute();

        if (response.isSuccessful() && response.body() != null &&  response.body().isSuccess()) {
            mFavoriteRestaurantModel.update(response.body().getData());
            return;
        } else {
            mFavoriteRestaurantModel.delete(mFavoriteRestaurant);
            post(new ChangeFavoriteStatusEvent());
        }
    }


    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        mFavoriteRestaurantModel.delete(mFavoriteRestaurant);
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
