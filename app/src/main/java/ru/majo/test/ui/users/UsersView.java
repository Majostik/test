package ru.majo.test.ui.users;

import java.util.ArrayList;

import ru.majo.test.api.model.User;
import ru.majo.test.base.BaseView;

/**
 * Created by Majo on 28.10.2017.
 */

public interface UsersView extends BaseView {

    void onGetUsersSuccess(ArrayList<User> users);
    void onGetUsersError(Throwable throwable);
}
