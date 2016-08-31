package com.devlin.core.event;

import com.devlin.core.model.entities.Category;

import java.util.List;

/**
 * Created by Administrator on 8/22/2016.
 */
public class FetchedCategoryEvent {

    private boolean mSuccess;

    private String mMessage;

    public FetchedCategoryEvent(boolean success) {
        mSuccess = success;
    }

    public FetchedCategoryEvent(boolean success, String message) {
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
