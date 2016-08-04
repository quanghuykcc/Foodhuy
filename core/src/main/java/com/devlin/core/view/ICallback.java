package com.devlin.core.view;

/**
 * Created by Administrator on 7/25/2016.
 */
public interface ICallback<T> {

    void onResult(T result);

    void onFailure(Throwable t);

}
