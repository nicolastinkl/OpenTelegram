package com.tencent.qmsp.sdk.g.f;

import android.content.Context;
import com.tencent.qmsp.sdk.base.IVendorCallback;
import com.tencent.qmsp.sdk.base.f;

/* loaded from: classes.dex */
public class a implements com.tencent.qmsp.sdk.base.b {
    private Context a;

    @Override // com.tencent.qmsp.sdk.base.b
    public String a() {
        return b.b(this.a);
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void a(Context context, IVendorCallback iVendorCallback) {
        this.a = context;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public String b() {
        Context context = this.a;
        return b.a(context, f.a(context));
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
        return b.a(this.a);
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void f() {
    }
}
