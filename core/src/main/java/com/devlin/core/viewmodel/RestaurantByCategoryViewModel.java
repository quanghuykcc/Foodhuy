package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.devlin.core.BR;
import com.devlin.core.R;
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

    private static final String TAG = "RestaurantByCategoryViewModel";

    private ObservableArrayList<Restaurant> mRestaurants;

    private Category mCategory;

    private IRestaurantService mIRestaurantService;


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

    @Override
    public EventBus getEventBus() {
        return super.getEventBus();
    }

    //endregion

    //region Constructors

    public RestaurantByCategoryViewModel(INavigator navigator, IRestaurantService restaurantService) {
        super(navigator);

        mIRestaurantService = restaurantService;
    }

    public RestaurantByCategoryViewModel() {
        super();
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();
        getEventBus().register(this);
        loadFirstPage();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

        getEventBus().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mCategory = null;
        mRestaurants = null;
    }

    //endregion

    //region Subscribe Methods

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void event(Category category) {
        getNavigator().getApplication().getCurrentActivity().setTitle(category.getName());

        setCategory(category);
    }

    //endregion

    //region Private Methods

    private void loadFirstPage() {
        mIRestaurantService.getRestaurantsByCategory(mCategory.getId(), 0, Configuration.NUMBER_RECORDS_PER_PAGE).enqueue(new Callback<APIResponse<List<Restaurant>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Restaurant>>> call, Response<APIResponse<List<Restaurant>>> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {
                    setRestaurants(response.body().getData());
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Restaurant>>> call, Throwable t) {

            }
        });
    }

    //endregion

    //region Public Methods

    public void showRestaurantDetails(Restaurant restaurant) {
        getNavigator().navigateTo(Constants.RESTAURANT_DETAIL_PAGE);

        getEventBus().postSticky(restaurant);
    }

    public void handleCommentViewClick(Restaurant restaurant) {
        if (getNavigator().getApplication().isUserLoggedIn()) {
            getNavigator().navigateTo(Constants.COMMENT_PAGE);

            getEventBus().postSticky(restaurant);

            return;
        }
        else {
            getNavigator().navigateTo(Constants.LOGIN_PAGE);
        }

    }

    public void getNextPageRestaurants(long currentOffset) {
        long nextOffset = currentOffset + 1;
        mIRestaurantService.getRestaurantsByCategory(mCategory.getId(), nextOffset, Configuration.NUMBER_RECORDS_PER_PAGE).enqueue(new Callback<APIResponse<List<Restaurant>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Restaurant>>> call, Response<APIResponse<List<Restaurant>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        mRestaurants.addAll(response.body().getData());
                    }
                }
            }
            @Override
            public void onFailure(Call<APIResponse<List<Restaurant>>> call, Throwable t) {
            }
        });
    }

    //endregion
}
