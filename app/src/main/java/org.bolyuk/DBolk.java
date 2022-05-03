package org.bolyuk;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DBolk extends Baser {
    private static int version = 3;

    private static Globals globals;
    private static Globals g;
/*
    private static ArrayList<Runner> observer = new ArrayList<Runner>();

    public interface Runner{
        public boolean onRecieved(String command, boolean isSomeoneRunBefore);
    }

    public static void addRunner(Runner runner){
        observer.add(runner);
    }

    public static void delRunner(Runner runner){
        observer.remove(runner);
    }
*/
    public static void init(){
        try {
            Kovalski.init();
            globals = JsePlatform.standardGlobals();
            globals.set("dbolk",CoerceJavaToLua.coerce(DBolk.class));
            kovalski("\n" +
                            "██████╗░██████╗░░█████╗░██╗░░░░░██╗░░██╗\n" +
                            "██╔══██╗██╔══██╗██╔══██╗██║░░░░░██║░██╔╝\n" +
                            "██║░░██║██████╦╝██║░░██║██║░░░░░█████═╝░\n" +
                            "██║░░██║██╔══██╗██║░░██║██║░░░░░██╔═██╗░\n" +
                            "██████╔╝██████╦╝╚█████╔╝███████╗██║░╚██╗\n" +
                            "╚═════╝░╚═════╝░░╚════╝░╚══════╝╚═╝░░╚═╝\n"+
                            "by Bolyuk 02.04.2022 [version: TEST ]",2);
        }catch(Exception e){
            kovalski(e);
        }
    }
    public static boolean compatibility(int v){
        return v == version;
    }

    public static void load(String className, String name){
        try {
            g.set(name, CoerceJavaToLua.coerce(Class.forName(className)));
        }catch (Exception e){ kovalski(e);}
    }

    public static void load(String LuaCode){
        g.call(LuaValue.valueOf(LuaCode));
    }

    public static void install(){
        try{
        LoadState.install(g);
        LuaC.install(g);
    }catch (Exception e){ kovalski(e); }
    }

    public static void loadG(String className, String name){
        try {
            globals.set(name, CoerceJavaToLua.coerce(Class.forName(className)));
        }catch (Exception e){ kovalski(e);}
    }

    public static void loadG(String LuaCode){
        globals.call(LuaValue.valueOf(LuaCode));
    }

    public static void installG(){
        try{
            LoadState.install(globals);
            LuaC.install(globals);
        }catch (Exception e){ kovalski(e); }
    }

    public static void run(String code){
        try {
        //boolean isRunner=false;
        //for(int i=0; i< observer.size();i++)
        //    isRunner=isRunner || observer.get(i).onRecieved(code, isRunner);
        //if(!isRunner) {
            kovalski(" > " + code,3);
            execute(code);
        //}
        }catch (Exception e){ kovalski(e); }

    }

    private static void execute(String code){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(baos, true, "utf-8");
            g = globals;
            g.STDOUT = printStream;
            g.load(code).call();
            String result =new String(baos.toByteArray());

            if(result != "")
                kovalski(result,3);
            printStream.close();
        }catch (Exception e){ kovalski(e); }
    }

}
