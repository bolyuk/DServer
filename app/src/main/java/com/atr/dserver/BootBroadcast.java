package com.atr.dserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.bolyuk.Kovalski;

public class BootBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, MainService.class));
        Kovalski.put(3,"On boot loading...");
    }
}
