package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.util.Log;

import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.services.storages.RestaurantStorageService;
import com.devlin.core.view.Constants;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;
import com.devlin.core.view.Navigator;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Administrator on 7/27/2016.
 */
public class AddRestaurantViewModel extends BaseViewModel {

    //region Properties

    private RestaurantStorageService mRestaurantStorageService;

    private static final String TAG = "AddRestaurantViewModel";

    private Restaurant mRestaurant;

    //endregion

    //region Constructors

    public AddRestaurantViewModel(INavigator navigator, RestaurantStorageService storageService) {
        super(navigator);

        mRestaurantStorageService = storageService;

        mRestaurant = new Restaurant();
    }

    //endregion

    //region Getter and Setter

    @Bindable
    public Restaurant getRestaurant() {
        return mRestaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        mRestaurant = restaurant;
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

    //region Public methods

    public void saveRestaurant(Restaurant restaurant) {
        Log.d(TAG, "Save Restaurant");
        if (restaurant == null) {
            return;
        }

        mRestaurantStorageService.saveRestaurant(restaurant, new ICallback<Boolean>() {

            @Override
            public void onResult(Boolean result) {
                if (result.booleanValue() == true) {

                    getNavigator().navigateTo(Constants.MAIN_PAGE);

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    //endregion
}
