package com.atr.dserver.dclasses;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.atr.dserver.MainActivity;
import com.atr.dserver.MainService;

import java.lang.reflect.Method;


public class EntryPoints extends DBClass{
 public static String context= "EntryPoints";


    public static void help(){
        Class[] classes = {DLogger.class,LuaRunner.class,EntryPoints.class, DFirebase.class};
        for(int i=0; i< classes.length;i++)
        {
            String[] methods = getMethods(classes[i]);
            log("CLASS: "+classes[i].getName());
            for(int y=0; y< methods.length;y++){
                DLogger.log(methods[y], classes[i].getName(),LogState);
            }
        }
    }


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

    public static void runStartUpScripts(){
        String path = DBolk.dbolkpath+"/Scripts/StartUp";
        if(!DFile.isExist(path)) DFile.makeDirectory(path);
        String[] scripts = DFile.getFilesFromDir(path);
        if(scripts != null)
     for(int i=0; i<scripts.length;i++)
         DBolk.run(DFile.readFromFile(scripts[i]));
    }

    public static void runServiceStartUpScripts() {
        String path = DBolk.dbolkpath + "/Scripts/ServiceStartUp";
        if(!DFile.isExist(path)) DFile.makeDirectory(path);
        String[] scripts = DFile.getFilesFromDir(path);
        if(scripts != null)
        for (int i = 0; i < scripts.length; i++)
            DBolk.run(DFile.readFromFile(scripts[i]));
    }

    public static void runServiceScripts() {
        String path = DBolk.dbolkpath + "/Scripts/Service";
        if(!DFile.isExist(path)) DFile.makeDirectory(path);
        String[] scripts = DFile.getFilesFromDir(path);
        if(scripts != null)
        for (int i = 0; i < scripts.length; i++)
            DBolk.run(DFile.readFromFile(scripts[i]));
    }

    public static void runService(){
        DBContext.getAppContext().startService(new Intent(DBContext.getAppContext(), MainService.class));
    }

    public static void killService(){
        DBContext.getAppContext().stopService(new Intent(DBContext.getAppContext(), MainService.class));
    }

    public static int getBattery(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = DBContext.getAppContext().registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;

        return (int)(batteryPct*100);
    }

    public static boolean isCharging(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = DBContext.getAppContext().registerReceiver(null, ifilter);
        return batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1) == BatteryManager.BATTERY_STATUS_CHARGING;
    }

}

