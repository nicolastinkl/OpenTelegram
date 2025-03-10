package com.tencent.beacon.c;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.beacon.a.b.g;
import com.tencent.beacon.a.c.b;
import com.tencent.beacon.base.util.c;
import com.tencent.qmsp.sdk.u.U;

/* compiled from: A.java */
/* loaded from: classes.dex */
public class a {
    private static boolean a = false;
    private static String b = "load_so";
    private static String c = "load_so_version";
    private static String d = "beacon_so_beacon";

    public static synchronized String a(Context context, int i, Activity activity, String str, int i2) {
        int a2;
        String str2;
        String str3;
        synchronized (a.class) {
            String a3 = b.a();
            if (c(context).equals(a3)) {
                a2 = a(context);
            } else {
                c.d("[audit] app update", new Object[0]);
                a(context, a3);
                a(context, 0);
                a2 = 0;
            }
            c.a("[audit] last load so occur fetal error cnt: %s", Integer.valueOf(a2));
            if (a2 >= i2) {
                g.e().a("502", "[audit] load so error count over max!");
                c.b("[audit] !!!!!!!!!!LOADERROR!!!!!!!!!! ", new Object[0]);
                str3 = "LOADERROR";
            } else {
                try {
                    try {
                        if (!a) {
                            a(context, a2 + 1);
                            c.d("[audit] load libBeacon.so success", new Object[0]);
                            a = true;
                        }
                        str2 = U.a(context, i, activity, str);
                    } catch (UnsatisfiedLinkError unused) {
                        a = true;
                        str2 = U.a(context, i, activity, str);
                    } catch (Throwable th) {
                        a(context, th);
                        str2 = "NOLIB";
                    }
                } catch (Throwable th2) {
                    a(context, th2);
                    str2 = "NOLIB";
                }
                a(context, 0);
                str3 = str2;
            }
            if (str3 == null || str3.isEmpty()) {
                g.e().a("501", "[audit] audit run fail! result is empty!");
            }
        }
        return str3;
    }

    private static SharedPreferences b(Context context) {
        return context.getSharedPreferences(d, 0);
    }

    private static String c(Context context) {
        return b(context).getString(c, "");
    }

    private static void a(Context context, Throwable th) {
        c.b("[audit] libBeacon.so load failed!", new Object[0]);
        c.a(th);
        g.e().a("501", "[audit] libBeacon.so load failed!", th);
    }

    private static void a(Context context, int i) {
        SharedPreferences.Editor edit = b(context).edit();
        if (com.tencent.beacon.base.util.b.a(edit)) {
            edit.putInt(b, i).apply();
        }
    }

    private static void a(Context context, String str) {
        SharedPreferences.Editor edit = b(context).edit();
        if (com.tencent.beacon.base.util.b.a(edit)) {
            edit.putString(c, str).apply();
        }
    }

    private static int a(Context context) {
        return b(context).getInt(b, 0);
    }
}
