package com.devlin.core.model.services.storages;

import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Comment;
import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.entities.SyncHistory;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.services.Configuration;
import com.devlin.core.view.Constants;
import com.devlin.core.view.ICallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Administrator on 7/31/2016.
 */
public class RestaurantModel extends BaseModel {

    //region Properties

    private static final String RESTAURANT_TABLE_NAME = "Restaurant";

    //endregion

    //region Constructors

    public RestaurantModel(Realm realm) {

        super(realm);

    }


    //endregion

    //region Override Methods


    public List<Restaurant> getLatest() {
        final Realm realm = Realm.getDefaultInstance();
        RealmResults<Restaurant> restaurants = realm.where(Restaurant.class).findAllSorted("mUpdatedAt", Sort.DESCENDING);

        if (restaurants == null || restaurants.size() == 0) {
            return null;
        }

        int cacheNumber = restaurants.size();
        List<Restaurant> latestRestaurants;
        if (cacheNumber > Configuration.NUMBER_RECORDS_PER_PAGE) {
            latestRestaurants = restaurants.subList(0, Configuration.NUMBER_RECORDS_PER_PAGE);
        }
        else {
            latestRestaurants = restaurants.subList(0, cacheNumber);
        }

        List<Restaurant> detachedRestaurants = realm.copyFromRealm(latestRestaurants);
        return detachedRestaurants;
    }

    public List<Restaurant> getByCategory(Category category) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Restaurant> restaurants = realm.where(Restaurant.class)
                                                    .equalTo("mCategoryId", category.getId())
                                                    .findAllSorted("mUpdatedAt", Sort.DESCENDING);
        List<Restaurant> detachedRestaurants = realm.copyFromRealm(restaurants);
        return detachedRestaurants;
    }

    public List<Restaurant> getFavorite(User user) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<FavoriteRestaurant> favorites = realm.where(FavoriteRestaurant.class)
                                                          .equalTo("mUserId", user.getId())
                                                          .findAllSorted("mUpdatedAt");

        List<Restaurant> restaurants = new ArrayList<>();
        for(FavoriteRestaurant favorite : favorites) {
            Restaurant restaurant = realm.where(Restaurant.class)
                    .equalTo("mId", favorite.getRestaurantId())
                    .findFirst();
            if (restaurant != null) {
                restaurants.add(realm.copyFromRealm(restaurant));
            }
        }

        return restaurants;
    }

    public Date getLatestSynchronize() {
        Realm realm = Realm.getDefaultInstance();

        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", RESTAURANT_TABLE_NAME).findFirst();

        if (syncHistory != null) {
            return syncHistory.getLastSyncTimestamp();
        }

        return null;
    }

    public void saveLatestSynchronize(Date latestSynchronizeTimestamp) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", RESTAURANT_TABLE_NAME).findFirst();

        if (syncHistory == null) {
            syncHistory = realm.createObject(SyncHistory.class);
            syncHistory.setNameTable(RESTAURANT_TABLE_NAME);
        }
        else {
            syncHistory.setLastSyncTimestamp(latestSynchronizeTimestamp);
        }

        realm.commitTransaction();
    }

    public void optimizeCached() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Restaurant> restaurants = realm.where(Restaurant.class).findAllSorted("mUpdatedAt", Sort.DESCENDING);
        for (int i = Configuration.NUMBER_CACHE_RESTAURANTS; i < restaurants.size(); i++) {
            restaurants.get(i).deleteFromRealm();
        }
        realm.commitTransaction();
    }

    public void delete(Restaurant restaurant) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<Restaurant> deleteRestaurants = realm.where(Restaurant.class).equalTo("mId", restaurant.getId()).findAll();
        if (deleteRestaurants.size() > 0) {
            deleteRestaurants.deleteAllFromRealm();
        }

        realm.commitTransaction();
    }

    public void addNewOrUpdate(Restaurant restaurant) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealmOrUpdate(restaurant);
        realm.commitTransaction();
    }

    public void addNewOrUpdate(List<Restaurant> restaurants) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealmOrUpdate(restaurants);
        realm.commitTransaction();
    }

    //endregion

}
