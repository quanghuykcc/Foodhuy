package com.devlin.core.event;

/**
 * Created by quanghuymr403 on 19/09/2016.
 */
public class LoggedOutEvent  {

    private final boolean mSuccess;

    private final String mMessage;

    public LoggedOutEvent(boolean success, String message) {
        mSuccess = success;

        mMessage = message;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public String getMessage() {
        return mMessage;
    }
}
