package com.tencent.qmsp.sdk.g.e;

import android.content.Context;
import android.util.Log;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class d {
    public static String a(Context context) {
        c a = c.a();
        return a.a(context.getApplicationContext(), a.c);
    }

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
        return c.a().a(context, false);
    }

    public static String b(Context context) {
        c a = c.a();
        return a.a(context.getApplicationContext(), a.b);
    }
}
