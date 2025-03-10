package io.openinstall.sdk;

import android.content.ClipData;
import android.content.Context;

/* loaded from: classes.dex */
public class aw {
    private static final aw a = new aw();
    private Context c;
    private String d;
    private String e;
    private Boolean f;
    private Boolean g;
    private Boolean h;
    private Boolean m;
    private ClipData n;
    private boolean b = false;
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;
    private boolean l = false;

    private aw() {
    }

    public static aw a() {
        return a;
    }

    public void a(ClipData clipData) {
        this.n = clipData;
    }

    public void a(Context context) {
        this.c = context.getApplicationContext();
    }

    public void a(Boolean bool) {
        this.m = bool;
    }

    public void a(String str) {
        this.d = str;
    }

    public void b(Boolean bool) {
        this.f = bool;
    }

    public void b(String str) {
        this.e = str;
    }

    public boolean b() {
        return this.b;
    }

    public Context c() {
        return this.c;
    }

    public void c(Boolean bool) {
        this.h = bool;
    }

    public String d() {
        return this.d;
    }

    public String e() {
        return this.e;
    }

    public Boolean f() {
        if (this.m == null) {
            this.m = Boolean.valueOf(fy.b(this.c));
        }
        return this.m;
    }

    public ClipData g() {
        return this.n;
    }

    public Boolean h() {
        Boolean bool = this.f;
        return bool == null ? Boolean.TRUE : bool;
    }

    public Boolean i() {
        if (this.g == null) {
            this.g = Boolean.valueOf(fy.c(this.c));
        }
        return this.g;
    }

    public Boolean j() {
        Boolean bool = this.h;
        return bool == null ? Boolean.FALSE : bool;
    }
}
