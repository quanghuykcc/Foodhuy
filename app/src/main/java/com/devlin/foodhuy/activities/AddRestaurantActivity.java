package com.devlin.foodhuy.activities;

import android.os.Bundle;

import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.view.BaseActivity;
import com.devlin.core.viewmodel.AddRestaurantViewModel;
import com.devlin.foodhuy.App;
import com.devlin.foodhuy.R;
import com.devlin.foodhuy.BR;
import com.devlin.foodhuy.databinding.ActivityAddRestaurantBinding;

public class AddRestaurantActivity extends BaseActivity<ActivityAddRestaurantBinding, AddRestaurantViewModel> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.sharedComponent().inject(this);

        super.onCreate(savedInstanceState);

        setBindingContentView(R.layout.activity_add_restaurant, BR.viewModel);
    }
}
