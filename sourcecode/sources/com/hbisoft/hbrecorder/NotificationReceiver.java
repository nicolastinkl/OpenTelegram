package com.hbisoft.hbrecorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes.dex */
public class NotificationReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        context.stopService(new Intent(context, (Class<?>) ScreenRecordService.class));
    }
}
