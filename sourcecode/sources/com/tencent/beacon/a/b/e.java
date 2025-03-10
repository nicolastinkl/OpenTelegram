package com.tencent.beacon.a.b;

import android.os.Build;
import android.text.TextUtils;
import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.call.Callback;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: AbstractAttaReport.java */
/* loaded from: classes.dex */
public abstract class e {
    private static final Map<String, String> a = new LinkedHashMap();
    private boolean b = false;
    private boolean c = false;

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void b(String str, String str2, Throwable th) {
        a(str, str2, th, true, new d(this));
    }

    private synchronized void e() {
        if (this.b) {
            return;
        }
        Map<String, String> map = a;
        map.put("attaid", b());
        map.put("token", c());
        map.put("error_code", "");
        map.put("platform", "Android");
        map.put("uin", com.tencent.beacon.a.c.e.l().d());
        map.put("model", Build.BOARD + " " + com.tencent.beacon.a.c.f.e().h());
        map.put("os", com.tencent.beacon.a.c.e.l().s());
        map.put("error_msg", "");
        map.put("error_stack_full", "");
        map.put("app_version", com.tencent.beacon.a.c.b.a());
        map.put("sdk_version", com.tencent.beacon.a.c.c.d().j());
        map.put("product_id", com.tencent.beacon.a.c.c.d().f());
        map.put("_dc", "");
        this.b = true;
    }

    abstract String b();

    abstract String c();

    public boolean d() {
        if (this.c) {
            return true;
        }
        if (com.tencent.beacon.base.util.c.b()) {
            return false;
        }
        String d = com.tencent.beacon.a.c.e.l().d();
        return !TextUtils.isEmpty(d) && ((double) Math.abs(d.hashCode() % 10000)) < 100.0d;
    }

    public void a(boolean z) {
        this.c = z;
    }

    public synchronized void a(String str, String str2) {
        a(str, str2, null);
    }

    public synchronized void a(String str, String str2, Throwable th) {
        a(str, str2, th, false, new b(this, str, str2, th));
    }

    public synchronized void a(String str, String str2, Throwable th, boolean z, Callback<BResponse> callback) {
        if (d()) {
            if (!this.b) {
                e();
            }
            if (TextUtils.isEmpty(str)) {
                com.tencent.beacon.base.util.c.b("[atta] errorCode isn't valid value!", new Object[0]);
            } else {
                a.a().a(new c(this, str, str2, th, z, callback));
            }
        }
    }
}
