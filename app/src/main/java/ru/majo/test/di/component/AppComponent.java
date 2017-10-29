package ru.majo.test.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ru.majo.test.di.module.AppModule;
import ru.majo.test.ui.edit.UserEditFragment;
import ru.majo.test.ui.users.UsersFragment;

/**
 * Created by Majo on 28.10.2017.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(UsersFragment fragment);
    void inject(UserEditFragment fragment);

}
