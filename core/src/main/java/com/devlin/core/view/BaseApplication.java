package com.devlin.core.view;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.devlin.core.model.entities.FavoriteRestaurant;
import com.devlin.core.model.entities.User;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 7/25/2016.
 */
public class BaseApplication extends Application implements  Application.ActivityLifecycleCallbacks {

    //region Properties

    private Activity mCurrentActivity;

    private transient User mLoginUser = null;

    private List<FavoriteRestaurant> mFavoriteRestaurantsOfUser;

    //endregion

    //region Setters and Getters

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public boolean isCurrentActivityAvailable() {
        return mCurrentActivity != null;
    }

    public List<FavoriteRestaurant> getFavoriteRestaurantsOfUser() {
        return mFavoriteRestaurantsOfUser;
    }

    public void setFavoriteRestaurantsOfUser(List<FavoriteRestaurant> favoriteRestaurantsOfUser) {
        mFavoriteRestaurantsOfUser = favoriteRestaurantsOfUser;
    }

    public User getLoginUser() {
        return mLoginUser;
    }

    public boolean isUserLoggedIn() {
        return mLoginUser != null;
    }

    public void setLoginUser(User loginUser) {
        this.mLoginUser = loginUser;
    }

    //endregion

    //region Application Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(this);

        mFavoriteRestaurantsOfUser = new ArrayList<>();

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getApplicationContext()).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    //endregion

    //region ActivityLifecycleCallbacks implement
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (mCurrentActivity != activity) {
            mCurrentActivity = activity;
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (mCurrentActivity != activity) {
            mCurrentActivity = activity;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    //endregion
}
