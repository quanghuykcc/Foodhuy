package com.devlin.core.model.services.storages;

import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.entities.User;
import com.devlin.core.view.ICallback;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Administrator on 7/31/2016.
 */
public class UserModel extends BaseModel {

    //region Properties

    public UserModel(Realm realm) {
        super(realm);
    }

    //endregion

    //region Override Methods

    public void cacheLoggedInUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<User> beRemovedUser = realm.where(User.class).findAll();
        beRemovedUser.deleteAllFromRealm();

        realm.copyToRealm(user);
        realm.commitTransaction();
    }



    public void addFavoriteRestaurant(final User user, final Restaurant restaurant, final ICallback<Boolean> callback) {

        final String email = user.getEmail();
        final int restaurantId = restaurant.getId();

        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                User checkedUser = bgRealm.where(User.class).equalTo("mEmail", email).findFirst();

                Restaurant checkedRestaurant = bgRealm.where(Restaurant.class).equalTo("mId", restaurantId).findFirst();

                checkedUser.getFavoriteRestaurant().add(checkedRestaurant);
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

    public void removeFavoriteRestaurant(final User user, final Restaurant restaurant, final ICallback<Boolean> callback) {
        final String email = user.getEmail();
        final int restaurantId = restaurant.getId();

        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                User checkedUser = bgRealm.where(User.class).equalTo("mEmail", email).findFirst();

                /*Restaurant removeRestaurant = checkedUser.getFavoriteRestaurant().where().equalTo("mId", restaurantId).findFirst();

                int removeIndex = checkedUser.getFavoriteRestaurant().indexOf(removeRestaurant);

                Log.d("TAG", removeIndex + "");

                checkedUser.getFavoriteRestaurant().deleteFromRealm(removeIndex);*/



                Restaurant checkedRestaurant = bgRealm.where(Restaurant.class).equalTo("mId", restaurantId).findFirst();
                checkedUser.getFavoriteRestaurant().remove(checkedRestaurant);


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

    public void loadFavoriteRestaurants(User user, final ICallback<List<Restaurant>> callback) {
        final User checkedUser = mRealm.where(User.class).equalTo("mId", user.getId()).findFirstAsync();

        checkedUser.addChangeListener(new RealmChangeListener<User>() {
            @Override
            public void onChange(User element) {
                callback.onResult(element.getFavoriteRestaurant());
                checkedUser.removeChangeListener(this);
            }
        });
    }

    public void clearLocalUsers() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> users = realm.where(User.class).findAll();

        realm.beginTransaction();
        users.deleteAllFromRealm();
        realm.commitTransaction();
    }

    //endregion

}
