package com.tencent.beacon.a.c;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Process;
import android.text.TextUtils;
import com.tencent.beacon.a.d.a;
import java.util.Date;
import java.util.List;

/* compiled from: AppInfo.java */
/* loaded from: classes.dex */
public class b {
    public static String a = null;
    public static int b = 0;
    public static String c = "";
    public static boolean d = false;
    private static String e = null;
    private static boolean f = false;
    private static String g = "";
    private static boolean h = false;
    private static boolean i = false;
    private static int j = -2;
    private static boolean k = true;

    public static String a() {
        if (a == null) {
            a = e();
        }
        return a;
    }

    public static String b() {
        Context c2 = c.d().c();
        if (c2 == null) {
            return null;
        }
        String packageName = c2.getPackageName();
        return TextUtils.isEmpty(packageName) ? "" : packageName;
    }

    public static String c(Context context) {
        return com.tencent.beacon.base.util.a.a();
    }

    public static String d() {
        if (!"".equals(c)) {
            return c;
        }
        if (b == 0) {
            b = Process.myPid();
        }
        c += b + "_";
        String str = c + new Date().getTime();
        c = str;
        return str;
    }

    public static synchronized boolean e(Context context) {
        synchronized (b.class) {
            boolean z = false;
            if (context == null) {
                com.tencent.beacon.base.util.c.b("[appInfo] context is null", new Object[0]);
                return false;
            }
            com.tencent.beacon.a.d.a a2 = com.tencent.beacon.a.d.a.a();
            String string = a2.getString("APPVER_DENGTA", "");
            String a3 = a();
            if (string.isEmpty() || !string.equals(a3)) {
                z = true;
                a.SharedPreferencesEditorC0016a edit = a2.edit();
                if (com.tencent.beacon.base.util.b.a((SharedPreferences.Editor) edit)) {
                    edit.putString("APPVER_DENGTA", a3);
                }
            }
            return z;
        }
    }

    public static boolean f(Context context) {
        return a(context, context.getPackageName());
    }

    public static synchronized boolean g() {
        boolean z;
        synchronized (b.class) {
            z = false;
            com.tencent.beacon.a.d.a a2 = com.tencent.beacon.a.d.a.a();
            String string = a2.getString("APPKEY_DENGTA", "");
            String f2 = c.d().f();
            if (TextUtils.isEmpty(string) || !f2.equals(string)) {
                z = true;
                a.SharedPreferencesEditorC0016a edit = a2.edit();
                if (com.tencent.beacon.base.util.b.a((SharedPreferences.Editor) edit)) {
                    edit.putString("APPKEY_DENGTA", f2);
                }
            }
        }
        return z;
    }

    private static void h() {
        try {
            com.tencent.beacon.a.d.a a2 = com.tencent.beacon.a.d.a.a();
            String string = a2.getString("APPVER_DENGTA", "");
            String a3 = a();
            if (TextUtils.isEmpty(string) || !a3.equals(string)) {
                f = true;
                a.SharedPreferencesEditorC0016a edit = a2.edit();
                if (com.tencent.beacon.base.util.b.a((SharedPreferences.Editor) edit)) {
                    edit.putString("APPVER_DENGTA", a3);
                }
            } else {
                f = false;
            }
        } catch (Exception e2) {
            com.tencent.beacon.base.util.c.b("[core] app version check fail!", new Object[0]);
            com.tencent.beacon.base.util.c.a(e2);
        }
    }

    public static String c() {
        return g;
    }

    public static void f() {
        h();
    }

    public static boolean a(Context context, String str) {
        if (i) {
            return k;
        }
        if (context != null && str != null && str.trim().length() > 0) {
            if (!com.tencent.beacon.e.b.a().k()) {
                com.tencent.beacon.base.util.c.a("[DeviceInfo] current collect Process Info be refused! isCollect Process Info: %s", Boolean.FALSE);
                return true;
            }
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
            if (runningAppProcesses != null && runningAppProcesses.size() != 0) {
                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                    if (runningAppProcessInfo.importance == 100) {
                        for (String str2 : runningAppProcessInfo.pkgList) {
                            if (str.equals(str2)) {
                                k = true;
                                i = true;
                                return true;
                            }
                        }
                    }
                }
                k = false;
                i = true;
                return false;
            }
            com.tencent.beacon.base.util.c.e("[appInfo] no running proc", new Object[0]);
        }
        return false;
    }

    public static int b(Context context) {
        if (h) {
            return j;
        }
        if (b == 0) {
            b = Process.myPid();
        }
        if (!com.tencent.beacon.e.b.a().k()) {
            com.tencent.beacon.base.util.c.a("[DeviceInfo] current collect Process Info be refused! isCollect Process Info: %s", Boolean.FALSE);
            return -2;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (activityManager != null && activityManager.getRunningAppProcesses() != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == b) {
                    int i2 = runningAppProcessInfo.importance;
                    j = i2;
                    h = true;
                    return i2;
                }
            }
        }
        j = 0;
        h = true;
        return 0;
    }

    public static boolean d(Context context) {
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Exception e2) {
            com.tencent.beacon.base.util.c.a(e2);
            return false;
        }
    }

    public static boolean g(Context context) {
        if (context == null) {
            return true;
        }
        String c2 = c(context);
        return TextUtils.isEmpty(c2) || c2.equals(context.getPackageName());
    }

    public static synchronized String e() {
        synchronized (b.class) {
            String b2 = b();
            if (TextUtils.isEmpty(b2)) {
                return null;
            }
            try {
                PackageInfo packageInfo = c.d().c().getPackageManager().getPackageInfo(b2, 0);
                String str = packageInfo.versionName;
                int i2 = packageInfo.versionCode;
                if (str != null && str.trim().length() > 0) {
                    String replace = str.trim().replace('\n', ' ').replace('\r', ' ').replace("|", "%7C");
                    int i3 = 0;
                    for (char c2 : replace.toCharArray()) {
                        if (c2 == '.') {
                            i3++;
                        }
                    }
                    if (i3 < 3) {
                        com.tencent.beacon.base.util.c.a("[appInfo] add versionCode: %s", Integer.valueOf(i2));
                        StringBuilder sb = new StringBuilder();
                        sb.append(replace);
                        sb.append(".");
                        sb.append(i2);
                        replace = sb.toString();
                    }
                    com.tencent.beacon.base.util.c.a("[appInfo] final Version: %s", replace);
                    return replace;
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append("");
                sb2.append(i2);
                return sb2.toString();
            } catch (Throwable th) {
                com.tencent.beacon.base.util.c.a(th);
                com.tencent.beacon.base.util.c.b(th.toString(), new Object[0]);
                return "";
            }
        }
    }

    public static synchronized String a(Context context) {
        String str;
        synchronized (b.class) {
            if (TextUtils.isEmpty(e)) {
                String str2 = "on_app_first_install_time_" + c(context);
                com.tencent.beacon.a.d.a a2 = com.tencent.beacon.a.d.a.a();
                long j2 = a2.getLong(str2, 0L);
                if (j2 == 0) {
                    j2 = new Date().getTime();
                    com.tencent.beacon.a.b.a.a().a(new a(a2, str2, j2));
                }
                String valueOf = String.valueOf(j2);
                e = valueOf;
                com.tencent.beacon.base.util.c.a("[appInfo] process: %s, getAppFirstInstallTime: %s", str2, valueOf);
            }
            com.tencent.beacon.base.util.c.a("[appInfo] getAppFirstInstallTime: %s", e);
            str = e;
        }
        return str;
    }

    public static void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                if (Long.parseLong(str) > 10000) {
                    g = str;
                    return;
                }
                return;
            } catch (Exception unused) {
                com.tencent.beacon.base.util.c.e("[appInfo] set qq is not available !", new Object[0]);
                return;
            }
        }
        com.tencent.beacon.base.util.c.e("[appInfo] set qq is null !", new Object[0]);
    }
}
