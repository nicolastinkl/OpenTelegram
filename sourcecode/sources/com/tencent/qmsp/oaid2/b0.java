package com.tencent.qmsp.oaid2;

import android.content.Context;

/* loaded from: classes.dex */
public class b0 implements b {
    public Context a;

    @Override // com.tencent.qmsp.oaid2.b
    public void a(Context context, IVendorCallback iVendorCallback) {
        this.a = context;
    }

    @Override // com.tencent.qmsp.oaid2.b
    public String d() {
        Context context = this.a;
        return c0.a(context, e.a(context));
    }

    @Override // com.tencent.qmsp.oaid2.b
    public boolean e() {
        return c0.a(this.a);
    }

    @Override // com.tencent.qmsp.oaid2.b
    public void j() {
    }

    @Override // com.tencent.qmsp.oaid2.b
    public boolean k() {
        return true;
    }

    @Override // com.tencent.qmsp.oaid2.b
    public void l() {
    }

    @Override // com.tencent.qmsp.oaid2.b
    public String a() {
        return c0.b(this.a);
    }
}
