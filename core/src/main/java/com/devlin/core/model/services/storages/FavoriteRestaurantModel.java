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

    public Date getLatestSynchronizeTimestamp() {
        Realm realm = Realm.getDefaultInstance();

        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", FAVORITE_RESTAURANT_TABLE_NAME).findFirst();

        if (syncHistory != null) {
            return syncHistory.getLastSyncTimestamp();
        }

        return null;
    }

    public void updateLatestSynchronizeTimestamp(Date latestSynchronizeTimestamp) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", FAVORITE_RESTAURANT_TABLE_NAME).findFirst();

        if (syncHistory == null) {
            syncHistory = realm.createObject(SyncHistory.class);
            syncHistory.setNameTable(FAVORITE_RESTAURANT_TABLE_NAME);
        }

        syncHistory.setLastSyncTimestamp(latestSynchronizeTimestamp);
        realm.commitTransaction();
    }

    public void clearLatestSynchronizeTimestamp() {
        Realm realm = Realm.getDefaultInstance();

        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", FAVORITE_RESTAURANT_TABLE_NAME).findFirst();

        realm.beginTransaction();
        if (syncHistory != null) {
            syncHistory.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    public void deleteFavoriteRestaurant(FavoriteRestaurant favoriteRestaurant) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<FavoriteRestaurant> deleteFavoriteRestaurants = realm.where(FavoriteRestaurant.class).equalTo("mUserId", favoriteRestaurant.getUserId()).equalTo("mRestaurantId", favoriteRestaurant.getRestaurantId()).findAll();
        if (deleteFavoriteRestaurants.size() > 0) {
            deleteFavoriteRestaurants.deleteAllFromRealm();
        }
    }

    public void addNewOrUpdateFavoriteRestaurant(FavoriteRestaurant favoriteRestaurant) {
        Realm realm = Realm.getDefaultInstance();

        realm.copyToRealmOrUpdate(favoriteRestaurant);
    }

    public void addNewFavoriteRestaurant(FavoriteRestaurant favoriteRestaurant) {
        Realm realm = Realm.getDefaultInstance();

        realm.copyToRealm(favoriteRestaurant);
    }

    public void clearFavoriteRestaurantsAsync() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<FavoriteRestaurant> deleteFavoriteRestaurants = realm.where(FavoriteRestaurant.class).findAll();
                deleteFavoriteRestaurants.deleteAllFromRealm();
            }
        });
    }

    public void cacheFavoriteRestaurants(List<FavoriteRestaurant> favoriteRestaurants) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (FavoriteRestaurant favoriteRestaurant : favoriteRestaurants) {
            if (favoriteRestaurant.isDeleted()) {
                deleteFavoriteRestaurant(favoriteRestaurant);
            }
            else {
                addNewOrUpdateFavoriteRestaurant(favoriteRestaurant);
            }
        }

        realm.commitTransaction();
    }

    public void getFavoriteRestaurantsOfUserAsync(User user, final ICallback<List<FavoriteRestaurant>> callback) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<FavoriteRestaurant> favoriteRestaurants = realm.where(FavoriteRestaurant.class).equalTo("mUserId", user.getId()).findAllAsync();

        favoriteRestaurants.addChangeListener(new RealmChangeListener<RealmResults<FavoriteRestaurant>>() {
            @Override
            public void onChange(RealmResults<FavoriteRestaurant> element) {
                List<FavoriteRestaurant> detachedFavoriteRestaurants = realm.copyFromRealm(element);
                callback.onResult(detachedFavoriteRestaurants);
                favoriteRestaurants.removeChangeListener(this);
            }
        });
    }

    public List<FavoriteRestaurant> getFavoriteRestaurantsOfUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<FavoriteRestaurant> favoriteRestaurants = realm.where(FavoriteRestaurant.class).equalTo("mUserId", user.getId()).findAll();

        List<FavoriteRestaurant> detachedFavoriteRestaurants = realm.copyFromRealm(favoriteRestaurants);
        return detachedFavoriteRestaurants;
    }

    public FavoriteRestaurant getByUserAndRestaurant(int userId, int restaurantId) {
        Realm realm = Realm.getDefaultInstance();
        FavoriteRestaurant favoriteRestaurant = realm.where(FavoriteRestaurant.class)
                                                    .equalTo("mUserId", userId)
                                                    .equalTo("mRestaurantId", restaurantId)
                                                    .findFirst();
        return favoriteRestaurant;
    }

    //endregion
}
