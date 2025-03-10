package com.tencent.qmsp.sdk.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public class f {
    private static Context a;

    public static Context a() {
        Context context;
        synchronized (f.class) {
            if (a == null) {
                a = b();
            }
            context = a;
        }
        return context;
    }

    public static String a(Context context) {
        ApplicationInfo applicationInfo;
        String str = "";
        if (context == null) {
            return "";
        }
        try {
            applicationInfo = context.getApplicationInfo();
        } catch (Exception unused) {
        }
        if (applicationInfo != null && !TextUtils.isEmpty(applicationInfo.packageName)) {
            str = applicationInfo.packageName;
            return str;
        }
        str = a().getPackageName();
        return str;
    }

    public static Context b() {
        try {
            return (Context) Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication", new Class[0]).invoke(null, new Object[0]);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
