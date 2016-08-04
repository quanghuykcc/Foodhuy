package com.devlin.core.model.services.storages;

import android.util.Log;

import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.services.IRestaurantService;
import com.devlin.core.view.ICallback;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Administrator on 7/31/2016.
 */
public class RestaurantStorageService extends  BaseStorageService implements IRestaurantService {

    //region Constructors

    public RestaurantStorageService(Realm realm) {

        super(realm);

    }


    //endregion

    //region Override Methods

    @Override
    public void getRestaurants(long from, long to, String orderBy, ICallback<List<Restaurant>> callback) {

    }

    @Override
    public void getAllRestaurants(final ICallback<List<Restaurant>> callback) {

        final RealmResults<Restaurant> restaurants = mRealm.where(Restaurant.class).findAllAsync();

        restaurants.addChangeListener(new RealmChangeListener<RealmResults<Restaurant>>() {
            @Override
            public void onChange(RealmResults<Restaurant> element) {
                callback.onResult(element);

                restaurants.removeChangeListener(this);
            }
        });

    }

    @Override
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

    @Override
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

    @Override
    public void getLatestRestaurants(final ICallback<List<Restaurant>> callback) {

        final RealmResults<Restaurant> restaurants = mRealm.where(Restaurant.class).findAllSortedAsync("mCreatedAt", Sort.DESCENDING);

        restaurants.addChangeListener(new RealmChangeListener<RealmResults<Restaurant>>() {
            @Override
            public void onChange(RealmResults<Restaurant> element) {
                callback.onResult(element);

                restaurants.removeChangeListener(this);
            }
        });
    }

    @Override
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

    @Override
    public void getRestaurantsByCategory(Category category, final ICallback<List<Restaurant>> callback) {

        final RealmResults<Restaurant> restaurants = mRealm.where(Restaurant.class).equalTo("mCategoryId", category.getId()).findAllSortedAsync("mCreatedAt", Sort.DESCENDING);

        restaurants.addChangeListener(new RealmChangeListener<RealmResults<Restaurant>>() {
            @Override
            public void onChange(RealmResults<Restaurant> element) {
                callback.onResult(element);

                restaurants.removeChangeListener(this);
            }
        });
    }

    //endregion

}
