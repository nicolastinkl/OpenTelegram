package com.tencent.beacon.base.util;

import android.text.TextUtils;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: StrictMode.java */
/* loaded from: classes.dex */
public class e {
    public static AtomicBoolean a = new AtomicBoolean(false);

    public static void a(String str) {
        c.b("[strict]  " + str, new Object[0]);
        if (a.get()) {
            throw new IllegalStateException("[strict] " + str);
        }
    }

    public static void a(Map map) {
        if (!a.get() || map == null) {
            return;
        }
        for (Object obj : map.keySet()) {
            if (!(obj instanceof String)) {
                a("Key必须为String类型!");
            }
            if (!(map.get(obj) instanceof String)) {
                a("Value必须为String类型!");
            }
        }
    }

    private static boolean a() {
        return a.get() || com.tencent.beacon.a.c.b.d(com.tencent.beacon.a.c.c.d().c());
    }

    public static boolean a(String str, Object obj) {
        boolean z;
        if (obj instanceof String) {
            z = TextUtils.isEmpty((String) obj);
        } else {
            z = obj == null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" ");
        sb.append(obj == null ? "=" : "!");
        sb.append("= null!");
        c.a(sb.toString(), new Object[0]);
        if (!z || !a()) {
            return z;
        }
        throw new NullPointerException(str + " == null!");
    }
}
