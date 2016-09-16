package com.devlin.core.viewmodel;

import android.databinding.Bindable;

import com.devlin.core.BR;
import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.services.clouds.IFavoriteRestaurantService;
import com.devlin.core.model.services.storages.FavoriteRestaurantModel;
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

    private boolean mIsFavorite;

    private FavoriteRestaurantModel mFavoriteRestaurantModel;

    private IFavoriteRestaurantService mIFavoriteRestaurantService;

    //endregion

    //region Constructors

    public RestaurantViewModel(INavigator navigator, FavoriteRestaurantModel favoriteRestaurantModel, IFavoriteRestaurantService iFavoriteRestaurantService) {
        super(navigator);

        mFavoriteRestaurantModel = favoriteRestaurantModel;

        mIFavoriteRestaurantService = iFavoriteRestaurantService;
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

    @Bindable
    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean favorite) {
        mIsFavorite = favorite;

        notifyPropertyChanged(BR.favorite);
    }

    @Override
    public INavigator getNavigator() {
        return super.getNavigator();
    }

    //endregion

    //region Override Methods

    @Override
    public void onCreate() {
        super.onCreate();

        register();
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

        unregister();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void event(Restaurant restaurant) {
        setRestaurant(restaurant);

        if (getNavigator().getApplication().isUserLoggedIn()) {
            FavoriteRestaurant favoriteRestaurant = mFavoriteRestaurantModel.getByUserAndRestaurant(getNavigator().getApplication().getLoginUser().getId(), mRestaurant.getId());
            if (favoriteRestaurant != null && favoriteRestaurant.isLoaded() && favoriteRestaurant.isValid()) {
                setFavorite(true);
            }
        }

    }

    //endregion


}
