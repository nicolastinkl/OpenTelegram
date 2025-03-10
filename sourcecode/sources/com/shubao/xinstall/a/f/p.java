package com.shubao.xinstall.a.f;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

/* loaded from: classes.dex */
public final class p {
    private static Object a(Context context, String str) {
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle != null) {
                return bundle.get(str);
            }
            return null;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public static String a(Context context) {
        Object a = a(context, "com.xinstall.APP_KEY");
        if (a == null) {
            return "";
        }
        try {
            return String.valueOf(a);
        } catch (Exception unused) {
            return "";
        }
    }
}
