package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.widget.Toast;

import com.devlin.core.BR;
import com.devlin.core.event.LoggedInEvent;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.services.storages.FavoriteRestaurantModel;
import com.devlin.core.model.services.storages.UserModel;
import com.devlin.core.view.Constants;
import com.devlin.core.view.INavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    //endregion

    //region Subcribes Methods

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(LoggedInEvent loggedInEvent) {
        if (loggedInEvent.isSuccess()) {
            final User loggedInUser = loggedInEvent.getLoggedInUser();
            setUser(loggedInUser);
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

        post(Constants.ACTION_LOGGED_OUT);

        getNavigator().navigateTo(Constants.LOGIN_PAGE);

    }

    //endregion





}
