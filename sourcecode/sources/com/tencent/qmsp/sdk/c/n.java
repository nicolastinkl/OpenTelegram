package com.tencent.qmsp.sdk.c;

import com.tencent.qmsp.sdk.c.f;

/* loaded from: classes.dex */
public class n implements e {
    private static volatile n a;

    class a implements f.InterfaceC0033f {
        a(n nVar) {
        }
    }

    private n() {
        f.a(3L, new a(this));
    }

    public static n b() {
        if (a == null) {
            synchronized (n.class) {
                if (a == null) {
                    a = new n();
                }
            }
        }
        return a;
    }

    @Override // com.tencent.qmsp.sdk.c.e
    public String a() {
        return "Rpt";
    }
}
