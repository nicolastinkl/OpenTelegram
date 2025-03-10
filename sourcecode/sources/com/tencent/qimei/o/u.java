package com.tencent.qimei.o;

import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.qimei.beaconid.U;
import com.tencent.qimei.log.IObservableLog;
import com.tencent.qimei.o.d;
import com.tencent.qimei.o.m;
import com.tencent.qimei.o.r;
import com.tencent.qimei.r.b;
import com.tencent.qimei.sdk.IAsyncQimeiListener;
import com.tencent.qimei.sdk.IQimeiSDK;
import com.tencent.qimei.sdk.Qimei;
import com.tencent.qimei.sdk.QimeiSDK;
import com.tencent.qimei.sdk.debug.IDebugger;
import com.tencent.qimei.strategy.terminal.ITerminalStrategy;
import com.tencent.qimei.upload.BuildConfig;
import com.xinstall.model.XAppError;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: QimeiSDKInfo.java */
/* loaded from: classes.dex */
public class u implements IQimeiSDK, com.tencent.qimei.u.b, com.tencent.qimei.u.c, com.tencent.qimei.g.c, d.a, r.b {
    public static final Map<String, u> a = new ConcurrentHashMap();
    public static final String b = QimeiSDK.class.getCanonicalName();
    public final IDebugger e;
    public final String g;
    public long l;
    public final List<IAsyncQimeiListener> c = Collections.synchronizedList(new ArrayList(8));
    public final ConcurrentHashMap<String, String> d = new ConcurrentHashMap<>();
    public final com.tencent.qimei.x.b f = new com.tencent.qimei.x.b();
    public Context h = null;
    public boolean i = false;
    public String j = "";
    public String k = "";

    public u(String str) {
        this.g = str;
        this.e = new com.tencent.qimei.p.a(str);
    }

    @Override // com.tencent.qimei.u.b
    public String H() {
        Qimei qimei = getQimei();
        return qimei == null ? "" : qimei.getQimei36();
    }

    @Override // com.tencent.qimei.u.b
    public String I() {
        return this.j;
    }

    @Override // com.tencent.qimei.u.c
    public Context J() {
        if (this.h == null) {
            com.tencent.qimei.k.a.b("SDK_INIT", "Context has been destroyed!!", new Object[0]);
        }
        return this.h;
    }

    @Override // com.tencent.qimei.u.b
    public String K() {
        return new JSONObject(this.d).toString();
    }

    @Override // com.tencent.qimei.u.b
    public String L() {
        return this.k;
    }

    @Override // com.tencent.qimei.u.b
    public void M() {
        synchronized (this.c) {
            Qimei qimei = getQimei();
            if (qimei != null && !qimei.isEmpty()) {
                Iterator<IAsyncQimeiListener> it = this.c.iterator();
                while (it.hasNext()) {
                    it.next().onQimeiDispatch(qimei);
                }
                this.c.clear();
            }
        }
    }

    @Override // com.tencent.qimei.u.b
    public String N() {
        Qimei qimei = getQimei();
        return qimei == null ? "" : qimei.getQimei16();
    }

    @Override // com.tencent.qimei.u.c
    public String O() {
        return com.tencent.qimei.m.b.a().b();
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK
    public IQimeiSDK addUserId(String str, String str2) {
        this.d.put(str, str2);
        return this;
    }

    @Override // com.tencent.qimei.g.c
    public void b() {
    }

    public final synchronized boolean c() {
        if (TextUtils.isEmpty(this.g)) {
            throw new AssertionError("Assertion failed: AppKey Forgot Set!");
        }
        return this.h != null;
    }

    public final synchronized boolean d() {
        boolean z;
        z = c() && this.i;
        if (!z) {
            com.tencent.qimei.k.a.a("SDK_INIT", "appkey:%s 未初始化", this.g);
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0137  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void e() {
        /*
            Method dump skipped, instructions count: 382
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.o.u.e():void");
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK
    public String getBeaconTicket() {
        if (!d()) {
            return "";
        }
        String str = this.g;
        if (com.tencent.qimei.a.a.g(str)) {
            return null;
        }
        l a2 = l.a(str);
        if (a2.e == 0) {
            a2.e = System.currentTimeMillis();
        }
        if (!TextUtils.isEmpty(a2.d)) {
            return a2.d + a2.e;
        }
        String c = com.tencent.qimei.i.f.a(a2.b).c("tt");
        a2.d = c;
        if (!TextUtils.isEmpty(c)) {
            return a2.d + a2.e;
        }
        if (com.tencent.qimei.c.a.j()) {
            return "";
        }
        a2.d = a2.b();
        return a2.d + a2.e;
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK
    public IDebugger getDebugger() {
        return this.e;
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK
    public Qimei getQimei() {
        if (d()) {
            return com.tencent.qimei.a.a.d(this.g);
        }
        return null;
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK, com.tencent.qimei.u.c
    public String getSdkVersion() {
        return BuildConfig.SDK_VERSION;
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK
    public ITerminalStrategy getStrategy() {
        return this.f.a;
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK
    public String getToken() {
        String str = "";
        if (!d()) {
            return "";
        }
        w a2 = w.a(this.g);
        String b2 = a2.b();
        if (b2.isEmpty()) {
            return a2.a();
        }
        long b3 = com.tencent.qimei.i.f.a(a2.b).b("t_s_t");
        if (!(0 != b3 && com.tencent.qimei.c.a.c() > b3)) {
            return com.tencent.qimei.a.a.a(com.tencent.qimei.i.f.a(a2.b).b("t_s_t")) ? a2.a() : b2;
        }
        try {
            JSONObject jSONObject = new JSONObject(b2);
            m.a aVar = m.a.KEY_ENCRYPT_KEY;
            String optString = jSONObject.optString(aVar.W);
            m.a aVar2 = m.a.KEY_PARAMS;
            String optString2 = jSONObject.optString(aVar2.W);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(m.a.KEY_PARAMS_APP_KEY.W, a2.b);
            jSONObject2.put(aVar.W, optString);
            jSONObject2.put(aVar2.W, optString2);
            str = jSONObject2.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String a3 = m.a.a(com.tencent.qimei.j.a.a(), a2.b, com.tencent.qimei.a.a.i(a2.b), str);
        a2.c(a3);
        return a3;
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK
    public synchronized boolean init(Context context) {
        boolean z;
        boolean z2;
        long uptimeMillis = SystemClock.uptimeMillis();
        if (this.i) {
            return true;
        }
        com.tencent.qimei.k.a.b("SDK_INIT", "\n\n\n\n_____________________________________\n< Welcome to QmSDK! Your AppKey is: %s SDK_VERSION is: %s>\n -------------------------------------\n        \\   ^__^\n         \\  (oo)\\_______\n            (__)\\       )\\/\\\n                ||----w |\n                ||     || \n\n\n\n", this.g, BuildConfig.SDK_VERSION);
        this.h = context;
        if (!c()) {
            com.tencent.qimei.k.a.a("SDK_INIT", "appkey:%s 参数异常", this.g);
            return false;
        }
        com.tencent.qimei.t.b.a().a("SdkInfo", this);
        String str = this.g;
        com.tencent.qimei.t.b.a().a("BizInfo" + str, this);
        com.tencent.qimei.n.i.a().a(this.h);
        com.tencent.qimei.r.b bVar = b.a.a;
        Context context2 = this.h;
        String str2 = b;
        if (bVar.a) {
            z2 = true;
        } else if (context2 == null) {
            z2 = false;
        } else {
            File filesDir = context2.getFilesDir();
            if (!filesDir.exists()) {
                filesDir.mkdir();
            }
            String absolutePath = new File(filesDir, str2).getAbsolutePath();
            if (U.a) {
                try {
                    U.n(context2, absolutePath);
                    z = true;
                } catch (UnsatisfiedLinkError e) {
                    e.printStackTrace();
                }
                bVar.a = z;
                com.tencent.qimei.k.a.b("SDK_INIT ｜ 本地加密", " 初始化完成（%b）,文件名:%s ", Boolean.valueOf(z), str2);
                z2 = bVar.a;
            }
            z = false;
            bVar.a = z;
            com.tencent.qimei.k.a.b("SDK_INIT ｜ 本地加密", " 初始化完成（%b）,文件名:%s ", Boolean.valueOf(z), str2);
            z2 = bVar.a;
        }
        if (!z2) {
            String str3 = this.g;
            com.tencent.qimei.n.c a2 = com.tencent.qimei.n.i.a().a(com.tencent.qimei.n.e.REPORT_QM_ERROR_CODE.K, XAppError.JSON_EXCEPTION);
            String str4 = com.tencent.qimei.n.e.REPORT_QM_ERROR_DESC.K;
            com.tencent.qimei.l.d a3 = com.tencent.qimei.l.d.a(str3);
            HashMap hashMap = new HashMap();
            hashMap.put("a1", com.tencent.qimei.c.a.g());
            hashMap.put("a2", com.tencent.qimei.c.a.f());
            hashMap.put("a3", Build.CPU_ABI);
            hashMap.put("a4", Build.CPU_ABI2);
            hashMap.put("a5", a3.e());
            hashMap.put("a6", Build.BRAND);
            hashMap.put("a7", Build.VERSION.SDK);
            com.tencent.qimei.n.c a4 = a2.a(str4, new JSONObject(hashMap).toString());
            a4.a = str3;
            a4.c = "/report";
            a4.a("v2");
        }
        com.tencent.qimei.i.f a5 = com.tencent.qimei.i.f.a(this.g);
        Context context3 = this.h;
        String str5 = b;
        a5.d = context3;
        if (context3 != null) {
            if (TextUtils.isEmpty(str5)) {
                str5 = "";
            }
            a5.b = a5.d.getSharedPreferences("QV1" + str5 + com.tencent.qimei.c.a.b().replace(context3.getPackageName(), "") + com.tencent.qimei.j.a.a(a5.c), 0);
        }
        d dVar = new d(this.g, this.h, this, this.f, this);
        com.tencent.qimei.b.a.a().a(dVar.k);
        com.tencent.qimei.b.a.a().a(dVar.l);
        com.tencent.qimei.k.a.b("SDK_INIT", "\n\n\n\n\t\t\t\t ----- 初始化结束! From appkey:%s ----- \n\n\n\n\t\t\t\t", this.g);
        this.i = true;
        this.l = SystemClock.uptimeMillis() - uptimeMillis;
        return true;
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK
    public boolean isQimeiValid(String str, String str2) {
        return true;
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK
    public IQimeiSDK setAppVersion(String str) {
        com.tencent.qimei.c.a.a(str);
        return this;
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK
    public IQimeiSDK setChannelID(String str) {
        this.j = str;
        return this;
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK
    public synchronized IQimeiSDK setLogAble(boolean z) {
        com.tencent.qimei.k.a.a(z);
        com.tencent.qimei.k.a.b(z);
        return this;
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK
    public synchronized IQimeiSDK setLogObserver(IObservableLog iObservableLog) {
        com.tencent.qimei.k.a.c = iObservableLog;
        return this;
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK
    public IQimeiSDK setSdkName(String str) {
        if (!this.i) {
            this.k = str;
        }
        return this;
    }

    public static synchronized IQimeiSDK a(String str) {
        u uVar;
        synchronized (u.class) {
            Map<String, u> map = a;
            uVar = map.get(str);
            if (uVar == null) {
                uVar = new u(str);
                map.put(str, uVar);
            }
        }
        return uVar;
    }

    @Override // com.tencent.qimei.sdk.IQimeiSDK
    public synchronized void getQimei(IAsyncQimeiListener iAsyncQimeiListener) {
        if (!d()) {
            a(iAsyncQimeiListener);
        } else {
            com.tencent.qimei.b.a.a().a(new s(this, iAsyncQimeiListener));
        }
    }

    public final void a(IAsyncQimeiListener iAsyncQimeiListener) {
        synchronized (this.c) {
            if (!this.c.contains(iAsyncQimeiListener)) {
                this.c.add(iAsyncQimeiListener);
            }
        }
    }

    @Override // com.tencent.qimei.g.c
    public void a() {
        e();
    }
}
