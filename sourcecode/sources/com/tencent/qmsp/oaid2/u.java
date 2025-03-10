package com.tencent.qmsp.oaid2;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes.dex */
public class u implements b, s {
    public r a;
    public Context b;
    public IVendorCallback c;

    @Override // com.tencent.qmsp.oaid2.b
    public void a(Context context, IVendorCallback iVendorCallback) {
        if (r.a(context)) {
            String a = e.a(context);
            if (!TextUtils.isEmpty(a)) {
                r.a(context, a);
            }
            this.a = new r(context, this);
            this.c = iVendorCallback;
            this.b = context;
        }
    }

    @Override // com.tencent.qmsp.oaid2.s
    public void b() {
        IVendorCallback iVendorCallback = this.c;
        if (iVendorCallback != null) {
            iVendorCallback.onResult(false, "", "");
        }
    }

    @Override // com.tencent.qmsp.oaid2.b
    public String d() {
        String a;
        return (!e() || (a = this.a.a()) == null) ? "" : a;
    }

    @Override // com.tencent.qmsp.oaid2.b
    public boolean e() {
        r rVar = this.a;
        if (rVar != null) {
            return rVar.c();
        }
        return false;
    }

    @Override // com.tencent.qmsp.oaid2.b
    public void j() {
        r rVar = this.a;
        if (rVar != null) {
            rVar.a(e.a(this.b));
        } else {
            b();
        }
    }

    @Override // com.tencent.qmsp.oaid2.b
    public boolean k() {
        return false;
    }

    @Override // com.tencent.qmsp.oaid2.b
    public void l() {
        r rVar = this.a;
        if (rVar != null) {
            rVar.d();
        }
    }

    @Override // com.tencent.qmsp.oaid2.b
    public String a() {
        String b;
        return (!e() || (b = this.a.b()) == null) ? "" : b;
    }

    @Override // com.tencent.qmsp.oaid2.s
    public void a(boolean z) {
        IVendorCallback iVendorCallback = this.c;
        if (iVendorCallback != null) {
            iVendorCallback.onResult(e(), d(), a());
        }
    }
}
