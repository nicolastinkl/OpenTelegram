package com.tencent.beacon.a.b;

/* compiled from: AttaAggregateReport.java */
/* loaded from: classes.dex */
public class f extends e {
    private static volatile f d;

    private f() {
    }

    public static f e() {
        if (d == null) {
            synchronized (f.class) {
                if (d == null) {
                    d = new f();
                }
            }
        }
        return d;
    }

    @Override // com.tencent.beacon.a.b.e
    String b() {
        return "03300051017";
    }

    @Override // com.tencent.beacon.a.b.e
    String c() {
        return "9462881773";
    }
}
