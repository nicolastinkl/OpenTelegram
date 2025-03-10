package io.openinstall.sdk;

import java.util.HashMap;

/* loaded from: classes.dex */
public class fk extends fj {
    private final dh c;

    public fk(az azVar, dh dhVar, fb fbVar) {
        super(azVar, fbVar);
        this.c = dhVar;
    }

    @Override // io.openinstall.sdk.et
    protected String k() {
        return "stat_share";
    }

    @Override // io.openinstall.sdk.fj
    protected ew o() {
        HashMap hashMap = new HashMap();
        hashMap.put("iewb", this.c.a());
        hashMap.put("ncbd", this.c.b());
        es d = e().d(hashMap);
        if (!(d instanceof ep)) {
            d = e().d(hashMap);
        }
        return ew.a(d);
    }
}
