package com.tencent.qimei.m;

/* compiled from: QimeiSec.java */
/* loaded from: classes.dex */
public class a implements Runnable {
    public final /* synthetic */ String a;
    public final /* synthetic */ b b;

    public a(b bVar, String str) {
        this.b = bVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.b(this.a);
    }
}
