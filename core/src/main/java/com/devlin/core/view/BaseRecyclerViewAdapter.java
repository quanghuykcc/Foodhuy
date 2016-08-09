package com.devlin.core.view;

import android.support.v7.widget.RecyclerView;

import com.devlin.core.viewmodel.BaseViewModel;

/**
 * Created by Administrator on 7/30/2016.
 */
public abstract class BaseRecyclerViewAdapter<V extends BaseViewModel, T> extends RecyclerView.Adapter {

    //region Properties

    protected V mViewModel;

    protected  T mData;

    //endregion

    //region Getter and Setter

    public void setData(T data) {
        mData = data;

        notifyDataSetChanged();
    }

    public T getData() {
        return mData;
    }

    public void setViewModel(V viewModel) {
        mViewModel = viewModel;
    }

    //endregion

}
