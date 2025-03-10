package com.tencent.qimei.o;

import com.tencent.qimei.sdk.Qimei;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: QimeiQueryTask.java */
/* loaded from: classes.dex */
public class p implements Runnable {
    public final /* synthetic */ r a;

    public p(r rVar) {
        this.a = rVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        AtomicInteger atomicInteger;
        Qimei i = com.tencent.qimei.a.a.i(this.a.i);
        if (i != null && !i.isEmpty()) {
            this.a.b();
            return;
        }
        atomicInteger = this.a.b;
        if (atomicInteger.getAndIncrement() > 30) {
            return;
        }
        this.a.d();
    }
}
