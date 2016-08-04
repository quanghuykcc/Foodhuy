package com.devlin.core.viewmodel;

import android.databinding.BaseObservable;

import com.devlin.core.view.INavigator;
import com.devlin.core.view.Navigator;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 7/25/2016.
 */
public abstract class BaseViewModel extends BaseObservable implements IViewModelLifecycle {

    //region Properties

    private INavigator mNavigator;

    private EventBus mEventBus;

    //endregion

    //region Setters and Getters

    protected INavigator getNavigator() {
        return mNavigator;
    }

    protected EventBus getEventBus() {
        if (mEventBus == null) {
            mEventBus = EventBus.getDefault();
        }
        return mEventBus;
    }

    //endregion

    //region Constructors;



    protected BaseViewModel(INavigator navigator) {
        mNavigator = navigator;
    }

    protected BaseViewModel() {
        super();
    }

    //endregion

    //region IViewModelLifecycle implements

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    //endregion
}
