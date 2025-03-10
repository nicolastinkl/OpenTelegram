package com.tencent.qimei.l;

/* compiled from: MultiAppKeyDeviceInfo.java */
/* loaded from: classes.dex */
public class c implements com.tencent.qimei.c.d {
    public final /* synthetic */ com.tencent.qimei.c.d a;
    public final /* synthetic */ d b;

    public c(d dVar, com.tencent.qimei.c.d dVar2) {
        this.b = dVar;
        this.a = dVar2;
    }

    @Override // com.tencent.qimei.c.d
    public void a(int i) {
        Object obj;
        boolean z;
        if (this.a != null) {
            obj = this.b.c;
            synchronized (obj) {
                z = this.b.d;
                if (!z) {
                    this.a.a(i);
                    this.b.d = true;
                }
            }
        }
    }
}
