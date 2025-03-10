package com.tencent.qmsp.sdk.a;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import java.io.File;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public class c {
    public static int a() {
        Context context;
        Context context2;
        Context context3;
        try {
            context = com.tencent.qmsp.sdk.app.a.getContext();
            PackageManager packageManager = context.getPackageManager();
            context2 = com.tencent.qmsp.sdk.app.a.getContext();
            String str = packageManager.getPackageInfo(context2.getPackageName(), 0).packageName;
            context3 = com.tencent.qmsp.sdk.app.a.getContext();
            ApplicationInfo applicationInfo = context3.getApplicationInfo();
            if (applicationInfo.packageName.equals(str)) {
                return Integer.valueOf((int) new File(applicationInfo.publicSourceDir).length()).intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String a(int i) {
        return String.format("%d.%d.%d", Integer.valueOf(i >> 24), Integer.valueOf((16711680 & i) >> 16), Integer.valueOf((i & 65280) >> 8));
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return com.tencent.qmsp.sdk.f.e.a(MessageDigest.getInstance("MD5").digest(str.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String b() {
        Context context;
        try {
            context = com.tencent.qmsp.sdk.app.a.getContext();
            String packageName = context.getPackageName();
            if (TextUtils.isEmpty(packageName)) {
                return null;
            }
            return packageName;
        } catch (Exception unused) {
            return null;
        }
    }

    public static String c() {
        Context context;
        Context context2;
        context = com.tencent.qmsp.sdk.app.a.getContext();
        PackageManager packageManager = context.getPackageManager();
        try {
            context2 = com.tencent.qmsp.sdk.app.a.getContext();
            PackageInfo packageInfo = packageManager.getPackageInfo(context2.getPackageName(), 0);
            if (TextUtils.isEmpty(packageInfo.versionName)) {
                return null;
            }
            return packageInfo.versionName.replaceAll("[^0-9.]", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String e() {
        try {
            return com.tencent.qmsp.sdk.c.b.c;
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String f() {
        Context context;
        PackageInfo packageInfo;
        Context context2;
        context = com.tencent.qmsp.sdk.app.a.getContext();
        PackageManager packageManager = context.getPackageManager();
        try {
            context2 = com.tencent.qmsp.sdk.app.a.getContext();
            packageInfo = packageManager.getPackageInfo(context2.getPackageName(), 64);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        Signature[] signatureArr = packageInfo.signatures;
        if (signatureArr.length <= 0) {
            return "";
        }
        try {
            return com.tencent.qmsp.sdk.f.e.a(MessageDigest.getInstance("MD5").digest(signatureArr[0].toByteArray()));
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static int g() {
        String[] split;
        int parseInt;
        String str;
        try {
            split = c().split("\\.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (split.length != 2) {
            if (split.length == 3) {
                parseInt = (Integer.parseInt(split[0]) << 24) | 0 | (Integer.parseInt(split[1]) << 16);
                str = split[2];
            }
            return 0;
        }
        parseInt = (Integer.parseInt(split[0]) << 16) | 0;
        str = split[1];
        return (Integer.parseInt(str) << 8) | parseInt;
    }

    public static boolean h() {
        String property = System.getProperty("os.arch");
        return property != null && property.contains("64");
    }
}
