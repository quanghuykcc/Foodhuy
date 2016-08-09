package com.devlin.core.model.services.storages;

import android.util.Log;

import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.services.IUserService;
import com.devlin.core.view.ICallback;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;

/**
 * Created by Administrator on 7/31/2016.
 */
public class UserStorageService extends BaseStorageService implements IUserService {

    //region Properties

    public UserStorageService(Realm realm) {
        super(realm);
    }

    //endregion

    //region Override Methods

    @Override
    public void logIn(User user, final ICallback<User> callback) {

        final User checkedUser = mRealm.where(User.class).equalTo("mEmail", user.getEmail()).equalTo("mPassword", user.getPassword()).findFirstAsync();

        checkedUser.addChangeListener(new RealmChangeListener<User>() {
            @Override
            public void onChange(User element) {
                callback.onResult(element);
                checkedUser.removeChangeListener(this);
            }
        });

    }

    @Override
    public void register(final User user, final ICallback<Boolean> callback) {

        User checkedUser = mRealm.where(User.class).equalTo("mEmail", user.getEmail()).findFirst();

        if (checkedUser == null) {

            user.setId(UUID.randomUUID().toString());
            user.setCreatedAt(Calendar.getInstance().getTime());

            mRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {

                    User realmRestaurant = bgRealm.copyToRealm(user);

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

        else {
            callback.onResult(false);
        }
    }

    @Override
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

    @Override
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

    @Override
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

    //endregion

}
