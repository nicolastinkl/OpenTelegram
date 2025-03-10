package com.tencent.qmsp.sdk.f;

import java.io.File;

/* loaded from: classes.dex */
public class k {
    public static int a(String str, String str2) {
        try {
            return j.a(str, new File(str2));
        } catch (Throwable th) {
            th.printStackTrace();
            return -1;
        }
    }
}
