package com.tencent.qimei.n;

import java.util.Map;

/* compiled from: Reporter.java */
/* loaded from: classes.dex */
public class g implements Runnable {
    public final /* synthetic */ String a;
    public final /* synthetic */ Map b;
    public final /* synthetic */ String c;
    public final /* synthetic */ String d;
    public final /* synthetic */ i e;

    public g(i iVar, String str, Map map, String str2, String str3) {
        this.e = iVar;
        this.a = str;
        this.b = map;
        this.c = str2;
        this.d = str3;
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this.e) {
            this.e.a(this.a, this.b, this.c, this.d);
        }
    }
}
