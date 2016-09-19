package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.JobManager;
import com.devlin.core.BR;
import com.devlin.core.R;
import com.devlin.core.event.AddMoreRestaurantsEvent;
import com.devlin.core.event.ReplaceRestaurantsEvent;
import com.devlin.core.event.ShowRestaurantsByCategoryEvent;
import com.devlin.core.job.BasicJob;
import com.devlin.core.job.ShowRestaurantsByCategoryJob;
import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.Configuration;
import com.devlin.core.model.services.clouds.IRestaurantService;
import com.devlin.core.model.services.storages.RestaurantModel;
import com.devlin.core.model.services.storages.UserModel;
import com.devlin.core.view.Constants;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 7/27/2016.
 */

public class RestaurantByCategoryViewModel extends BaseViewModel {

    //region Properties

    private ObservableArrayList<Restaurant> mRestaurants;

    private Category mCategory;

    private IRestaurantService mRestaurantService;

    private RestaurantModel mRestaurantModel;

    private JobManager mJobManager;

    //endregion

    //region Getter and Setter

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    @Bindable
    public List<Restaurant> getRestaurants() {
        return mRestaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        mRestaurants = new ObservableArrayList<>();
        mRestaurants.addAll(restaurants);
        notifyPropertyChanged(BR.restaurants);
    }

    //endregion

    //region Constructors

    public RestaurantByCategoryViewModel(INavigator navigator, IRestaurantService restaurantService, RestaurantModel restaurantModel, JobManager jobManager) {
        super(navigator);

        mRestaurantService = restaurantService;

        mRestaurantModel = restaurantModel;

        mJobManager = jobManager;
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        register();

        loadPage(0);
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
        mCategory = null;
        mRestaurants = null;
    }

    //endregion

    //region Public methods

    public void loadPage(int offset) {
        mJobManager.addJobInBackground(new ShowRestaurantsByCategoryJob(BasicJob.UI_HIGH, offset, mCategory, mRestaurantService, mRestaurantModel));
    }

    public void showRestaurantDetailsCommand(Restaurant restaurant) {
        getNavigator().navigateTo(Constants.RESTAURANT_DETAIL_PAGE);

        postSticky(restaurant);
    }

    public void inputCommentCommand(Restaurant restaurant) {
        if (getNavigator().getApplication().isUserLoggedIn()) {
            getNavigator().navigateTo(Constants.COMMENT_PAGE);
            postSticky(restaurant);
        } else {
            getNavigator().navigateTo(Constants.LOGIN_PAGE);
        }
    }

    //endregion

    //region Subscriber methods

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void event(ShowRestaurantsByCategoryEvent event) {
        setCategory(event.getCategory());

        getNavigator().getApplication().getCurrentActivity().setTitle(mCategory.getName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(ReplaceRestaurantsEvent replaceRestaurantsEvent) {
        setRestaurants(replaceRestaurantsEvent.getRestaurants());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(AddMoreRestaurantsEvent addMoreRestaurantsEvent) {
        mRestaurants.addAll(addMoreRestaurantsEvent.getRestaurants());
    }

    //endregion

}
