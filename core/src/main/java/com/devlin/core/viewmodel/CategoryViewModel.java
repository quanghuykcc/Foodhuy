package com.devlin.core.viewmodel;

import android.databinding.Bindable;

import com.birbit.android.jobqueue.JobManager;
import com.devlin.core.BR;
import com.devlin.core.event.ReplaceCategoriesEvent;
import com.devlin.core.event.ShowRestaurantsByCategoryEvent;
import com.devlin.core.job.BasicJob;
import com.devlin.core.job.FetchCategoryJob;
import com.devlin.core.model.entities.Category;
import com.devlin.core.model.services.clouds.ICategoryService;
import com.devlin.core.model.services.storages.CategoryModel;
import com.devlin.core.view.INavigator;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by Administrator on 7/29/2016.
 */
public class CategoryViewModel extends BaseViewModel {

    //region Properties

    private List<Category> mCategories;

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
        register();
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
        unregister();
        mCategories = null;
    }

    //endregion

    //region Private methods

    public void loadCategories() {
        mJobManager.addJobInBackground(new FetchCategoryJob(BasicJob.UI_HIGH, mICategoryService, mCategoryStorageService));
    }

    //endregion

    //region Public Methods

    public void showRestaurantsByCategoryCommand(Category category) {
        postSticky(new ShowRestaurantsByCategoryEvent(category));
    }

    //endregion

    //region Subcribe methods

    @Subscribe
    public void event(ReplaceCategoriesEvent fetchedCategoryEvent) {
        setCategories(fetchedCategoryEvent.getCategories());
    }

    //endregion

}
