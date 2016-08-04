package com.devlin.core.viewmodel;

import android.databinding.Bindable;

import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.services.storages.RestaurantStorageService;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;
import com.devlin.core.BR;

import java.util.List;

/**
 * Created by Administrator on 8/2/2016.
 */
public class LatestRestaurantViewModel extends BaseViewModel {

    //region Properties

    private RestaurantStorageService mRestaurantStorageService;

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

    public LatestRestaurantViewModel(INavigator navigator, RestaurantStorageService storageService) {
        super(navigator);

        mRestaurantStorageService = storageService;
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadLatestRestaurants();
        getNavigator().showBusyIndicator("");
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

    private void loadLatestRestaurants() {
        mRestaurantStorageService.getLatestRestaurants(new ICallback<List<Restaurant>>() {
            @Override
            public void onResult(List<Restaurant> result) {
                setRestaurants(result);
                getNavigator().hideBusyIndicator();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    //endregion
}
