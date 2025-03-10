package io.openinstall.sdk;

import java.util.concurrent.ThreadPoolExecutor;

/* loaded from: classes.dex */
public abstract class et {
    protected final az a;
    protected final fb b;

    public et(az azVar, fb fbVar) {
        this.a = azVar;
        this.b = fbVar;
    }

    protected String a() {
        return aw.a().d();
    }

    protected au b() {
        return this.a.e();
    }

    protected ax c() {
        return this.a.d();
    }

    protected ba d() {
        return this.a.f();
    }

    protected ej e() {
        return this.a.c();
    }

    protected bc f() {
        return this.a.g();
    }

    protected Cdo g() {
        return this.a.i();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public dt h() {
        return this.a.h();
    }

    protected ThreadPoolExecutor i() {
        return fc.a();
    }

    protected ThreadPoolExecutor j() {
        return fc.b();
    }

    protected abstract String k();

    public void l() {
        m();
        j().execute(new eu(this));
    }

    protected void m() {
    }

    protected abstract ew n();
}
