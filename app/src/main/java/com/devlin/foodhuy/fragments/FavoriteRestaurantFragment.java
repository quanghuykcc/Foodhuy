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

import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.view.BaseFragment;
import com.devlin.core.viewmodel.FavoriteRestaurantViewModel;
import com.devlin.core.viewmodel.LatestRestaurantViewModel;
import com.devlin.foodhuy.App;
import com.devlin.foodhuy.BR;
import com.devlin.foodhuy.R;
import com.devlin.foodhuy.adapters.BindingRecyclerViewAdapter;
import com.devlin.foodhuy.adapters.DividerItemDecoration;
import com.devlin.foodhuy.adapters.EndlessRecyclerViewScrollListener;
import com.devlin.foodhuy.adapters.FavoriteRestaurantListAdapter;
import com.devlin.foodhuy.adapters.binder.CompositeItemBinder;
import com.devlin.foodhuy.adapters.binder.RestaurantBinder;
import com.devlin.foodhuy.databinding.FragmentFavoriteRestaurantBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 8/2/2016.
 */
public class FavoriteRestaurantFragment extends BaseFragment<FragmentFavoriteRestaurantBinding, FavoriteRestaurantViewModel> {

    //region Properties

    private static final String TAG = "FavoriteRestaurantFragment";

    private BindingRecyclerViewAdapter<Restaurant, FavoriteRestaurantViewModel> mFavoriteRestaurantListAdapter;

    private  RecyclerView mRecyclerView;

    //endregion

    //region Lifecycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.sharedComponent().inject(this);
        super.onCreate(savedInstanceState);

        mViewModel.getEventBus().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.favorite));

        setBindingContentView(inflater, container, R.layout.fragment_favorite_restaurant, BR.viewModel);

        View view = mViewDataBinding.getRoot();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.restaurant_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mViewModel.getNextPageRestaurants(totalItemsCount);
            }
        });

        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_restaurant);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);

        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mFavoriteRestaurantListAdapter = new BindingRecyclerViewAdapter<Restaurant, FavoriteRestaurantViewModel>(new CompositeItemBinder<Restaurant>(new RestaurantBinder(BR.restaurant, R.layout.item_favorite_restaurant)), null);

        mFavoriteRestaurantListAdapter.setViewModel(mViewModel);

        mRecyclerView.setAdapter(mFavoriteRestaurantListAdapter);

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

        mViewModel.getEventBus().unregister(this);
    }

    //endregion

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(Restaurant removeRestaurant) {
        mFavoriteRestaurantListAdapter.notifyDataSetChanged();
    }
}
