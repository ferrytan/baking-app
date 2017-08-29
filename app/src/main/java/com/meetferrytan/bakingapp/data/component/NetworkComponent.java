package com.meetferrytan.bakingapp.data.component;

import android.content.Context;
import android.content.SharedPreferences;

import com.meetferrytan.bakingapp.data.module.ApplicationModule;
import com.meetferrytan.bakingapp.data.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by ferrytan on 7/4/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface NetworkComponent {
    // downstream components need these exposed with the return type
    // method name does not really matter
    Retrofit retrofit();
    Context getApplicationContext();
    SharedPreferences getSharedPreferences();
}