package com.tencent.qimei.sdk;

import com.tencent.qimei.o.u;

/* loaded from: classes.dex */
public class QimeiSDK {
    public static final String TAG = "QmSDK";

    public static IQimeiSDK getInstance(String str) {
        return u.a(str);
    }
}
