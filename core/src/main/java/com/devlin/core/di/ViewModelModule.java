package com.devlin.core.di;

import com.birbit.android.jobqueue.JobManager;
import com.devlin.core.model.services.clouds.ICategoryService;
import com.devlin.core.model.services.clouds.ICommentService;
import com.devlin.core.model.services.clouds.IFavoriteRestaurantService;
import com.devlin.core.model.services.clouds.IRestaurantService;
import com.devlin.core.model.services.clouds.IUserService;
import com.devlin.core.model.services.storages.CategoryModel;
import com.devlin.core.model.services.storages.FavoriteRestaurantModel;
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
    public RestaurantByCategoryViewModel providesRestaurantByCategoryViewModel(INavigator navigator, IRestaurantService restaurantService, RestaurantModel restaurantModel, JobManager jobManager) {
        return new RestaurantByCategoryViewModel(navigator, restaurantService, restaurantModel, jobManager);
    }

    @Provides
    @Singleton
    public CategoryViewModel providesCategoryViewModel(INavigator navigator, CategoryModel categoryStorageService, JobManager jobManager, ICategoryService categoryService) {
        return new CategoryViewModel(navigator, categoryStorageService, jobManager, categoryService);
    }

    @Provides
    @Singleton
    public AddRestaurantViewModel providesAddRestaurantViewModel(INavigator navigator, RestaurantModel storageService) {
        return new AddRestaurantViewModel(navigator, storageService);
    }

    @Provides
    @Singleton
    MainViewModel providesMainViewModel(INavigator navigator, FavoriteRestaurantModel favoriteRestaurantModel, UserModel userModel, IUserService userService, IFavoriteRestaurantService favoriteRestaurantService, JobManager jobManager) {
        return new MainViewModel(navigator, favoriteRestaurantModel, userModel, userService, favoriteRestaurantService, jobManager);
    }

    @Provides
    @Singleton
    LoginViewModel providesLoginViewModel(INavigator navigator, UserModel userModel, IUserService userService, JobManager jobManager) {
        return new LoginViewModel(navigator, userModel, userService, jobManager);
    }

    @Provides
    @Singleton
    RegisterViewModel providesRegisterViewModel(INavigator navigator, IUserService userService, JobManager jobManager) {
        return new RegisterViewModel(navigator, userService, jobManager);
    }

    @Provides
    @Singleton
    LatestRestaurantViewModel providesLatestRestaurantViewModel(INavigator navigator, RestaurantModel restaurantModel, IRestaurantService restaurantService, JobManager jobManager, FavoriteRestaurantModel favoriteRestaurantModel, IFavoriteRestaurantService iFavoriteRestaurantService) {
        return new LatestRestaurantViewModel(navigator, restaurantModel, restaurantService, jobManager, favoriteRestaurantModel, iFavoriteRestaurantService);
    }

    @Provides
    @Singleton
    FavoriteRestaurantViewModel providesFavoriteRestaurantViewModel(INavigator navigator, IRestaurantService restaurantService, RestaurantModel restaurantModel, JobManager jobManager) {
        return new FavoriteRestaurantViewModel(navigator, restaurantService, restaurantModel, jobManager);
    }

    @Provides
    @Singleton
    RestaurantViewModel providesRestaurantViewModelViewModel(INavigator navigator, FavoriteRestaurantModel favoriteRestaurantModel, IFavoriteRestaurantService iFavoriteRestaurantService, JobManager jobManager) {
        return new RestaurantViewModel(navigator, favoriteRestaurantModel, iFavoriteRestaurantService, jobManager);
    }

    @Provides
    @Singleton
    CommentViewModel providesCommentViewModelViewModel(INavigator navigator, ICommentService commentService) {
        return new CommentViewModel(navigator, commentService);
    }

    //endregion

}
