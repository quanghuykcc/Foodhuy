package com.devlin.core.di;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.devlin.core.job.BasicJob;
import com.devlin.core.view.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 8/24/2016.
 */
@Module(includes = AppModule.class)
public class JobModule {

    @Provides
    @Singleton
    JobManager providesJobManager(BaseApplication baseApplication) {
        Configuration.Builder builder = new Configuration.Builder(baseApplication)
                .minConsumerCount(1)
                .maxConsumerCount(3)
                .loadFactor(3)
                .consumerKeepAlive(120);

        return new JobManager(builder.build());
    }


}
