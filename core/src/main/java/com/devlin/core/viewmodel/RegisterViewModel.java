package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.util.Patterns;
import android.widget.Toast;

import com.birbit.android.jobqueue.JobManager;
import com.devlin.core.BR;
import com.devlin.core.event.RegisteredEvent;
import com.devlin.core.job.BasicJob;
import com.devlin.core.job.RegisterJob;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.clouds.IUserService;
import com.devlin.core.model.services.storages.UserModel;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 8/3/2016.
 */
public class RegisterViewModel extends BaseViewModel {

    //region Properties

    private IUserService mIUserService;

    private String mError;

    private String mEmail;

    private String mPassword;

    private String mRetypePassword;

    private String mName;

    private JobManager mJobManager;

    //endregion

    //region Getter and Setter

    public void setError(String error) {
        mError = error;

        notifyPropertyChanged(BR.error);
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getRetypePassword() {
        return mRetypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        mRetypePassword = retypePassword;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Bindable
    public String getError() {
        return mError;
    }

    //endregion

    //region Constructors

    public RegisterViewModel(INavigator navigator, IUserService userService, JobManager jobManager) {
        super(navigator);

        mIUserService = userService;

        mJobManager = jobManager;
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();
        register();

        mEmail = "";
        mPassword = "";
        mRetypePassword = "";
        mName = "";
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

        mEmail = null;
        mPassword = null;
        mRetypePassword = null;
        mName = null;
        mError = null;
    }

    //endregion

    //region Public Methods

    public void goBack() {
        getNavigator().goBack();
    }

    public boolean validateInput() {

        if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            setError("Email không hợp lệ");
            return false;
        }

        if (mPassword.trim().length() < 6 || mPassword.trim().length() > 50) {
            setError("Mật khẩu phải từ 6 đến 50 ký tự");
            return false;
        }

        if (!mPassword.trim().equals(mRetypePassword.trim())) {
            setError("Mật khẩu và mật khẩu nhập lại không trùng");
            return false;
        }

        if (mName.trim().length() < 6 || mName.trim().length() > 50) {
            setError("Tên hiển thị phải từ 6 đến 50 ký tự");
            return false;
        }

        return true;
    }

    public void registerCommand() {
        if (validateInput()) {
            getNavigator().showBusyIndicator("Đăng ký...");

            User user = new User();
            user.setEmail(mEmail);
            user.setPassword(mPassword);
            user.setName(mName);

            mJobManager.addJobInBackground(new RegisterJob(BasicJob.UI_HIGH, mIUserService, user));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(RegisteredEvent registeredEvent) {
        getNavigator().hideBusyIndicator();

        if (registeredEvent.isSuccess()) {
            showToast(registeredEvent.getMessage(), Toast.LENGTH_LONG);
            getNavigator().goBack();
        }
        else {
            setError(registeredEvent.getMessage());
        }
    }

    //endregion
}
