package com.devlin.core.model.services.clouds;

/**
 * Created by Administrator on 7/31/2016.
 */
public abstract class BaseCloudService<T> {

    //region Properties

    T mICloudService;

    //endregion

    //region Getter and Setter

    public T getICloudService() {
        return mICloudService;
    }

    //endregion
}
