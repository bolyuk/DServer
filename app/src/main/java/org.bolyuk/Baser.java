package org.bolyuk;

public class Baser {

    public static void init(){

    }

    protected static void kovalski(String text, int num){
        Kovalski.put(num, text);
    }
    protected static void kovalski(Exception e){
     Kovalski.handle(e);
    }



}
