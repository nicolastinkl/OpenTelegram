package com.tencent.bugly.crashreport;

import android.util.Log;
import com.tencent.bugly.proguard.ao;
import com.tencent.bugly.proguard.p;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public class BuglyLog {
    public static void v(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (p.c) {
            Log.v(str, str2);
        }
        ao.a("V", str, str2);
    }

    public static void d(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (p.c) {
            Log.d(str, str2);
        }
        ao.a("D", str, str2);
    }

    public static void i(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (p.c) {
            Log.i(str, str2);
        }
        ao.a("I", str, str2);
    }

    public static void w(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (p.c) {
            Log.w(str, str2);
        }
        ao.a("W", str, str2);
    }

    public static void e(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (p.c) {
            Log.e(str, str2);
        }
        ao.a("E", str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (p.c) {
            Log.e(str, str2, th);
        }
        ao.a("E", str, th);
    }

    public static void setCache(int i) {
        ao.a(i);
    }
}
