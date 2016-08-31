package com.devlin.core.viewmodel;

import android.databinding.Bindable;

import com.devlin.core.BR;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.services.storages.UserModel;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;

/**
 * Created by Administrator on 8/3/2016.
 */
public class RegisterViewModel extends BaseViewModel {

    //region Properties

    private UserModel mUserStorageService;

    private User mUser;

    private String mError;

    //endregion

    //region Getter and Setter

    public void setError(String error) {
        mError = error;

        notifyPropertyChanged(BR.error);
    }

    public void setUser(User user) {
        mUser = user;
    }

    public User getUser() {
        return mUser;
    }

    @Bindable
    public String getError() {
        return mError;
    }

    //endregion

    //region Constructors

    public RegisterViewModel(INavigator navigator, UserModel storageService) {
        super(navigator);

        mUserStorageService = storageService;

    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        mUser = new User();
        mUser.setUserName("");
        mUser.setPassword("");
        mUser.setEmail("");
        mUser.setRetypePassword("");
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

    //region Public Methods

    public void goBack() {
        getNavigator().goBack();
    }

    public boolean validateUser(User user) {
        if (user.getEmail().trim().equals("")) {
            setError("Chưa nhập Email");
            return false;
        }

        if (user.getUserName().trim().equals("")) {
            setError("Chưa nhập tên hiển thị");
            return false;
        }

        if (user.getPassword().trim().equals("")) {
            setError("Chưa nhập mật khẩu");
            return false;
        }

        if (!user.getPassword().trim().equals(user.getRetypePassword().trim())) {
            setError("Mật khẩu và mật khẩu nhập lại không trùng");
            return false;
        }

        return true;
    }

    public void registerUser(User user) {

        if (validateUser(user)) {
            getNavigator().showBusyIndicator("Đăng ký");

            mUserStorageService.register(user, new ICallback<Boolean>() {
                @Override
                public void onResult(Boolean result) {
                    if (result == true) {
                        getNavigator().goBack();
                    }
                    else {
                        setError("Email đã tồn tại");
                    }

                    getNavigator().hideBusyIndicator();
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
