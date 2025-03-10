package io.openinstall.sdk;

import android.content.Context;

/* loaded from: classes.dex */
public class o extends ez {
    private final Context a;

    public o(Context context) {
        this.a = context;
    }

    @Override // io.openinstall.sdk.ez
    public boolean a() {
        return true;
    }

    @Override // io.openinstall.sdk.ez
    protected String b() {
        return "gR";
    }

    @Override // io.openinstall.sdk.ez
    protected String c() {
        return "nosw";
    }

    @Override // io.openinstall.sdk.ez
    protected String d() {
        v vVar = new v();
        vVar.a(this.a);
        return vVar.a();
    }
}
