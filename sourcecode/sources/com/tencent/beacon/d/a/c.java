package com.tencent.beacon.d.a;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseArray;
import com.tencent.beacon.a.d.a;
import com.tencent.beacon.module.StatModule;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* compiled from: LifecycleCallbacks.java */
/* loaded from: classes.dex */
public class c implements Application.ActivityLifecycleCallbacks {
    private static SparseArray<WeakReference<Activity>> a = new SparseArray<>();
    private boolean b = false;
    private long c = 0;
    private long d = 0;
    private long e = 20000;
    private String f = "";
    private Map<String, String> g;
    private StatModule h;

    public c(StatModule statModule) {
        this.h = statModule;
        HashMap hashMap = new HashMap(6);
        this.g = hashMap;
        hashMap.put("A63", "N");
        this.g.put("A66", "F");
    }

    private boolean c() {
        String d = com.tencent.beacon.base.util.b.d();
        if ("".equals(this.f)) {
            this.f = com.tencent.beacon.a.d.a.a().getString("LAUEVE_DENGTA", "");
        }
        boolean z = false;
        if (!d.equals(this.f)) {
            a.SharedPreferencesEditorC0016a edit = com.tencent.beacon.a.d.a.a().edit();
            if (com.tencent.beacon.base.util.b.a((SharedPreferences.Editor) edit)) {
                edit.putString("LAUEVE_DENGTA", d);
            }
            if (!"".equals(this.f)) {
                com.tencent.beacon.base.util.c.a("[core] -> report new day launcher event.", new Object[0]);
                z = true;
            }
            this.f = d;
        }
        return z;
    }

    private void d() {
        com.tencent.beacon.a.b.a.a().a(new a(this));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        a(activity);
        a(true, activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        a(activity);
        a(false, activity);
    }

    public static SparseArray<WeakReference<Activity>> a() {
        return a;
    }

    private static void b(Activity activity) {
        if (activity == null || a == null) {
            return;
        }
        int hashCode = activity.hashCode();
        if (a.get(hashCode) == null) {
            a.put(hashCode, new WeakReference<>(activity));
        }
    }

    private void a(boolean z, Activity activity) {
        if (z) {
            long currentTimeMillis = System.currentTimeMillis();
            if (this.c > 0) {
                long j = this.d;
                if (j > 0 && j + b() <= currentTimeMillis) {
                    com.tencent.beacon.base.util.c.a("[lifecycle] -> return foreground more than 20s.", new Object[0]);
                    d();
                    StatModule statModule = this.h;
                    if (statModule != null) {
                        statModule.a();
                    }
                }
            }
            this.c = currentTimeMillis;
            this.d = 0L;
            return;
        }
        long currentTimeMillis2 = System.currentTimeMillis();
        this.d = currentTimeMillis2;
        long j2 = this.c;
        if (800 + j2 > currentTimeMillis2) {
            com.tencent.beacon.base.util.c.a("[lifecycle] -> debounce activity switch.", new Object[0]);
            this.c = 0L;
            return;
        }
        if (j2 == 0) {
            this.c = currentTimeMillis2;
        }
        StatModule statModule2 = this.h;
        if (statModule2 != null) {
            statModule2.b();
        }
    }

    private long b() {
        if (this.e <= 20000) {
            String a2 = com.tencent.beacon.e.a.a().a("hotLauncher");
            if (a2 != null) {
                try {
                    this.e = Long.valueOf(a2).longValue();
                    com.tencent.beacon.base.util.c.a("[strategy] -> change launcher time: %s ms", a2);
                } catch (NumberFormatException unused) {
                    com.tencent.beacon.base.util.c.b("[strategy] -> event param 'hotLauncher' error.", new Object[0]);
                }
            }
            this.e++;
        }
        return this.e;
    }

    private void a(Activity activity) {
        com.tencent.beacon.a.c.b.d = true;
        b(activity);
        if (!this.b) {
            com.tencent.beacon.base.util.c.a("[event] lifecycle callback recover active user.", new Object[0]);
            com.tencent.beacon.a.b.a.a().a(new b(this, activity));
            this.b = true;
        }
        if (c()) {
            d();
        }
    }
}
