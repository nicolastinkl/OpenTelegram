package com.tencent.qimei.o;

/* compiled from: TokenHolder.java */
/* loaded from: classes.dex */
public class v implements Runnable {
    public final /* synthetic */ String a;
    public final /* synthetic */ w b;

    public v(w wVar, String str) {
        this.b = wVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.b(this.a);
    }
}
