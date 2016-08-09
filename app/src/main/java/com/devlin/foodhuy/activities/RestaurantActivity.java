package com.devlin.foodhuy.activities;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.android.databinding.library.baseAdapters.BR;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.view.BaseActivity;
import com.devlin.core.viewmodel.RestaurantViewModel;
import com.devlin.foodhuy.App;
import com.devlin.foodhuy.R;
import com.devlin.foodhuy.adapters.CommentListAdapter;
import com.devlin.foodhuy.adapters.DividerItemDecoration;
import com.devlin.foodhuy.adapters.LatestRestaurantListAdapter;
import com.devlin.foodhuy.databinding.ActivityRestaurantBinding;

public class RestaurantActivity extends BaseActivity<ActivityRestaurantBinding, RestaurantViewModel> {
    private CommentListAdapter mCommentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.sharedComponent().inject(this);

        super.onCreate(savedInstanceState);

        setBindingContentView(R.layout.activity_restaurant, BR.viewModel);

        setToolbar(R.id.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comment_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);

        recyclerView.addItemDecoration(dividerItemDecoration);

        mCommentListAdapter = new CommentListAdapter();
        mCommentListAdapter.setViewModel(mViewModel);
        recyclerView.setAdapter(mCommentListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
