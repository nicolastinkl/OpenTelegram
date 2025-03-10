package com.tencent.qimei.a;

import android.util.Base64;
import j$.util.concurrent.ConcurrentHashMap;

/* compiled from: Compliant.java */
/* loaded from: classes.dex */
public class b {
    public static final ConcurrentHashMap<Integer, String> a;

    static {
        ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
        a = concurrentHashMap;
        concurrentHashMap.put(0, a("aWVtaXE="));
        concurrentHashMap.put(1, a("NjNpZW1pcQ=="));
        concurrentHashMap.put(2, a("QVRHTkVEX0lFTUlR"));
        concurrentHashMap.put(3, a("MV9JRU1JUV9OT0NBRUI="));
        concurrentHashMap.put(4, a("MnZfaWVtaXE="));
        concurrentHashMap.put(5, a("ZGlhbw=="));
        concurrentHashMap.put(6, a("aWVtaQ=="));
        concurrentHashMap.put(7, a("aXNtaQ=="));
        concurrentHashMap.put(8, a("Y2Ft"));
        concurrentHashMap.put(9, a("ZGlj"));
        concurrentHashMap.put(10, a("aWVtaVFldGFkcHU="));
        concurrentHashMap.put(11, a("ZElkaW9yZG5h"));
        concurrentHashMap.put(12, a("ZHJhb2IudGN1ZG9ycC5vcg=="));
        concurrentHashMap.put(13, a("ZG5hcmIudGN1ZG9ycC5vcg=="));
        concurrentHashMap.put(14, a("ZWNpdmVkLnRjdWRvcnAub3I="));
        concurrentHashMap.put(15, a("bGV2ZWxfaXBhX3RzcmlmLnRjdWRvcnAub3I="));
        concurrentHashMap.put(16, a("cmVydXRjYWZ1bmFtLnRjdWRvcnAub3I="));
        concurrentHashMap.put(17, a("ZW1hbi50Y3Vkb3JwLm9y"));
        concurrentHashMap.put(18, a("dHNvaC5kbGl1Yi5vcg=="));
    }

    public static String a(String str) {
        try {
            return new StringBuilder(new String(Base64.decode(str, 2))).reverse().toString();
        } catch (Exception e) {
            com.tencent.qimei.k.a.a(e);
            return "";
        }
    }

    public static String a(int i) {
        String str = a.get(Integer.valueOf(i));
        return str == null ? "" : str;
    }
}
