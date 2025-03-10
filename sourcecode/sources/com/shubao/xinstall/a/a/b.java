package com.shubao.xinstall.a.a;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import com.android.installreferrer.api.InstallReferrerClient;
import com.shubao.xinstall.a.a.C0010a;
import com.shubao.xinstall.a.a.a.j;
import com.shubao.xinstall.a.f.o;
import com.xinstall.OnePXActivity;
import com.xinstall.listener.XInstallListener;
import com.xinstall.listener.XWakeUpListener;
import com.xinstall.model.XAppError;

/* loaded from: classes.dex */
public final class b extends com.shubao.xinstall.a.a.a {
    private static boolean k;
    public a j;
    private int l;
    private long m;
    private long n;

    public class a extends com.shubao.xinstall.a.c {
        long a = 0;
        public OnePXActivity b;

        a() {
        }

        @Override // com.shubao.xinstall.a.c, android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityDestroyed(Activity activity) {
            if (this.b != null) {
                this.b = null;
            }
        }

        @Override // com.shubao.xinstall.a.c, android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityStarted(Activity activity) {
            if (b.this.l == 0) {
                this.a = System.currentTimeMillis();
                d.a();
            }
            b.b(b.this);
            if (activity instanceof OnePXActivity) {
                this.b = (OnePXActivity) activity;
            }
        }

        @Override // com.shubao.xinstall.a.c, android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityStopped(Activity activity) {
            b.c(b.this);
        }
    }

    static {
        try {
            Class.forName("com.android.installreferrer.api.InstallReferrerClient");
            k = true;
        } catch (Throwable unused) {
            k = false;
        }
    }

    public b(Context context, h hVar, com.shubao.xinstall.a.b.a aVar, g gVar) {
        super(context, hVar, aVar, gVar);
        this.m = 0L;
        this.n = 0L;
        this.j = new a();
        ((Application) this.a.getApplicationContext()).registerActivityLifecycleCallbacks(this.j);
    }

    static /* synthetic */ int b(b bVar) {
        int i = bVar.l;
        bVar.l = i + 1;
        return i;
    }

    static /* synthetic */ int c(b bVar) {
        int i = bVar.l;
        bVar.l = i - 1;
        return i;
    }

    public final void a(long j, XInstallListener xInstallListener) {
        if (j <= 0) {
            j = 10;
        }
        i iVar = new i(new com.shubao.xinstall.a.a.a.c(this, j), new com.shubao.xinstall.a.a.a.e(xInstallListener, this), this);
        iVar.b = j;
        this.h.execute(iVar);
    }

    public final void a(Uri uri) {
        this.h.execute(new com.shubao.xinstall.a.a.a.h(uri, this));
    }

    public final void a(Uri uri, XWakeUpListener xWakeUpListener) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.m < 1000) {
            if (xWakeUpListener != null) {
                xWakeUpListener.onWakeUpFinish(null, new XAppError(XAppError.ERROR_GETWAKEUP_TOO_OFTEN, "回调请求过于频繁、XInstall进行了过滤"));
            }
        } else {
            this.m = currentTimeMillis;
            if (o.a) {
                o.a("调用上报功能成功");
            }
            this.h.execute(new i(new j(uri, false, this), new com.shubao.xinstall.a.a.a.i(xWakeUpListener, uri, this), this));
        }
    }

    public final void a(XWakeUpListener xWakeUpListener) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.n < 1000) {
            if (xWakeUpListener != null) {
                xWakeUpListener.onWakeUpFinish(null, new XAppError(XAppError.ERROR_GETWAKEUP_TOO_OFTEN, "回调请求过于频繁、XInstall进行了过滤"));
            }
        } else {
            this.n = currentTimeMillis;
            if (o.a) {
                o.a("调用YYB上报功能成功");
            }
            this.h.execute(new i(new j(null, true, this), new com.shubao.xinstall.a.a.a.i(xWakeUpListener, null, this), this));
        }
    }

    public final com.shubao.xinstall.a.a k() {
        if (!k) {
            return null;
        }
        com.shubao.xinstall.a.a aVar = new com.shubao.xinstall.a.a();
        Context applicationContext = this.a.getApplicationContext();
        if (o.a) {
            o.a("PlayInstallReferrer setUp");
        }
        InstallReferrerClient build = InstallReferrerClient.newBuilder(applicationContext).build();
        aVar.b = build;
        build.startConnection(aVar.new C0010a());
        return aVar;
    }

    public final void l() {
        h hVar = this.b;
        if (hVar == null || hVar.a != c.e) {
            return;
        }
        m();
    }

    public final void m() {
        this.h.execute(new com.shubao.xinstall.a.a.a.d(this));
    }
}
