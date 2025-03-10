package io.openinstall.sdk;

import android.content.Context;

/* loaded from: classes.dex */
public class r extends ez {
    private final Context a;

    public r(Context context) {
        this.a = context;
    }

    @Override // io.openinstall.sdk.ez
    public boolean a() {
        return true;
    }

    @Override // io.openinstall.sdk.ez
    protected String b() {
        return "si";
    }

    @Override // io.openinstall.sdk.ez
    protected String c() {
        return "bnwp";
    }

    @Override // io.openinstall.sdk.ez
    protected String d() {
        if (k.a().a(this.a)) {
            return String.valueOf(true);
        }
        return null;
    }
}
