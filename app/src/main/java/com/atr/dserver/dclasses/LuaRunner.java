package com.atr.dserver.dclasses;


import com.atr.dserver.MainService;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LuaRunner extends DBClass{
   private String context = "LuaJ";

    public static boolean run(String code){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(baos, true, "utf-8");

            Globals globals = JsePlatform.standardGlobals();
            globals.set("logger", CoerceJavaToLua.coerce(new DLogger()));
            globals.set("dbolk", CoerceJavaToLua.coerce(new DBolk()));
            globals.set("entry", CoerceJavaToLua.coerce(new EntryPoints()));
            globals.set("file", CoerceJavaToLua.coerce(new DFile()));
            globals.set("luaj", CoerceJavaToLua.coerce(new LuaRunner()));
            globals.set("firebase", CoerceJavaToLua.coerce(new DFirebase()));
            globals.set("vars", CoerceJavaToLua.coerce(new DVars()));
            globals.set("bot", CoerceJavaToLua.coerce(new DTelegram()));
            globals.STDOUT = printStream;

            LoadState.install(globals);
            LuaC.install(globals);
            globals.load(code).call();
            String result =new String(baos.toByteArray());

            if(result != "")
            log(result);
            printStream.close();
            return true;
        }catch (Exception e){
            log("Luaj error excepted!\n "+e.getMessage());
            return false;
        }



    }

}
