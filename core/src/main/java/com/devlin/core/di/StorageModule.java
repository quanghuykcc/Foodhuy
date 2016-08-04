package com.devlin.core.di;

import com.devlin.core.model.services.IRestaurantService;
import com.devlin.core.model.services.storages.CategoryStorageService;
import com.devlin.core.model.services.storages.RestaurantStorageService;
import com.devlin.core.model.services.storages.UserStorageService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 7/26/2016.
 */
@Module
public class StorageModule {

    //region Provide methods

    @Provides
    @Singleton
    public Realm providesRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    public RestaurantStorageService providesRestaurantStorageService(Realm realm) {
        return new RestaurantStorageService(realm);
    }

    @Provides
    @Singleton
    public UserStorageService providesUserStorageService(Realm realm) {
        return new UserStorageService(realm);
    }

    @Provides
    @Singleton
    public CategoryStorageService providesCategoryStorageService(Realm realm) {
        return new CategoryStorageService(realm);
    }

    //endregion

}
