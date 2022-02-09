package com.atr.dserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.atr.dserver.dclasses.DLogger;

public class BootBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, MainService.class));
        DLogger.log("On boot loading...", "BootBroadcast",true);
    }
}
