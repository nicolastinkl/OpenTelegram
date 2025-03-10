package com.tencent.qcloud.core.util;

import android.content.Context;

/* loaded from: classes.dex */
public class ContextHolder {
    private static Context appContext;

    public static void setContext(Context context) {
        appContext = context.getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }
}
