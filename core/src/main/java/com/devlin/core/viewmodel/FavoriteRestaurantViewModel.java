package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.util.Log;

import com.devlin.core.BR;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.Configuration;
import com.devlin.core.model.services.clouds.IRestaurantService;
import com.devlin.core.model.services.storages.RestaurantModel;
import com.devlin.core.model.services.storages.UserModel;
import com.devlin.core.view.Constants;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 05-Aug-16.
 */
public class FavoriteRestaurantViewModel extends BaseViewModel {

    //region Properties

    private IRestaurantService mIRestaurantService;

    private ObservableArrayList<Restaurant> mRestaurants;

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

    public FavoriteRestaurantViewModel(INavigator navigator, IRestaurantService restaurantService) {
        super(navigator);

        mIRestaurantService = restaurantService;
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();
        loadFavoriteRestaurants();
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
        super.onDestroy();
    }

    //endregion

    //region Private Methods

    private void loadFavoriteRestaurants() {
        User user = getNavigator().getApplication().getLoginUser();

        mIRestaurantService.getFavoriteRestaurants(getNavigator().getApplication().getLoginUser().getId(), 0, Configuration.NUMBER_RECORDS_PER_PAGE)
                .enqueue(new Callback<APIResponse<List<Restaurant>>>() {
                    @Override
                    public void onResponse(Call<APIResponse<List<Restaurant>>> call, Response<APIResponse<List<Restaurant>>> response) {
                        if (response.isSuccessful() && response.body().isSuccess()) {
                            setRestaurants(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse<List<Restaurant>>> call, Throwable t) {

                    }
                });
    }

    //endregion

    //region Override Methods

    @Override
    public INavigator getNavigator() {
        return super.getNavigator();
    }

    //endregion

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

        mIRestaurantService.getFavoriteRestaurants(getNavigator().getApplication().getLoginUser().getId(), nextOffset, Configuration.NUMBER_RECORDS_PER_PAGE).enqueue(new Callback<APIResponse<List<Restaurant>>>() {
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
}
