package com.devlin.core.event;

import com.devlin.core.model.entities.Category;

/**
 * Created by quanghuymr403 on 18/09/2016.
 */
public class ShowRestaurantsByCategoryEvent {
    private final Category mCategory;

    public ShowRestaurantsByCategoryEvent(Category category) {
        mCategory = category;
    }

    public Category getCategory() {
        return mCategory;
    }
}
