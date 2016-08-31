package com.devlin.core.event;

import com.devlin.core.model.entities.User;

/**
 * Created by Administrator on 8/25/2016.
 */
public class LoggedInEvent {
    private boolean mSuccess;

    private User mLoggedInUser;

    private String mMessage;

    public LoggedInEvent(boolean success, User loggedInUser, String message) {
        mSuccess = success;
        mLoggedInUser = loggedInUser;
        mMessage = message;
    }

    public LoggedInEvent(boolean success) {
        mSuccess = success;
    }

    public LoggedInEvent(boolean success, String message) {
        mSuccess = success;
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
