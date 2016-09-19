package com.devlin.core.event;

/**
 * Created by Administrator on 9/6/2016.
 */
public class AddedNewFavoriteEvent {

    private final boolean mSuccess;

    public AddedNewFavoriteEvent(boolean success) {
        mSuccess = success;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

}
