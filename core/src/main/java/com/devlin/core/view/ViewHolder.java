package com.devlin.core.view;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 7/30/2016.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    //region Properties

    private ViewDataBinding mViewDataBinding;

    //endregion

    //region Constructors

    public ViewHolder(ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());

        mViewDataBinding = viewDataBinding;
    }

    //endregion

    //region Getter and Setter

    public ViewDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }

    //endregion

}
