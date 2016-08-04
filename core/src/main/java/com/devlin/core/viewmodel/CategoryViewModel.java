package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.util.Log;

import com.devlin.core.BR;
import com.devlin.core.R;
import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.services.storages.CategoryStorageService;
import com.devlin.core.view.Constants;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;
import com.devlin.core.view.Navigator;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Administrator on 7/29/2016.
 */
public class CategoryViewModel extends BaseViewModel {

    //region Properties

    private static final String TAG = "CategoryViewModel";

    private List<Category> mCategories;

    private CategoryStorageService mCategoryStorageService;

    //endregion

    //region Constructors

    public CategoryViewModel(INavigator navigator, CategoryStorageService storageService) {
        super(navigator);

        mCategoryStorageService = storageService;
    }

    protected CategoryViewModel() {
        super();
    }

    //endregion

    //region Getter and Setter

    @Bindable
    public List<Category> getCategories() {
        return mCategories;
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;

        notifyPropertyChanged(BR.categories);
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        loadCategories();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //endregion

    //region Private methods

    private void loadCategories() {
        mCategoryStorageService.getAllCategories(new ICallback<List<Category>>() {
            @Override
            public void onResult(List<Category> result) {
                Log.d(TAG, "DONE");
                setCategories(result);
                for (Category category : result) {
                    Log.d(TAG, category.toString());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    //endregion

    //region Public Methods

    public void showRestaurantsByCategory(Category category) {
        getEventBus().postSticky(category);
    }

    //endregion




}
