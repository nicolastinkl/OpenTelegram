package io.openinstall.sdk;

import java.io.Serializable;

/* loaded from: classes.dex */
public class dh implements Serializable {
    private String a;
    private String b;

    public dh(String str) {
        this.a = str;
    }

    public String a() {
        return this.a;
    }

    public void a(String str) {
        this.b = str;
    }

    public String b() {
        return this.b;
    }
}
