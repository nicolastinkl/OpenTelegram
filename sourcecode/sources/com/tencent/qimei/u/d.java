package com.tencent.qimei.u;

import android.content.Context;
import com.tencent.qimei.shellapi.IDependency;

/* compiled from: SdkInfo.java */
/* loaded from: classes.dex */
public class d implements c {
    public static volatile d a;
    public c b;

    public static d b() {
        if (a == null) {
            synchronized (d.class) {
                if (a == null) {
                    a = new d();
                }
            }
        }
        return a;
    }

    @Override // com.tencent.qimei.u.c
    public synchronized Context J() {
        if (a() == null) {
            return null;
        }
        return a().J();
    }

    @Override // com.tencent.qimei.u.c
    public String O() {
        return a() == null ? "" : a().O();
    }

    public final c a() {
        IDependency a2 = com.tencent.qimei.t.b.a().a("SdkInfo");
        if (!(a2 instanceof c)) {
            return null;
        }
        c cVar = (c) a2;
        this.b = cVar;
        return cVar;
    }

    @Override // com.tencent.qimei.u.c
    public String getSdkVersion() {
        return a() == null ? "" : a().getSdkVersion();
    }
}
