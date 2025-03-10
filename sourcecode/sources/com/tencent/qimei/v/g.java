package com.tencent.qimei.v;

import j$.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/* compiled from: StrategyDataCache.java */
/* loaded from: classes.dex */
public class g {
    public static Map<String, String> a = new ConcurrentHashMap();

    public static String a(String str) {
        return a.get(str);
    }
}
