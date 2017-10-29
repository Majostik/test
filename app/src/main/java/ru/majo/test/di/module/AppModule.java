package ru.majo.test.di.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.majo.test.Application;
import ru.majo.test.api.ApiService;

/**
 * Created by Majo on 28.10.2017.
 */

@Module
public class AppModule {

    private Application mApp;

    public AppModule(Application mApp) {
        this.mApp = mApp;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

    @Singleton
    @Provides
    public GsonConverterFactory provideGsonConverterFactory() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides
    public RxJavaCallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(){
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(GsonConverterFactory gsonConverterFactory,
                                    RxJavaCallAdapterFactory rxJavaCallAdapterFactory, OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl("https://bb-test-server.herokuapp.com/")
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .client(okHttpClient)
                .build();
    }


    @Singleton
    @Provides
    public ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

}
