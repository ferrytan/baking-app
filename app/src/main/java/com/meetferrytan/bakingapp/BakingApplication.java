package com.meetferrytan.bakingapp;

import android.app.Application;

import com.meetferrytan.bakingapp.data.component.AppComponent;
import com.meetferrytan.bakingapp.data.component.DaggerAppComponent;
import com.meetferrytan.bakingapp.data.module.ApplicationModule;
import com.meetferrytan.bakingapp.data.module.NetworkModule;

/**
 * Created by ferrytan on 8/29/17.
 */

public class BakingApplication extends Application {
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(BuildConfig.BASE_URL))
                .build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
