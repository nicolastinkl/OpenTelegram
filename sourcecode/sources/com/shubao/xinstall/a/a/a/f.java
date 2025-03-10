package com.shubao.xinstall.a.a.a;

import android.content.Context;
import com.shubao.xinstall.a.f.o;
import com.xinstall.XINConfiguration;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class f implements Runnable {
    public static final BlockingQueue a = new LinkedBlockingQueue(1);
    private final Context b;

    static class a implements InvocationHandler {
        a() {
        }

        @Override // java.lang.reflect.InvocationHandler
        public final Object invoke(Object obj, Method method, Object[] objArr) {
            Class<?> cls;
            try {
                if ("OnSupport".equals(method.getName())) {
                    try {
                        try {
                            try {
                                cls = Class.forName("com.bun.miitmdid.supplier.IdSupplier");
                            } catch (ClassNotFoundException unused) {
                                cls = Class.forName("com.bun.miitmdid.interfaces.IdSupplier");
                            }
                        } catch (ClassNotFoundException unused2) {
                            cls = null;
                        }
                    } catch (ClassNotFoundException unused3) {
                        cls = Class.forName("com.bun.supplier.IdSupplier");
                    }
                    if (cls != null) {
                        String str = (String) cls.getDeclaredMethod("getOAID", new Class[0]).invoke(objArr[1], new Object[0]);
                        o.a("oaid:".concat(String.valueOf(str)));
                        f.a.offer(str);
                        XINConfiguration xINConfiguration = com.shubao.xinstall.a.b.a;
                        if (xINConfiguration != null) {
                            xINConfiguration.changeOaid(str);
                        }
                    } else {
                        f.a.offer("");
                        XINConfiguration xINConfiguration2 = com.shubao.xinstall.a.b.a;
                        if (xINConfiguration2 != null) {
                            xINConfiguration2.changeOaid("");
                        }
                    }
                }
            } catch (Exception e) {
                if (o.a) {
                    e.printStackTrace();
                }
                f.a.offer("");
                XINConfiguration xINConfiguration3 = com.shubao.xinstall.a.b.a;
                if (xINConfiguration3 != null) {
                    xINConfiguration3.changeOaid("");
                }
            }
            return null;
        }
    }

    public f(Context context) {
        this.b = context;
    }

    public static String a() {
        String str;
        try {
            str = (String) a.poll(3000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            str = "";
        }
        XINConfiguration xINConfiguration = com.shubao.xinstall.a.b.a;
        if (xINConfiguration != null) {
            xINConfiguration.changeOaid(str);
        }
        return str;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0035 A[Catch: Exception -> 0x0027, TRY_LEAVE, TryCatch #8 {Exception -> 0x0027, blocks: (B:3:0x0003, B:5:0x0009, B:7:0x0038, B:8:0x004c, B:13:0x003f, B:15:0x0046, B:20:0x0031, B:22:0x0035), top: B:2:0x0003 }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r10 = this;
            r0 = 0
            r1 = 0
            r2 = 1
            java.lang.String r3 = "com.bun.miitmdid.core.JLibrary"
            java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch: java.lang.Exception -> L27 java.lang.reflect.InvocationTargetException -> L29 java.lang.IllegalAccessException -> L2b java.lang.NoSuchMethodException -> L2d java.lang.ClassNotFoundException -> L2f
            java.lang.String r4 = "InitEntry"
            java.lang.Class[] r5 = new java.lang.Class[r2]     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L21 java.lang.NoSuchMethodException -> L23 java.lang.ClassNotFoundException -> L25 java.lang.Exception -> L27
            java.lang.Class<android.content.Context> r6 = android.content.Context.class
            r5[r1] = r6     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L21 java.lang.NoSuchMethodException -> L23 java.lang.ClassNotFoundException -> L25 java.lang.Exception -> L27
            java.lang.reflect.Method r4 = r3.getDeclaredMethod(r4, r5)     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L21 java.lang.NoSuchMethodException -> L23 java.lang.ClassNotFoundException -> L25 java.lang.Exception -> L27
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L21 java.lang.NoSuchMethodException -> L23 java.lang.ClassNotFoundException -> L25 java.lang.Exception -> L27
            android.content.Context r6 = r10.b     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L21 java.lang.NoSuchMethodException -> L23 java.lang.ClassNotFoundException -> L25 java.lang.Exception -> L27
            r5[r1] = r6     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L21 java.lang.NoSuchMethodException -> L23 java.lang.ClassNotFoundException -> L25 java.lang.Exception -> L27
            r4.invoke(r0, r5)     // Catch: java.lang.reflect.InvocationTargetException -> L1f java.lang.IllegalAccessException -> L21 java.lang.NoSuchMethodException -> L23 java.lang.ClassNotFoundException -> L25 java.lang.Exception -> L27
            goto L38
        L1f:
            r4 = move-exception
            goto L31
        L21:
            r4 = move-exception
            goto L31
        L23:
            r4 = move-exception
            goto L31
        L25:
            r4 = move-exception
            goto L31
        L27:
            r0 = move-exception
            goto L89
        L29:
            r4 = move-exception
            goto L30
        L2b:
            r4 = move-exception
            goto L30
        L2d:
            r4 = move-exception
            goto L30
        L2f:
            r4 = move-exception
        L30:
            r3 = r0
        L31:
            boolean r5 = com.shubao.xinstall.a.f.o.a     // Catch: java.lang.Exception -> L27
            if (r5 == 0) goto L38
            r4.printStackTrace()     // Catch: java.lang.Exception -> L27
        L38:
            java.lang.String r4 = "com.bun.miitmdid.core.IIdentifierListener"
            java.lang.Class r3 = java.lang.Class.forName(r4)     // Catch: java.lang.Exception -> L27 java.lang.ClassNotFoundException -> L3f
            goto L4c
        L3f:
            java.lang.String r4 = "com.bun.supplier.IIdentifierListener"
            java.lang.Class r3 = java.lang.Class.forName(r4)     // Catch: java.lang.Exception -> L27 java.lang.ClassNotFoundException -> L46
            goto L4c
        L46:
            java.lang.String r4 = "com.bun.miitmdid.interfaces.IIdentifierListener"
            java.lang.Class r3 = java.lang.Class.forName(r4)     // Catch: java.lang.Exception -> L27 java.lang.ClassNotFoundException -> L4c
        L4c:
            com.shubao.xinstall.a.a.a.f$a r4 = new com.shubao.xinstall.a.a.a.f$a     // Catch: java.lang.Exception -> L27
            r4.<init>()     // Catch: java.lang.Exception -> L27
            android.content.Context r5 = r10.b     // Catch: java.lang.Exception -> L27
            java.lang.ClassLoader r5 = r5.getClassLoader()     // Catch: java.lang.Exception -> L27
            java.lang.Class[] r6 = new java.lang.Class[r2]     // Catch: java.lang.Exception -> L27
            r6[r1] = r3     // Catch: java.lang.Exception -> L27
            java.lang.Object r4 = java.lang.reflect.Proxy.newProxyInstance(r5, r6, r4)     // Catch: java.lang.Exception -> L27
            java.lang.String r5 = "com.bun.miitmdid.core.MdidSdkHelper"
            java.lang.Class r5 = java.lang.Class.forName(r5)     // Catch: java.lang.Exception -> L27
            java.lang.String r6 = "InitSdk"
            r7 = 3
            java.lang.Class[] r8 = new java.lang.Class[r7]     // Catch: java.lang.Exception -> L27
            java.lang.Class<android.content.Context> r9 = android.content.Context.class
            r8[r1] = r9     // Catch: java.lang.Exception -> L27
            java.lang.Class r9 = java.lang.Boolean.TYPE     // Catch: java.lang.Exception -> L27
            r8[r2] = r9     // Catch: java.lang.Exception -> L27
            r9 = 2
            r8[r9] = r3     // Catch: java.lang.Exception -> L27
            java.lang.reflect.Method r3 = r5.getDeclaredMethod(r6, r8)     // Catch: java.lang.Exception -> L27
            java.lang.Object[] r5 = new java.lang.Object[r7]     // Catch: java.lang.Exception -> L27
            android.content.Context r6 = r10.b     // Catch: java.lang.Exception -> L27
            r5[r1] = r6     // Catch: java.lang.Exception -> L27
            java.lang.Boolean r1 = java.lang.Boolean.TRUE     // Catch: java.lang.Exception -> L27
            r5[r2] = r1     // Catch: java.lang.Exception -> L27
            r5[r9] = r4     // Catch: java.lang.Exception -> L27
            r3.invoke(r0, r5)     // Catch: java.lang.Exception -> L27
            return
        L89:
            boolean r1 = com.shubao.xinstall.a.f.o.a
            if (r1 == 0) goto L90
            r0.printStackTrace()
        L90:
            java.util.concurrent.BlockingQueue r0 = com.shubao.xinstall.a.a.a.f.a
            java.lang.String r1 = ""
            r0.offer(r1)
            com.xinstall.XINConfiguration r0 = com.shubao.xinstall.a.b.a
            if (r0 == 0) goto L9e
            r0.changeOaid(r1)
        L9e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shubao.xinstall.a.a.a.f.run():void");
    }
}
