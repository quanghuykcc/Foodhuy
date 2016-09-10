package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.util.Log;

import com.devlin.core.model.entities.Bundle;
import com.devlin.core.model.entities.Comment;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.services.storages.RestaurantModel;
import com.devlin.core.view.Constants;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import com.devlin.core.BR;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 08-Aug-16.
 */
public class CommentViewModel extends BaseViewModel {

    //region Properties

    private Comment mComment;

    private Restaurant mRestaurant;

    private RestaurantModel mRestaurantStorageService;

    //endregion

    //region Getter and Setter

    @Bindable
    public Restaurant getRestaurant() {
        return mRestaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        mRestaurant = restaurant;

        notifyPropertyChanged(BR.restaurant);
    }

    public Comment getComment() {
        return mComment;
    }

    public void setComment(Comment comment) {
        mComment = comment;
    }

    //endregion

    //region Constructors

    public CommentViewModel(INavigator navigator, RestaurantModel restaurantStorageService) {
        super(navigator);

        mRestaurantStorageService = restaurantStorageService;
    }

    //endregion

    //region Override Methods

    @Override
    public void onCreate() {
        super.onCreate();

        getEventBus().register(this);

        mComment = new Comment();
        mComment.setContent("");
        mComment.setTitle("");
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

        getEventBus().unregister(this);

        mRestaurant = null;
        mComment = null;
    }

    @Override
    public EventBus getEventBus() {
        return super.getEventBus();
    }

    //endregion

    //region Public Methods

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void event(Restaurant restaurant) {
        setRestaurant(restaurant);
    }

    //endregion

}
