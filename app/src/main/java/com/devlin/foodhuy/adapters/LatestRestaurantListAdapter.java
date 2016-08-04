package com.devlin.foodhuy.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.view.BaseRecyclerViewAdapter;
import com.devlin.core.view.ViewHolder;
import com.devlin.core.viewmodel.LatestRestaurantViewModel;
import com.devlin.foodhuy.R;

import java.util.List;

/**
 * Created by Administrator on 8/2/2016.
 */
public class LatestRestaurantListAdapter extends BaseRecyclerViewAdapter<LatestRestaurantViewModel, List<Restaurant>> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_restaurant, parent, false);
        return new ViewHolder(viewDataBinding);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewDataBinding viewDataBinding = ((ViewHolder) holder).getViewDataBinding();

        viewDataBinding.setVariable(BR.restaurant, mData.get(position));
        viewDataBinding.setVariable(BR.viewModel, mViewModel);

        viewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
