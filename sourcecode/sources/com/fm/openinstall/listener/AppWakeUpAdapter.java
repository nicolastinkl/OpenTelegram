package com.fm.openinstall.listener;

import com.fm.openinstall.model.AppData;
import com.fm.openinstall.model.Error;

/* loaded from: classes.dex */
public abstract class AppWakeUpAdapter implements AppWakeUpListener {
    public abstract void onWakeUp(AppData appData);

    @Override // com.fm.openinstall.listener.AppWakeUpListener
    public void onWakeUpFinish(AppData appData, Error error) {
        if (error != null || appData == null || appData.isEmpty()) {
            return;
        }
        onWakeUp(appData);
    }
}
