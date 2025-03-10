package com.tencent.qimei.v;

import com.tencent.qimei.shellapi.IDependency;

/* compiled from: Strategy.java */
/* loaded from: classes.dex */
public class d {
    public static final b a = new a();

    public static void a(String str, b bVar) {
        com.tencent.qimei.t.b.a().a("StrategyProvider" + str, bVar);
    }

    public static b a(String str) {
        IDependency a2 = com.tencent.qimei.t.b.a().a("StrategyProvider" + str);
        return a2 instanceof b ? (b) a2 : a;
    }
}
