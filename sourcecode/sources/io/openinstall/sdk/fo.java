package io.openinstall.sdk;

import android.util.Pair;
import java.net.UnknownHostException;
import java.util.List;

/* loaded from: classes.dex */
public class fo extends et {
    private static final String[] c = {"1.2.4.8", "223.5.5.5", "8.8.8.8", "180.76.76.76", "119.29.29.29", "208.67.222.222", "114.114.114.114"};
    private final fn d;

    public fo(az azVar, fn fnVar) {
        super(azVar, null);
        this.d = fnVar;
    }

    private Pair<String, String> a(String str) {
        if (str == null || !(str.length() == 0 || str.equals("\"\""))) {
            return fm.b(str);
        }
        c().a(false);
        return Pair.create(null, null);
    }

    private String o() {
        fn fnVar = this.d;
        if (fnVar == null) {
            return null;
        }
        String format = String.format(fnVar.b(), a());
        if (c().h()) {
            return this.a.c().a(format);
        }
        return null;
    }

    private String p() {
        fn fnVar = this.d;
        String str = null;
        if (fnVar == null) {
            return null;
        }
        String format = String.format(fnVar.a(), a());
        if (!c().h()) {
            return null;
        }
        int i = c().i();
        try {
            String[] strArr = c;
            bs bsVar = new bs(strArr[i % strArr.length]);
            bsVar.a(3);
            bl a = bsVar.a(bl.a(bq.a(bn.a(format), 16, 255)));
            if (a != null && a.b() == 0) {
                List<bq> a2 = a.a(1);
                if (a2.size() > 0) {
                    str = a2.get(0).c();
                }
            }
        } catch (ce | UnknownHostException unused) {
        }
        if (str == null) {
            c().a((i + 1) % c.length);
        }
        return str;
    }

    @Override // io.openinstall.sdk.et
    protected String k() {
        return "dynamic";
    }

    @Override // io.openinstall.sdk.et
    protected void m() {
        super.m();
        this.a.c().a();
    }

    @Override // io.openinstall.sdk.et
    protected ew n() {
        Pair<String, String> a = a(p());
        if (a.first == null || a.second == null) {
            a = a(o());
        }
        this.a.c().a((String) a.first, (String) a.second);
        return ew.a();
    }
}
