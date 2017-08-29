package com.meetferrytan.bakingapp;

import android.app.Application;

import com.meetferrytan.bakingapp.data.component.DaggerNetworkComponent;
import com.meetferrytan.bakingapp.data.component.NetworkComponent;
import com.meetferrytan.bakingapp.data.module.ApplicationModule;
import com.meetferrytan.bakingapp.data.module.NetworkModule;

/**
 * Created by ferrytan on 8/29/17.
 */

public class BakingApplication extends Application {
    private static NetworkComponent sNetworkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sNetworkComponent = DaggerNetworkComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(BuildConfig.BASE_URL))
                .build();
    }

    public static NetworkComponent getNetworkComponent() {
        return sNetworkComponent;
    }
}
