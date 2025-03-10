package com.shubao.xinstall.a.f;

import android.app.Application;
import android.util.Log;

/* loaded from: classes.dex */
public class d {
    public static final Application a;
    private static String b = "d";

    static {
        Application application;
        Throwable th;
        try {
            try {
                application = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication", new Class[0]).invoke(null, new Object[0]);
                if (application != null) {
                    a = application;
                    return;
                }
                try {
                    throw new IllegalStateException("Static initialization of Applications must be on main thread.");
                } catch (Exception e) {
                    e = e;
                    Log.e(b, "Failed to get current application from AppGlobals." + e.getMessage());
                    try {
                        application = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke(null, new Object[0]);
                    } catch (Exception unused) {
                        Log.e(b, "Failed to get current application from ActivityThread." + e.getMessage());
                    }
                    a = application;
                }
            } catch (Exception e2) {
                e = e2;
                application = null;
            } catch (Throwable th2) {
                application = null;
                th = th2;
                a = application;
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            a = application;
            throw th;
        }
    }
}
