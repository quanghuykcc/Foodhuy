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

    public RealmResults<Category> getAllCategories() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Category> categories = realm.where(Category.class).findAll();
        return categories;
    }

    public void getAllCategoriesAsync(final ICallback<List<Category>> callback) {
        final Realm realm = Realm.getDefaultInstance();

        final RealmResults<Category> categories = realm.where(Category.class).findAllAsync();
        categories.addChangeListener(new RealmChangeListener<RealmResults<Category>>() {
            @Override
            public void onChange(RealmResults<Category> element) {
                List<Category> unmanagedCategories = realm.copyFromRealm(element);
                callback.onResult(unmanagedCategories);
                categories.removeChangeListener(this);
            }
        });
    }

    private void addNewOrUpdateCategory(Category category) {
        Realm realm = Realm.getDefaultInstance();

        realm.copyToRealmOrUpdate(category);
    }


    public Date getLatestSynchronizeTimestamp() {
        Realm realm = Realm.getDefaultInstance();

        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", CATEGORY_TABLE_NAME).findFirst();

        if (syncHistory != null) {
            return syncHistory.getLastSyncTimestamp();
        }

        return null;
    }

    private void updateLatestSynchronizeTimestamp(Date latestSynchronizeTimestamp) {
        Realm realm = Realm.getDefaultInstance();

        SyncHistory syncHistory = realm.where(SyncHistory.class).equalTo("mNameTable", CATEGORY_TABLE_NAME).findFirst();

        if (syncHistory == null) {
            syncHistory = realm.createObject(SyncHistory.class);
            syncHistory.setNameTable(CATEGORY_TABLE_NAME);
        }

        syncHistory.setLastSyncTimestamp(latestSynchronizeTimestamp);
    }

    public void handleFetchedCategories(List<Category> categories, Date latestSynchronizeTimestamp) {
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        for (Category category : categories) {

            Log.d(TAG, category.toString());
            if (category.isDeleted()) {
                deleteCategory(category);
            }
            else {
                addNewOrUpdateCategory(category);
            }
        }

        updateLatestSynchronizeTimestamp(latestSynchronizeTimestamp);

        realm.commitTransaction();
    }


    private void deleteCategory(Category category) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Category> deleteCategories = realm.where(Category.class).equalTo("mId", category.getId()).findAll();
        if (deleteCategories.size() > 0) {
            deleteCategories.deleteAllFromRealm();
        }
    }

}
