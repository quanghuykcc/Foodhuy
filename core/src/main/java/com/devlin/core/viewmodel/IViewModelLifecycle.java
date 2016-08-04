package com.devlin.core.viewmodel;

/**
 * Created by Administrator on 7/25/2016.
 */
public interface IViewModelLifecycle {
    void onCreate();
    void onStart();
    void onStop();
    void onDestroy();
}
