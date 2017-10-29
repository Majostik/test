package ru.majo.test.ui.edit;

import java.util.ArrayList;

import ru.majo.test.api.model.User;
import ru.majo.test.base.BaseView;

/**
 * Created by Majo on 28.10.2017.
 */

public interface UserEditView extends BaseView {

    void onUserCreateSuccess();
    void onUserCreateError(Throwable throwable);
}
