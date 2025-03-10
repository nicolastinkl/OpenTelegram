package io.openinstall.sdk;

import io.openinstall.sdk.ew;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public abstract class ff extends et implements Callable<ew> {
    public ff(az azVar, fb fbVar) {
        super(azVar, fbVar);
    }

    @Override // io.openinstall.sdk.et
    protected ew n() {
        Future submit = i().submit(this);
        try {
            return (ew) submit.get(r(), TimeUnit.SECONDS);
        } catch (TimeoutException unused) {
            submit.cancel(true);
            return ew.a.REQUEST_TIMEOUT.a();
        } catch (Exception e) {
            return ew.a.REQUEST_FAIL.a(e.getMessage());
        }
    }

    protected void o() {
        h().a(k());
    }

    @Override // java.util.concurrent.Callable
    /* renamed from: p, reason: merged with bridge method [inline-methods] */
    public ew call() {
        o();
        b().a(k(), r());
        if (!b().c()) {
            return ew.a.REQUEST_TIMEOUT.a();
        }
        if (b().b()) {
            return q();
        }
        return ew.a.INIT_ERROR.a(c().b());
    }

    protected abstract ew q();

    protected abstract int r();
}
