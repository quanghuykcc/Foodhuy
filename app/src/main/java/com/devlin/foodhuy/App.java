package com.devlin.foodhuy;

import com.devlin.core.di.AppModule;
import com.devlin.core.di.CloudModule;
import com.devlin.core.di.JobModule;
import com.devlin.core.di.StorageModule;
import com.devlin.core.di.ViewModelModule;
import com.devlin.core.view.BaseApplication;
import com.devlin.core.view.Constants;
import com.devlin.core.view.INavigator;
import com.devlin.foodhuy.activities.AddRestaurantActivity;
import com.devlin.foodhuy.activities.CommentActivity;
import com.devlin.foodhuy.activities.LoginActivity;
import com.devlin.foodhuy.activities.MainActivity;
import com.devlin.foodhuy.activities.RegisterActivity;
import com.devlin.foodhuy.activities.RestaurantActivity;

/**
 * Created by Administrator on 7/25/2016.
 */
public class App extends BaseApplication {

    //region Properties

    private static AppComponent sAppComponent;

    //endregion

    //region Getter and Setter

    public synchronized static AppComponent sharedComponent() {
        return sAppComponent;
    }

    //endregion

    //region Lifecycle


    @Override
    public void onCreate() {
        super.onCreate();

        AppModule appModule = new AppModule(this);
        INavigator navigator = appModule.getNavigator();
        navigator.configure(Constants.MAIN_PAGE, MainActivity.class);
        navigator.configure(Constants.LOGIN_PAGE, LoginActivity.class);
        navigator.configure(Constants.REGISTER_PAGE, RegisterActivity.class);
        navigator.configure(Constants.ADD_RESTAURANT_PAGE, AddRestaurantActivity.class);
        navigator.configure(Constants.RESTAURANT_DETAIL_PAGE, RestaurantActivity.class);
        navigator.configure(Constants.COMMENT_PAGE, CommentActivity.class);

        sAppComponent = DaggerAppComponent.builder()
                .appModule(appModule)
                .cloudModule(new CloudModule())
                .jobModule(new JobModule())
                .storageModule(new StorageModule())
                .viewModelModule(new ViewModelModule())
                .build();
    }
}
