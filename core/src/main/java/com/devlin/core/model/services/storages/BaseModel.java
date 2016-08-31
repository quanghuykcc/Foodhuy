package com.devlin.core.model.services.storages;

import io.realm.Realm;

/**
 * Created by Administrator on 8/1/2016.
 */
public abstract class BaseModel {

    //region Properties

    protected Realm mRealm;

    //endregion

    //region Constructors

    public BaseModel(Realm realm) {
        mRealm = realm;
    }

    //endregion

}
