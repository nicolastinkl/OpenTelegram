package com.tencent.qmsp.oaid2;

import android.content.Context;

/* loaded from: classes.dex */
public class m0 implements b {
    public l0 a;

    @Override // com.tencent.qmsp.oaid2.b
    public void a(Context context, IVendorCallback iVendorCallback) {
        this.a = new l0(context);
    }

    @Override // com.tencent.qmsp.oaid2.b
    public String d() {
        return null;
    }

    @Override // com.tencent.qmsp.oaid2.b
    public boolean e() {
        return "1".equals(a("persist.sys.identifierid.supported", "0"));
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
        return this.a.a(0, "");
    }

    public static String a(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, "unknown");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
