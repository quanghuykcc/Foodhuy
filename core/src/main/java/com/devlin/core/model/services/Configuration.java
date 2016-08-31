package com.devlin.core.model.services;

/**
 * Created by Administrator on 7/25/2016.
 */
public final class Configuration {

    //region Constructors

    private Configuration() {

    }

    //endregion

    public static final String FOODHUY_API_URL = "http://585deee1.ngrok.io/";

    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final long NUMBER_RECORDS_PER_PAGE = 20;

    public static final int NUMBER_CACHE_RESTAURANTS = 30;
}
