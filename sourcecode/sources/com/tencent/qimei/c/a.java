package com.tencent.qimei.c;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.List;

/* compiled from: AppInfo.java */
/* loaded from: classes.dex */
public class a {
    public static String a;
    public static String b;

    static {
        new HashMap();
    }

    public static String a() {
        if (a == null) {
            a = e();
        }
        return a;
    }

    public static String b() {
        String str = b;
        if (str != null) {
            return str;
        }
        try {
            String str2 = (String) Class.forName("android.app.ActivityThread").getDeclaredMethod(Build.VERSION.SDK_INT >= 18 ? "currentProcessName" : "currentPackageName", new Class[0]).invoke(null, new Object[0]);
            b = str2;
            return str2;
        } catch (Throwable th) {
            com.tencent.qimei.k.a.a(th);
            return "";
        }
    }

    public static long c() {
        Context J = com.tencent.qimei.u.d.b().J();
        if (J == null) {
            return 0L;
        }
        try {
            return J.getPackageManager().getPackageInfo(J.getPackageName(), 0).firstInstallTime;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static String d() {
        Context J = com.tencent.qimei.u.d.b().J();
        String packageName = J != null ? J.getPackageName() : null;
        return TextUtils.isEmpty(packageName) ? "" : packageName;
    }

    public static synchronized String e() {
        synchronized (a.class) {
            String d = d();
            if (TextUtils.isEmpty(d)) {
                return "";
            }
            try {
                PackageInfo packageInfo = com.tencent.qimei.u.d.b().J().getPackageManager().getPackageInfo(d, 0);
                String str = packageInfo.versionName;
                int i = packageInfo.versionCode;
                if (str != null && str.trim().length() > 0) {
                    String replace = str.trim().replace('\n', ' ').replace('\r', ' ').replace("|", "%7C");
                    int i2 = 0;
                    for (char c : replace.toCharArray()) {
                        if (c == '.') {
                            i2++;
                        }
                    }
                    if (i2 < 3) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(replace);
                        sb.append(".");
                        sb.append(i);
                        replace = sb.toString();
                    }
                    return replace;
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append("");
                sb2.append(i);
                return sb2.toString();
            } catch (Exception e) {
                com.tencent.qimei.k.a.a(e);
                e.toString();
                return "";
            }
        }
    }

    public static String f() {
        Context J = com.tencent.qimei.u.d.b().J();
        return J == null ? "" : (String) com.tencent.qimei.a.a.a(ApplicationInfo.class.getName(), J.getApplicationInfo(), "nativeLibraryDir");
    }

    public static String g() {
        Context J = com.tencent.qimei.u.d.b().J();
        return J == null ? "" : (String) com.tencent.qimei.a.a.a(ApplicationInfo.class.getName(), J.getApplicationInfo(), "primaryCpuAbi");
    }

    public static void h() {
        com.tencent.qimei.k.a.b("SDK_INIT ｜ AppInfo", " 初始化完成 ", new Object[0]);
    }

    public static boolean i() {
        Context J = com.tencent.qimei.u.d.b().J();
        if (J != null) {
            String b2 = b();
            if (TextUtils.isEmpty(b2) || b2.equals(J.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean j() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static boolean a(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        String packageName = context.getPackageName();
        if (packageName == null || packageName.trim().length() <= 0 || (runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) == null || runningAppProcesses.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.importance == 100) {
                for (String str : runningAppProcessInfo.pkgList) {
                    if (packageName.equals(str)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        a = str;
    }
}
