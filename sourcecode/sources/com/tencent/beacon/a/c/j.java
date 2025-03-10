package com.tencent.beacon.a.c;

import android.content.Context;
import com.tencent.beacon.base.util.BeaconLogger;
import com.tencent.qimei.sdk.IAsyncQimeiListener;
import com.tencent.qimei.sdk.Qimei;
import com.tencent.qimei.sdk.QimeiSDK;
import java.util.HashMap;
import java.util.Map;

/* compiled from: QimeiWrapper.java */
/* loaded from: classes.dex */
public class j {
    private static boolean a = true;

    @Deprecated
    public static Qimei b() {
        Context c = c.d().c();
        if (c == null) {
            return null;
        }
        String f = c.d().f();
        c(c, f);
        com.tencent.beacon.base.util.c.a("QimeiWrapper", "getQimei  appkey is %s", f);
        return QimeiSDK.getInstance(f).getQimei();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean c(Context context, String str) {
        if (!a) {
            return false;
        }
        QimeiSDK.getInstance(str).setSdkName("beacon");
        return QimeiSDK.getInstance(str).init(context);
    }

    private static void d(String str) {
        QimeiSDK.getInstance(str).setAppVersion(b.a()).setChannelID(c.d().a()).setLogAble(com.tencent.beacon.base.util.c.b()).addUserId("QQ", b.c()).addUserId("OMGID", c.d().g());
    }

    private static void e(String str) {
        BeaconLogger a2 = com.tencent.beacon.base.util.c.a();
        if (a2 != null) {
            QimeiSDK.getInstance(str).setLogObserver(new h(a2));
        }
    }

    public static String f() {
        return QimeiSDK.getInstance(c.d().f()).getSdkVersion();
    }

    public static String g() {
        String beaconTicket;
        return (com.tencent.beacon.e.b.a().g() && (beaconTicket = QimeiSDK.getInstance(c.d().f()).getBeaconTicket()) != null) ? beaconTicket : "";
    }

    public static void h() {
        String f = c.d().f();
        com.tencent.beacon.base.util.c.a("QimeiWrapper", "initQimei: appkey is %s , qimei init %s", f, Boolean.valueOf(c(c.d().c(), f)));
        QimeiSDK.getInstance(f).getQimei(new g());
    }

    public static void a(boolean z) {
        a = z;
    }

    public static void a() {
        if (a) {
            try {
                String f = c.d().f();
                d(f);
                a(f);
                e(f);
            } catch (Throwable th) {
                com.tencent.beacon.a.b.g.e().a("203", "sdk init error! package name: " + b.b() + " , msg:" + th.getMessage(), th);
                com.tencent.beacon.base.util.c.a(th);
            }
        }
    }

    public static Map<String, String> e() {
        HashMap hashMap = new HashMap(2);
        hashMap.put("A3", c());
        hashMap.put("A153", d());
        return hashMap;
    }

    public static String c() {
        Qimei qimei = QimeiSDK.getInstance(c.d().f()).getQimei();
        return qimei == null ? "" : qimei.getQimei16();
    }

    public static Qimei b(String str) {
        Context c = c.d().c();
        if (c == null) {
            return null;
        }
        com.tencent.beacon.base.util.c.a("QimeiWrapper", "getQimeiWithAppkey  appkey is %s", str);
        c(c, str);
        return QimeiSDK.getInstance(str).getQimei();
    }

    public static void c(String str) {
        com.tencent.beacon.base.util.c.b("内部版该接口无效", new Object[0]);
    }

    public static String d() {
        Qimei qimei = QimeiSDK.getInstance(c.d().f()).getQimei();
        return qimei == null ? "" : qimei.getQimei36();
    }

    public static Qimei b(Context context, String str) {
        if (context == null || context.getApplicationContext() == null) {
            return null;
        }
        c(context.getApplicationContext(), str);
        return QimeiSDK.getInstance(str).getQimei();
    }

    public static void a(String str) {
        QimeiSDK.getInstance(str).getStrategy().enableProcessInfo(com.tencent.beacon.e.b.a().k());
    }

    @Deprecated
    public static void a(IAsyncQimeiListener iAsyncQimeiListener) throws NullPointerException {
        Context c = c.d().c();
        com.tencent.beacon.base.util.e.a("should call start() first to init beaconsdk! old async getQimei context", c);
        String f = c.d().f();
        com.tencent.beacon.base.util.c.a("QimeiWrapper", "async getQimei  appkey is %s", f);
        c(c, f);
        QimeiSDK.getInstance(c.d().f()).getQimei(iAsyncQimeiListener);
    }

    public static void a(String str, Context context, IAsyncQimeiListener iAsyncQimeiListener) {
        com.tencent.beacon.base.util.e.a("context", context);
        com.tencent.beacon.base.util.e.a("ApplicationContext", context.getApplicationContext());
        com.tencent.beacon.a.b.a.a().a(new i(str, context, iAsyncQimeiListener));
    }

    @Deprecated
    public static Qimei a(Context context) {
        if (context == null || context.getApplicationContext() == null) {
            return null;
        }
        String f = c.d().f();
        com.tencent.beacon.base.util.c.a("QimeiWrapper", "getRtQimei  appkey is %s", f);
        c(context, f);
        return QimeiSDK.getInstance(f).getQimei();
    }

    public static synchronized void a(String str, String str2) {
        synchronized (j.class) {
            com.tencent.beacon.base.util.c.b("内部版该接口无效", new Object[0]);
        }
    }
}
