package io.openinstall.sdk;

import android.net.Uri;
import io.openinstall.sdk.ew;
import java.util.HashMap;

/* loaded from: classes.dex */
public class fl extends fj {
    private final Uri c;

    public fl(az azVar, Uri uri) {
        super(azVar, null);
        this.c = uri;
    }

    @Override // io.openinstall.sdk.et
    protected String k() {
        return "stat_wake";
    }

    @Override // io.openinstall.sdk.fj
    protected ew o() {
        if (!d().b()) {
            if (ga.a) {
                ga.a("wakeupStatsEnabled is disable", new Object[0]);
            }
            return ew.a.REQUEST_ERROR.a("wakeupStatsEnabled is disable");
        }
        HashMap hashMap = new HashMap();
        Uri uri = this.c;
        if (uri != null) {
            hashMap.put("qpxs", uri.toString());
        }
        return ew.a(e().c(hashMap));
    }
}
