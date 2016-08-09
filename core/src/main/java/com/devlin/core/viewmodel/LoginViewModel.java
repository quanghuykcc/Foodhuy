package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.util.Log;

import com.devlin.core.BR;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.services.storages.UserStorageService;
import com.devlin.core.view.Constants;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;

/**
 * Created by Administrator on 8/1/2016.
 */
public class LoginViewModel extends BaseViewModel {

    //region Properties

    private static final String TAG = "LoginViewModel";

    private UserStorageService mUserStorageService;

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

    public LoginViewModel(INavigator navigator, UserStorageService storageService) {
        super(navigator);

        mUserStorageService = storageService;

    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        mUser = new User();
        mUser.setEmail("");
        mUser.setPassword("");
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


            mUserStorageService.logIn(user, new ICallback<User>() {
                @Override
                public void onResult(User result) {
                    if (result.isLoaded() && result.isValid()) {
                        getNavigator().hideBusyIndicator();

                        getEventBus().post(result);

                        getNavigator().getApplication().setLoginUser(result);

                        getNavigator().goBack();
                    }
                    else {
                        setError("Tài khoản hoặc mật khẩu không đúng");
                        getNavigator().hideBusyIndicator();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    getNavigator().hideBusyIndicator();
                }
            });
        }


    }

    //endregion
}
