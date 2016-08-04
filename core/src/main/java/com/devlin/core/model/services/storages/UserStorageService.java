package com.devlin.core.model.services.storages;

import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.entities.User;
import com.devlin.core.model.services.IUserService;
import com.devlin.core.view.ICallback;

import java.util.Calendar;
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
        User checkedUser = mRealm.where(User.class).equalTo("mUserName", user.getUserName()).equalTo("mPassword", user.getPassword()).findFirstAsync();

        checkedUser.addChangeListener(new RealmChangeListener<User>() {
            @Override
            public void onChange(User element) {
                callback.onResult(element);
            }
        });

    }

    @Override
    public void register(final User user, final ICallback<Boolean> callback) {

        User checkedUser = mRealm.where(User.class).equalTo("mUserName", user.getUserName()).findFirst();

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

    //endregion

}
