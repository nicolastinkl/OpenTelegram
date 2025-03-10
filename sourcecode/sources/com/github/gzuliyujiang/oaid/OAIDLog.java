package com.github.gzuliyujiang.oaid;

import android.util.Log;

/* loaded from: classes.dex */
public final class OAIDLog {
    private static boolean enable = false;

    public static void print(Object obj) {
        if (enable) {
            if (obj == null) {
                obj = "<null>";
            }
            Log.i("OAID", obj.toString());
        }
    }
}
