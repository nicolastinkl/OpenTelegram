package com.tencent.qimei.e;

import com.tencent.qimei.beaconid.U;
import com.tencent.qimei.j.b;

/* compiled from: NetConstant.java */
/* loaded from: classes.dex */
public final class a {
    public static final String a;

    static {
        String p;
        if (U.a) {
            try {
                p = U.p();
            } catch (UnsatisfiedLinkError e) {
                e.printStackTrace();
            }
            a = p;
        }
        p = "";
        a = p;
    }

    public static String a() {
        return b.a ? "https://test.snowflake.qq.com/ola" : "https://snowflake.qq.com/ola";
    }

    public static String b() {
        return a;
    }
}
