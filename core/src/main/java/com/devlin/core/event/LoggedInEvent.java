package com.devlin.core.event;

import android.support.annotation.Nullable;

import com.devlin.core.model.entities.User;

/**
 * Created by Administrator on 8/25/2016.
 */
public class LoggedInEvent {
    private final boolean mSuccess;

    private final User mLoggedInUser;

    private final String mMessage;

    public LoggedInEvent(boolean success, @Nullable User loggedInUser, @Nullable String message) {
        mSuccess = success;
        mLoggedInUser = loggedInUser;
        mMessage = message;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public User getLoggedInUser() {
        return mLoggedInUser;
    }

    public String getMessage() {
        return mMessage;
    }
}
