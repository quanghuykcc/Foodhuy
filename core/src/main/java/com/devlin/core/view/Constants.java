package com.devlin.core.view;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 7/25/2016.
 */
public class Constants {

    //region Constructors

    private Constants() {

    }

    //endregion

    //region Pages

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            MAIN_PAGE, LOGIN_PAGE, REGISTER_PAGE, ADD_RESTAURANT_PAGE
    })
    public @interface PageKey {}

    public static final int MAIN_PAGE = 0;
    public static final int LOGIN_PAGE = 1;
    public static final int REGISTER_PAGE = 2;
    public static final int ADD_RESTAURANT_PAGE = 3;


    //endregion

    //region Fragments

    public static final int CATEGORY_FRAGMENT = 1;
    public static final int RESTAURANT_FRAGMENT = 2;

    //endregion

}
