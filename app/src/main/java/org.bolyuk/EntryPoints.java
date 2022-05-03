package org.bolyuk;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import java.lang.reflect.Method;


public class EntryPoints extends Baser {


    public static String getDevice(){
       return android.os.Build.DEVICE;
    }
    public static String getModel(){
        return android.os.Build.MODEL+"("+android.os.Build.PRODUCT+")";
    }
    public static int getSDK(){
        return android.os.Build.VERSION.SDK_INT;
    }
    public static String getOS(){
       return System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")";
    }


    public static String[] getMethods(Class from){
        Method[] methods = from.getDeclaredMethods();
        String[] result = new String[methods.length];
        for(int i=0; i<methods.length;i++) {
            result[i] = methods[i].getName();
        }
        return result;
    }

    public static int getBattery(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = App.getContext().registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;

        return (int)(batteryPct*100);
    }

    public static boolean isCharging(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = App.getContext().registerReceiver(null, ifilter);
        return batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1) == BatteryManager.BATTERY_STATUS_CHARGING;
    }

    public static String color(String text, String color){
        return "<font color=" + color + ">" + text + "</font>";
    }

}

