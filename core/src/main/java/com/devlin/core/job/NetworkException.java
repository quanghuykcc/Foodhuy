package com.devlin.core.job;

/**
 * Created by Administrator on 8/22/2016.
 */
public class NetworkException extends RuntimeException {
    private final int mErrorCode;

    public NetworkException(int errorCode) {
        mErrorCode = errorCode;
    }

    public boolean shouldRetry() {
        return mErrorCode < 400 || mErrorCode > 499;
    }
}
