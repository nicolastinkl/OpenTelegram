package io.openinstall.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* loaded from: classes.dex */
public class dm extends Handler {
    protected au a;
    protected ba b;
    protected ej c;
    protected ax d;
    private final di e;
    private long f;
    private int g;

    public dm(Looper looper, az azVar) {
        super(looper);
        this.g = 0;
        this.a = azVar.e();
        this.b = azVar.f();
        this.c = azVar.c();
        this.d = azVar.d();
        this.e = new di(b(), c());
        this.f = this.d.d();
    }

    private void a(boolean z) {
        if (z || b(false)) {
            f();
        }
    }

    private Context b() {
        return aw.a().c();
    }

    private boolean b(de deVar) {
        if (deVar.b() == 2 && !this.b.f()) {
            if (ga.a) {
                ga.b("eventStatsEnabled is false", new Object[0]);
            }
            return false;
        }
        if (deVar.b() == 1 && !this.b.f()) {
            if (ga.a) {
                ga.b("eventStatsEnabled is false", new Object[0]);
            }
            return false;
        }
        if (deVar.b() != 0 || this.b.d()) {
            return true;
        }
        if (ga.a) {
            ga.b("registerStatsEnabled is false", new Object[0]);
        }
        return false;
    }

    private boolean b(boolean z) {
        if (!this.a.c()) {
            if (!z) {
                this.a.a();
            }
            return false;
        }
        if (z) {
            if (!this.b.f() && !this.b.d()) {
                this.e.d();
                return false;
            }
            if (this.e.a()) {
                return false;
            }
        }
        if (this.e.b()) {
            return true;
        }
        return this.b.g() * 1000 < System.currentTimeMillis() - this.f;
    }

    private String c() {
        return aw.a().d();
    }

    private void c(de deVar) {
        boolean c;
        if (b(deVar)) {
            this.e.a(deVar);
            c = deVar.c();
        } else {
            c = false;
        }
        a(c);
    }

    private void d() {
        this.g = 0;
    }

    private void e() {
        int i = this.g;
        if (i < 10) {
            this.g = i + 1;
        }
    }

    private void f() {
        if (!this.a.b()) {
            this.a.a();
            return;
        }
        es b = this.c.b(this.e.e());
        this.f = System.currentTimeMillis();
        if (!(b instanceof ep)) {
            if (ga.a) {
                ga.c("statEvents fail : %s", b.f());
            }
            e();
        } else {
            if (((ep) b).a() == 0) {
                if (ga.a) {
                    ga.a("statEvents success", new Object[0]);
                }
                d();
                this.e.c();
            }
            this.d.a(this.f);
        }
    }

    public void a() {
        Message obtain = Message.obtain();
        obtain.what = 23;
        obtain.obj = null;
        sendMessage(obtain);
    }

    public void a(de deVar) {
        Message obtain = Message.obtain();
        obtain.what = 21;
        obtain.obj = deVar;
        sendMessage(obtain);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i = message.what;
        if (i == 21) {
            c((de) message.obj);
        } else if (i == 23 && this.g < 10 && b(true)) {
            f();
        }
    }
}
