package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.event.LoggedInEvent;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.clouds.IUserService;
import com.devlin.core.model.services.storages.UserModel;

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

    private static final String TAG = "LogInJob";

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

    //region Override Methods

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {

        Call<APIResponse<User>> call;

        call = mIUserService.logIn(mLogInUser.getEmail(), mLogInUser.getPassword());

        Response<APIResponse<User>> response = call.execute();
        if (response.isSuccessful()) {
            APIResponse<User> apiResponse = response.body();

            if (apiResponse.isSuccess()) {
                getEventBus().post(new LoggedInEvent(true, apiResponse.getData(), apiResponse.getMessage()));

                mUserModel.cacheLoggedInUser(apiResponse.getData());

                return;
        }
        else {
            getEventBus().post(new LoggedInEvent(false, apiResponse.getMessage()));
            return;
        }
        }
        else {
            getEventBus().post(new LoggedInEvent(false));
            return;
        }
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        getEventBus().post(new LoggedInEvent(false, throwable.getMessage()));
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
        return 5;
    }

    //endregion
}
