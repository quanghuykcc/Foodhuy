package com.devlin.core.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;

import com.devlin.core.R;

/**
 * Created by Administrator on 7/25/2016.
 */
public class Navigator implements INavigator {

    //region Properties

    private BaseApplication mApplication;

    private BusyIndicator mBusyIndicator;

    private SparseArray<Class> mPages = new SparseArray<>();

    //endregion

    //region Constructors

    public Navigator(BaseApplication application) {
        mApplication = application;
    }

    //endregion

    //region INavigator implement

    @Override
    public void configure(@Constants.PageKey int pageKey, Class pageClass) {
        mPages.put(pageKey, pageClass);
    }

    @Override
    public BaseApplication getApplication() {
        return mApplication;
    }

    @Override
    public @Constants.PageKey int getCurrentKey() {
        Class currentClass = mApplication.getCurrentActivity()
                .getClass();

        int valueIndex = mPages.indexOfValue(currentClass);
        @Constants.PageKey int pageKey = mPages.keyAt(valueIndex);
        return pageKey;
    }

    @Override
    public void goBack() {
        if (mApplication.isCurrentActivityAvailable()) {
            ActivityCompat.finishAfterTransition(mApplication.getCurrentActivity());
        }
    }

    @Override
    public void navigateTo(@Constants.PageKey int pageKey) {
        if (mApplication.isCurrentActivityAvailable()) {
            Activity currentActivity = mApplication.getCurrentActivity();
            Class targetClass = mPages.get(pageKey);

            Intent intent = new Intent(currentActivity, targetClass);
            ActivityCompat.startActivity(currentActivity, intent, null);
        }
    }

    @Override
    public void showMessage(
            String title,
            String message,
            String buttonText,
            final ICallback callback) {

        if (mApplication.isCurrentActivityAvailable()) {
            Activity currentActivity = mApplication.getCurrentActivity();

            new AlertDialog.Builder(currentActivity)
                    .setNegativeButton(buttonText, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (callback != null) {
                                callback.onResult(false);
                            }

                            dialogInterface.dismiss();
                        }

                    })
                    .show();
        }
    }

    @Override
    public void showMessage(
            String title,
            String message,
            String buttonConfirmText,
            String buttonCancelText,
            final ICallback callback) {

        if (mApplication.isCurrentActivityAvailable()) {
            Activity currentActivity = mApplication.getCurrentActivity();

            new AlertDialog.Builder(currentActivity)
                    .setPositiveButton(buttonConfirmText, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (callback != null) {
                                callback.onResult(true);
                            }

                            dialogInterface.dismiss();
                        }

                    })
                    .setNegativeButton(buttonCancelText, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (callback != null) {
                                callback.onResult(false);
                            }

                            dialogInterface.dismiss();
                        }

                    })
                    .show();
        }
    }

    @Override
    public void showBusyIndicator(String title) {
        if (mApplication.isCurrentActivityAvailable()) {
            mBusyIndicator = new BusyIndicator(mApplication.getCurrentActivity());
            mBusyIndicator.setTitle(title);
            mBusyIndicator.show();
        }
    }

    @Override
    public void hideBusyIndicator() {
        if (isBusyIndicatorShowing()) {
            try {
                mBusyIndicator.dismiss();
            } catch (Exception e) {
                Log.e(Navigator.class.getSimpleName(), e.getMessage());
            } finally {
                mBusyIndicator = null;
            }
        }
    }

    @Override
    public boolean isBusyIndicatorShowing() {
        return mBusyIndicator != null && mBusyIndicator.isShowing();
    }

    @Override
    public void navigateTo(int fragmentId, Fragment fragment) {
        FragmentManager fragmentManager = getApplication().getCurrentActivity().getFragmentManager();

        fragmentManager.beginTransaction().replace(fragmentId, fragment).commit();
    }

    //endregion
}
