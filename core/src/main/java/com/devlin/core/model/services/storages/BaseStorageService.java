package com.devlin.core.model.services.storages;

import io.realm.Realm;

/**
 * Created by Administrator on 8/1/2016.
 */
public abstract class BaseStorageService {

    //region Properties

    protected Realm mRealm;

    //endregion

    //region Constructors

    public BaseStorageService(Realm realm) {

        mRealm = realm;

    }

    //endregion

}
