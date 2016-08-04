package com.devlin.core.di;

import com.devlin.core.model.services.Configuration;
import com.devlin.core.model.services.IUserService;
import com.devlin.core.model.services.clouds.IUserCloudService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    public IUserCloudService providesUserService() {
        Gson gson = createGson();

        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(Configuration.FOODHUY_API_URL)
                                        .addConverterFactory(GsonConverterFactory.create(gson))
                                        .build();

        return retrofit.create(IUserCloudService.class);
    }

    //endregion

    //region Private methods

    private Gson createGson() {
        return new GsonBuilder().setLenient()
                                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                                .create();
    }

    //endregion
}
