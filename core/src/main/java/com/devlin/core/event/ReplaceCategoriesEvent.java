package com.devlin.core.event;

import com.devlin.core.model.entities.Category;

import java.util.List;

/**
 * Created by Administrator on 8/22/2016.
 */
public class ReplaceCategoriesEvent {

    private final List<Category> mCategories;

    public ReplaceCategoriesEvent(List<Category> categories) {
        mCategories = categories;
    }

    public List<Category> getCategories() {
        return mCategories;
    }
}
