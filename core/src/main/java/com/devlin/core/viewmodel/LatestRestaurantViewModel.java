package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.birbit.android.jobqueue.JobManager;
import com.devlin.core.R;
import com.devlin.core.event.FetchedRestaurantEvent;
import com.devlin.core.job.BasicJob;
import com.devlin.core.job.FetchRestaurantJob;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.Configuration;
import com.devlin.core.model.services.clouds.IRestaurantService;
import com.devlin.core.model.services.clouds.RestaurantCloudService;
import com.devlin.core.model.services.storages.RestaurantModel;
import com.devlin.core.model.services.storages.UserModel;
import com.devlin.core.view.Constants;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;
import com.devlin.core.BR;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 8/2/2016.
 */
public class LatestRestaurantViewModel extends BaseViewModel {

    //region Properties

    private RestaurantModel mRestaurantModel;

    private List<Restaurant> mRestaurants;

    private IRestaurantService mIRestaurantService;

    private JobManager mJobManager;

    //endregion

    //region Getter and Setter

    @Bindable
    public List<Restaurant> getRestaurants() {
        return mRestaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        mRestaurants = restaurants;

        notifyPropertyChanged(BR.restaurants);
    }

    //endregion

    //region Constructors

    public LatestRestaurantViewModel(INavigator navigator, RestaurantModel restaurantModel, IRestaurantService restaurantService, JobManager jobManager) {
        super(navigator);

        mRestaurantModel = restaurantModel;

        mJobManager = jobManager;

        mIRestaurantService = restaurantService;
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        getNavigator().showBusyIndicator("");

        getEventBus().register(this);

        loadInitLatestRestaurants();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        getEventBus().unregister(this);

        super.onDestroy();
    }


    //endregion

    //region Private Methods

    private void loadInitLatestRestaurants() {
        RealmResults<Restaurant> latestRestaurants = mRestaurantModel.getLatestRestaurants();
        setRestaurants(latestRestaurants);
        latestRestaurants.addChangeListener(new RealmChangeListener<RealmResults<Restaurant>>() {
            @Override
            public void onChange(RealmResults<Restaurant> element) {
                setRestaurants(element);
            }
        });

        mJobManager.addJobInBackground(new FetchRestaurantJob(BasicJob.UI_HIGH, mIRestaurantService, mRestaurantModel));
    }

    public void showRestaurantDetails(Restaurant restaurant) {
        getNavigator().navigateTo(Constants.RESTAURANT_DETAIL_PAGE);

        getEventBus().postSticky(restaurant);
    }

    public void handleCommentViewClick(Restaurant restaurant) {
        if (getNavigator().getApplication().isUserLoggedIn()) {
            getNavigator().navigateTo(Constants.COMMENT_PAGE);

            getEventBus().postSticky(restaurant);

            return;
        }
        else {
            getNavigator().navigateTo(Constants.LOGIN_PAGE);
        }

    }


    public void getNextPageRestaurants(long currentOffset) {
        long nextOffset = currentOffset + 1;

        mIRestaurantService.getRestaurants(nextOffset, Configuration.NUMBER_RECORDS_PER_PAGE).enqueue(new Callback<APIResponse<List<Restaurant>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Restaurant>>> call, Response<APIResponse<List<Restaurant>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        mRestaurants.addAll(response.body().getData());
                        notifyPropertyChanged(BR.restaurants);
                    }
                }
            }
            @Override
            public void onFailure(Call<APIResponse<List<Restaurant>>> call, Throwable t) {
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(FetchedRestaurantEvent fetchedRestaurantEvent) {
        if (fetchedRestaurantEvent.isSuccess()) {
            setRestaurants(fetchedRestaurantEvent.getRestaurants());
        }
    }

    //endregion
}
