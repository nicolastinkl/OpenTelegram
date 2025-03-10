package com.tencent.qimei.y;

/* compiled from: SysBrowser.java */
/* loaded from: classes.dex */
public class f implements Runnable {
    public final /* synthetic */ g a;

    public f(g gVar) {
        this.a = gVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.a.a();
        } catch (Exception e) {
            com.tencent.qimei.k.a.a(e);
        }
    }
}
