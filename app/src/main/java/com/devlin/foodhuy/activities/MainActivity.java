package com.devlin.foodhuy.activities;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.devlin.core.model.entities.Category;
import com.devlin.core.view.BaseActivity;
import com.devlin.core.view.Constants;
import com.devlin.core.viewmodel.MainViewModel;
import com.devlin.foodhuy.App;
import com.devlin.foodhuy.BR;
import com.devlin.foodhuy.R;
import com.devlin.foodhuy.databinding.ActivityMainBinding;
import com.devlin.foodhuy.databinding.NavHeaderBinding;
import com.devlin.foodhuy.dialogs.AddDialog;
import com.devlin.foodhuy.fragments.CategoryFragment;
import com.devlin.foodhuy.fragments.FavoriteRestaurantFragment;
import com.devlin.foodhuy.fragments.LatestRestaurantFragment;
import com.devlin.foodhuy.fragments.RestaurantByCategoryFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements NavigationView.OnNavigationItemSelectedListener {

    //region Properties

    private static final String TAG = "MainActivity";

    //endregion

    //region Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.sharedComponent().inject(this);

        super.onCreate(savedInstanceState);

        setBindingContentView(R.layout.activity_main, BR.viewModel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NavHeaderBinding navHeaderBinding = NavHeaderBinding.inflate(LayoutInflater.from(navigationView.getContext()));
        navHeaderBinding.setVariable(BR.viewModel, mViewModel);
        navigationView.addHeaderView(navHeaderBinding.getRoot());
        navHeaderBinding.executePendingBindings();

        mViewModel.getNavigator().navigateTo(R.id.main_content, new LatestRestaurantFragment());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.getEventBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.getEventBus().unregister(this);
    }

    //endregion

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_option_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        else if (id ==R.id.action_add) {
            Dialog dialog = new AddDialog(this);
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_latest) {
            fragment = new LatestRestaurantFragment();
        }
        else if (id == R.id.nav_category) {
            fragment = new CategoryFragment();
        }
        else if (id == R.id.nav_favorite) {
            if (mViewModel.getNavigator().getApplication().isUserLoggedIn()) {
                fragment = new FavoriteRestaurantFragment();
            }
            else {
                mViewModel.getNavigator().navigateTo(Constants.LOGIN_PAGE);
            }
        }
        else if (id == R.id.nav_manage) {

        }
        else if (id == R.id.nav_share) {

        }
        else if (id == R.id.nav_send) {

        }

        if (fragment != null) {
            mViewModel.getNavigator().navigateTo(R.id.main_content, fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(Category data) {
        mViewModel.getNavigator().navigateTo(R.id.main_content, new RestaurantByCategoryFragment());
    }


}
