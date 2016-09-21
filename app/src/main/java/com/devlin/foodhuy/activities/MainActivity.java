package com.devlin.foodhuy.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.devlin.core.event.ShowRestaurantsByCategoryEvent;
import com.devlin.core.model.entities.Category;
import com.devlin.core.view.BaseActivity;
import com.devlin.core.view.Constants;
import com.devlin.core.viewmodel.MainViewModel;
import com.devlin.foodhuy.App;
import com.devlin.foodhuy.BR;
import com.devlin.foodhuy.R;
import com.devlin.foodhuy.databinding.ActivityMainBinding;
import com.devlin.foodhuy.databinding.NavHeaderBinding;
import com.devlin.foodhuy.fragments.CategoryFragment;
import com.devlin.foodhuy.fragments.FavoriteRestaurantFragment;
import com.devlin.foodhuy.fragments.LatestRestaurantFragment;
import com.devlin.foodhuy.fragments.RestaurantByCategoryFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements NavigationView.OnNavigationItemSelectedListener {

    //region Properties

    NavigationView mNavigationView;

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

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        NavHeaderBinding navHeaderBinding = NavHeaderBinding.inflate(LayoutInflater.from(mNavigationView.getContext()));
        navHeaderBinding.setVariable(BR.viewModel, mViewModel);
        mNavigationView.addHeaderView(navHeaderBinding.getRoot());
        navHeaderBinding.executePendingBindings();

        getSupportFragmentManager().addOnBackStackChangedListener(mBackStackChangedListener);
        changeFragment(new LatestRestaurantFragment());
        mNavigationView.setCheckedItem(R.id.nav_latest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        register();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregister();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.isChecked()) {
            return true;
        }

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_latest:
                fragment = new LatestRestaurantFragment();
                break;
            case R.id.nav_category:
                fragment = new CategoryFragment();
                break;
            case R.id.nav_favorite:
                if (mViewModel.getNavigator().getApplication().isUserLoggedIn()) {
                    fragment = new FavoriteRestaurantFragment();
                } else {
                    mViewModel.getNavigator().navigateTo(Constants.LOGIN_PAGE);
                    return false;
                }
                break;
            case R.id.nav_share:
                mViewModel.logOut();
                break;
            default:
                break;
        }

        if (fragment != null) {
            changeFragment(fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ShowRestaurantsByCategoryEvent event) {
        addFragment(new RestaurantByCategoryFragment());
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitNow();
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_content, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private FragmentManager.OnBackStackChangedListener mBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_content);
            if (fragment instanceof  CategoryFragment) {
                setTitle(getString(R.string.category));
            }
        }
    };


}
