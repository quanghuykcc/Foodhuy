package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.widget.Toast;

import com.devlin.core.BR;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.clouds.IUserService;
import com.devlin.core.model.services.storages.UserModel;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 8/3/2016.
 */
public class RegisterViewModel extends BaseViewModel {

    //region Properties

    private IUserService mIUserService;

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

    public RegisterViewModel(INavigator navigator, IUserService userService) {
        super(navigator);

        mIUserService = userService;
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

            mIUserService.signUp(user.getEmail(), user.getPassword(), user.getUserName()).enqueue(new Callback<APIResponse<Boolean>>() {
                @Override
                public void onResponse(Call<APIResponse<Boolean>> call, Response<APIResponse<Boolean>> response) {
                    if (!response.isSuccessful()) {
                        setError("Xảy ra lỗi đăng ký tài khoản");
                        getNavigator().hideBusyIndicator();
                        return;
                    } else if (!response.body().isSuccess()) {
                        setError(response.body().getMessage());
                        getNavigator().hideBusyIndicator();
                        return;
                    }

                    getNavigator().hideBusyIndicator();
                    Toast.makeText(getNavigator().getApplication().getCurrentActivity(), "Bạn đã đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();

                    getNavigator().goBack();
                }

                @Override
                public void onFailure(Call<APIResponse<Boolean>> call, Throwable t) {
                    setError("Xảy ra lỗi đăng ký tài khoản");
                    getNavigator().hideBusyIndicator();
                }
            });
        }
    }

    //endregion
}
