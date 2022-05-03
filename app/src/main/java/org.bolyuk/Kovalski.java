package org.bolyuk;

import java.util.ArrayList;

public class Kovalski extends Baser {

    /*
     3 - console
     2 - log
     1 - warning
     0 - exception
    */
    public static void init(){
     for(int i=0; i<4;i++){
         inAppLog[i] = new ArrayList<String>();
         observer[i] = new ArrayList<Getter>();
     }
    }

    public static ArrayList<String>[] inAppLog = new ArrayList[4];
    private static ArrayList<Getter>[] observer = new ArrayList[4];

    public static void addListener(int i,Getter listener){
        observer[i].add(listener);
    }

    public static void delListener(int i,Getter listener){
        observer[i].remove(listener);
    }

    public interface Getter{
        public void onLogAdded(String text);
    }

    private static void runListener(int i){
            for(int y=0; y< observer[i].size(); y++)
                observer[i].get(y).onLogAdded(inAppLog[i].get(inAppLog[i].size()-1));

    }

    public static void handle(Exception e){
     put(3,e.getMessage());
     put(0,e.toString());
    }

    public static void put(int i, String text){
        try{
            inAppLog[i].add(text);
            runListener(i);
        } catch (Exception e) { handle(e);}
    }
}
