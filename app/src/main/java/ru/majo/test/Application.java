package ru.majo.test;

import android.content.Context;

import ru.majo.test.di.component.AppComponent;
import ru.majo.test.di.component.DaggerAppComponent;
import ru.majo.test.di.module.AppModule;

/**
 * Created by Majo on 28.10.2017.
 */

public class Application extends android.app.Application {

    private AppComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = createComponent();

    }

    private AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getComponent(Context context) {
        return ((Application)context.getApplicationContext()).mApplicationComponent;
    }
}
