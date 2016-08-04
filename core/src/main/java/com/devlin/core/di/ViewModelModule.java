package com.devlin.core.di;

import com.devlin.core.model.services.storages.CategoryStorageService;
import com.devlin.core.model.services.storages.RestaurantStorageService;
import com.devlin.core.model.services.storages.UserStorageService;
import com.devlin.core.view.INavigator;
import com.devlin.core.viewmodel.AddRestaurantViewModel;
import com.devlin.core.viewmodel.CategoryViewModel;
import com.devlin.core.viewmodel.LatestRestaurantViewModel;
import com.devlin.core.viewmodel.LoginViewModel;
import com.devlin.core.viewmodel.MainViewModel;
import com.devlin.core.viewmodel.RegisterViewModel;
import com.devlin.core.viewmodel.RestaurantByCategoryViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 7/30/2016.
 */

@Module(includes = { CloudModule.class, StorageModule.class })
public class ViewModelModule {

    //region Provide methods

    @Provides
    @Singleton
    public RestaurantByCategoryViewModel providesRestaurantByCategoryViewModel(INavigator navigator, RestaurantStorageService storageService) {
        return new RestaurantByCategoryViewModel(navigator, storageService);
    }

    @Provides
    @Singleton
    public CategoryViewModel providesCategoryViewModel(INavigator navigator, CategoryStorageService storageService) {
        return new CategoryViewModel(navigator, storageService);
    }

    @Provides
    @Singleton
    public AddRestaurantViewModel providesAddRestaurantViewModel(INavigator navigator, RestaurantStorageService storageService) {
        return new AddRestaurantViewModel(navigator, storageService);
    }

    @Provides
    @Singleton
    MainViewModel providesMainViewModel(INavigator navigator) {
        return new MainViewModel(navigator);
    }

    @Provides
    @Singleton
    LoginViewModel providesLoginViewModel(INavigator navigator, UserStorageService storageService) {
        return new LoginViewModel(navigator, storageService);
    }

    @Provides
    @Singleton
    RegisterViewModel providesRegisterViewModel(INavigator navigator, UserStorageService storageService) {
        return new RegisterViewModel(navigator, storageService);
    }

    @Provides
    @Singleton
    LatestRestaurantViewModel providesLatestRestaurantViewModel(INavigator navigator, RestaurantStorageService storageService) {
        return new LatestRestaurantViewModel(navigator, storageService);
    }

    //endregion

}
