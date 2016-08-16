package com.devlin.foodhuy;

import android.util.Log;

import com.devlin.core.di.AppModule;
import com.devlin.core.di.CloudModule;
import com.devlin.core.di.StorageModule;
import com.devlin.core.di.ViewModelModule;
import com.devlin.core.model.entities.Category;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.services.storages.CategoryStorageService;
import com.devlin.core.model.services.storages.RestaurantStorageService;
import com.devlin.core.view.BaseApplication;
import com.devlin.core.view.Constants;
import com.devlin.core.view.ICallback;
import com.devlin.core.view.INavigator;
import com.devlin.foodhuy.activities.AddRestaurantActivity;
import com.devlin.foodhuy.activities.CommentActivity;
import com.devlin.foodhuy.activities.LoginActivity;
import com.devlin.foodhuy.activities.MainActivity;
import com.devlin.foodhuy.activities.RegisterActivity;
import com.devlin.foodhuy.activities.RestaurantActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

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
                .storageModule(new StorageModule())
                .viewModelModule(new ViewModelModule())
                .build();

        initCategories();
        initRestaurants();

    }

    private void initRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1, 1, "Grill Garden Đà Nẵng", "60 Nguyễn Chí Thanh , Quận Hải Châu, Đà Nẵng", "10:00 AM", "02:00 PM", "20320130213", "CV.png", "Grill Garden có không gian rộng rãi, thoáng mát, được thiết kế hiện đại, sang trọng. Thích hợp để tiếp khách, tổ chức các bữa ăn gia đình, đãi tiệc... Thực đơn chuyên về các món lẩu và nướng. Món ăn đa dạng, tẩm ướp đậm đà. Ngoài ra, buổi trưa, nhà hàng có phục vụ cơm trưa văn phòng. Nhân viên phục vụ lịch sự, thân thiện và tận tình.", new Date(1000), new Date(1000), new Date(1000)));
        restaurants.add(new Restaurant(2, 2, "Highlands Coffee - 74 Bạch Đằng", "74 Bạch Đằng, Quận Hải Châu, Đà Nẵng", "8:00AM", "10:00PM", "20320130213", "CV.png", "Không gian thoáng mát, lịch sự. Menu đa dạng các loại thức uống, pha chế hương vị hấp dẫn. Phục vụ nhanh nhẹn, nhiệt tình", new Date(2000), new Date(2000), new Date(1000)));
        restaurants.add(new Restaurant(3, 3, "Sunway Tea & Coffee", "56D Lê Hồng Phong, Quận Hải Châu, Đà Nẵng", "8:00AM", "10:00PM", "20320130213", "CV.png", "Là rạp chiếu mini tại gia đi kèm với các dịch vụ ăn uống. Menu phong phú món, hương vị ngon, hấp dẫn. Phục vụ chu đáo, nhanh nhẹn với khách hàng.", new Date(5000), new Date(5000), new Date(1000)));
        restaurants.add(new Restaurant(4, 4, "BonPas Bakery & Coffee", "112 Lê Duẩn, Quận Hải Châu, Đà Nẵng", "8:00AM", "11:PM", "20320130213", "CV.png", "Không gian thoáng, lịch sự và thoải mái. Menu đa dạng thức uống, bánh crepe, hương vị ngon. Phục vụ nhanh nhẹn, nhiệt tình và chu đáo", new Date(6000), new Date(6000), new Date(1000)));
        restaurants.add(new Restaurant(5, 5, "Liferia Coffee - Bingsu", "186 - 188 Phan Chu Trinh, Quận Hải Châu, Đà Nẵng", "8:00AM", "10:00PM", "20320130213", "CV.png", "Không gian thoáng mát, rộng rãi. Kem tuyết mát lạnh, hương vị hấp dẫn. Phục vụ nhanh nhẹn, nhiệt tình", new Date(4000), new Date(4000), new Date(1000)));
        restaurants.add(new Restaurant(6, 1, "Han Cook Cafe & Food", "316 - 318 Hải Phòng, Quận Thanh Khê, Đà Nẵng", "8:00AM", "10:00PM", "20320130213", "CV.png", "Không gian đẹp, thoáng mát. Chuyên phục vụ đồ uống đa dạng và nhiều món ăn hấp dẫn khác. Phục vụ nhanh nhẹn, chu đáo với khách hàng.", new Date(3000), new Date(3000), new Date(1000)));

        new RestaurantStorageService(Realm.getDefaultInstance()).saveRestaurants(restaurants, new ICallback<Boolean>() {
            @Override
            public void onResult(Boolean result) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void initCategories() {
        List<Category> categories = new ArrayList<>();

        categories.add(new Category(1, "Thăm quan & chụp ảnh", "1.png", new Date(634534), new Date(634534), new Date(1000)));
        categories.add(new Category(2, "Khách sạn & căn hộ", "2.png", new Date(634534), new Date(634534), new Date(1000)));
        categories.add(new Category(3, "Khu nghỉ dưỡng", "3.png", new Date(634534), new Date(634534), new Date(1000)));
        categories.add(new Category(4, "Du lịch sinh thái", "4.png", new Date(634534), new Date(634534), new Date(1000)));
        categories.add(new Category(5, "Tàu du lịch", "5.png", new Date(634534), new Date(634534), new Date(1000)));
        categories.add(new Category(6, "Công viên vui chơi", "6.png", new Date(634534), new Date(634534), new Date(1000)));
        categories.add(new Category(7, "Bảo tàng & di tích", "7.png", new Date(634534), new Date(634534), new Date(1000)));
        categories.add(new Category(8, "Chùa & Nhà thờ", "8.png", new Date(634534), new Date(634534), new Date(1000)));
        categories.add(new Category(9, "Phòng vé", "9.png", new Date(634534), new Date(634534), new Date(1000)));
        categories.add(new Category(10, "Cho thuê xe", "10.png", new Date(634534), new Date(634534), new Date(1000)));
        categories.add(new Category(11, "Homestay", "11.png", new Date(634534), new Date(634534), new Date(1000)));
        categories.add(new Category(12, "Ăn uống", "12.png", new Date(634534), new Date(634534), new Date(1000)));


        new CategoryStorageService(Realm.getDefaultInstance()).saveCategories(categories, new ICallback<Boolean>() {
            @Override
            public void onResult(Boolean result) {
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
