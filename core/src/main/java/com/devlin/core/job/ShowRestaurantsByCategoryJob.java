package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.event.AddMoreRestaurantsEvent;
import com.devlin.core.event.ReplaceRestaurantsEvent;
import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.Configuration;
import com.devlin.core.model.services.clouds.IRestaurantService;
import com.devlin.core.model.services.storages.RestaurantModel;

import java.util.List;

import retrofit2.Response;

/**
 * Created by quanghuymr403 on 18/09/2016.
 */
public class ShowRestaurantsByCategoryJob extends BasicJob{

    //region Properties

    private static final String GROUP = "ShowRestaurantsByCategoryJob";

    private IRestaurantService mRestaurantService;

    private RestaurantModel mRestaurantModel;

    private Category mCategory;

    private int mOffset;

    //endregion

    //region Constructors

    public ShowRestaurantsByCategoryJob(@BasicJob.Priority int priority, int offset, Category category, IRestaurantService restaurantService, RestaurantModel restaurantModel) {
        super(new Params(priority).groupBy(GROUP).requireNetwork());

        mRestaurantService = restaurantService;

        mRestaurantModel = restaurantModel;

        mOffset = offset;

        mCategory = category;
    }

    //endregion

    //region Override methods

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        if (mOffset == 0) {
            List<Restaurant> localRestaurants = mRestaurantModel.getByCategory(mCategory);
            if (localRestaurants != null && localRestaurants.size() > 0) {
                post(new ReplaceRestaurantsEvent(localRestaurants));
            }
        }

        Response<APIResponse<List<Restaurant>>> response = mRestaurantService
                .getByCategory(mCategory.getId(), mOffset, Configuration.NUMBER_RECORDS_PER_PAGE)
                .execute();

        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
            List<Restaurant> fetchedRestaurants = response.body().getData();
            if (fetchedRestaurants != null && fetchedRestaurants.size() > 0) {
                if (mOffset == 0) {
                    post(new ReplaceRestaurantsEvent(fetchedRestaurants));
                } else {
                    post(new AddMoreRestaurantsEvent(fetchedRestaurants));
                }
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
