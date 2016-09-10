package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.event.AddedNewFavoriteEvent;
import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.clouds.IFavoriteRestaurantService;
import com.devlin.core.model.services.storages.FavoriteRestaurantModel;

import io.realm.FavoriteRestaurantRealmProxy;
import io.realm.Realm;
import retrofit2.Call;
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

    private static final String TAG = "AddNewFavoriteJob";

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
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        mFavoriteRestaurantModel.addNewFavoriteRestaurant(mFavoriteRestaurant);
        realm.commitTransaction();

        Call<APIResponse<FavoriteRestaurant>> call = mIFavoriteRestaurantService.addNewFavoriteRestaurant(mFavoriteRestaurant);
        Response<APIResponse<FavoriteRestaurant>> response = call.execute();

        if (!response.isSuccessful() || !response.body().isSuccess()) {
            getEventBus().post(new AddedNewFavoriteEvent(false));
            deleteFavorite();
            return;
        }

        Log.d(TAG, response.body().getData().toString());


    }

    private void deleteFavorite() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        mFavoriteRestaurantModel.deleteFavoriteRestaurant(mFavoriteRestaurant);
        realm.commitTransaction();
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        deleteFavorite();
        getEventBus().post(new AddedNewFavoriteEvent(false));
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
