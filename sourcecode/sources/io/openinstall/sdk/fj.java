package io.openinstall.sdk;

import io.openinstall.sdk.ew;

/* loaded from: classes.dex */
public abstract class fj extends et {
    public fj(az azVar, fb fbVar) {
        super(azVar, fbVar);
    }

    @Override // io.openinstall.sdk.et
    protected ew n() {
        ew o;
        try {
            b().a();
            if (!b().c()) {
                o = ew.a.REQUEST_TIMEOUT.a();
            } else if (b().b()) {
                o = o();
            } else {
                o = ew.a.INIT_ERROR.a(c().b());
            }
            return o;
        } catch (Exception e) {
            return ew.a.REQUEST_FAIL.a(e.getMessage());
        }
    }

    protected abstract ew o();
}
