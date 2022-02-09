package com.atr.dserver.dclasses;

import java.util.ArrayList;

public class DBolk extends DBClass{
    static String dbolkpath= DFile.getRootDirectory()+"/DBolk";
    static String user="root";

    public static String sessionID;
    public static int version =2;
    private static int serviceDelay=1000;

    private static ArrayList<Runner> observer = new ArrayList<Runner>();

    public interface Runner{
        public boolean onRecieved(String command, boolean isSomeoneRunBefore);
    }

    public static void setDelay(int value){
        serviceDelay=value;
    }

    public static int getDelay(){
        return serviceDelay;
    }

    public static boolean checkScriptVersion(int index){
        return index == version;
    }

    public static void addRunner(Runner runner){
        observer.add(runner);
    }

    public static void delRunner(Runner runner){
        observer.remove(runner);
    }

    public static void init(){
        try {
            setContext("DBolk");
            log("DBolk initializing...");
            if(!DFile.isExist(dbolkpath)) DFile.makeDirectory(dbolkpath);
            if(!DFile.isExist(dbolkpath+"/Scripts")) DFile.makeDirectory(dbolkpath+"/Scripts");
            sessionID = Long.toString(System.currentTimeMillis() % (long) 53);
            DLogger.init();
            DLogger.reset();
            DVars.init();
            log("Device: \n"+EntryPoints.getModel()+"\n"+EntryPoints.getDevice());
            log("Battery: "+EntryPoints.getBattery()+"%");
            log("\n" +
                            "██████╗░██████╗░░█████╗░██╗░░░░░██╗░░██╗\n" +
                            "██╔══██╗██╔══██╗██╔══██╗██║░░░░░██║░██╔╝\n" +
                            "██║░░██║██████╦╝██║░░██║██║░░░░░█████═╝░\n" +
                            "██║░░██║██╔══██╗██║░░██║██║░░░░░██╔═██╗░\n" +
                            "██████╔╝██████╦╝╚█████╔╝███████╗██║░╚██╗\n" +
                            "╚═════╝░╚═════╝░░╚════╝░╚══════╝╚═╝░░╚═╝\n"+
                            "by Bolyuk 02.04.2022 [version: "+version+" ]");
            log("try to get startup scripts...");
            EntryPoints.runStartUpScripts();
            log("succefully started");
        }catch(Exception e){
            log("One from DBolk component can`t start properly:\n"+e.getMessage());
        }
    }

    public static void run(String code){
        boolean isRunner=false;
        for(int i=0; i< observer.size();i++)
            isRunner=isRunner || observer.get(i).onRecieved(code, isRunner);

        if(!isRunner) {
            DLogger.log(" > " + code, context + "/" + user,getLogState());
            LuaRunner.run(code);
        }

    }

}
