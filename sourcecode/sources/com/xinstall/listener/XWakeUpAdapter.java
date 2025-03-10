package com.xinstall.listener;

import com.xinstall.model.XAppData;
import com.xinstall.model.XAppError;

/* loaded from: classes.dex */
public abstract class XWakeUpAdapter implements XWakeUpListener {
    public void onWakeUp(XAppData xAppData) {
    }

    @Override // com.xinstall.listener.XWakeUpListener
    public void onWakeUpFinish(XAppData xAppData, XAppError xAppError) {
        if (xAppData == null) {
            return;
        }
        try {
            onWakeUp(xAppData);
        } catch (Exception unused) {
        }
    }
}
