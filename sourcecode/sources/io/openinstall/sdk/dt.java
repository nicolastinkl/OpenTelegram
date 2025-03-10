package io.openinstall.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class dt {
    private static dt a;
    private final boolean b;
    private dy c;
    private Application f;
    private Application.ActivityLifecycleCallbacks g;
    private boolean d = true;
    private WeakReference<Activity> e = null;
    private final Runnable h = new dv(this);

    private dt(Context context) {
        boolean booleanValue = aw.a().f().booleanValue();
        this.b = booleanValue;
        if (!booleanValue) {
            if (ga.a) {
                ga.a("clipBoardEnabled = false", new Object[0]);
            }
        } else {
            this.c = new dy(context);
            this.f = (Application) context.getApplicationContext();
            du duVar = new du(this);
            this.g = duVar;
            this.f.registerActivityLifecycleCallbacks(duVar);
        }
    }

    public static dt a(Context context) {
        if (a == null) {
            synchronized (dt.class) {
                if (a == null) {
                    a = new dt(context);
                }
            }
        }
        return a;
    }

    public void a(String str) {
        if (this.b && this.d) {
            if (ga.a) {
                ga.a("%s release", str);
            }
            this.c.b();
        }
    }

    public void a(WeakReference<Activity> weakReference) {
        if (!this.b || weakReference == null) {
            return;
        }
        this.c.a(weakReference);
    }

    public void a(boolean z) {
        this.d = z;
    }

    public boolean a() {
        return this.b;
    }

    public dw b() {
        return b(false);
    }

    public dw b(boolean z) {
        Application.ActivityLifecycleCallbacks activityLifecycleCallbacks;
        if (!this.b) {
            return null;
        }
        dw a2 = dw.a(z ? this.c.e() : this.c.d());
        if (a2 != null) {
            if (ga.a) {
                ga.a("data type is %d", Integer.valueOf(a2.c()));
            }
            Application application = this.f;
            if (application != null && (activityLifecycleCallbacks = this.g) != null) {
                application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
                this.g = null;
            }
        } else if (ga.a) {
            ga.a("data is null", new Object[0]);
        }
        return a2;
    }

    public void b(String str) {
        if (this.b && this.d) {
            if (ga.a) {
                ga.a("%s access", str);
            }
            this.c.a();
        }
    }
}
