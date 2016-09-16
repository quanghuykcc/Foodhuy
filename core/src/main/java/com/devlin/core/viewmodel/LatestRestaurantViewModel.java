package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;

import com.birbit.android.jobqueue.JobManager;
import com.devlin.core.event.FetchedRestaurantEvent;
import com.devlin.core.job.BasicJob;
import com.devlin.core.job.FetchRestaurantJob;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.Configuration;
import com.devlin.core.model.services.clouds.IFavoriteRestaurantService;
import com.devlin.core.model.services.clouds.IRestaurantService;
import com.devlin.core.model.services.storages.FavoriteRestaurantModel;
import com.devlin.core.model.services.storages.RestaurantModel;
import com.devlin.core.view.Constants;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;
import com.devlin.core.BR;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 8/2/2016.
 */
public class LatestRestaurantViewModel extends BaseViewModel {

    //region Properties

    private RestaurantModel mRestaurantModel;

    private ObservableArrayList<Restaurant> mRestaurants;

    private IRestaurantService mIRestaurantService;

    private JobManager mJobManager;

    private FavoriteRestaurantModel mFavoriteRestaurantModel;

    private IFavoriteRestaurantService mIFavoriteRestaurantService;

    //endregion

    //region Getter and Setter

    @Bindable
    public List<Restaurant> getRestaurants() {
        return mRestaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        mRestaurants = new ObservableArrayList<>();
        mRestaurants.addAll(restaurants);
        notifyPropertyChanged(BR.restaurants);
    }


    //endregion

    //region Constructors

    public LatestRestaurantViewModel(INavigator navigator, RestaurantModel restaurantModel, IRestaurantService restaurantService, JobManager jobManager, FavoriteRestaurantModel favoriteRestaurantModel, IFavoriteRestaurantService iFavoriteRestaurantService) {
        super(navigator);

        mRestaurantModel = restaurantModel;

        mJobManager = jobManager;

        mIRestaurantService = restaurantService;

        mFavoriteRestaurantModel = favoriteRestaurantModel;

        mIFavoriteRestaurantService = iFavoriteRestaurantService;
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        loadInitLatestRestaurants();
    }

    @Override
    public void onStart() {
        super.onStart();

        register();
    }

    @Override
    public void onStop() {
        super.onStop();

        unregister();
    }

    //endregion

    //region Private Methods

    private void loadInitLatestRestaurants() {
        mRestaurantModel.getLatestAsync(new ICallback<List<Restaurant>>() {
            @Override
            public void onResult(final List<Restaurant> result) {
                setRestaurants(result);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        mJobManager.addJobInBackground(new FetchRestaurantJob(BasicJob.UI_HIGH, mIRestaurantService, mRestaurantModel));
    }

    public void showRestaurantDetails(Restaurant restaurant) {
        getNavigator().navigateTo(Constants.RESTAURANT_DETAIL_PAGE);

        postSticky(restaurant);
    }

    public void handleCommentViewClick(Restaurant restaurant) {
        if (getNavigator().getApplication().isUserLoggedIn()) {
            getNavigator().navigateTo(Constants.COMMENT_PAGE);

            postSticky(restaurant);

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
                    }
                }
            }
            @Override
            public void onFailure(Call<APIResponse<List<Restaurant>>> call, Throwable t) {
            }
        });
    }

    //endregion

    //region Subscribe Methods

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(FetchedRestaurantEvent fetchedRestaurantEvent) {
        if (fetchedRestaurantEvent.isSuccess()) {
            setRestaurants(fetchedRestaurantEvent.getRestaurants());
        }
        else {

        }
    }

    //endregion
}
