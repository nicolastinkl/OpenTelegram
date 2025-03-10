package com.tencent.qimei.o;

/* compiled from: QimeiQueryTask.java */
/* loaded from: classes.dex */
public class q implements Runnable {
    public final /* synthetic */ r a;

    public q(r rVar) {
        this.a = rVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.tencent.qimei.u.a aVar = new com.tencent.qimei.u.a(this.a.i);
        if (aVar.a() == null) {
            return;
        }
        aVar.a().M();
    }
}
