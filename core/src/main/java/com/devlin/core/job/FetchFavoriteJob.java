package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.clouds.IFavoriteRestaurantService;
import com.devlin.core.model.services.storages.FavoriteRestaurantModel;
import com.devlin.core.util.QueryDate;

import java.util.Date;
import java.util.List;

import retrofit2.Response;

/**
 * Created by quanghuymr403 on 19/09/2016.
 */
public class FetchFavoriteJob extends BasicJob {

    //region Properties

    private IFavoriteRestaurantService mFavoriteRestaurantService;

    private FavoriteRestaurantModel mFavoriteRestaurantModel;

    private User mUser;

    private static final String GROUP = "LogInJob";

    //endregion

    //region Constructors

    public FetchFavoriteJob(@BasicJob.Priority int priority, FavoriteRestaurantModel favoriteRestaurantModel, IFavoriteRestaurantService favoriteRestaurantService, User user) {
        super(new Params(priority).groupBy(GROUP).requireNetwork());

        mFavoriteRestaurantModel = favoriteRestaurantModel;

        mFavoriteRestaurantService = favoriteRestaurantService;

        mUser = user;
    }

    //endregion

    //region Override methods

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Date lastSyncedAt = mFavoriteRestaurantModel.getLastSyncedAt();

        if (lastSyncedAt != null) {
            Response<APIResponse<List<FavoriteRestaurant>>> response = mFavoriteRestaurantService
                    .getNew(mUser.getId(), new QueryDate(lastSyncedAt))
                    .execute();

            if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                for (FavoriteRestaurant favoriteRestaurant : response.body().getData()) {
                    if (favoriteRestaurant.isDeleted()) {
                        mFavoriteRestaurantModel.delete(favoriteRestaurant);
                    }
                    else {
                        mFavoriteRestaurantModel.addNewOrUpdate(favoriteRestaurant);
                    }
                }
            }
        } else {
            Response<APIResponse<List<FavoriteRestaurant>>> response = mFavoriteRestaurantService
                    .getAll(mUser.getId())
                    .execute();

            if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                mFavoriteRestaurantModel.addOrUpdate(response.body().getData());
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
