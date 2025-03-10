package io.openinstall.sdk;

import io.openinstall.sdk.ew;

/* loaded from: classes.dex */
public abstract class fp extends et {
    public fp(az azVar, fb fbVar) {
        super(azVar, fbVar);
    }

    @Override // io.openinstall.sdk.et
    protected ew n() {
        try {
            return o();
        } catch (Exception e) {
            return ew.a.REQUEST_FAIL.a(e.getMessage());
        }
    }

    protected abstract ew o();
}
