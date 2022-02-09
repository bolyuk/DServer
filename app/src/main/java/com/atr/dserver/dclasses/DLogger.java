package com.atr.dserver.dclasses;

import com.atr.dserver.MainActivity;

import java.util.ArrayList;


public class DLogger extends DBClass{

    public static String path;
    public static ArrayList<String> inAppLog = new ArrayList<String>();

    private static boolean state=true;
    private static ArrayList<DLogInterface> observer = new ArrayList<DLogInterface>();

    public interface DLogInterface{
        public void onLogChanged();
    }

    public static void addListener(DLogInterface listener){
        observer.add(listener);
    }

    public static void delListener(DLogInterface listener){
        observer.remove(listener);
    }

    public static void unplug(){
        try {
            DLogger.delListener(MainActivity.listener);
        }catch(Exception e){
            DLogger.log("Unplug log erorr...","DLogger", LogState);
        }
    }

    private static void runListener(){
        if(state)
        for(int i=0; i< observer.size(); i++)
            observer.get(i).onLogChanged();
    }

    public static void init(){
        setContext("DLogger");
        path= DBolk.dbolkpath+"/Logs/log-"+DBolk.sessionID+".txt";
        if(!DFile.isExist(DFile.getRootDirectory()+"/DBolk/Logs"))
             DFile.makeDirectory(DFile.getRootDirectory()+"/DBolk/Logs");
        inAppLog = new ArrayList<String>();
        log("DLogger init...", "DLogger",LogState);
    }

    public static int log(Object data, String from, boolean enable){
           if(enable)
              return logE(data,from, inAppLog.size());
           else
               return -1;
    }
    public static int logE(Object data, String from, int index){
        String result;
        if(state){
            if(path == null)
                result = ("BAD LOG CALL, LOGGER IS NOT INIT \n CALL: "+data+" : "+from);
            else
             result = from + ": " + data.toString();
            System.out.println(result);
            if(path != null)
                DFile.writeToFile(DFile.readFromFile(path)+"\n"+result, path);
            if(inAppLog.size() > index)
                inAppLog.set(index,result);
            else
                inAppLog.add(index,result);
        }
        runListener();
        return inAppLog.size();
    }

    public static void logD(int index){
        inAppLog.remove(index);
        runListener();
    }

    public static int getSize(){
        return inAppLog.size();
    }

    public static String getLog(){
        String result="";
        for(int i=0;i < inAppLog.size();i++)
         result+="\n"+inAppLog.get(i);
        return result;
    }

    public static void setState(boolean newState){
        state=newState;
    }

    public static void reset(){
        DFile.writeToFile("",path);
        inAppLog = new ArrayList<String>();
    }
}
