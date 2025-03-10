package com.tencent.qmsp.sdk.g.g;

import android.content.Context;

/* loaded from: classes.dex */
public class a {
    public static boolean a;
    public static boolean b;

    public static Context a(Context context) {
        return (context == null || context.getApplicationContext() == null) ? context : context.getApplicationContext();
    }
}
