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

    public void clearLocalUsers() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> users = realm.where(User.class).findAll();

        realm.beginTransaction();
        users.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public User getRemembered() {
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();
        if (user != null) {
            return realm.copyFromRealm(user);
        } else {
            return null;
        }
    }

    //endregion

}
