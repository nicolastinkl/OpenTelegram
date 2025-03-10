package com.tencent.beacon.d.a;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.tencent.beacon.module.StatModule;
import java.util.HashMap;
import java.util.Map;

/* compiled from: PageTimeLifeCallbacks.java */
@TargetApi(14)
/* loaded from: classes.dex */
public class d implements Application.ActivityLifecycleCallbacks {
    private long a = System.currentTimeMillis();
    private Map<Activity, Long> b = new HashMap(3);
    private StatModule c;

    public d(StatModule statModule) {
        this.c = statModule;
    }

    private void a(Activity activity) {
        Long l = this.b.get(activity);
        if (l == null) {
            l = Long.valueOf(this.a);
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.c.b(activity.getLocalClassName(), currentTimeMillis - l.longValue(), currentTimeMillis);
        this.b.remove(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        this.b.put(activity, Long.valueOf(System.currentTimeMillis()));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }
}
