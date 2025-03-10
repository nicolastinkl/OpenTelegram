package com.tencent.qmsp.sdk.g.e;

import android.content.Context;
import com.tencent.qmsp.sdk.base.IVendorCallback;

/* loaded from: classes.dex */
public class f implements com.tencent.qmsp.sdk.base.b {
    private Context a;

    @Override // com.tencent.qmsp.sdk.base.b
    public String a() {
        return d.b(this.a);
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void a(Context context, IVendorCallback iVendorCallback) {
        this.a = context;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public String b() {
        return d.a(this.a);
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void c() {
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public boolean d() {
        return true;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public boolean e() {
        return d.a();
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void f() {
    }
}
