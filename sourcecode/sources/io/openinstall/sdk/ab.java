package io.openinstall.sdk;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

/* loaded from: classes.dex */
public class ab {
    public static aa a(Context context) {
        String lowerCase = Build.MANUFACTURER.toLowerCase();
        if (lowerCase.equals("huawei") || lowerCase.equals("tianyi")) {
            return new af();
        }
        if (lowerCase.equals("honor")) {
            return ae.b(context) ? new ae() : new af();
        }
        if (lowerCase.equals("xiaomi") || lowerCase.equals("redmi") || lowerCase.equals("blackshark") || lowerCase.equals("meitu")) {
            return new am();
        }
        if (lowerCase.equals("vivo")) {
            return new al();
        }
        if (lowerCase.equals("oppo") || lowerCase.equals("realme") || lowerCase.equals("oneplus")) {
            return new aj(context);
        }
        if (lowerCase.equals("lenovo") || lowerCase.equals("motorola") || lowerCase.equals("zuk") || lowerCase.equals("motolora")) {
            return new ag();
        }
        if (lowerCase.equals("samsung")) {
            return new ak();
        }
        if (lowerCase.equals("meizu") || lowerCase.equals("mblu")) {
            return new ah();
        }
        if (lowerCase.equals("nubia")) {
            return new ai();
        }
        if (lowerCase.equals("zte")) {
            return new an();
        }
        if (lowerCase.equals("asus")) {
            return new ac();
        }
        if (a(context, "com.coolpad.deviceidsupport")) {
            return new ad();
        }
        String a = a("ro.build.freeme.label", "");
        if (a != null && a.equalsIgnoreCase("freemeos")) {
            return new an();
        }
        String a2 = a("ro.ssui.product", "unknown");
        return (a2 == null || a2.equalsIgnoreCase("unknown")) ? (TextUtils.isEmpty(a("ro.build.version.emui", "")) && TextUtils.isEmpty(a("hw_sc.build.platform.version", ""))) ? !TextUtils.isEmpty(a("ro.build.version.magic", "")) ? ae.b(context) ? new ae() : new af() : !TextUtils.isEmpty(a("ro.miui.ui.version.name", "")) ? new am() : !TextUtils.isEmpty(a("ro.vivo.os.version", "")) ? new al() : !TextUtils.isEmpty(a("ro.build.version.opporom", "")) ? new aj(context) : new x() : new af() : new an();
    }

    public static String a(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean a(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
