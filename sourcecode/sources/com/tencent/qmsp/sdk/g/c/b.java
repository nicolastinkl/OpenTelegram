package com.tencent.qmsp.sdk.g.c;

import android.content.Context;
import com.tencent.qmsp.sdk.base.IVendorCallback;
import com.tencent.qmsp.sdk.g.c.c;

/* loaded from: classes.dex */
public class b implements com.tencent.qmsp.sdk.base.b, c.b {
    private c a;
    private IVendorCallback b;

    @Override // com.tencent.qmsp.sdk.base.b
    public String a() {
        String a;
        return (e() && (a = this.a.a()) != null) ? a : "";
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void a(Context context, IVendorCallback iVendorCallback) {
        this.b = iVendorCallback;
        this.a = new c(context, this);
    }

    @Override // com.tencent.qmsp.sdk.g.c.c.b
    public void a(c cVar) {
        try {
            IVendorCallback iVendorCallback = this.b;
            if (iVendorCallback != null) {
                iVendorCallback.onResult(e(), b(), a());
            }
        } catch (Exception unused) {
            IVendorCallback iVendorCallback2 = this.b;
            if (iVendorCallback2 != null) {
                iVendorCallback2.onResult(false, null, null);
            }
        }
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public String b() {
        String c;
        return (e() && (c = this.a.c()) != null) ? c : "";
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void c() {
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public boolean d() {
        return false;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public boolean e() {
        c cVar = this.a;
        if (cVar != null) {
            return cVar.b();
        }
        return false;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void f() {
        c cVar = this.a;
        if (cVar != null) {
            cVar.d();
        }
    }
}
