package com.fm.openinstall.listener;

import com.fm.openinstall.model.AppData;
import com.fm.openinstall.model.Error;

/* loaded from: classes.dex */
public abstract class AppInstallAdapter implements AppInstallListener {
    public abstract void onInstall(AppData appData);

    @Override // com.fm.openinstall.listener.AppInstallListener
    public void onInstallFinish(AppData appData, Error error) {
        if (appData == null) {
            appData = new AppData();
        }
        onInstall(appData);
    }
}
