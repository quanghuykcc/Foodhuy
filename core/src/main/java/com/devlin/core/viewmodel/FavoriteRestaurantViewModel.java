package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.devlin.core.BR;
import com.devlin.core.R;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.services.storages.RestaurantStorageService;
import com.devlin.core.model.services.storages.UserStorageService;
import com.devlin.core.view.BaseRecyclerViewAdapter;
import com.devlin.core.view.Constants;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 05-Aug-16.
 */
public class FavoriteRestaurantViewModel extends BaseViewModel {

    //region Properties

    private RestaurantStorageService mRestaurantStorageService;

    private UserStorageService mUserStorageService;

    private List<Restaurant> mRestaurants;

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

    public FavoriteRestaurantViewModel(INavigator navigator, RestaurantStorageService restaurantStorageService, UserStorageService userStorageService) {
        super(navigator);

        mRestaurantStorageService = restaurantStorageService;

        mUserStorageService = userStorageService;
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();
        loadFavoriteRestaurants();
        getNavigator().showBusyIndicator("");
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

        mUserStorageService.loadFavoriteRestaurants(user, new ICallback<List<Restaurant>>() {
            @Override
            public void onResult(List<Restaurant> result) {
                setRestaurants(result);
                getNavigator().hideBusyIndicator();
            }

            @Override
            public void onFailure(Throwable t) {
                getNavigator().hideBusyIndicator();
            }
        });
    }

    public void removeFavoriteRestaurant(final Restaurant restaurant) {
        Log.d("TAG", "REMOVE FAVORITE RESTAURANT");

        mUserStorageService.removeFavoriteRestaurant(getNavigator().getApplication().getLoginUser(), restaurant, new ICallback<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                getEventBus().post(restaurant);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("TAG", "", t);
            }
        });
    }

    //endregion

    //region Override Methods

    @Override
    public EventBus getEventBus() {
        return super.getEventBus();
    }

    @Override
    public INavigator getNavigator() {
        return super.getNavigator();
    }

    //endregion

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
}
