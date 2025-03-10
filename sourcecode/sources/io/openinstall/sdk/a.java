package io.openinstall.sdk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.fm.openinstall.Configuration;
import com.fm.openinstall.listener.AppInstallListener;
import com.fm.openinstall.listener.AppWakeUpListener;
import com.fm.openinstall.listener.ResultCallback;
import com.fm.openinstall.model.Error;
import io.openinstall.sdk.ew;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Map;

/* loaded from: classes.dex */
public final class a {
    private final az a;
    private dj b;

    /* renamed from: io.openinstall.sdk.a$a, reason: collision with other inner class name */
    private static class C0049a {
        public static a a = new a(null);
    }

    private a() {
        this.a = new i();
    }

    /* synthetic */ a(b bVar) {
        this();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0040, code lost:
    
        if (r1.has(com.tencent.qimei.o.d.a) != false) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0027, code lost:
    
        if (r1.has(com.tencent.qimei.j.c.a) != false) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.fm.openinstall.model.AppData a(java.lang.String r4) throws org.json.JSONException {
        /*
            r3 = this;
            com.fm.openinstall.model.AppData r0 = new com.fm.openinstall.model.AppData
            r0.<init>()
            boolean r1 = android.text.TextUtils.isEmpty(r4)
            if (r1 == 0) goto Lc
            return r0
        Lc:
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>(r4)
            java.lang.String r4 = "channelCode"
            boolean r2 = r1.has(r4)
            if (r2 == 0) goto L21
        L19:
            java.lang.String r4 = r1.optString(r4)
            r0.setChannel(r4)
            goto L2a
        L21:
            java.lang.String r4 = "c"
            boolean r2 = r1.has(r4)
            if (r2 == 0) goto L2a
            goto L19
        L2a:
            java.lang.String r4 = "bind"
            boolean r2 = r1.has(r4)
            if (r2 == 0) goto L3a
        L32:
            java.lang.String r4 = r1.optString(r4)
            r0.setData(r4)
            goto L43
        L3a:
            java.lang.String r4 = "d"
            boolean r2 = r1.has(r4)
            if (r2 == 0) goto L43
            goto L32
        L43:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.openinstall.sdk.a.a(java.lang.String):com.fm.openinstall.model.AppData");
    }

    public static a a() {
        return C0049a.a;
    }

    private void a(Uri uri, AppWakeUpListener appWakeUpListener) {
        if (ga.a) {
            ga.a("decodeWakeUp", new Object[0]);
        }
        System.currentTimeMillis();
        new fh(this.a, uri, new b(this, appWakeUpListener, uri)).l();
        System.currentTimeMillis();
    }

    public void a(Intent intent, AppWakeUpListener appWakeUpListener) {
        a(intent.getData(), appWakeUpListener);
    }

    public void a(Uri uri) {
        new fl(this.a, uri).l();
    }

    public void a(Configuration configuration, WeakReference<Activity> weakReference, long j) {
        if (configuration == null) {
            configuration = Configuration.getDefault();
        }
        aw.a().c(Boolean.valueOf(configuration.isStorageDisabled()));
        this.a.c().a(new h(this.a.g(), configuration));
        this.a.c().a(new f());
        fr frVar = new fr(this.a, weakReference);
        frVar.a(new g(aw.a().c(), configuration));
        frVar.l();
        this.b.b();
        System.currentTimeMillis();
    }

    public void a(AppWakeUpListener appWakeUpListener) {
        a((Uri) null, appWakeUpListener);
    }

    public void a(ResultCallback<File> resultCallback) {
        if (ga.a) {
            ga.a("getOriginalApk", new Object[0]);
        }
        System.currentTimeMillis();
        new fq(this.a, new e(this, resultCallback)).l();
        System.currentTimeMillis();
    }

    public void a(String str, long j) {
        a(str, j, (Map<String, String>) null);
    }

    public void a(String str, long j, Map<String, String> map) {
        if (ga.a) {
            ga.a("reportEffectPoint", new Object[0]);
        }
        this.b.a(str, j, map);
    }

    public void a(String str, String str2, ResultCallback<Void> resultCallback) {
        if (ga.a) {
            ga.a("reportShare", new Object[0]);
        }
        if (TextUtils.isEmpty(str)) {
            if (ga.a) {
                ga.c("shareCode 为空", new Object[0]);
            }
            resultCallback.onResult(null, Error.fromInner(ew.a.REQUEST_ERROR.a("shareCode 不能为空").c()));
            return;
        }
        if (str.length() > 128 && ga.a) {
            ga.b("shareCode 长度超过128位", new Object[0]);
        }
        System.currentTimeMillis();
        dh dhVar = new dh(str);
        dhVar.a(str2);
        new fk(this.a, dhVar, new d(this, resultCallback)).l();
        System.currentTimeMillis();
    }

    public void a(boolean z, int i, AppInstallListener appInstallListener) {
        if (ga.a) {
            ga.a("getInstallData", new Object[0]);
        }
        System.currentTimeMillis();
        fg fgVar = new fg(this.a, z, new c(this, appInstallListener));
        fgVar.a(i);
        fgVar.l();
        System.currentTimeMillis();
    }

    public String b() {
        return this.a.f().h();
    }

    public void c() {
        dj djVar = new dj(this.a);
        this.b = djVar;
        djVar.a();
        new fo(this.a, new j()).l();
    }

    public void d() {
        if (ga.a) {
            ga.a("reportRegister", new Object[0]);
        }
        this.b.c();
    }
}
