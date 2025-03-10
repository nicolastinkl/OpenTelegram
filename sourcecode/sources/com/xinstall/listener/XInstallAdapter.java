package com.xinstall.listener;

import com.xinstall.model.XAppData;
import com.xinstall.model.XAppError;

/* loaded from: classes.dex */
public abstract class XInstallAdapter implements XInstallListener {
    public abstract void onInstall(XAppData xAppData);

    @Override // com.xinstall.listener.XInstallListener
    public void onInstallFinish(XAppData xAppData, XAppError xAppError) {
        if (xAppData == null) {
            xAppData = new XAppData();
        }
        onInstall(xAppData);
    }
}
