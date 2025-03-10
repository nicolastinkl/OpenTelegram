package com.tencent.beacon.base.net;

/* compiled from: NetFailure.java */
/* loaded from: classes.dex */
public final class d {
    public String a;
    public String b;
    public int c;
    public String d;
    public Throwable e;

    public d(String str, String str2, int i, String str3) {
        this.a = str;
        this.b = str2;
        this.c = i;
        this.d = str3;
    }

    public String toString() {
        return "NetFailure{requestType='" + this.a + "', attaCode='" + this.b + "', responseCode=" + this.c + ", msg='" + this.d + "', exception=" + this.e + '}';
    }

    public d(String str, String str2, int i, String str3, Throwable th) {
        this.a = str;
        this.b = str2;
        this.c = i;
        this.d = str3;
        this.e = th;
    }
}
