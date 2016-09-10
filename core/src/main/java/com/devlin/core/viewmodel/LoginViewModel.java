package com.devlin.core.viewmodel;

import android.databinding.Bindable;

import com.birbit.android.jobqueue.JobManager;
import com.devlin.core.BR;
import com.devlin.core.event.LoggedInEvent;
import com.devlin.core.job.BasicJob;
import com.devlin.core.job.FetchFavoriteRestaurantJob;
import com.devlin.core.job.LogInJob;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.services.clouds.IFavoriteRestaurantService;
import com.devlin.core.model.services.clouds.IUserService;
import com.devlin.core.model.services.storages.FavoriteRestaurantModel;
import com.devlin.core.model.services.storages.UserModel;
import com.devlin.core.view.Constants;
import com.devlin.core.view.INavigator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 8/1/2016.
 */
public class LoginViewModel extends BaseViewModel {

    //region Properties

    private static final String TAG = "LoginViewModel";

    private UserModel mUserModel;

    private IUserService mIUserService;

    private JobManager mJobManager;

    private FavoriteRestaurantModel mFavoriteRestaurantModel;

    private IFavoriteRestaurantService mIFavoriteRestaurantService;

    private User mUser;

    private String mError;

    //endregion

    //region Getter and Setter

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    @Bindable
    public String getError() {
        return mError;
    }

    public void setError(String error) {
        mError = error;

        notifyPropertyChanged(BR.error);
    }

    //endregion

    //region Constructors

    protected LoginViewModel() {
        super();
    }

    public LoginViewModel(INavigator navigator, UserModel userModel, IUserService userService, JobManager jobManager, FavoriteRestaurantModel favoriteRestaurantModel, IFavoriteRestaurantService iFavoriteRestaurantService) {
        super(navigator);

        mUserModel = userModel;

        mIUserService = userService;

        mJobManager = jobManager;

        mFavoriteRestaurantModel = favoriteRestaurantModel;

        mIFavoriteRestaurantService = iFavoriteRestaurantService;
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        mUser = new User();
        mUser.setEmail("");
        mUser.setPassword("");

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

        mUser = null;
        mError = null;

        getEventBus().unregister(this);
    }

    //endregion

    //region Private Methods

    private boolean validateUser(User user) {
        if (user.getEmail().trim().equals("")) {
            setError("Chưa nhập Email");
            return false;
        }

        if (user.getPassword().trim().equals("")) {
            setError("Chưa nhập mật khẩu");
            return false;
        }

        return true;
    }

    //endregion

    //region Public Methods

    public void showRegisterLayout() {
        getNavigator().navigateTo(Constants.REGISTER_PAGE);
    }

    public void logIn(User user) {
        if (validateUser(user)) {
            getNavigator().showBusyIndicator("Đăng nhập");

            mJobManager.addJobInBackground(new LogInJob(BasicJob.UI_HIGH, mIUserService, mUserModel, user));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(LoggedInEvent loggedInEvent) {
        if (loggedInEvent.isSuccess()) {
            getNavigator().getApplication().setLoginUser(loggedInEvent.getLoggedInUser());
            getNavigator().hideBusyIndicator();
            mJobManager.addJobInBackground(new FetchFavoriteRestaurantJob(BasicJob.UI_HIGH, mFavoriteRestaurantModel, mIFavoriteRestaurantService, loggedInEvent.getLoggedInUser()));
            getNavigator().goBack();
        }

        else {
            setError(loggedInEvent.getMessage());
            getNavigator().hideBusyIndicator();
        }
    }
    //endregion
}
