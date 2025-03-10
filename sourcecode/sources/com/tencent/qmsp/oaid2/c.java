package com.tencent.qmsp.oaid2;

import android.util.Log;

/* loaded from: classes.dex */
public class c {
    public static boolean a = false;
    public static String b = "2g.outt";

    public static void a(String str) {
        if (a) {
            Log.d(b, str);
        }
    }

    public static void b(String str) {
        if (a) {
            Log.e(b, str);
        }
    }

    public static void c(String str) {
        if (a) {
            Log.i(b, str);
        }
    }
}
