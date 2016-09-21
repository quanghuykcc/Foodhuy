package com.devlin.foodhuy.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devlin.core.model.entities.Category;
import com.devlin.core.view.BaseRecyclerViewAdapter;
import com.devlin.core.view.ViewHolder;
import com.devlin.core.viewmodel.CategoryViewModel;
import com.devlin.foodhuy.BR;
import com.devlin.foodhuy.R;

import java.util.List;

/**
 * Created by Administrator on 8/2/2016.
 */

public class CategoryListAdapter extends BaseRecyclerViewAdapter<CategoryViewModel, List<Category>> {

    //region Override Methods

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_category, parent, false);
        return new ViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewDataBinding viewDataBinding = ((ViewHolder) holder).getViewDataBinding();

        viewDataBinding.setVariable(BR.category, mData.get(position));
        viewDataBinding.setVariable(BR.viewModel, mViewModel);

        viewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    //endregion
}
