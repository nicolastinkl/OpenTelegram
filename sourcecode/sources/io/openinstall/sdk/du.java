package io.openinstall.sdk;

import android.app.Activity;
import android.view.View;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
class du extends at {
    final /* synthetic */ dt a;

    du(dt dtVar) {
        this.a = dtVar;
    }

    @Override // io.openinstall.sdk.at, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        Runnable runnable;
        WeakReference<Activity> weakReference;
        View decorView = activity.getWindow().getDecorView();
        runnable = this.a.h;
        decorView.postDelayed(runnable, 300L);
        this.a.e = new WeakReference(activity);
        dt dtVar = this.a;
        weakReference = dtVar.e;
        dtVar.a(weakReference);
    }
}
