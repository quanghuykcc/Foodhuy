package com.devlin.core.model.services.storages;

import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.services.ICategoryService;
import com.devlin.core.view.ICallback;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Administrator on 8/2/2016.
 */
public class CategoryStorageService extends BaseStorageService implements ICategoryService {

    public CategoryStorageService(Realm realm) {
        super(realm);
    }

    @Override
    public void getAllCategories(final ICallback<List<Category>> callback) {
        final RealmResults<Category> categories = mRealm.where(Category.class).findAllAsync();
        categories.addChangeListener(new RealmChangeListener<RealmResults<Category>>() {
            @Override
            public void onChange(RealmResults<Category> element) {
                callback.onResult(element);

                categories.removeChangeListener(this);
            }
        });
    }

    @Override
    public void saveCategories(final List<Category> categories, final ICallback<Boolean> callback) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.copyToRealmOrUpdate(categories);

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
}
