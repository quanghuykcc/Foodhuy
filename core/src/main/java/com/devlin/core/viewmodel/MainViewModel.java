package com.devlin.core.viewmodel;

import com.devlin.core.view.INavigator;
import com.devlin.core.view.Navigator;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 7/26/2016.
 */
public class MainViewModel extends BaseViewModel {

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

    //endregion

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();
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

}
