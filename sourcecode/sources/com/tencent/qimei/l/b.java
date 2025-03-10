package com.tencent.qimei.l;

/* compiled from: MultiAppKeyDeviceInfo.java */
/* loaded from: classes.dex */
public class b implements Runnable {
    public final /* synthetic */ com.tencent.qimei.c.d a;
    public final /* synthetic */ d b;

    public b(d dVar, com.tencent.qimei.c.d dVar2) {
        this.b = dVar;
        this.a = dVar2;
    }

    @Override // java.lang.Runnable
    public void run() {
        Object obj;
        boolean z;
        obj = this.b.c;
        synchronized (obj) {
            z = this.b.d;
            if (!z) {
                com.tencent.qimei.c.c.j().d = 10L;
                this.a.a(4);
                this.b.d = true;
            }
        }
    }
}
