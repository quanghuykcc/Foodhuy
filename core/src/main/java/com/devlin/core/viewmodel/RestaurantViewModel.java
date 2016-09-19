package com.devlin.core.viewmodel;

import android.databinding.Bindable;

import com.birbit.android.jobqueue.JobManager;
import com.devlin.core.BR;
import com.devlin.core.event.ChangeFavoriteStatusEvent;
import com.devlin.core.job.AddNewFavoriteJob;
import com.devlin.core.job.BasicJob;
import com.devlin.core.job.DeleteFavoriteJob;
import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.services.clouds.IFavoriteRestaurantService;
import com.devlin.core.model.services.storages.FavoriteRestaurantModel;
import com.devlin.core.model.services.storages.RestaurantModel;
import com.devlin.core.model.services.storages.UserModel;
import com.devlin.core.view.Constants;
import com.devlin.core.view.INavigator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.realm.Realm;

/**
 * Created by Administrator on 07-Aug-16.
 */
public class RestaurantViewModel extends BaseViewModel {

    //region Properties

    private Restaurant mRestaurant;

    private boolean mIsFavorite;

    private FavoriteRestaurantModel mFavoriteRestaurantModel;

    private IFavoriteRestaurantService mIFavoriteRestaurantService;

    private JobManager mJobManager;

    //endregion

    //region Constructors

    public RestaurantViewModel(INavigator navigator, FavoriteRestaurantModel favoriteRestaurantModel, IFavoriteRestaurantService iFavoriteRestaurantService, JobManager jobManager) {
        super(navigator);

        mFavoriteRestaurantModel = favoriteRestaurantModel;

        mIFavoriteRestaurantService = iFavoriteRestaurantService;

        mJobManager = jobManager;
    }

    //endregion

    //region Getters and Setters

    @Bindable
    public Restaurant getRestaurant() {
        return mRestaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        mRestaurant = restaurant;

        notifyPropertyChanged(BR.restaurant);
    }

    @Bindable
    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean favorite) {
        mIsFavorite = favorite;

        notifyPropertyChanged(BR.favorite);
    }

    //endregion

    //region Override Methods


    @Override
    public INavigator getNavigator() {
        return super.getNavigator();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mIsFavorite = false;

        register();
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

        mIsFavorite = false;

        unregister();
    }

    //endregion

    //region Subscribe methods

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void event(Restaurant restaurant) {
        setRestaurant(restaurant);

        if (getNavigator().getApplication().isUserLoggedIn()
                && mFavoriteRestaurantModel.isFavorite(getNavigator().getApplication().getLoginUser().getId(), restaurant.getId())) {
            setFavorite(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(ChangeFavoriteStatusEvent changeFavoriteStatusEvent) {
        setFavorite(!mIsFavorite);
    }

    //endregion

    //region Public methods

    public void changeFavoriteStatusCommand() {
        if (getNavigator().getApplication().isUserLoggedIn()) {
            FavoriteRestaurant favoriteRestaurant = new FavoriteRestaurant();
            favoriteRestaurant.setRestaurantId(mRestaurant.getId());
            favoriteRestaurant.setUserId(getNavigator().getApplication().getLoginUser().getId());

            if (mIsFavorite) {
                setFavorite(false);
                mJobManager.addJobInBackground(new DeleteFavoriteJob(BasicJob.UI_HIGH, mFavoriteRestaurantModel, mIFavoriteRestaurantService, favoriteRestaurant));
                return;
            } else {
                setFavorite(true);
                int nextId = Realm.getDefaultInstance().where(FavoriteRestaurant.class).max("mId").intValue() + 1;
                favoriteRestaurant.setId(nextId);
                mJobManager.addJobInBackground(new AddNewFavoriteJob(BasicJob.UI_HIGH, mFavoriteRestaurantModel, mIFavoriteRestaurantService, favoriteRestaurant));
            }

        } else {
            getNavigator().navigateTo(Constants.LOGIN_PAGE);
            return;
        }
    }

    //endregion

}
