package com.tencent.qmsp.sdk.g.g;

import android.content.Context;

/* loaded from: classes.dex */
public class b {
    public static String a(Context context) {
        if (a.a) {
            return e.f.a(a.a(context), "AUID");
        }
        throw new RuntimeException("SDK Need Init First!");
    }

    public static boolean a() {
        if (a.a) {
            return a.b;
        }
        throw new RuntimeException("SDK Need Init First!");
    }

    public static String b(Context context) {
        if (a.a) {
            return e.f.a(a.a(context), "OUID");
        }
        throw new RuntimeException("SDK Need Init First!");
    }

    public static void c(Context context) {
        a.b = e.f.a(a.a(context));
        a.a = true;
    }
}
