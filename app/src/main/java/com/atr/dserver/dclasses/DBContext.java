package com.atr.dserver.dclasses;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class DBContext  extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        DBContext.context = getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static Context getAppContext() {
        return DBContext.context;
    }
}
