package com.devlin.foodhuy.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devlin.core.view.BaseFragment;
import com.devlin.core.viewmodel.LatestRestaurantViewModel;
import com.devlin.foodhuy.App;
import com.devlin.foodhuy.BR;
import com.devlin.foodhuy.R;
import com.devlin.foodhuy.adapters.DividerItemDecoration;
import com.devlin.foodhuy.adapters.LatestRestaurantListAdapter;
import com.devlin.foodhuy.databinding.FragmentLatestRestaurantBinding;

/**
 * Created by Administrator on 8/2/2016.
 */
public class LatestRestaurantFragment extends BaseFragment<FragmentLatestRestaurantBinding, LatestRestaurantViewModel> {

    //region Properties

    private static final String TAG = "LatestRestaurantFragment";

    private LatestRestaurantListAdapter mLastestRestaurantListAdapter;

    //endregion

    //region Lifecycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.sharedComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.latest));

        setBindingContentView(inflater, container, R.layout.fragment_latest_restaurant, BR.viewModel);

        View view = mViewDataBinding.getRoot();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.restaurant_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);

        recyclerView.addItemDecoration(dividerItemDecoration);

        mLastestRestaurantListAdapter = new LatestRestaurantListAdapter();
        mLastestRestaurantListAdapter.setViewModel(mViewModel);
        recyclerView.setAdapter(mLastestRestaurantListAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //endregion
}
