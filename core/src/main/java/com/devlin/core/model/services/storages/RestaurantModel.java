package com.devlin.core.model.services.storages;

import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Comment;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.entities.SyncHistory;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.services.Configuration;
import com.devlin.core.view.ICallback;

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

    public void getRestaurants(long offset, long limit, ICallback<List<Restaurant>> callback) {

    }


    public void getAllRestaurantsAsync(final ICallback<List<Restaurant>> callback) {
        Realm realm = Realm.getDefaultInstance();

        final RealmResults<Restaurant> restaurants = realm.where(Restaurant.class).findAllAsync();

        restaurants.addChangeListener(new RealmChangeListener<RealmResults<Restaurant>>() {
            @Override
            public void onChange(RealmResults<Restaurant> element) {
                callback.onResult(element);

                restaurants.removeChangeListener(this);
            }
        });
    }

    public void getRestaurantById(String id, final ICallback<Restaurant> callback) {

        final Restaurant restaurant = mRealm.where(Restaurant.class).equalTo("id", id).findFirstAsync();

        restaurant.addChangeListener(new RealmChangeListener<Restaurant>() {
            @Override
            public void onChange(Restaurant element) {
                callback.onResult(element);

                restaurant.removeChangeListener(this);
            }
        });

    }

    public void saveRestaurant(final Restaurant restaurant, final ICallback<Boolean> callback) {

        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                Restaurant realmRestaurant = bgRealm.copyToRealm(restaurant);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onResult(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onFailure(error);
            }
        });

    }

    public RealmResults<Restaurant> getLatestRestaurants() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Restaurant> restaurants = realm.where(Restaurant.class).findAllSortedAsync("mCreatedAt", Sort.DESCENDING);
        return restaurants;
    }

    public void saveRestaurants(final List<Restaurant> restaurants, final ICallback<Boolean> callback) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                bgRealm.copyToRealmOrUpdate(restaurants);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onResult(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onFailure(error);
            }
        });
    }

    public void getRestaurantsByCategory(Category category, long offset, long limit, final ICallback<List<Restaurant>> callback) {

        final RealmResults<Restaurant> restaurants = mRealm.where(Restaurant.class).equalTo("mCategoryId", category.getId()).findAllSortedAsync("mCreatedAt", Sort.DESCENDING);

        restaurants.addChangeListener(new RealmChangeListener<RealmResults<Restaurant>>() {
            @Override
            public void onChange(RealmResults<Restaurant> element) {
                callback.onResult(element);

                restaurants.removeChangeListener(this);
            }
        });
    }

    public void addComment(final Comment comment, final Restaurant restaurant, final ICallback<Boolean> callback) {

        final int restaurantId = restaurant.getId();

        final String userId = comment.getCommenter().getId();

        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Restaurant restaurant = realm.where(Restaurant.class).equalTo("mId", restaurantId).findFirst();

                User user = realm.where(User.class).equalTo("mId", userId).findFirst();

                comment.setCommenter(user);

                restaurant.getComments().add(comment);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onResult(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onFailure(error);
            }
        });
    }

    public Date getLatestSynchronizeTimestamp() {
        Realm realm = Realm.getDefaultInstance();

        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", RESTAURANT_TABLE_NAME).findFirst();

        if (syncHistory != null) {
            return syncHistory.getLastSyncTimestamp();
        }

        return null;
    }

    private void updateLatestSynchronizeTimestamp(Date latestSynchronizeTimestamp) {
        Realm realm = Realm.getDefaultInstance();

        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", RESTAURANT_TABLE_NAME).findFirst();

        if (syncHistory == null) {
            syncHistory = realm.createObject(SyncHistory.class);
            syncHistory.setNameTable(RESTAURANT_TABLE_NAME);
        }
        else {
            syncHistory.setLastSyncTimestamp(latestSynchronizeTimestamp);
        }

    }

    public void handleFetchedRestaurants(List<Restaurant> restaurants, Date latestSynchronizeTimestamp) {
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        for (Restaurant restaurant : restaurants) {

            if (restaurant.isDeleted()) {
                deleteRestaurant(restaurant);
            }
            else {
                addNewOrUpdateRestaurant(restaurant);
            }
        }

        optimizeCachedRestaurants();

        updateLatestSynchronizeTimestamp(latestSynchronizeTimestamp);

        realm.commitTransaction();
    }

    private void optimizeCachedRestaurants() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Restaurant> restaurants = realm.where(Restaurant.class).findAllSorted("mUpdatedAt", Sort.DESCENDING);
        for (int i = Configuration.NUMBER_CACHE_RESTAURANTS; i < restaurants.size(); i++) {
            restaurants.get(i).deleteFromRealm();
        }
    }

    private void deleteRestaurant(Restaurant restaurant) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Restaurant> deleteRestaurants = realm.where(Restaurant.class).equalTo("mId", restaurant.getId()).findAll();
        if (deleteRestaurants.size() > 0) {
            deleteRestaurants.deleteAllFromRealm();
        }
    }

    private void addNewOrUpdateRestaurant(Restaurant restaurant) {
        Realm realm = Realm.getDefaultInstance();

        realm.copyToRealmOrUpdate(restaurant);
    }

    //endregion

}
