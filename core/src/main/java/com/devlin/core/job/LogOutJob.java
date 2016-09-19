package com.devlin.core.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.devlin.core.event.LoggedInEvent;
import com.devlin.core.event.LoggedOutEvent;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.clouds.IFavoriteRestaurantService;
import com.devlin.core.model.services.clouds.IUserService;
import com.devlin.core.model.services.storages.FavoriteRestaurantModel;
import com.devlin.core.model.services.storages.UserModel;

import retrofit2.Response;

/**
 * Created by quanghuymr403 on 19/09/2016.
 */
public class LogOutJob extends BasicJob {

    //region Properties

    private FavoriteRestaurantModel mFavoriteRestaurantModel;

    private UserModel mUserModel;

    private User mUser;

    private static final String GROUP = "LogOutJob";

    //endregion

    //region Constructors

    public LogOutJob(@BasicJob.Priority int priority, FavoriteRestaurantModel favoriteRestaurantModel, UserModel userModel, User user) {
        super(new Params(priority).groupBy(GROUP).requireNetwork());

        mFavoriteRestaurantModel = favoriteRestaurantModel;

        mUserModel = userModel;

        mUser = user;
    }

    //endregion

    //region Override methods

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        mFavoriteRestaurantModel.clear();
        mFavoriteRestaurantModel.clearLastSyncedAt();
        mUserModel.clearLocalUsers();
        post(new LoggedOutEvent(true, "Đăng xuất thành công"));
    }


    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        post(new LoggedOutEvent(false, "Lỗi đăng xuất. Vui lòng thử lại!"));
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return RetryConstraint.CANCEL;
    }

    //endregion


}
