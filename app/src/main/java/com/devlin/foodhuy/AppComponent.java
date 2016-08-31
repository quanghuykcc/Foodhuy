package com.devlin.foodhuy;

import com.devlin.core.di.ViewModelModule;
import com.devlin.foodhuy.activities.AddRestaurantActivity;
import com.devlin.foodhuy.activities.CommentActivity;
import com.devlin.foodhuy.activities.LoginActivity;
import com.devlin.foodhuy.activities.MainActivity;
import com.devlin.foodhuy.activities.RegisterActivity;
import com.devlin.foodhuy.activities.RestaurantActivity;
import com.devlin.foodhuy.fragments.CategoryFragment;
import com.devlin.foodhuy.fragments.FavoriteRestaurantFragment;
import com.devlin.foodhuy.fragments.LatestRestaurantFragment;
import com.devlin.foodhuy.fragments.RestaurantByCategoryFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 7/30/2016.
 */
@Singleton
@Component(modules = {ViewModelModule.class })
public interface AppComponent {

    //region Activities

    void inject(AddRestaurantActivity activity);

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(RegisterActivity activity);

    void inject(RestaurantActivity activity);

    void inject(CommentActivity activity);

    //endregion

    //region Fragments

    void inject(RestaurantByCategoryFragment fragment);

    void inject(CategoryFragment fragment);

    void inject(LatestRestaurantFragment fragment);

    void inject(FavoriteRestaurantFragment fragment);

    //endregion

}
