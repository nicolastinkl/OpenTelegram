package com.tencent.qimei.y;

/* compiled from: SysBrowser.java */
/* loaded from: classes.dex */
public class e implements Runnable {
    public final /* synthetic */ g a;

    public e(g gVar) {
        this.a = gVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            g.a(this.a);
        } catch (Exception e) {
            com.tencent.qimei.k.a.a(e);
        }
    }
}
