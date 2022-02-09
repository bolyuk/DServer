package com.atr.dserver.dclasses;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

public class DVars extends DBClass{
    private static HashMap<String, Object> vars = new HashMap<String, Object>();
    private static String path = DBolk.dbolkpath+"/DVar.config";

    public static void init(){
        log("DVars initializing...");
        path = DBolk.dbolkpath+"/DVar.config";
        if(DFile.isExist(path))
            load();
        else
            save();
    }

    public static void set(String name, Object value){
        if(contains(name)) vars.remove(name);
     vars.put(name, value);
     save();
    }

    public static boolean contains(String name)
    {
        return vars.containsKey(name);
    }

    public static long getL(String name){
        return Long.parseLong(getS(name));
    }


    public static int getI(String name){
    return Integer.parseInt(getS(name));
    }

    public static String getS(String name){
        return get(name).toString();
    }


    public static Object get(String name)
    {
        return vars.get(name);
    }

    public static void save(){
        log("DVars saving...");
        DFile.writeToFile(new Gson().toJson(vars),path);
    }


    public static void load(){
        log("DVars loading...");
        vars = new Gson().fromJson(
                DFile.readFromFile(path), new TypeToken<HashMap<String, Object>>() {}.getType()
        );
    }

}
