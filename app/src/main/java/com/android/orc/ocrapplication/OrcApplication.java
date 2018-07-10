package com.android.orc.ocrapplication;

import android.app.Application;

import com.android.orc.ocrapplication.model.Contextor;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by j.poobest on 9/20/2017 AD.
 */

public class OrcApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize thing(s) here.
        Contextor.getInstance().init(getApplicationContext());

        // Facebook Login
        AppEventsLogger.activateApp(this);
    }
//Hello world
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
