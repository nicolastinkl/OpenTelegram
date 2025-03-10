package com.tencent.qimei.o;

import android.os.MessageQueue;

/* compiled from: HidBuilder.java */
/* loaded from: classes.dex */
public class h implements MessageQueue.IdleHandler {
    public final /* synthetic */ j a;

    public h(j jVar) {
        this.a = jVar;
    }

    @Override // android.os.MessageQueue.IdleHandler
    public boolean queueIdle() {
        this.a.b();
        return false;
    }
}
