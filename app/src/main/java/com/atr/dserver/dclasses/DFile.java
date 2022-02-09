package com.atr.dserver.dclasses;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class DFile extends DBClass{

    public static boolean isExist(String path){
        return new File(path).exists();
    }

    public static boolean isDirectory(String path){
        return new File(path).isDirectory();
    }

    public static void writeToFile(String content, String path) {
        try {
            File file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.append(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {e.printStackTrace(); }
    }

    public static String readFromFile(String path){
        File file = new File(path);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (Exception e) { e.printStackTrace();}
        return text.toString();
    }

    public static void makeDirectory(String path){
        log("Try to create dir ("+path+")");
        File f = new File(path);
        f.mkdir();
    }

    public static String getRootDirectory(){
        log("Try get root ("+ Environment.getExternalStorageDirectory().getAbsolutePath()+")");
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String[] getFilesFromDir(String path){
        File directory = new File(path);
        File[] files = directory.listFiles();
        if(files == (null) || files.length == 0) return null;
        String[] result = new String[files.length];
        for(int i=0; i<files.length;i++)
            result[i]=files[i].getAbsolutePath();
        return result;
    }
}
