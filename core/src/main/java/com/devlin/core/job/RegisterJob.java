package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.event.RegisteredEvent;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.clouds.IUserService;

import retrofit2.Response;

/**
 * Created by Administrator on 9/16/2016.
 */
public class RegisterJob extends BasicJob {

    //region Properties

    private IUserService mIUserService;

    private User mRegisterUser;

    private static final String GROUP = "RegisterJob";

    //endregion

    //region Constructors

    public RegisterJob(@BasicJob.Priority int priority, IUserService userService, User registerUser) {
        super(new Params(priority).groupBy(GROUP).requireNetwork());

        mIUserService = userService;

        mRegisterUser = registerUser;
    }

    //endregion

    //region Override methods

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Response<APIResponse<Boolean>> response = mIUserService
                                    .signUp(mRegisterUser.getEmail(), mRegisterUser.getPassword(), mRegisterUser.getName())
                                    .execute();

        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
            post(new RegisteredEvent(true, "Bạn đã đăng ký tài khoản Foodhuy thành công"));
        } else {
            post(new RegisteredEvent(false, "Xảy ra lỗi đăng ký tài khoản. Vui lòng kiểm tra kết nối mạng!"));
        }
    }


    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        post(new RegisteredEvent(false, "Xảy ra lỗi đăng ký tài khoản. Vui lòng kiểm tra kết nối mạng"));
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
