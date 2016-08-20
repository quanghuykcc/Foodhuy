package com.devlin.core.di;

import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.services.Configuration;
import com.devlin.core.model.services.IUserService;
import com.devlin.core.model.services.clouds.CategoryCloudService;
import com.devlin.core.model.services.clouds.ICategoryCloudService;
import com.devlin.core.model.services.clouds.IRestaurantCloudService;
import com.devlin.core.model.services.clouds.IUserCloudService;
import com.devlin.core.model.services.clouds.RestaurantCloudService;
import com.devlin.core.model.services.clouds.UserCloudService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.annotations.PrimaryKey;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 7/26/2016.
 */

@Module(includes = { AppModule.class })
public class CloudModule {

    //region Provide methods
    @Provides
    @Singleton
    public IUserCloudService providesUserService() {
        Gson gson = createGson();

        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(Configuration.FOODHUY_API_URL)
                                        .addConverterFactory(GsonConverterFactory.create(gson))
                                        .build();

        return retrofit.create(IUserCloudService.class);
    }

    @Provides
    @Singleton
    public IRestaurantCloudService providesRestaurantService() {
        Gson gson = createGson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.FOODHUY_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(IRestaurantCloudService.class);
    }

    @Provides
    @Singleton
    public ICategoryCloudService providesCategoryService() {
        Gson gson = createGson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.FOODHUY_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ICategoryCloudService.class);
    }

    @Provides
    @Singleton
    public RestaurantCloudService providesRestaurantCloudService(IRestaurantCloudService iRestaurantCloudService) {
        return new RestaurantCloudService(iRestaurantCloudService);
    }

    @Provides
    @Singleton
    public UserCloudService providesUserCloudService(IUserCloudService iUserCloudService) {
        return new UserCloudService(iUserCloudService);
    }

    @Provides
    @Singleton
    public CategoryCloudService providesCategoryCloudService(ICategoryCloudService iCategoryCloudService) {
        return new CategoryCloudService(iCategoryCloudService);
    }

    //endregion

    //region Private methods

    private Gson createGson() {
        return new GsonBuilder().setLenient()
                                .setDateFormat(Configuration.TIMESTAMP_FORMAT)
                                .create();
    }

    //endregion
}
