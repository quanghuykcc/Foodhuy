package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.util.Log;
import android.widget.Toast;

import com.devlin.core.BR;
import com.devlin.core.event.FetchedFavoriteRestaurantEvent;
import com.devlin.core.event.LoggedInEvent;
import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.services.storages.FavoriteRestaurantModel;
import com.devlin.core.model.services.storages.UserModel;
import com.devlin.core.util.Utils;
import com.devlin.core.view.Constants;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Administrator on 7/26/2016.
 */
public class MainViewModel extends BaseViewModel {

    //region Properties

    private static final String TAG = "MainViewModel";

    private User mUser;

    private FavoriteRestaurantModel mFavoriteRestaurantModel;

    private UserModel mUserModel;

    //endregion

    //region Constructors

    public MainViewModel(INavigator navigator, FavoriteRestaurantModel favoriteRestaurantModel, UserModel userModel) {
        super(navigator);

        mFavoriteRestaurantModel = favoriteRestaurantModel;

        mUserModel = userModel;
    }

    //endregion

    //region Getter and Setter

    @Override
    public INavigator getNavigator() {
        return super.getNavigator();
    }

    @Override
    public EventBus getEventBus() {
        return super.getEventBus();
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;

        notifyPropertyChanged(BR.user);
    }

    //endregion

    //region Lifecycle

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

    //endregion

    //region Subcribes Methods

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(LoggedInEvent loggedInEvent) {
        if (loggedInEvent.isSuccess()) {
            final User loggedInUser = loggedInEvent.getLoggedInUser();
            setUser(loggedInUser);
            mFavoriteRestaurantModel.getFavoriteRestaurantsOfUserAsync(loggedInUser, new ICallback<List<FavoriteRestaurant>>() {
                @Override
                public void onResult(List<FavoriteRestaurant> result) {
                    getNavigator().getApplication().setFavoriteRestaurantsOfUser(result);
                    for (FavoriteRestaurant favoriteRestaurant : result) {
                        Log.d(TAG, favoriteRestaurant.toString());
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 5)
    public void event(FetchedFavoriteRestaurantEvent fetchedFavoriteRestaurantEvent) {
        if (fetchedFavoriteRestaurantEvent.isSuccess()) {
            getNavigator().getApplication().setFavoriteRestaurantsOfUser(fetchedFavoriteRestaurantEvent.getFavoriteRestaurants());
        }
    }

    public void logOut() {
        if (!getNavigator().getApplication().isUserLoggedIn()) {
            Toast.makeText(getNavigator().getApplication().getCurrentActivity(), "Bạn vẫn chưa đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        getNavigator().getApplication().setLoginUser(null);
        setUser(null);
        mFavoriteRestaurantModel.clearFavoriteRestaurantsAsync();
        mFavoriteRestaurantModel.clearLatestSynchronizeTimestamp();
        mUserModel.clearLocalUsers();

        getEventBus().post(Constants.ACTION_LOGGED_OUT);

        getNavigator().navigateTo(Constants.LOGIN_PAGE);

    }

    //endregion





}
