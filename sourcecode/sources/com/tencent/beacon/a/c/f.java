package com.tencent.beacon.a.c;

/* compiled from: PrivateInfo.java */
/* loaded from: classes.dex */
public class f {
    private static volatile f a;
    private String b = "";
    private String c = "";
    private String d = "";
    private String e = "";
    private String f = "";
    private String g = "unset";
    private String h = "";
    private String i = "";
    private String j = "";
    private String k = "";

    private f() {
    }

    public static f e() {
        if (a == null) {
            synchronized (f.class) {
                if (a == null) {
                    a = new f();
                }
            }
        }
        return a;
    }

    public String a() {
        return this.b;
    }

    public String b() {
        return this.c;
    }

    public String c() {
        return this.d;
    }

    public String d() {
        return this.e;
    }

    public void f(String str) {
        this.f = str;
    }

    public String g() {
        return this.f;
    }

    public String h() {
        return this.g;
    }

    public void i(String str) {
        this.i = str;
    }

    public String j() {
        return this.i;
    }

    public String k() {
        return this.j;
    }

    public void a(String str) {
        this.b = str;
    }

    public void b(String str) {
        this.c = str;
    }

    public void c(String str) {
        this.d = str;
    }

    public void d(String str) {
        this.e = str;
    }

    public String f() {
        return this.h;
    }

    public void g(String str) {
        this.g = str;
    }

    public void h(String str) {
        this.k = str;
    }

    public String i() {
        return this.k;
    }

    public void j(String str) {
        this.j = str;
    }

    public void e(String str) {
        this.h = str;
    }
}
