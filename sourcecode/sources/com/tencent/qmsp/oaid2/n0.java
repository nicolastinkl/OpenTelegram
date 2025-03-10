package com.tencent.qmsp.oaid2;

import android.content.Context;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class n0 implements b {
    public static Class b;
    public static Object c;
    public static Method d;
    public static Method e;
    public Context a;

    public n0() {
        try {
            c.c("xm start");
            Class<?> cls = Class.forName("com.android.id.impl.IdProviderImpl");
            b = cls;
            c = cls.newInstance();
            d = b.getMethod("getOAID", Context.class);
            e = b.getMethod("getAAID", Context.class);
        } catch (Exception e2) {
            c.a("xm reflect exception!" + e2);
        }
    }

    @Override // com.tencent.qmsp.oaid2.b
    public void a(Context context, IVendorCallback iVendorCallback) {
        this.a = context;
    }

    @Override // com.tencent.qmsp.oaid2.b
    public String d() {
        Method method;
        Object obj = c;
        if (obj == null || (method = e) == null) {
            return null;
        }
        return a(this.a, obj, method);
    }

    @Override // com.tencent.qmsp.oaid2.b
    public boolean e() {
        return (b == null || c == null) ? false : true;
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
        Method method;
        Object obj = c;
        if (obj == null || (method = d) == null) {
            return null;
        }
        return a(this.a, obj, method);
    }

    public final String a(Context context, Object obj, Method method) {
        if (obj == null || method == null) {
            return null;
        }
        try {
            Object invoke = method.invoke(obj, context);
            if (invoke != null) {
                return (String) invoke;
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }
}
