package com.tencent.qmsp.sdk.f;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* loaded from: classes.dex */
public class f {
    public static NetworkInfo a(Context context) {
        try {
            return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable th) {
            g.b("Qp.NU", 0, th.toString());
            return null;
        }
    }

    public static boolean b(Context context) {
        NetworkInfo a;
        return (context == null || (a = a(context)) == null || a.getType() != 1) ? false : true;
    }
}
