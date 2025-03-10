package com.tencent.qmsp.sdk.f;

/* loaded from: classes.dex */
public class i {
    public static String a(String str) {
        return str.trim().replace(" ", "").replace("\t", "").replace("&", "").replace(":", "").replace("=", "").replace(";", "");
    }
}
