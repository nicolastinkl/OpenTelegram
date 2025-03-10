package com.tencent.qmsp.sdk.base;

import android.util.Log;

/* loaded from: classes.dex */
public class c {
    private static String a = "2g.outt";
    private static boolean b = false;

    public static void a(String str) {
        if (b) {
            Log.d(a, str);
        }
    }

    public static void a(boolean z) {
        b = z;
    }

    public static void b(String str) {
        if (b) {
            Log.e(a, str);
        }
    }

    public static void c(String str) {
        if (b) {
            Log.i(a, str);
        }
    }
}
