package com.devlin.core.di;

import com.birbit.android.jobqueue.JobManager;
import com.devlin.core.model.services.clouds.CategoryCloudService;
import com.devlin.core.model.services.clouds.ICategoryService;
import com.devlin.core.model.services.clouds.IRestaurantService;
import com.devlin.core.model.services.clouds.IUserService;
import com.devlin.core.model.services.clouds.RestaurantCloudService;
import com.devlin.core.model.services.clouds.UserCloudService;
import com.devlin.core.model.services.storages.CategoryModel;
import com.devlin.core.model.services.storages.RestaurantModel;
import com.devlin.core.model.services.storages.UserModel;
import com.devlin.core.view.INavigator;
import com.devlin.core.viewmodel.AddRestaurantViewModel;
import com.devlin.core.viewmodel.CategoryViewModel;
import com.devlin.core.viewmodel.CommentViewModel;
import com.devlin.core.viewmodel.FavoriteRestaurantViewModel;
import com.devlin.core.viewmodel.LatestRestaurantViewModel;
import com.devlin.core.viewmodel.LoginViewModel;
import com.devlin.core.viewmodel.MainViewModel;
import com.devlin.core.viewmodel.RegisterViewModel;
import com.devlin.core.viewmodel.RestaurantByCategoryViewModel;
import com.devlin.core.viewmodel.RestaurantViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 7/30/2016.
 */

@Module(includes = { CloudModule.class, StorageModule.class, JobModule.class })
public class ViewModelModule {

    //region Provide methods

    @Provides
    @Singleton
    public RestaurantByCategoryViewModel providesRestaurantByCategoryViewModel(INavigator navigator, RestaurantModel restaurantStorageService, UserModel userStorageService, RestaurantCloudService restaurantCloudService) {
        return new RestaurantByCategoryViewModel(navigator, restaurantStorageService, userStorageService, restaurantCloudService);
    }

    @Provides
    @Singleton
    public CategoryViewModel providesCategoryViewModel(INavigator navigator, CategoryModel categoryStorageService, CategoryCloudService categoryCloudService, JobManager jobManager, ICategoryService categoryService) {
        return new CategoryViewModel(navigator, categoryStorageService, categoryCloudService, jobManager, categoryService);
    }

    @Provides
    @Singleton
    public AddRestaurantViewModel providesAddRestaurantViewModel(INavigator navigator, RestaurantModel storageService) {
        return new AddRestaurantViewModel(navigator, storageService);
    }

    @Provides
    @Singleton
    MainViewModel providesMainViewModel(INavigator navigator) {
        return new MainViewModel(navigator);
    }

    @Provides
    @Singleton
    LoginViewModel providesLoginViewModel(INavigator navigator, UserModel userModel, IUserService userService, JobManager jobManager) {
        return new LoginViewModel(navigator, userModel, userService, jobManager);
    }

    @Provides
    @Singleton
    RegisterViewModel providesRegisterViewModel(INavigator navigator, UserModel storageService) {
        return new RegisterViewModel(navigator, storageService);
    }

    @Provides
    @Singleton
    LatestRestaurantViewModel providesLatestRestaurantViewModel(INavigator navigator, RestaurantModel restaurantModel, IRestaurantService restaurantService, JobManager jobManager) {
        return new LatestRestaurantViewModel(navigator, restaurantModel, restaurantService, jobManager);
    }

    @Provides
    @Singleton
    FavoriteRestaurantViewModel providesFavoriteRestaurantViewModel(INavigator navigator, RestaurantModel restaurantStorageService, UserModel userStorageService) {
        return new FavoriteRestaurantViewModel(navigator, restaurantStorageService, userStorageService);
    }

    @Provides
    @Singleton
    RestaurantViewModel providesRestaurantViewModelViewModel(INavigator navigator, RestaurantModel restaurantStorageService, UserModel userStorageService) {
        return new RestaurantViewModel(navigator, restaurantStorageService, userStorageService);
    }

    @Provides
    @Singleton
    CommentViewModel providesCommentViewModelViewModel(INavigator navigator, RestaurantModel restaurantStorageService) {
        return new CommentViewModel(navigator, restaurantStorageService);
    }

    //endregion

}
