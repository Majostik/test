package ru.majo.test.ui.edit;

import android.util.Log;

import javax.inject.Inject;

import ru.majo.test.api.ApiService;
import ru.majo.test.api.model.User;
import ru.majo.test.base.BasePresenter;
import ru.majo.test.utils.ApiUtils;

/**
 * Created by Majo on 28.10.2017.
 */

public class UserEditPresenter extends BasePresenter<UserEditView> {

    private ApiService mApiService;

    @Inject
    public UserEditPresenter(ApiService apiService){
        mApiService = apiService;
    }

    public void createUser(String firstName, String lastName, String email){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        mApiService.createUser(user)
                .compose(ApiUtils.async())
                .subscribe(success -> {
                    getView().onUserCreateSuccess();
                }, throwable -> {
                    getView().onUserCreateError(throwable);
                });
    }

    public void editUser(long id, String firstName, String lastName, String email){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        mApiService.editUser(id, user)
                .compose(ApiUtils.async())
                .subscribe(success -> {
                    getView().onUserCreateSuccess();
                }, throwable -> {
                    getView().onUserCreateError(throwable);
                });
    }
}
