package com.tencent.beacon.base.net.b;

import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: Constant.java */
/* loaded from: classes.dex */
public final class b {
    public static String a = "https://otheve.beacon.qq.com/analytics/v2_upload";
    public static String b = "https://othstr.beacon.qq.com/analytics/v2_upload";
    public static String c = "oth.eve.mdt.qq.com";
    public static String d = "oth.str.mdt.qq.com";
    private static boolean e = false;

    public static String a(String str) {
        if (str == null || "".equals(str)) {
            return "";
        }
        String str2 = str.contains(CosXmlServiceConfig.HTTPS_PROTOCOL) ? "https://" : "http://";
        int indexOf = str.indexOf(str2);
        if (indexOf == -1) {
            return str;
        }
        String substring = str.substring(indexOf + str2.length(), str.indexOf("/", str2.length()));
        int indexOf2 = substring.indexOf(":");
        return indexOf2 != -1 ? substring.substring(0, indexOf2) : substring;
    }

    public static void b(String str) {
        if (TextUtils.isEmpty(str) || e) {
            return;
        }
        c = str;
    }

    public static void c(String str) {
        if (TextUtils.isEmpty(str) || e) {
            return;
        }
        a = str;
    }

    public static void d(String str) {
        if (TextUtils.isEmpty(str) || e) {
            return;
        }
        d = str;
    }

    public static void e(String str) {
        if (TextUtils.isEmpty(str) || e) {
            return;
        }
        b = str;
    }

    public static String b(boolean z) {
        return z ? d : b;
    }

    public static void b(String str, String str2) {
        Pattern compile = Pattern.compile("((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}");
        Matcher matcher = compile.matcher(str);
        Matcher matcher2 = compile.matcher(str2);
        if (matcher.matches() && matcher2.matches()) {
            d = str;
            b = b.replace("othstr.beacon.qq.com", str);
            c = str2;
            String replace = a.replace("otheve.beacon.qq.com", str2);
            a = replace;
            com.tencent.beacon.base.util.c.a("[event url] ip modified by api, socketStrategyHost: %s, httpsStrategyUrl: %s, socketLogHost: %s ,httpsLogUrl: %s", d, b, c, replace);
            return;
        }
        com.tencent.beacon.base.util.e.a("[event url] set report ip is not valid IP address!");
    }

    public static String a(boolean z) {
        return z ? c : a;
    }

    public static void a(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            d = str;
            b = b.replace("othstr.beacon.qq.com", str);
            e = true;
        }
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        c = str2;
        a = a.replace("otheve.beacon.qq.com", str2);
        e = true;
    }
}
