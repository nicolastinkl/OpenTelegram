package io.openinstall.sdk;

import android.util.Log;

/* loaded from: classes.dex */
public class ga {
    public static boolean a = true;

    public static void a(String str, Object... objArr) {
        Log.d("OpenInstall", String.format(str, objArr));
    }

    public static void b(String str, Object... objArr) {
        Log.w("OpenInstall", String.format(str, objArr));
    }

    public static void c(String str, Object... objArr) {
        Log.e("OpenInstall", String.format(str, objArr));
    }
}
