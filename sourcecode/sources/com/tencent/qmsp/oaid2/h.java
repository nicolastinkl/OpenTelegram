package com.tencent.qmsp.oaid2;

import android.content.Context;
import android.os.IBinder;

/* loaded from: classes.dex */
public class h implements b, g {
    public IVendorCallback a;
    public i d;
    public String b = "";
    public String c = "";
    public boolean e = false;
    public boolean f = false;

    @Override // com.tencent.qmsp.oaid2.b
    public void a(Context context, IVendorCallback iVendorCallback) {
        this.a = iVendorCallback;
        i iVar = new i(context);
        this.d = iVar;
        iVar.a(this);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return null;
    }

    @Override // com.tencent.qmsp.oaid2.g
    public void b() {
        IVendorCallback iVendorCallback = this.a;
        if (iVendorCallback != null) {
            iVendorCallback.onResult(false, null, null);
        }
    }

    @Override // com.tencent.qmsp.oaid2.b
    public String d() {
        return this.c;
    }

    @Override // com.tencent.qmsp.oaid2.b
    public boolean e() {
        return this.f;
    }

    @Override // com.tencent.qmsp.oaid2.b
    public void j() {
        this.d.a(this);
    }

    @Override // com.tencent.qmsp.oaid2.b
    public boolean k() {
        return false;
    }

    @Override // com.tencent.qmsp.oaid2.b
    public void l() {
        i iVar;
        if (!this.e || (iVar = this.d) == null) {
            return;
        }
        iVar.a();
    }

    @Override // com.tencent.qmsp.oaid2.b
    public String a() {
        return this.b;
    }

    @Override // com.tencent.qmsp.oaid2.g
    public void a(f fVar) {
        try {
            String c = fVar.c();
            this.b = c;
            if (c == null) {
                this.b = "";
            }
        } catch (Exception unused) {
        }
        try {
            String i = fVar.i();
            this.c = i;
            if (i == null) {
                this.c = "";
            }
        } catch (Exception unused2) {
        }
        try {
            this.f = fVar.b();
        } catch (Exception unused3) {
        }
        this.e = true;
        IVendorCallback iVendorCallback = this.a;
        if (iVendorCallback != null) {
            iVendorCallback.onResult(this.f, this.c, this.b);
        }
    }
}
