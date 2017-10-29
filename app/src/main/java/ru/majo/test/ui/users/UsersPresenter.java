package ru.majo.test.ui.users;

import android.util.Log;

import javax.inject.Inject;

import ru.majo.test.api.ApiService;
import ru.majo.test.base.BasePresenter;
import ru.majo.test.utils.ApiUtils;

/**
 * Created by Majo on 28.10.2017.
 */

public class UsersPresenter extends BasePresenter<UsersView> {

    private ApiService mApiService;

    @Inject
    public UsersPresenter(ApiService apiService){
        mApiService = apiService;
    }

    public void loadUsers(){
        mApiService.getUsers()
                .compose(ApiUtils.async())
                .subscribe(users -> {
                    getView().onGetUsersSuccess(users);
                }, throwable -> {
                    getView().onGetUsersError(throwable);
                });
    }
}
