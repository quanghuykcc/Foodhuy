package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.util.Log;

import com.birbit.android.jobqueue.JobManager;
import com.devlin.core.BR;
import com.devlin.core.event.FetchedCategoryEvent;
import com.devlin.core.job.BasicJob;
import com.devlin.core.job.FetchCategoryJob;
import com.devlin.core.model.entities.Category;
import com.devlin.core.model.services.clouds.ICategoryService;
import com.devlin.core.model.services.storages.CategoryModel;
import com.devlin.core.view.INavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Administrator on 7/29/2016.
 */
public class CategoryViewModel extends BaseViewModel {

    //region Properties

    private static final String TAG = "CategoryViewModel";

    private RealmResults<Category> mCategories;

    private CategoryModel mCategoryStorageService;


    private ICategoryService mICategoryService;

    private JobManager mJobManager;

    //endregion

    //region Constructors

    public CategoryViewModel(INavigator navigator, CategoryModel categoryStorageService, JobManager jobManager, ICategoryService categoryService) {
        super(navigator);

        mCategoryStorageService = categoryStorageService;

        mJobManager = jobManager;

        mICategoryService = categoryService;
    }

    protected CategoryViewModel() {
        super();
    }

    //endregion

    //region Getter and Setter


    @Override
    public EventBus getEventBus() {
        return super.getEventBus();
    }

    @Bindable
    public RealmResults<Category> getCategories() {
        return mCategories;
    }

    public void setCategories(RealmResults<Category> categories) {
        mCategories = categories;

        notifyPropertyChanged(BR.categories);
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        getNavigator().showBusyIndicator("Đang tải...");

        getEventBus().register(this);

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

        mCategories = null;

        getEventBus().unregister(this);
    }

    //endregion

    //region Private methods

    private void loadCategories() {
        RealmResults<Category> categories = mCategoryStorageService.getAllCategories();
        setCategories(categories);
        getNavigator().hideBusyIndicator();

        categories.addChangeListener(new RealmChangeListener<RealmResults<Category>>() {
            @Override
            public void onChange(RealmResults<Category> element) {
                setCategories(element);
            }
        });

        mJobManager.addJobInBackground(new FetchCategoryJob(BasicJob.UI_HIGH, mICategoryService, mCategoryStorageService));
    }

    //endregion

    //region Public Methods

    public void showRestaurantsByCategory(Category category) {
        getEventBus().postSticky(category);
    }

    @Subscribe
    public void event(FetchedCategoryEvent fetchedCategoryEvent) {
        if (fetchedCategoryEvent.isSuccess()) {
            Log.d(TAG, "Fetched categories successfully");
        }
        else {
            Log.d(TAG, "Fetched categories failed");
        }
    }

    //endregion




}
