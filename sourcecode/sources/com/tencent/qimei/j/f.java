package com.tencent.qimei.j;

/* compiled from: ThreadLock.java */
/* loaded from: classes.dex */
public class f {
    public final Object a;
    public int b;

    public f(Object obj, int i) {
        this.a = obj;
        this.b = i;
    }

    public void a() {
        synchronized (this.a) {
            this.a.notifyAll();
        }
    }

    public void b() {
        try {
            synchronized (this.a) {
                this.a.wait(this.b);
            }
        } catch (InterruptedException e) {
            com.tencent.qimei.k.a.a(e);
        }
    }
}
