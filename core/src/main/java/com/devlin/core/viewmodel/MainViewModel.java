package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.util.Log;

import com.devlin.core.BR;
import com.devlin.core.event.LoggedInEvent;
import com.devlin.core.model.entities.User;
import com.devlin.core.view.INavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 7/26/2016.
 */
public class MainViewModel extends BaseViewModel {

    //region Properties

    private static final String TAG = "MainViewModel";

    private User mUser;

    //endregion

    //region Constructors

    public MainViewModel(INavigator navigator) {
        super(navigator);
    }

    //endregion

    //region Getter and Setter

    @Override
    public INavigator getNavigator() {
        return super.getNavigator();
    }

    @Override
    public EventBus getEventBus() {
        return super.getEventBus();
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;

        notifyPropertyChanged(BR.user);
    }

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        getEventBus().register(this);
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
    }

    //endregion

    //region Subcribes Methods

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(LoggedInEvent loggedInEvent) {
        if (loggedInEvent.isSuccess()) {
            setUser(loggedInEvent.getLoggedInUser());
        }
    }

    //endregion





}
