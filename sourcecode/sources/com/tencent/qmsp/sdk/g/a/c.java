package com.tencent.qmsp.sdk.g.a;

import android.content.Context;
import android.os.IBinder;
import com.tencent.qmsp.sdk.base.IVendorCallback;

/* loaded from: classes.dex */
public class c implements com.tencent.qmsp.sdk.base.b, b {
    private IVendorCallback a;
    private d d;
    private String b = "";
    private String c = "";
    private boolean e = false;
    private boolean f = false;

    @Override // com.tencent.qmsp.sdk.base.b
    public String a() {
        return this.b;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void a(Context context, IVendorCallback iVendorCallback) {
        this.a = iVendorCallback;
        d dVar = new d(context);
        this.d = dVar;
        dVar.a(this);
    }

    @Override // com.tencent.qmsp.sdk.g.a.b
    public void a(a aVar) {
        try {
            String c = aVar.c();
            this.b = c;
            if (c == null) {
                this.b = "";
            }
        } catch (Exception unused) {
        }
        try {
            String h = aVar.h();
            this.c = h;
            if (h == null) {
                this.c = "";
            }
        } catch (Exception unused2) {
        }
        try {
            this.f = aVar.g();
        } catch (Exception unused3) {
        }
        this.e = true;
        IVendorCallback iVendorCallback = this.a;
        if (iVendorCallback != null) {
            iVendorCallback.onResult(this.f, this.c, this.b);
        }
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return null;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public String b() {
        return this.c;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void c() {
        this.d.a(this);
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public boolean d() {
        return false;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public boolean e() {
        return this.f;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void f() {
        d dVar;
        if (!this.e || (dVar = this.d) == null) {
            return;
        }
        dVar.a();
    }

    @Override // com.tencent.qmsp.sdk.g.a.b
    public void g() {
        IVendorCallback iVendorCallback = this.a;
        if (iVendorCallback != null) {
            iVendorCallback.onResult(false, null, null);
        }
    }
}
