package com.atr.dserver.dclasses;

public class DBClass {
    protected static boolean LogState=true;
    protected static String context="";

    public static void init(){

    }

    protected static void setContext(String newContext){
        context=newContext;
    }

    public static String getContext(){
        return context;
    }

    protected static int log(String text){
        return DLogger.log(text,context,LogState);
    }

    public static void setLogState(boolean newState){
        LogState = newState;
    }

    public static boolean getLogState() {
        return LogState;
    }


}
