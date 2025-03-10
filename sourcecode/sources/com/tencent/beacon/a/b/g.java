package com.tencent.beacon.a.b;

/* compiled from: AttaReport.java */
/* loaded from: classes.dex */
public class g extends e {
    private static volatile g d;

    private g() {
    }

    public static g e() {
        if (d == null) {
            synchronized (g.class) {
                if (d == null) {
                    d = new g();
                }
            }
        }
        return d;
    }

    @Override // com.tencent.beacon.a.b.e
    String b() {
        return "00400014144";
    }

    @Override // com.tencent.beacon.a.b.e
    String c() {
        return "6478159937";
    }
}
