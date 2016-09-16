package com.devlin.core.viewmodel;

import android.app.usage.UsageEvents;
import android.databinding.BaseObservable;
import android.widget.Toast;

import com.devlin.core.view.INavigator;
import com.devlin.core.view.Navigator;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 7/25/2016.
 */

public abstract class BaseViewModel extends BaseObservable implements IViewModelLifecycle {

    //region Properties

    private INavigator mNavigator;

    //endregion

    //region Setters and Getters

    protected INavigator getNavigator() {
        return mNavigator;
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

    //region Protected methods

    protected static final void post(Object event) {
        EventBus.getDefault().post(event);
    }

    protected  static final void postSticky(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    protected final void register() {
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    protected final void unregister() {
        EventBus eventBus = EventBus.getDefault();
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }

    }

    protected final void showToast(String message, int toastLength) {
        Toast.makeText(getNavigator().getApplication().getCurrentActivity(), message, toastLength).show();
    }

    //endregion

}
