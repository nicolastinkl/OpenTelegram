package com.tencent.qmsp.oaid2;

import android.content.Context;
import android.util.Log;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class y {
    public static final boolean a() {
        Context context = null;
        try {
            Method method = Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]);
            method.setAccessible(true);
            context = (Context) method.invoke(null, new Object[0]);
        } catch (Exception e) {
            Log.e("OpenIdHelper", "ActivityThread:currentApplication --> " + e.toString());
        }
        if (context == null) {
            return false;
        }
        return x.a().a(context, false);
    }

    public static String b(Context context) {
        x a = x.a();
        return a.a(context.getApplicationContext(), a.b);
    }

    public static String a(Context context) {
        x a = x.a();
        return a.a(context.getApplicationContext(), a.c);
    }
}
