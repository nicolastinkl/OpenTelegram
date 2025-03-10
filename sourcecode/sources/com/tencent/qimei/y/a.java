package com.tencent.qimei.y;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;

/* compiled from: JsInteraction.java */
/* loaded from: classes.dex */
public class a {
    public final Object a;
    public String b;
    public b c;
    public final com.tencent.qimei.j.f d;

    public a(String str) {
        Object obj = new Object();
        this.a = obj;
        this.b = str;
        this.c = null;
        this.d = new com.tencent.qimei.j.f(obj, 30000);
    }

    public void a(String str) {
        this.b = str;
    }

    public void b() {
        this.d.b();
    }

    @JavascriptInterface
    public void callback(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            this.c = new b(str, this.b, str2, str3);
        }
        this.d.a();
    }

    public b a() {
        return this.c;
    }
}
