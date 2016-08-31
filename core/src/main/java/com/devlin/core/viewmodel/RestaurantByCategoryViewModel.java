package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.devlin.core.BR;
import com.devlin.core.R;
import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.services.Configuration;
import com.devlin.core.model.services.clouds.RestaurantCloudService;
import com.devlin.core.model.services.storages.RestaurantModel;
import com.devlin.core.model.services.storages.UserModel;
import com.devlin.core.view.Constants;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Administrator on 7/27/2016.
 */
public class RestaurantByCategoryViewModel extends BaseViewModel {

    //region Properties

    private static final String TAG = "RestaurantByCategoryViewModel";

    private List<Restaurant> mRestaurants;

    private Category mCategory;

    private RestaurantModel mRestaurantStorageService;

    private UserModel mUserStorageService;

    private RestaurantCloudService mRestaurantCloudService;

    //endregion

    //region Getter and Setter

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    @Bindable
    public List<Restaurant> getRestaurants() {
        return mRestaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        mRestaurants = restaurants;

        notifyPropertyChanged(BR.restaurants);
    }

    @Override
    public EventBus getEventBus() {
        return super.getEventBus();
    }

    //endregion

    //region Constructors

    public RestaurantByCategoryViewModel(INavigator navigator, RestaurantModel restaurantStorageService, UserModel userStorageService, RestaurantCloudService restaurantCloudService) {
        super(navigator);

        mRestaurantStorageService = restaurantStorageService;

        mUserStorageService = userStorageService;

        mRestaurantCloudService = restaurantCloudService;
    }

    public RestaurantByCategoryViewModel() {
        super();
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        getNavigator().showBusyIndicator("Đang tải...");
    }

    @Override
    public void onStart() {
        super.onStart();

        getEventBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        getEventBus().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mCategory = null;
        mRestaurants = null;
    }

    //endregion

    //region Subscribe Methods

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void event(Category category) {
        getNavigator().getApplication().getCurrentActivity().setTitle(category.getName());

        setCategory(category);

        mRestaurantCloudService.getRestaurantsByCategory(category, 0, 20, new ICallback<List<Restaurant>>() {
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

        /*mRestaurantStorageService.getRestaurantsByCategory(category, new ICallback<List<Restaurant>>() {
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

        getEventBus().unregister(this);*/
    }

    //endregion

    //region Private Methods

    private void addFavoriteRestaurant(final View view, Restaurant restaurant) {
        Log.d("TAG", "ADD FAVORITE RESTAURANT");

        mUserStorageService.addFavoriteRestaurant(getNavigator().getApplication().getLoginUser(), restaurant, new ICallback<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                if (result == true) {
                    Log.d("TAG", "ADD FAVORITE RESTAURANT SUCCESS");
                    view.setBackgroundColor(ContextCompat.getColor(getNavigator().getApplication().getCurrentActivity(), R.color.colorPrimary));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("TAG", "", t);
            }
        });
    }

    private void removeFavoriteRestaurant(final View view, Restaurant restaurant) {
        Log.d("TAG", "REMOVE FAVORITE RESTAURANT");

        mUserStorageService.removeFavoriteRestaurant(getNavigator().getApplication().getLoginUser(), restaurant, new ICallback<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                Log.d("TAG", "REMOVE FAVORITE RESTAURANT SUCCESS");
                view.setBackgroundColor(ContextCompat.getColor(getNavigator().getApplication().getCurrentActivity(), R.color.colorWhite));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("TAG", "", t);
            }
        });
    }

    public boolean isFavoriteRestaurant(Restaurant restaurant) {
        if (getNavigator().getApplication().isUserLoggedIn()) {
            if (getNavigator().getApplication().getLoginUser().getFavoriteRestaurant().contains(restaurant)) {
                return true;
            }
        }

        return false;
    }

    //endregion

    //region Public Methods

    public void handleFavoriteRestaurantViewClick(View view, Restaurant restaurant) {

        if (!getNavigator().getApplication().isUserLoggedIn()) {
            getNavigator().navigateTo(Constants.LOGIN_PAGE);
            return;
        }

        if (!isFavoriteRestaurant(restaurant)) {
            addFavoriteRestaurant(view, restaurant);
            return;
        }

        removeFavoriteRestaurant(view, restaurant);
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

        mRestaurantCloudService.getRestaurantsByCategory(mCategory, nextOffset, Configuration.NUMBER_RECORDS_PER_PAGE, new ICallback<List<Restaurant>>() {
            @Override
            public void onResult(List<Restaurant> result) {
                mRestaurants.addAll(result);

                notifyPropertyChanged(BR.restaurants);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    //endregion
}
