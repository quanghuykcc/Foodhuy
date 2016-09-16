package com.devlin.core.di;

import com.devlin.core.model.entities.Comment;
import com.devlin.core.model.services.Configuration;
import com.devlin.core.model.services.clouds.ICategoryService;
import com.devlin.core.model.services.clouds.ICommentService;
import com.devlin.core.model.services.clouds.IFavoriteRestaurantService;
import com.devlin.core.model.services.clouds.IRestaurantService;
import com.devlin.core.model.services.clouds.IUserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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
    public IUserService providesUserService() {
        Gson gson = createGson();

        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(Configuration.FOODHUY_API_URL)
                                        .addConverterFactory(GsonConverterFactory.create(gson))
                                        .build();

        return retrofit.create(IUserService.class);
    }

    @Provides
    @Singleton
    public IRestaurantService providesRestaurantService() {
        Gson gson = createGson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.FOODHUY_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(IRestaurantService.class);
    }

    @Provides
    @Singleton
    public ICategoryService providesCategoryService() {
        Gson gson = createGson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.FOODHUY_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ICategoryService.class);
    }

    @Provides
    @Singleton
    public ICommentService providesCommentService() {
        Gson gson = createGson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.FOODHUY_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ICommentService.class);
    }

    @Provides
    @Singleton
    public IFavoriteRestaurantService providesFavoriteRestaurantService() {
        Gson gson = createGson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configuration.FOODHUY_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(IFavoriteRestaurantService.class);
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
