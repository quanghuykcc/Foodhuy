package com.devlin.core.model.services.storages;

import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.entities.SyncHistory;
import com.devlin.core.model.entities.User;
import com.devlin.core.view.ICallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Administrator on 9/5/2016.
 */
public class FavoriteRestaurantModel extends BaseModel {

    //region Properties

    private static final String FAVORITE_RESTAURANT_TABLE_NAME = "FavoriteRestaurant";

    //endregion

    //region Constructors

    public FavoriteRestaurantModel(Realm realm) {
        super(realm);
    }

    //endregion

    //region Public Methods

    public Date getLastSyncedAt() {
        Realm realm = Realm.getDefaultInstance();

        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", FAVORITE_RESTAURANT_TABLE_NAME).findFirst();

        if (syncHistory != null) {
            return syncHistory.getLastSyncTimestamp();
        }

        return null;
    }

    public void saveLastSyncedAt(Date lastSyncedAt) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", FAVORITE_RESTAURANT_TABLE_NAME).findFirst();

        if (syncHistory == null) {
            syncHistory = realm.createObject(SyncHistory.class);
            syncHistory.setNameTable(FAVORITE_RESTAURANT_TABLE_NAME);
        }

        syncHistory.setLastSyncTimestamp(lastSyncedAt);
        realm.commitTransaction();
    }

    public void clearLastSyncedAt() {
        Realm realm = Realm.getDefaultInstance();

        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", FAVORITE_RESTAURANT_TABLE_NAME).findFirst();

        realm.beginTransaction();
        if (syncHistory != null) {
            syncHistory.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    public void delete(FavoriteRestaurant favoriteRestaurant) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<FavoriteRestaurant> deleteFavoriteRestaurants = realm
                                .where(FavoriteRestaurant.class)
                                .equalTo("mUserId", favoriteRestaurant.getUserId())
                                .equalTo("mRestaurantId", favoriteRestaurant.getRestaurantId())
                                .findAll();
        if (deleteFavoriteRestaurants.size() > 0) {
            deleteFavoriteRestaurants.deleteAllFromRealm();
        }

        realm.commitTransaction();
    }

    public FavoriteRestaurant find(int userId, int restaurantId) {
        Realm realm = Realm.getDefaultInstance();
        FavoriteRestaurant restaurant = realm
                .where(FavoriteRestaurant.class)
                .equalTo("mUserId", userId)
                .equalTo("mRestaurantId", restaurantId)
                .findFirst();

        return (restaurant != null) ? realm.copyFromRealm(restaurant) : null;
    }

    public void addNewOrUpdate(FavoriteRestaurant favoriteRestaurant) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealmOrUpdate(favoriteRestaurant);

        realm.commitTransaction();
    }

    public void add(List<FavoriteRestaurant> favoriteRestaurants) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealm(favoriteRestaurants);

        realm.commitTransaction();
    }

    public void addOrUpdate(List<FavoriteRestaurant> favoriteRestaurants) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealmOrUpdate(favoriteRestaurants);

        realm.commitTransaction();
    }

    public void add(FavoriteRestaurant favoriteRestaurant) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealm(favoriteRestaurant);

        realm.commitTransaction();
    }

    public void update(FavoriteRestaurant favoriteRestaurant) {
        Realm realm = Realm.getDefaultInstance();
        FavoriteRestaurant checkedFavorite = realm.where(FavoriteRestaurant.class)
                                                      .equalTo("mRestaurantId", favoriteRestaurant.getRestaurantId())
                                                      .equalTo("mUserId", favoriteRestaurant.getUserId())
                                                      .findFirst();
        if (checkedFavorite != null) {
            realm.beginTransaction();

            checkedFavorite.setCreatedAt(favoriteRestaurant.getCreatedAt());
            checkedFavorite.setDeletedAt(favoriteRestaurant.getDeletedAt());
            checkedFavorite.setUpdatedAt(favoriteRestaurant.getUpdatedAt());
            checkedFavorite.setId(favoriteRestaurant.getId());

            realm.commitTransaction();
        }
    }

    public void clear() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<FavoriteRestaurant> deleteFavoriteRestaurants = realm.where(FavoriteRestaurant.class).findAll();
        deleteFavoriteRestaurants.deleteAllFromRealm();

        realm.commitTransaction();

    }

    public boolean isFavorite(int userId, int restaurantId) {
        Realm realm = Realm.getDefaultInstance();
        FavoriteRestaurant favoriteRestaurant = realm.where(FavoriteRestaurant.class)
                                                    .equalTo("mUserId", userId)
                                                    .equalTo("mRestaurantId", restaurantId)
                                                    .findFirst();
        if (favoriteRestaurant != null) {
            return true;
        } else  {
            return false;
        }
    }

    //endregion

}
