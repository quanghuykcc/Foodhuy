package com.devlin.core.model.entities;

import com.devlin.core.view.Constants;

/**
 * Created by Administrator on 08-Aug-16.
 */
public class Bundle<T> {
    private @Constants.ActionKey int code;

    private T data;

    public Bundle() {
        super();
    }

    public Bundle(@Constants.ActionKey int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Bundle(@Constants.ActionKey int code) {
        this.code = code;
    }

    public @Constants.ActionKey int getCode() {
        return code;
    }

    public void setCode(@Constants.ActionKey int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
