package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.event.AddMoreRestaurantsEvent;
import com.devlin.core.event.ReplaceRestaurantsEvent;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.Configuration;
import com.devlin.core.model.services.clouds.IRestaurantService;
import com.devlin.core.model.services.storages.RestaurantModel;
import com.devlin.core.util.QueryDate;

import java.util.Date;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Administrator on 8/25/2016.
 */
public class FetchRestaurantJob extends BasicJob {

    //region Properties

    private static final String GROUP = "FetchRestaurantJob";

    private IRestaurantService mIRestaurantService;

    private RestaurantModel mRestaurantModel;

    private int mOffset;
    //endregion

    //region Constructors

    public FetchRestaurantJob(@BasicJob.Priority int priority, int offset, IRestaurantService restaurantService, RestaurantModel restaurantModel) {
        super(new Params(priority).groupBy(GROUP).requireNetwork());

        mIRestaurantService = restaurantService;

        mRestaurantModel = restaurantModel;

        mOffset = offset;
    }

    //endregion

    //region Override methods

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        if (isFirstSync()) {
            List<Restaurant> localResults = mRestaurantModel.getLatest();
            if (localResults != null) {
                post(new ReplaceRestaurantsEvent(localResults));
            }

            Date lastSyncedAt = mRestaurantModel.getLatestSynchronize();
            List<Restaurant> fetchedRestaurants;
            if (lastSyncedAt != null) {
                fetchedRestaurants = getSyncData(lastSyncedAt);

            } else {
                fetchedRestaurants = getMoreData(mOffset + 1);
            }

            if (fetchedRestaurants != null && fetchedRestaurants.size() > 0) {
                syncRestaurants(fetchedRestaurants);
                mRestaurantModel.saveLatestSynchronize(lastSyncedAt);
                List<Restaurant> newRestaurants = mRestaurantModel.getLatest();
                post(new ReplaceRestaurantsEvent(newRestaurants));
            }
        }
        else {
            List<Restaurant> moreRestaurants = getMoreData(mOffset);
            if (moreRestaurants != null && moreRestaurants.size() > 0) {
                post(new AddMoreRestaurantsEvent(moreRestaurants));
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

    //region Private methods

    private boolean isFirstSync() {
        return mOffset == -1;
    }

    private List<Restaurant> getMoreData(int offset) throws Throwable {
        Response<APIResponse<List<Restaurant>>> response = mIRestaurantService
                .getRestaurants(offset, Configuration.NUMBER_RECORDS_PER_PAGE).execute();

        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
            return response.body().getData();
        }
        return null;
    }

    private List<Restaurant> getSyncData(Date lastSyncedAt) throws Throwable {
        Response<APIResponse<List<Restaurant>>> response = mIRestaurantService
                .getNewRestaurants(new QueryDate(lastSyncedAt)).execute();

        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {

            List<Restaurant> syncRestaurants = response.body().getData();
            return syncRestaurants;
        }
        return  null;
    }

    private void syncRestaurants(List<Restaurant> restaurants) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.isDeleted()) {
                mRestaurantModel.delete(restaurant);
            } else {
                mRestaurantModel.addNewOrUpdate(restaurant);
            }
        }
        mRestaurantModel.optimizeCached();
    }

    //endregion

}
