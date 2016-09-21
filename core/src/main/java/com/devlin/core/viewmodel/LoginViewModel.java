package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.util.Patterns;

import com.birbit.android.jobqueue.JobManager;
import com.devlin.core.BR;
import com.devlin.core.event.LoggedInEvent;
import com.devlin.core.job.BasicJob;
import com.devlin.core.job.FetchFavoriteJob;
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

    private String mError;

    private String mPassword;

    private String mEmail;

    //endregion

    //region Getter and Setter

    @Bindable
    public String getError() {
        return mError;
    }

    public void setError(String error) {
        mError = error;

        notifyPropertyChanged(BR.error);
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    //endregion

    //region Constructors

    public LoginViewModel(INavigator navigator, UserModel userModel, IUserService userService, JobManager jobManager) {
        super(navigator);

        mUserModel = userModel;

        mIUserService = userService;

        mJobManager = jobManager;
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        mEmail = "";
        mPassword = "";
        mError = "";

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

        mEmail = null;
        mPassword = null;
        mError = null;

        unregister();
    }

    //endregion

    //region Private Methods

    private boolean validateInput() {
        if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            setError("Email không hợp lệ");
            return false;
        }

        if (mPassword.trim().length() < 6 || mPassword.length() > 50) {
            setError("Mật khẩu phải từ 6 tới 50 ký tự");
            return false;
        }

        return true;
    }

    //endregion

    //region Public Methods

    public void showRegisterLayout() {
        getNavigator().navigateTo(Constants.REGISTER_PAGE);
    }

    public void logInCommand() {
        if (validateInput()) {
            getNavigator().showBusyIndicator("Đăng nhập...");

            User user = new User();
            user.setEmail(mEmail);
            user.setPassword(mPassword);

            mJobManager.addJobInBackground(new LogInJob(BasicJob.UI_HIGH, mIUserService, mUserModel, user));
        }
    }

    //endregion

    //region Subscribe methods

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(LoggedInEvent loggedInEvent) {
        if (loggedInEvent.isSuccess()) {
            getNavigator().getApplication().setLoginUser(loggedInEvent.getLoggedInUser());
            getNavigator().hideBusyIndicator();
            getNavigator().goBack();
        }

        else {
            setError(loggedInEvent.getMessage());
            getNavigator().hideBusyIndicator();
        }
    }
    //endregion

}
