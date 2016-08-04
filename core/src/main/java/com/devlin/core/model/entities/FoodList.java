package com.devlin.core.model.entities;

import android.databinding.ObservableArrayList;

/**
 * Created by Administrator on 7/26/2016.
 */
public class FoodList {
    //region Properties

    private ObservableArrayList<Food> mFoodList;

    private int mTotalCount;

    //endregion

    //region Contructors

    public FoodList() {
        mFoodList = new ObservableArrayList<Food>();
    }

    //endregion

    //region Public methods


    public ObservableArrayList<Food> getFoodList() {
        return mFoodList;
    }

    public void add(Food food) {
        mFoodList.add(food);
    }

    //endregion

}
