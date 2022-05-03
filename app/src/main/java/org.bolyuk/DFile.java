package org.bolyuk;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class DFile extends Baser {

    public static boolean isExist(String path){
        return new File(path).exists();
    }

    public static boolean isDir(String path){
        return new File(path).isDirectory();
    }

    public static void append(String content, String path) {
        try {
            File file = new File(path);

            FileWriter writer = new FileWriter(file);
            writer.append(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {kovalski(e); }
    }

    public static void write(String content, String path) {
        try {
            File file = new File(path);

            if (!file.exists()) file.createNewFile();

            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {kovalski(e); }
    }

    public static String read(String path){
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
        catch (Exception e) { kovalski(e);}
        return text.toString();
    }

    public static void mkDir(String path){
        try {
            File f = new File(path);
            f.mkdir();
        }catch (Exception e){
            kovalski(e);
        }
    }

    public static String getRoot(){
        try {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
        }catch (Exception e){
            kovalski(e);
            return null;
        }
    }

    public static String[] getFilesFromDir(String path){
        try {
            File directory = new File(path);
            File[] files = directory.listFiles();
            if (files == (null) || files.length == 0) return null;
            String[] result = new String[files.length];
            for (int i = 0; i < files.length; i++)
                result[i] = files[i].getAbsolutePath();
            return result;
        }catch (Exception e){ kovalski(e);}
        return new String[0];
    }
}
