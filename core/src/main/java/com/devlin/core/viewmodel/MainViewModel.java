package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.widget.Toast;

import com.birbit.android.jobqueue.JobManager;
import com.devlin.core.BR;
import com.devlin.core.event.LoggedInEvent;
import com.devlin.core.event.LoggedOutEvent;
import com.devlin.core.job.BasicJob;
import com.devlin.core.job.FetchFavoriteJob;
import com.devlin.core.job.LogInJob;
import com.devlin.core.job.LogOutJob;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.services.clouds.IFavoriteRestaurantService;
import com.devlin.core.model.services.clouds.IUserService;
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

    private JobManager mJobManager;

    private IUserService mUserService;

    private IFavoriteRestaurantService mFavoriteRestaurantService;

    //endregion

    //region Constructors

    public MainViewModel(INavigator navigator, FavoriteRestaurantModel favoriteRestaurantModel, UserModel userModel, IUserService userService, IFavoriteRestaurantService favoriteRestaurantService, JobManager jobManager) {
        super(navigator);

        mFavoriteRestaurantModel = favoriteRestaurantModel;

        mUserModel = userModel;

        mJobManager = jobManager;

        mUserService = userService;

        mFavoriteRestaurantService = favoriteRestaurantService;
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

        logInIfRemember();
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

            mJobManager.addJobInBackground(new FetchFavoriteJob(BasicJob.UI_HIGH, mFavoriteRestaurantModel, mFavoriteRestaurantService, loggedInEvent.getLoggedInUser()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(LoggedOutEvent loggedOutEvent) {
        if (loggedOutEvent.isSuccess()) {
            setUser(null);
            getNavigator().getApplication().setLoginUser(null);
            getNavigator().navigateTo(Constants.LOGIN_PAGE);
            showToast(loggedOutEvent.getMessage(), Toast.LENGTH_LONG);

        }
        else {
            showToast(loggedOutEvent.getMessage(), Toast.LENGTH_LONG);
        }
    }

    //endregion

    //region Public methods

    public void logOut() {
        if (!getNavigator().getApplication().isUserLoggedIn()) {
            Toast.makeText(getNavigator().getApplication().getCurrentActivity(), "Bạn vẫn chưa đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        mJobManager.addJobInBackground(new LogOutJob(BasicJob.UI_HIGH, mFavoriteRestaurantModel, mUserModel, getNavigator().getApplication().getLoginUser()));
    }

    //endregion

    //region Private methods

    private void logInIfRemember() {
        User rememberedUser = mUserModel.getRemembered();
        if (rememberedUser != null) {
            mJobManager.addJobInBackground(new LogInJob(BasicJob.UI_HIGH, mUserService, mUserModel, rememberedUser));
        }
    }

    //endregion





}
