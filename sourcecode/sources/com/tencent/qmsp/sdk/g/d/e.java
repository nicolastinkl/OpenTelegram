package com.tencent.qmsp.sdk.g.d;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.qmsp.sdk.base.IVendorCallback;
import com.tencent.qmsp.sdk.base.f;

/* loaded from: classes.dex */
public class e implements com.tencent.qmsp.sdk.base.b, c {
    private b a;
    private Context b;
    private IVendorCallback c;

    @Override // com.tencent.qmsp.sdk.base.b
    public String a() {
        String b;
        return (!e() || (b = this.a.b()) == null) ? "" : b;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void a(Context context, IVendorCallback iVendorCallback) {
        if (b.a(context)) {
            String a = f.a(context);
            if (!TextUtils.isEmpty(a)) {
                b.a(context, a);
            }
            this.a = new b(context, this);
            this.c = iVendorCallback;
            this.b = context;
        }
    }

    @Override // com.tencent.qmsp.sdk.g.d.c
    public void a(boolean z) {
        IVendorCallback iVendorCallback = this.c;
        if (iVendorCallback != null) {
            iVendorCallback.onResult(e(), b(), a());
        }
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public String b() {
        String a;
        return (!e() || (a = this.a.a()) == null) ? "" : a;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void c() {
        b bVar = this.a;
        if (bVar != null) {
            bVar.a(f.a(this.b));
        } else {
            g();
        }
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public boolean d() {
        return false;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public boolean e() {
        b bVar = this.a;
        if (bVar != null) {
            return bVar.c();
        }
        return false;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void f() {
        b bVar = this.a;
        if (bVar != null) {
            bVar.d();
        }
    }

    @Override // com.tencent.qmsp.sdk.g.d.c
    public void g() {
        IVendorCallback iVendorCallback = this.c;
        if (iVendorCallback != null) {
            iVendorCallback.onResult(false, "", "");
        }
    }
}
