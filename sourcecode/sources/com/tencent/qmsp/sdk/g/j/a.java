package com.tencent.qmsp.sdk.g.j;

import android.content.Context;
import com.tencent.qmsp.sdk.base.IVendorCallback;
import com.tencent.qmsp.sdk.base.b;
import com.tencent.qmsp.sdk.base.c;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class a implements b {
    private static Class b;
    private static Object c;
    private static Method d;
    private static Method e;
    private Context a;

    public a() {
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

    private String a(Context context, Object obj, Method method) {
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

    @Override // com.tencent.qmsp.sdk.base.b
    public String a() {
        Method method;
        Object obj = c;
        if (obj == null || (method = d) == null) {
            return null;
        }
        return a(this.a, obj, method);
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void a(Context context, IVendorCallback iVendorCallback) {
        this.a = context;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public String b() {
        Method method;
        Object obj = c;
        if (obj == null || (method = e) == null) {
            return null;
        }
        return a(this.a, obj, method);
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
        return (b == null || c == null) ? false : true;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void f() {
    }
}
