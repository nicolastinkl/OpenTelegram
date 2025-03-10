package com.shubao.xinstall.a.a;

import android.content.Context;
import android.text.TextUtils;
import com.shubao.xinstall.a.f.k;
import java.util.IdentityHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/* loaded from: classes.dex */
public class a {
    protected Context a;
    protected h b;
    protected com.shubao.xinstall.a.b.a c;
    protected String d;
    protected g e;
    protected com.shubao.xinstall.a.f.i f;
    protected com.shubao.xinstall.a.f.h g;
    protected e i;
    private String j = "";
    protected ThreadPoolExecutor h = k.a();

    public a(Context context, h hVar, com.shubao.xinstall.a.b.a aVar, g gVar) {
        this.a = context;
        this.b = hVar;
        this.c = aVar;
        this.e = gVar;
        this.f = com.shubao.xinstall.a.f.i.a(context);
        this.g = com.shubao.xinstall.a.f.h.a(context);
        this.i = e.a(gVar, aVar);
    }

    public final String a(String str) {
        return this.j.equals("dddd") ? String.format("%s/e/a/xinstall/app/android/%s/%s", com.shubao.xinstall.a.e.c.a(), this.d, str) : String.format("%s/e/a/xinstall/app/android/%s/%s", com.shubao.xinstall.a.e.c.a, this.d, str);
    }

    public final IdentityHashMap<String, String> a() {
        IdentityHashMap<String, String> a = this.f.a();
        a.put("installId", "");
        a.put("isx", "true");
        return a;
    }

    public final Context b() {
        return this.a;
    }

    public final void b(String str) {
        this.d = str;
    }

    public final h c() {
        return this.b;
    }

    public final void c(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.c.a(com.shubao.xinstall.a.b.a.a(str), true);
        this.i.a(this.c);
        this.e.a(this.c);
        this.c.d();
    }

    public final com.shubao.xinstall.a.b.a d() {
        return this.c;
    }

    public final void d(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.c.a(com.shubao.xinstall.a.b.a.a(str), false);
        this.i.a(this.c);
        this.e.a(this.c);
        this.c.d();
    }

    public final String e() {
        return this.d;
    }

    public final g f() {
        return this.e;
    }

    public final com.shubao.xinstall.a.f.i g() {
        return this.f;
    }

    public final com.shubao.xinstall.a.f.h h() {
        return this.g;
    }

    public final ThreadPoolExecutor i() {
        return this.h;
    }

    public final e j() {
        return this.i;
    }
}
