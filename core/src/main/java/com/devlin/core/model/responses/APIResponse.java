package com.devlin.core.model.responses;

import com.devlin.core.model.entities.Restaurant;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 7/30/2016.
 */
public class APIResponse<E> {

    //region Properties

    @SerializedName("success")
    private boolean mSuccess;

    @SerializedName("data")
    private E mData;

    @SerializedName("message")
    private String mMessage;

    @SerializedName("last_sync_timestamp")
    private Date mLastSyncTimestamp;

    //endregion

    //region Getter and Setter

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        mSuccess = success;
    }

    public E getData() {
        return mData;
    }

    public void setData(E data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Date getLastSyncTimestamp() {
        return mLastSyncTimestamp;
    }

    public void setLastSyncTimestamp(Date lastSyncTimestamp) {
        mLastSyncTimestamp = lastSyncTimestamp;
    }

    //endregion

}
