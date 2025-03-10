package com.tencent.qimei.o;

import com.tencent.qimei.sdk.IAsyncQimeiListener;
import com.tencent.qimei.sdk.Qimei;

/* compiled from: QimeiSDKInfo.java */
/* loaded from: classes.dex */
public class s implements Runnable {
    public final /* synthetic */ IAsyncQimeiListener a;
    public final /* synthetic */ u b;

    public s(u uVar, IAsyncQimeiListener iAsyncQimeiListener) {
        this.b = uVar;
        this.a = iAsyncQimeiListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        Qimei qimei = this.b.getQimei();
        if (qimei == null || qimei.isEmpty()) {
            this.b.a(this.a);
        } else {
            this.a.onQimeiDispatch(qimei);
        }
    }
}
