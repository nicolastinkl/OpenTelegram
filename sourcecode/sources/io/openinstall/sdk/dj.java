package io.openinstall.sdk;

import android.app.Application;
import android.os.HandlerThread;
import android.text.TextUtils;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes.dex */
public class dj {
    private final dm d;
    private Application.ActivityLifecycleCallbacks e;
    private final Object a = new Object();
    private final LinkedBlockingQueue<Object> c = new LinkedBlockingQueue<>(1);
    private final Application b = (Application) aw.a().c();

    public dj(az azVar) {
        HandlerThread handlerThread = new HandlerThread("EventsHandler");
        handlerThread.start();
        this.d = new dm(handlerThread.getLooper(), azVar);
    }

    private boolean a(String str) {
        return !TextUtils.isEmpty(str) && str.indexOf(44) < 0 && str.indexOf(59) < 0;
    }

    public void a() {
        synchronized (this.a) {
            if (this.e != null) {
                return;
            }
            dk dkVar = new dk(this);
            this.e = dkVar;
            this.b.registerActivityLifecycleCallbacks(dkVar);
        }
    }

    public void a(long j) {
        if (j >= 1) {
            de a = de.a(j);
            a.a(true);
            this.d.a(a);
        }
    }

    public void a(String str, long j, Map<String, String> map) {
        if (!a(str)) {
            if (ga.a) {
                ga.b(fx.event_name_invalid.a(), str);
            }
        } else if (map == null || map.size() <= 10) {
            this.d.a(de.a(str, j, map));
        } else if (ga.a) {
            ga.c(fx.event_extra_larger.a(), new Object[0]);
        }
    }

    public void b() {
        Thread thread = new Thread(new dl(this));
        thread.setName("el");
        thread.start();
    }

    public void c() {
        de a = de.a();
        a.a(true);
        this.d.a(a);
    }
}
