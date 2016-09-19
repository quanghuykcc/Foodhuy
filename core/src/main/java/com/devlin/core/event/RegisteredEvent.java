package com.devlin.core.event;

/**
 * Created by Administrator on 9/16/2016.
 */
public class RegisteredEvent {

    private final boolean mSuccess;

    private final String mMessage;

    public RegisteredEvent(boolean success, String message) {
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
