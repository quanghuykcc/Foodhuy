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
import com.devlin.core.viewmodel.RestaurantByCategoryViewModel;
import com.devlin.foodhuy.App;
import com.devlin.foodhuy.BR;
import com.devlin.foodhuy.R;
import com.devlin.foodhuy.adapters.DividerItemDecoration;
import com.devlin.foodhuy.adapters.EndlessRecyclerViewScrollListener;
import com.devlin.foodhuy.adapters.RestaurantByCategoryListAdapter;
import com.devlin.foodhuy.databinding.FragmentRestaurantByCategoryBinding;

/**
 * Created by Administrator on 7/26/2016.
 */
public class RestaurantByCategoryFragment extends BaseFragment<FragmentRestaurantByCategoryBinding, RestaurantByCategoryViewModel> {

    //region Properties

    private static final String TAG = "RestaurantByCategoryFragment";

    private RestaurantByCategoryListAdapter mRestaurantByCategoryListAdapter;

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

        setBindingContentView(inflater, container, R.layout.fragment_restaurant_by_category, BR.viewModel);

        View view = mViewDataBinding.getRoot();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.restaurant_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mViewModel.getNextPageRestaurants(totalItemsCount);
            }
        });

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_restaurant);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);

        recyclerView.addItemDecoration(dividerItemDecoration);

        mRestaurantByCategoryListAdapter = new RestaurantByCategoryListAdapter();
        mRestaurantByCategoryListAdapter.setViewModel(mViewModel);
        recyclerView.setAdapter(mRestaurantByCategoryListAdapter);

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

    //region Constructors

    public RestaurantByCategoryFragment() {

    }

    //endregion

}
