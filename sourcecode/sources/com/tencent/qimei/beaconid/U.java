package com.tencent.qimei.beaconid;

import android.content.Context;

/* loaded from: classes.dex */
public class U {
    public static boolean a = false;

    static {
        int i = 0;
        do {
            try {
                System.loadLibrary("beaconid");
                a = true;
            } catch (Throwable th) {
                th.printStackTrace();
            }
            i++;
            if (a) {
                return;
            }
        } while (i < 2);
    }

    public static String a(String str) {
        if (!a) {
            return "";
        }
        try {
            return x(str);
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            return "";
        }
    }

    public static native byte[] a(int i, byte[] bArr, byte[] bArr2, byte[] bArr3);

    public static native String[] c(int i);

    public static native byte[] d(long j);

    public static native byte e(String str, long j);

    public static native void n(Context context, String str);

    public static native String o();

    public static native String p();

    public static native String r();

    public static native String s();

    public static native String u();

    public static native String x(String str);

    public static native String z(Context context);
}
