package com.tencent.beacon.base.util;

import android.util.Log;
import java.util.Locale;

/* compiled from: ELog.java */
/* loaded from: classes.dex */
public class c {
    public static boolean a = true;
    private static BeaconLogger b = null;
    private static boolean c = false;

    private c() {
    }

    public static BeaconLogger a() {
        return b;
    }

    public static synchronized void b(boolean z) {
        synchronized (c.class) {
            a = z;
        }
    }

    public static synchronized boolean c() {
        boolean z;
        synchronized (c.class) {
            z = a;
        }
        return z;
    }

    private static boolean d() {
        return b();
    }

    public static void e(String str, Object... objArr) {
        if (d()) {
            BeaconLogger beaconLogger = b;
            if (beaconLogger == null) {
                Log.w("beacon", c(str, objArr));
            } else {
                beaconLogger.w("beacon", c(str, objArr));
            }
        }
    }

    private static String f() {
        StackTraceElement e;
        if (!c() || (e = e()) == null) {
            return "";
        }
        String fileName = e.getFileName();
        String str = fileName != null ? fileName : "";
        String methodName = e.getMethodName();
        if (methodName.contains("$")) {
            methodName = methodName.substring(methodName.indexOf("$") + 1, methodName.lastIndexOf("$") - 2);
        }
        return "(" + str + ":" + e.getLineNumber() + ")" + methodName + " ";
    }

    public static void a(BeaconLogger beaconLogger) {
        b = beaconLogger;
    }

    public static synchronized boolean b() {
        boolean z;
        synchronized (c.class) {
            z = c;
        }
        return z;
    }

    public static String c(String str, Object... objArr) {
        String f = f();
        if (str == null) {
            return f + "msg is null";
        }
        if (objArr == null || objArr.length == 0) {
            return f + str;
        }
        return f + String.format(Locale.US, str, objArr);
    }

    public static void d(String str, Object... objArr) {
        if (d()) {
            BeaconLogger beaconLogger = b;
            if (beaconLogger == null) {
                Log.i("beacon", c(str, objArr));
            } else {
                beaconLogger.i("beacon", c(str, objArr));
            }
        }
    }

    public static synchronized void a(boolean z) {
        synchronized (c.class) {
            Log.i("beacon", "beacon logAble: " + z);
            c = z;
        }
    }

    public static void b(String str, Object... objArr) {
        if (d()) {
            BeaconLogger beaconLogger = b;
            if (beaconLogger == null) {
                Log.e("beacon", c(str, objArr));
            } else {
                beaconLogger.e("beacon", c(str, objArr));
            }
        }
    }

    public static void a(String str, Object... objArr) {
        if (d()) {
            BeaconLogger beaconLogger = b;
            if (beaconLogger == null) {
                Log.d("beacon", c(str, objArr));
            } else {
                beaconLogger.d("beacon", c(str, objArr));
            }
        }
    }

    private static StackTraceElement e() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int a2 = a(stackTrace, c.class);
        if (a2 == -1 && (a2 = a(stackTrace, Log.class)) == -1) {
            return null;
        }
        return stackTrace[a2];
    }

    public static void a(String str, String str2, Object... objArr) {
        if (d()) {
            BeaconLogger beaconLogger = b;
            if (beaconLogger == null) {
                Log.d("beacon", c(str + " " + str2, objArr));
                return;
            }
            beaconLogger.d("beacon", c(str + " " + str2, objArr));
        }
    }

    public static void a(String str, int i, String str2, Object... objArr) {
        if (d()) {
            BeaconLogger beaconLogger = b;
            if (beaconLogger == null) {
                Log.d("beacon", c(str + " step: " + i + ". " + str2, objArr));
                return;
            }
            beaconLogger.d("beacon", c(str + " step: " + i + ". " + str2, objArr));
        }
    }

    public static void a(Throwable th) {
        if (th == null || !d()) {
            return;
        }
        BeaconLogger beaconLogger = b;
        if (beaconLogger == null) {
            th.printStackTrace();
        } else {
            beaconLogger.printStackTrace(th);
        }
    }

    private static int a(StackTraceElement[] stackTraceElementArr, Class cls) {
        for (int i = 5; i < stackTraceElementArr.length; i++) {
            String className = stackTraceElementArr[i].getClassName();
            if (!(cls.equals(Log.class) && i < stackTraceElementArr.length - 1 && stackTraceElementArr[i + 1].getClassName().equals(Log.class.getName())) && className.equals(cls.getName())) {
                return i + 1;
            }
        }
        return -1;
    }
}
