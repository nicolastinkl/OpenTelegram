package com.tencent.qmsp.sdk.g.e;

/* loaded from: classes.dex */
public class b {
    public int a;
    public long b;
    public String c;
    public String d;

    public b(String str) {
        this.c = str;
    }

    public void a(int i) {
        this.a = i;
    }

    public void a(long j) {
        this.b = j;
    }

    public void a(String str) {
        this.d = str;
    }

    public boolean a() {
        return this.b > System.currentTimeMillis();
    }

    public void b() {
        this.b = 0L;
    }
}
