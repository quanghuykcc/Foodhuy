package com.devlin.core.viewmodel;

import android.databinding.Bindable;

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

        mUser = new User();
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

    //region Public Methods

    public void showRegisterLayout() {
        getNavigator().navigateTo(Constants.REGISTER_PAGE);
    }

    public void logIn(User user) {
        mUserStorageService.logIn(user, new ICallback<User>() {
            @Override
            public void onResult(User result) {
                if (result != null) {
                    getNavigator().goBack();
                }
                else {
                    setError("Tài khoản hoặc mật khẩu không đúng");
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    //endregion
}
