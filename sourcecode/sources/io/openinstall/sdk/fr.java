package io.openinstall.sdk;

import android.app.Activity;
import android.text.TextUtils;
import j$.util.DesugarCollections;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class fr extends fp {
    private final WeakReference<Activity> c;
    private dw d;
    private fa e;

    public fr(az azVar, WeakReference<Activity> weakReference) {
        super(azVar, null);
        this.c = weakReference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public dw a(dw dwVar) {
        if (dwVar == null || dwVar.c() == 0) {
            dw e = c().e();
            if (e != null) {
                return e;
            }
        } else {
            c().a(dwVar);
        }
        return dwVar;
    }

    private Map<String, Object> p() {
        int i;
        ey eyVar;
        Map<String, Object> synchronizedMap = DesugarCollections.synchronizedMap(new HashMap());
        synchronizedMap.put("ntrh", f().h());
        synchronizedMap.put("regh", f().i());
        synchronizedMap.put("mrth", f().j());
        synchronizedMap.put("krtn", f().k());
        synchronizedMap.put("fuqd", f().m());
        String e = aw.a().e();
        if (!TextUtils.isEmpty(e)) {
            synchronizedMap.put("gpde", e);
        }
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        if (this.d == null) {
            j().execute(new fs(this, linkedBlockingQueue));
            i = 1;
        } else {
            h().a(false);
            dw a = a(this.d);
            if (a.c(2)) {
                synchronizedMap.put("pwcf", a.b());
            } else if (a.c(1)) {
                synchronizedMap.put("aviw", a.a());
            } else {
                synchronizedMap.put("aviw", null);
            }
            i = 0;
        }
        int i2 = i + 1;
        j().execute(new ft(this, linkedBlockingQueue));
        for (ez ezVar : this.e.a()) {
            if (ezVar.a()) {
                i2++;
                j().execute(new fu(this, linkedBlockingQueue, ezVar));
            } else {
                ey a_ = ezVar.a_();
                if (!TextUtils.isEmpty(a_.b()) && !TextUtils.isEmpty(a_.c())) {
                    synchronizedMap.put(a_.b(), a_.c());
                }
            }
        }
        while (i2 > 0) {
            try {
                eyVar = (ey) linkedBlockingQueue.poll(1L, TimeUnit.SECONDS);
            } catch (InterruptedException unused) {
                eyVar = null;
            }
            if (eyVar != null) {
                i2--;
                if (!TextUtils.isEmpty(eyVar.b()) && !TextUtils.isEmpty(eyVar.c())) {
                    synchronizedMap.put(eyVar.b(), eyVar.c());
                    if (ga.a) {
                        ga.a(fx.getting_data.a(), eyVar.a());
                    }
                }
            }
        }
        return synchronizedMap;
    }

    private void q() {
        String a = TextUtils.isEmpty(d().h()) ? g().a(a()) : d().h();
        if (ga.a) {
            ga.a("opid = %s", a);
        }
    }

    private void r() {
    }

    public void a(fa faVar) {
        this.e = faVar;
    }

    @Override // io.openinstall.sdk.et
    protected String k() {
        return "init";
    }

    @Override // io.openinstall.sdk.et
    protected void m() {
        super.m();
        boolean booleanValue = aw.a().f().booleanValue();
        dw a = dw.a(aw.a().g());
        boolean z = a == null || a.c() == 0;
        if (booleanValue && z) {
            System.currentTimeMillis();
            av a2 = c().a(aw.a().d());
            if (a2 == av.a || a2 == av.c || a2 == av.e) {
                h().a(this.c);
                a = h().b();
                System.currentTimeMillis();
            }
        }
        this.d = a;
    }

    @Override // io.openinstall.sdk.fp
    protected ew o() {
        au b;
        av avVar;
        System.currentTimeMillis();
        av d = b().d();
        if (d == null) {
            d = c().a(a());
        }
        av avVar2 = av.a;
        if (d == avVar2) {
            c().k();
        }
        if (d == avVar2 || d == av.c || d == av.e) {
            b().a(av.b);
            Map<String, Object> p = p();
            es a = e().a((Map<String, ?>) p);
            int i = 1;
            while (!(a instanceof ep)) {
                try {
                    b().a(i);
                } catch (InterruptedException unused) {
                }
                a = e().a((Map<String, ?>) p);
                i = Math.min(i * 2, 60);
            }
            ep epVar = (ep) a;
            if (epVar.a() == 0) {
                c().b(epVar.c());
                c().c(epVar.b());
                b = b();
                avVar = av.d;
            } else {
                c().c(epVar.b());
                if (epVar.a() == 1 || epVar.a() == 15) {
                    b = b();
                    avVar = av.f;
                } else {
                    b = b();
                    avVar = av.e;
                }
            }
            b.a(avVar);
            c().a((dw) null);
            b().e();
            c().a(a(), b().d());
            q();
        } else if (d == av.d || d == av.f) {
            d().a(c().c());
            b().a(d);
            b().e();
            h().a(false);
            q();
            r();
        }
        System.currentTimeMillis();
        return ew.a();
    }
}
