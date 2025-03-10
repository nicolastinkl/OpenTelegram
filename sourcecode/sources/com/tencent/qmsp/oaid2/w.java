package com.tencent.qmsp.oaid2;

/* loaded from: classes.dex */
public class w {
    public int a;
    public long b;
    public String c;
    public String d;

    public w(String str) {
        this.c = str;
    }

    public void a(long j) {
        this.b = j;
    }

    public void b() {
        this.b = 0L;
    }

    public void a(String str) {
        this.d = str;
    }

    public boolean a() {
        return this.b > System.currentTimeMillis();
    }

    public void a(int i) {
        this.a = i;
    }
}
