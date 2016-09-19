package com.devlin.core.model.services.storages;

import android.util.Log;

import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.SyncHistory;
import com.devlin.core.view.ICallback;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Administrator on 8/2/2016.
 */
public class CategoryModel extends BaseModel {

    //region Properties

    private static final String CATEGORY_TABLE_NAME = "Category";

    //endregion

    //region Constructors

    public CategoryModel(Realm realm) {
        super(realm);
    }

    //endregion

    //region Public methods

    public List<Category> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Category> categories = realm.where(Category.class).findAll();
        List<Category> detachedCategories = realm.copyFromRealm(categories);
        return detachedCategories;
    }

    public Date getLastSyncAt() {
        Realm realm = Realm.getDefaultInstance();

        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", CATEGORY_TABLE_NAME).findFirst();

        if (syncHistory != null) {
            return syncHistory.getLastSyncTimestamp();
        }

        return null;
    }

    public void addOrUpdate(Category category) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        realm.copyToRealmOrUpdate(category);

        realm.commitTransaction();
    }

    public void saveLastSyncAt(Date lastSyncAt) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", CATEGORY_TABLE_NAME).findFirst();

        if (syncHistory == null) {
            syncHistory = realm.createObject(SyncHistory.class);
            syncHistory.setNameTable(CATEGORY_TABLE_NAME);
        }

        syncHistory.setLastSyncTimestamp(lastSyncAt);

        realm.commitTransaction();
    }

    public void delete(Category category) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<Category> deleteCategories = realm.where(Category.class).equalTo("mId", category.getId()).findAll();
        if (deleteCategories.size() > 0) {
            deleteCategories.deleteAllFromRealm();
        }

        realm.commitTransaction();
    }

    //endregion
}
