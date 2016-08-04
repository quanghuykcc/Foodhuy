package com.devlin.core.viewmodel;

import com.devlin.core.model.entities.User;
import com.devlin.core.model.services.storages.UserStorageService;
import com.devlin.core.view.INavigator;

/**
 * Created by Administrator on 8/3/2016.
 */
public class RegisterViewModel extends BaseViewModel {

    //region Properties

    private UserStorageService mUserStorageService;

    //endregion

    //region Constructors

    public RegisterViewModel(INavigator navigator, UserStorageService storageService) {
        super(navigator);

        mUserStorageService = storageService;
    }

    //endregion

    //region Lifecycle

    //region Lifecycle

    @Override
    public void onCreate() {
        super.onCreate();
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

    //region Public Methods

    public void goBack() {
        getNavigator().goBack();
    }

    public void registerUser(User user) {

    }

    //endregion
}
