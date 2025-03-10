package io.openinstall.sdk;

import android.net.Uri;
import android.util.Pair;
import io.openinstall.sdk.ew;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class fh extends ff {
    private final Uri c;

    public fh(az azVar, Uri uri, fb fbVar) {
        super(azVar, fbVar);
        this.c = uri;
    }

    private ew s() {
        List<String> pathSegments = this.c.getPathSegments();
        if (pathSegments == null || pathSegments.size() <= 0) {
            return ew.a.INVALID_DATA.a();
        }
        int indexOf = pathSegments.indexOf("applinks");
        if (pathSegments.size() < indexOf + 3) {
            return ew.a.INVALID_DATA.a();
        }
        int i = indexOf + 1;
        if (pathSegments.get(i).equalsIgnoreCase(com.tencent.qimei.j.c.a)) {
            return ew.a(fv.b(pathSegments.get(indexOf + 2)));
        }
        if (!pathSegments.get(i).equalsIgnoreCase("h")) {
            return ew.a.INVALID_DATA.a();
        }
        HashMap hashMap = new HashMap();
        hashMap.put("wpxk", this.c.toString());
        es b = e().b(hashMap);
        if (!(b instanceof ep)) {
            b = e().b(hashMap);
        }
        return ew.a(b);
    }

    private ew t() {
        HashMap hashMap = new HashMap();
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(1);
        j().execute(new fi(this, linkedBlockingQueue));
        try {
            Pair pair = (Pair) linkedBlockingQueue.poll(3L, TimeUnit.SECONDS);
            hashMap.put(pair.first, pair.second);
            h().a(k());
        } catch (InterruptedException unused) {
        }
        es b = e().b(hashMap);
        if (!(b instanceof ep)) {
            b = e().b(hashMap);
        }
        return ew.a(b);
    }

    @Override // io.openinstall.sdk.et
    protected String k() {
        return "wakeup";
    }

    @Override // io.openinstall.sdk.ff
    protected ew q() {
        return this.c == null ? t() : s();
    }

    @Override // io.openinstall.sdk.ff
    protected int r() {
        return 6;
    }
}
