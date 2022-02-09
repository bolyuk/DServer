package com.atr.dserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.atr.dserver.dclasses.DBolk;
import com.atr.dserver.dclasses.DLogger;
import com.atr.dserver.dclasses.EntryPoints;

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
        log("Service created...");
    }

    public void setLogState(boolean newState){
        LogState=newState;
    }

    private int log(String text){
        return DLogger.log(text,context,LogState);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("Service is running...");
        EntryPoints.runServiceStartUpScripts();
        handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                try {
                    log("scheduled service code start. schedule time is[" + DBolk.getDelay() + "]");
                    EntryPoints.runServiceScripts();
                }catch(Exception e){
                    log("Service error!: \n"+e.toString());
                }
                handler.postDelayed(this, DBolk.getDelay());
            }
        };

        handler.postDelayed(r, DBolk.getDelay());

        return super.onStartCommand(intent, flags, startId);
    }

    public void run(String code){
        log("Try to run code on service...");
        DBolk.run(code);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        log("Service is destroyed...");
    }
}