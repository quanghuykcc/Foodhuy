package com.devlin.core.viewmodel;

import android.databinding.Bindable;

import com.devlin.core.BR;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.services.storages.RestaurantModel;
import com.devlin.core.model.services.storages.UserModel;
import com.devlin.core.view.INavigator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 07-Aug-16.
 */
public class RestaurantViewModel extends BaseViewModel {

    //region Properties

    private Restaurant mRestaurant;

    private UserModel mUserStorageService;


    private RestaurantModel mRestaurantStorageService;
    //endregion

    //region Constructors

    public RestaurantViewModel(INavigator navigator, RestaurantModel restaurantStorageService, UserModel userStorageService) {
        super(navigator);

        mUserStorageService = userStorageService;

        mRestaurantStorageService = restaurantStorageService;
    }

    //endregion

    //endregion Getter and Setter

    @Bindable
    public Restaurant getRestaurant() {
        return mRestaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        mRestaurant = restaurant;

        notifyPropertyChanged(BR.restaurant);
    }


    //endregion

    //region Override Methods

    @Override
    public void onCreate() {
        super.onCreate();

        getEventBus().register(this);
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

        getEventBus().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void event(Restaurant restaurant) {
        setRestaurant(restaurant);

        getNavigator().getApplication().getCurrentActivity().setTitle(restaurant.getName());

        getEventBus().unregister(this);
    }

    //endregion


}
