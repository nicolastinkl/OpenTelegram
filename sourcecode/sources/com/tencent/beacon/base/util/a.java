package com.tencent.beacon.base.util;

import android.os.Build;

/* compiled from: CloseUnitTestUtils.java */
/* loaded from: classes.dex */
public class a {
    private static String a;

    public static String a() {
        String str = a;
        if (str != null) {
            return str;
        }
        try {
            a = (String) Class.forName("android.app.ActivityThread").getDeclaredMethod(Build.VERSION.SDK_INT >= 18 ? "currentProcessName" : "currentPackageName", new Class[0]).invoke(null, new Object[0]);
            return "";
        } catch (Throwable th) {
            c.a(th);
            return "";
        }
    }
}
