package io.openinstall.sdk;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

/* loaded from: classes.dex */
public abstract class df extends at {
    private Runnable a = null;
    private final Handler b = new Handler(Looper.getMainLooper());
    private volatile boolean c = true;
    private volatile boolean d = false;

    protected df() {
    }

    public abstract void a();

    public abstract void b();

    @Override // io.openinstall.sdk.at, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        super.onActivityPaused(activity);
        this.d = true;
        Runnable runnable = this.a;
        if (runnable != null) {
            this.b.removeCallbacks(runnable);
        }
        dg dgVar = new dg(this);
        this.a = dgVar;
        this.b.postDelayed(dgVar, 500L);
    }

    @Override // io.openinstall.sdk.at, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        super.onActivityResumed(activity);
        boolean z = !this.c;
        this.c = true;
        this.d = false;
        Runnable runnable = this.a;
        if (runnable != null) {
            this.b.removeCallbacks(runnable);
            this.a = null;
        }
        if (z) {
            a();
        }
    }
}
