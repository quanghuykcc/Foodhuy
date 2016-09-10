package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.event.DeletedFavoriteEvent;
import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.services.clouds.IFavoriteRestaurantService;
import com.devlin.core.model.services.storages.FavoriteRestaurantModel;

import io.realm.Realm;

/**
 * Created by Administrator on 9/9/2016.
 */
public class DeleteFavoriteJob extends BasicJob {

    private static final String GROUP = "DeleteFavoriteJob";
    private FavoriteRestaurantModel mFavoriteRestaurantModel;
    private IFavoriteRestaurantService mIFavoriteRestaurantService;
    private FavoriteRestaurant mFavoriteRestaurant;

    public DeleteFavoriteJob(@BasicJob.Priority int priority, FavoriteRestaurantModel favoriteRestaurantModel, IFavoriteRestaurantService iFavoriteRestaurantService, FavoriteRestaurant favoriteRestaurant) {
        super(new Params(priority).requireNetwork().groupBy(GROUP));
        mFavoriteRestaurant = favoriteRestaurant;
        mFavoriteRestaurantModel = favoriteRestaurantModel;
        mIFavoriteRestaurantService = iFavoriteRestaurantService;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        addNewFavorite();
        getEventBus().post(new DeletedFavoriteEvent(false));
    }

    private void addNewFavorite() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        mFavoriteRestaurantModel.addNewFavoriteRestaurant(mFavoriteRestaurant);
        realm.commitTransaction();
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
