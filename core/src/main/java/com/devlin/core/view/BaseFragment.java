package com.devlin.core.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devlin.core.viewmodel.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by Administrator on 7/27/2016.
 */
public class BaseFragment<B extends ViewDataBinding, V extends BaseViewModel> extends Fragment {

    //region Properties

    protected B mViewDataBinding;

    @Inject
    protected V mViewModel;

    //endregion

    //region Lifecycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mViewModel != null) {
            mViewModel.onCreate();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mViewModel != null) {
            mViewModel.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mViewModel != null) {
            mViewModel.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mViewModel != null) {
            mViewModel.onDestroy();
        }
    }

    //endregion

    //region Protected Methods

    protected void setBindingContentView(LayoutInflater inflater, @Nullable ViewGroup container, int layoutResId, int variableId) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false);
        mViewDataBinding.setVariable(variableId, mViewModel);
    }

    //endregion

}
