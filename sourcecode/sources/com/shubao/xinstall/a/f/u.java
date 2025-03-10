package com.shubao.xinstall.a.f;

import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes.dex */
public final class u {
    public static String a(Date date, String str) {
        return new SimpleDateFormat(str).format(date);
    }

    public static boolean a(String str) {
        try {
            return Integer.parseInt(String.valueOf((System.currentTimeMillis() - new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str).getTime()) / 1000)) > 300;
        } catch (Exception unused) {
            return false;
        }
    }
}
