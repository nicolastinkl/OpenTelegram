package com.tencent.qimei.p;

import android.annotation.SuppressLint;
import com.tencent.qimei.j.b;
import com.tencent.qimei.sdk.debug.IDebugger;

/* compiled from: QimeiDebugger.java */
/* loaded from: classes.dex */
public class a implements IDebugger {
    public final String a;

    public a(String str) {
        this.a = str;
    }

    @Override // com.tencent.qimei.sdk.debug.IDebugger
    @SuppressLint({"MissingPermission"})
    public void requestQm() {
    }

    @Override // com.tencent.qimei.sdk.debug.IDebugger
    public void requestStrategy() {
    }

    @Override // com.tencent.qimei.sdk.debug.IDebugger
    public void setDebug(boolean z) {
        b.a = z;
    }
}
