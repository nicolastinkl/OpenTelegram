package com.tencent.qimei.y;

/* compiled from: X5Browser.java */
/* loaded from: classes.dex */
public class j implements Runnable {
    public final /* synthetic */ k a;

    public j(k kVar) {
        this.a = kVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.a.a();
        } catch (Throwable th) {
            com.tencent.qimei.k.a.a(th);
        }
    }
}
