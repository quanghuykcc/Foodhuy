package com.devlin.core.viewmodel;

import android.databinding.Bindable;

import com.devlin.core.BR;
import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.services.storages.RestaurantStorageService;
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

    private RestaurantStorageService mRestaurantStorageService;

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

    @Override
    public EventBus getEventBus() {
        return super.getEventBus();
    }

    //endregion

    //region Constructors

    public RestaurantByCategoryViewModel(INavigator navigator, RestaurantStorageService storageService) {
        super(navigator);

        mRestaurantStorageService = storageService;
    }

    public RestaurantByCategoryViewModel() {
        super();
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        getNavigator().showBusyIndicator("Loading");
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

        mRestaurants = null;
    }

    //endregion

    //region Private methods

    //endregion

    //region Subscribe Methods

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void event(Category category) {
        getNavigator().getApplication().getCurrentActivity().setTitle(category.getName());

        mRestaurantStorageService.getRestaurantsByCategory(category, new ICallback<List<Restaurant>>() {
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
