package com.devlin.core.model.services;

import com.devlin.core.model.entities.Category;
import com.devlin.core.view.ICallback;

import java.util.List;

/**
 * Created by Administrator on 7/25/2016.
 */
public interface ICategoryService {

    void getAllCategories(ICallback<List<Category>> callback);

    void saveCategories(List<Category> categories, ICallback<Boolean> callback);

}
