package io.openinstall.sdk;

import android.content.Context;
import android.os.Build;

/* loaded from: classes.dex */
public class aq extends dq {
    private final Context a = aw.a().c();

    @Override // io.openinstall.sdk.ay
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public dp a_() {
        return (aw.a().j().booleanValue() || !fz.b(this.a)) ? new ar() : Build.VERSION.SDK_INT >= 29 ? new ap(this.a) : new as(this.a);
    }
}
