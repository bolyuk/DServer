package com.atr.dserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;


public class MainService extends Service {

    Handler handler;
    public static String context="MainService";
    public static boolean LogState=true;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setLogState(boolean newState){
        LogState=newState;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                try {
                }catch(Exception e){

                }
                handler.postDelayed(this,0);
            }
        };

        handler.postDelayed(r, 0);

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}