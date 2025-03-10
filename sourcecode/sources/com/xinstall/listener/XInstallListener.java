package com.xinstall.listener;

import com.xinstall.model.XAppData;
import com.xinstall.model.XAppError;

/* loaded from: classes.dex */
public interface XInstallListener {
    void onInstallFinish(XAppData xAppData, XAppError xAppError);
}
