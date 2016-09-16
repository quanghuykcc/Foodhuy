package com.devlin.core.util;

import com.devlin.core.model.services.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 9/12/2016.
 */
public class QueryDate {
    private static final ThreadLocal<DateFormat> DF = new ThreadLocal<DateFormat>() {
        @Override public DateFormat initialValue() {
            return new SimpleDateFormat(Configuration.TIMESTAMP_FORMAT);
        }
    };

    private final Date date;

    public QueryDate(Date date) {
        this.date = date;
    }

    @Override public String toString() {
        return DF.get().format(date);
    }
}
