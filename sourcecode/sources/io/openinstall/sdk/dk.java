package io.openinstall.sdk;

import android.os.SystemClock;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
class dk extends df {
    long a = SystemClock.uptimeMillis();
    final /* synthetic */ dj b;

    dk(dj djVar) {
        this.b = djVar;
    }

    @Override // io.openinstall.sdk.df
    public void a() {
        this.a = SystemClock.uptimeMillis();
    }

    @Override // io.openinstall.sdk.df
    public void b() {
        this.b.a(TimeUnit.MILLISECONDS.toSeconds(SystemClock.uptimeMillis() - this.a));
    }
}
