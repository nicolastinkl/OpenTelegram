package com.tencent.qimei.u;

import com.tencent.qimei.shellapi.IDependency;

/* compiled from: BizInfo.java */
/* loaded from: classes.dex */
public class a implements b {
    public final String a;
    public b b;

    public a(String str) {
        this.a = str;
    }

    @Override // com.tencent.qimei.u.b
    public String H() {
        return a() == null ? "" : a().H();
    }

    @Override // com.tencent.qimei.u.b
    public String I() {
        return a() == null ? "" : a().I();
    }

    @Override // com.tencent.qimei.u.b
    public String K() {
        return a() == null ? "" : a().K();
    }

    @Override // com.tencent.qimei.u.b
    public String L() {
        return a() == null ? "" : a().L();
    }

    @Override // com.tencent.qimei.u.b
    public void M() {
        if (a() == null) {
            return;
        }
        a().M();
    }

    @Override // com.tencent.qimei.u.b
    public String N() {
        return a() == null ? "" : a().N();
    }

    public final b a() {
        b bVar = this.b;
        if (bVar != null) {
            return bVar;
        }
        IDependency a = com.tencent.qimei.t.b.a().a("BizInfo" + this.a);
        if (!(a instanceof b)) {
            return null;
        }
        b bVar2 = (b) a;
        this.b = bVar2;
        return bVar2;
    }
}
