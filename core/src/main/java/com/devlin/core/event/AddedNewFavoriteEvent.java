package com.devlin.core.event;

/**
 * Created by Administrator on 9/6/2016.
 */
public class AddedNewFavoriteEvent {
    private boolean mSuccess;

    private String mMessage;

    public AddedNewFavoriteEvent(boolean success) {
        mSuccess = success;
    }

    public AddedNewFavoriteEvent(boolean success, String message) {
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
