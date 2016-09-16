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

    private static final String CATEGORY_TABLE_NAME = "Category";

    private static final String TAG = "CategoryModel";

    //region Constructors

    public CategoryModel(Realm realm) {
        super(realm);
    }

    //endregion

    public RealmResults<Category> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Category> categories = realm.where(Category.class).findAll();
        return categories;
    }

    public void getAllAsync(final ICallback<RealmResults<Category>> callback) {
        final Realm realm = Realm.getDefaultInstance();

        final RealmResults<Category> categories = realm.where(Category.class).findAllAsync();
        categories.addChangeListener(new RealmChangeListener<RealmResults<Category>>() {
            @Override
            public void onChange(RealmResults<Category> element) {
                callback.onResult(element);
                categories.removeChangeListener(this);
            }
        });
    }


    public Date getLatestSynchronize() {
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

    public void saveLatestSynchronize(Date latestSynchronizeTimestamp) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", CATEGORY_TABLE_NAME).findFirst();

        if (syncHistory == null) {
            syncHistory = realm.createObject(SyncHistory.class);
            syncHistory.setNameTable(CATEGORY_TABLE_NAME);
        }

        syncHistory.setLastSyncTimestamp(latestSynchronizeTimestamp);

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
}
