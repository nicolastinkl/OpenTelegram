package io.openinstall.sdk;

import android.content.Context;

/* loaded from: classes.dex */
public abstract class ao implements dp {
    protected Context a;
    private volatile String b = null;
    private volatile boolean c = false;

    public ao(Context context) {
        this.a = context;
    }

    @Override // io.openinstall.sdk.dp
    public synchronized String a(String str) {
        if (this.c) {
            return this.b;
        }
        return b(str);
    }

    @Override // io.openinstall.sdk.dp
    public synchronized void a(String str, String str2) {
        if (str2 == null) {
            return;
        }
        if (this.c && str2.equals(this.b)) {
            return;
        }
        if (b(str, str2)) {
            this.c = true;
        } else {
            this.c = false;
        }
        this.b = str2;
    }

    public abstract String b(String str);

    public abstract boolean b(String str, String str2);
}
