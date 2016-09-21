package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.event.LoggedInEvent;
import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.clouds.IUserService;
import com.devlin.core.model.services.storages.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 8/25/2016.
 */
public class LogInJob extends BasicJob {

    //region Properties

    private IUserService mIUserService;

    private UserModel mUserModel;

    private User mLogInUser;

    private static final String GROUP = "LogInJob";

    //endregion

    //region Constructors

    public LogInJob(@BasicJob.Priority int priority, IUserService userService, UserModel userModel, User logInUser) {
        super(new Params(priority).groupBy(GROUP).requireNetwork());

        mIUserService = userService;

        mUserModel = userModel;

        mLogInUser = logInUser;
    }

    //endregion

    //region Override methods

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Response<APIResponse<User>> response;
        if (mLogInUser.getRemmemberToken() != null && mLogInUser.getRemmemberToken().equals("")) {
            response = mIUserService.logInIfRemember(mLogInUser.getRemmemberToken()).execute();
        } else {
            response = mIUserService.logIn(mLogInUser.getEmail(), mLogInUser.getPassword()).execute();
        }


        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
            User loggedInUser = response.body().getData();
            post(new LoggedInEvent(true, loggedInUser, null));

            mUserModel.cacheLoggedInUser(loggedInUser);


        }
        else {
            post(new LoggedInEvent(false, null, "Thông tin đăng nhập không chính xác"));
        }
    }


    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        post(new LoggedInEvent(false, null, "Có lỗi trong quá trình đăng nhập! Vui lòng kiểm tra kết nối mạng"));
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        if (shouldRetry(throwable)) {
            return RetryConstraint.RETRY;
        }
        return RetryConstraint.CANCEL;
    }

    @Override
    protected int getRetryLimit() {
        return 2;
    }

    //endregion

}
