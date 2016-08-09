package com.devlin.foodhuy.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.devlin.core.model.entities.Comment;
import com.devlin.core.view.BaseRecyclerViewAdapter;
import com.devlin.core.view.ViewHolder;
import com.devlin.core.viewmodel.CommentViewModel;
import com.devlin.core.viewmodel.RestaurantViewModel;
import com.devlin.foodhuy.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

/**
 * Created by Administrator on 08-Aug-16.
 */
public class CommentListAdapter extends BaseRecyclerViewAdapter<RestaurantViewModel, List<Comment>> {
    //region Override Methods

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_comment, parent, false);
        return new ViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewDataBinding viewDataBinding = ((ViewHolder) holder).getViewDataBinding();


        viewDataBinding.setVariable(BR.comment, mData.get(position));
        viewDataBinding.setVariable(BR.viewModel, mViewModel);

        viewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    //endregion

}
