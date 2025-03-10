package io.openinstall.sdk;

import android.content.Context;
import android.text.TextUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class w {
    private static final LinkedBlockingQueue<String> a = new LinkedBlockingQueue<>();
    private Method b;
    private Class<?> c;
    private Method d;

    static class a implements InvocationHandler {
        a() {
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            Object invoke;
            Class<?> cls;
            String str = "NULL";
            try {
            } catch (Throwable th) {
                if (ga.a) {
                    ga.b("IdSupplier getOAID failed : %s", th.toString());
                }
            }
            if (!"OnSupport".equals(method.getName())) {
                if ("onSupport".equals(method.getName())) {
                    Class<?> cls2 = Class.forName("com.bun.miitmdid.interfaces.IdSupplier");
                    Object invoke2 = cls2.getDeclaredMethod("isSupported", new Class[0]).invoke(objArr[0], new Object[0]);
                    if (invoke2 != null && Boolean.parseBoolean(invoke2.toString())) {
                        invoke = cls2.getDeclaredMethod("getOAID", new Class[0]).invoke(objArr[0], new Object[0]);
                    } else if (ga.a) {
                        ga.b("IdSupplier isSupport = false", new Object[0]);
                    }
                } else {
                    if (ga.a) {
                        ga.b("IIdentifierListener invoke %s", method.getName());
                    }
                    str = null;
                }
                w.a.offer(str);
                return null;
            }
            if (!(objArr[0] != null ? Boolean.parseBoolean(String.valueOf(objArr[0])) : false)) {
                if (ga.a) {
                    ga.b("IdSupplier isSupport = false", new Object[0]);
                }
                w.a.offer(str);
                return null;
            }
            try {
                try {
                    cls = Class.forName("com.bun.miitmdid.interfaces.IdSupplier");
                } catch (ClassNotFoundException unused) {
                    cls = Class.forName("com.bun.supplier.IdSupplier");
                }
            } catch (ClassNotFoundException unused2) {
                cls = Class.forName("com.bun.miitmdid.supplier.IdSupplier");
            }
            invoke = cls.getDeclaredMethod("getOAID", new Class[0]).invoke(objArr[1], new Object[0]);
            str = (String) invoke;
            w.a.offer(str);
            return null;
        }
    }

    public String a(Context context) {
        String str = null;
        try {
            Method method = this.b;
            if (method != null) {
                method.invoke(null, context);
            }
            if (this.c != null && this.d != null) {
                Object newProxyInstance = Proxy.newProxyInstance(context.getClassLoader(), new Class[]{this.c}, new a());
                String str2 = null;
                String str3 = null;
                int i = 0;
                while (TextUtils.isEmpty(str2) && i < 2) {
                    try {
                        Integer num = (Integer) this.d.invoke(null, context, Boolean.TRUE, newProxyInstance);
                        if (ga.a) {
                            ga.a("MdidSdkHelper InitSdk return value：" + num, new Object[0]);
                        }
                        str2 = a.poll(1100L, TimeUnit.MILLISECONDS);
                        if ("NULL".equals(str2)) {
                            return str3;
                        }
                        i++;
                        str3 = str2;
                    } catch (Throwable th) {
                        th = th;
                        str = str3;
                        if (ga.a) {
                            ga.b("MdidSdkHelper InitSdk failed : %s", th.toString());
                        }
                        return str;
                    }
                }
                return str3;
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public boolean a() {
        try {
            this.b = Class.forName("com.bun.miitmdid.core.JLibrary").getDeclaredMethod("InitEntry", Context.class);
        } catch (ClassNotFoundException | NoSuchMethodException unused) {
        }
        String[] strArr = {"com.bun.miitmdid.interfaces.IIdentifierListener", "com.bun.supplier.IIdentifierListener", "com.bun.miitmdid.core.IIdentifierListener"};
        for (int i = 0; i < 3; i++) {
            try {
                this.c = Class.forName(strArr[i]);
            } catch (ClassNotFoundException unused2) {
            }
        }
        if (this.c == null) {
            return false;
        }
        try {
            this.d = Class.forName("com.bun.miitmdid.core.MdidSdkHelper").getDeclaredMethod("InitSdk", Context.class, Boolean.TYPE, this.c);
        } catch (ClassNotFoundException | NoSuchMethodException unused3) {
        }
        return this.d != null;
    }
}
