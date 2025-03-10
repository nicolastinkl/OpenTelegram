package com.tencent.qmsp.sdk.g.h;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.tencent.qmsp.sdk.g.h.a;
import java.util.Objects;

/* loaded from: classes.dex */
public class b {
    private static String e = "SDI";
    public static String f = "SI";
    public InterfaceC0048b a;
    private ServiceConnection b;
    private Context c;
    public com.tencent.qmsp.sdk.g.h.a d;

    class a implements ServiceConnection {
        a() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (this) {
                b.this.d = a.AbstractBinderC0046a.a(iBinder);
                b bVar = b.this;
                InterfaceC0048b interfaceC0048b = bVar.a;
                if (interfaceC0048b != null) {
                    interfaceC0048b.a(bVar);
                }
                StringBuilder sb = new StringBuilder();
                sb.append(b.f);
                sb.append(" Service onServiceConnected");
                com.tencent.qmsp.sdk.base.c.c(sb.toString());
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            b.this.d = null;
            com.tencent.qmsp.sdk.base.c.c(b.f + " Service onServiceDisconnected");
        }
    }

    /* renamed from: com.tencent.qmsp.sdk.g.h.b$b, reason: collision with other inner class name */
    public interface InterfaceC0048b {
        void a(b bVar);
    }

    public b(Context context, InterfaceC0048b interfaceC0048b) {
        this.a = null;
        this.c = null;
        Objects.requireNonNull(context, "Context can not be null.");
        this.c = context;
        this.a = interfaceC0048b;
        this.b = new a();
    }

    public String a() {
        StringBuilder sb;
        String str;
        Context context = this.c;
        if (context == null) {
            com.tencent.qmsp.sdk.base.c.c(f + " Context is null.");
            throw new IllegalArgumentException("Context is null, must be new SxCore first");
        }
        String packageName = context.getPackageName();
        com.tencent.qmsp.sdk.base.c.a(f + "apackage：" + packageName);
        if (packageName == null || packageName.equals("")) {
            sb = new StringBuilder();
            sb.append(f);
            str = " input package is null!";
        } else {
            try {
                com.tencent.qmsp.sdk.g.h.a aVar = this.d;
                if (aVar == null) {
                    return null;
                }
                String a2 = aVar.a(packageName);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(f);
                sb2.append(" getAAID Package: ");
                sb2.append(packageName);
                com.tencent.qmsp.sdk.base.c.a(sb2.toString());
                return a2;
            } catch (Exception unused) {
                sb = new StringBuilder();
                sb.append(f);
                str = " geta error, RemoteException!";
            }
        }
        sb.append(str);
        com.tencent.qmsp.sdk.base.c.c(sb.toString());
        return null;
    }

    public String b() {
        if (this.c == null) {
            com.tencent.qmsp.sdk.base.c.c(f + " Context is null.");
            throw new IllegalArgumentException("Context is null, must be new SxCore first");
        }
        try {
            com.tencent.qmsp.sdk.g.h.a aVar = this.d;
            if (aVar == null) {
                return null;
            }
            String a2 = aVar.a();
            StringBuilder sb = new StringBuilder();
            sb.append(e);
            sb.append(" geto call");
            com.tencent.qmsp.sdk.base.c.c(sb.toString());
            return a2;
        } catch (Exception e2) {
            com.tencent.qmsp.sdk.base.c.c(f + " geto error, RemoteException!");
            e2.printStackTrace();
            return null;
        }
    }

    public void c() {
        StringBuilder sb;
        String str;
        Intent intent = new Intent();
        intent.setClassName("com.samsung.android.deviceidservice", "com.samsung.android.deviceidservice.DeviceIdService");
        if (this.c.bindService(intent, this.b, 1)) {
            sb = new StringBuilder();
            sb.append(f);
            str = " bindService Successful!";
        } else {
            this.a.a(this);
            sb = new StringBuilder();
            sb.append(f);
            str = " bindService Failed!";
        }
        sb.append(str);
        com.tencent.qmsp.sdk.base.c.c(sb.toString());
    }

    public boolean d() {
        try {
            if (this.d == null) {
                StringBuilder sb = new StringBuilder();
                sb.append(f);
                sb.append(" Device not support opendeviceid");
                com.tencent.qmsp.sdk.base.c.c(sb.toString());
                return false;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(f);
            sb2.append(" Device support opendeviceid");
            com.tencent.qmsp.sdk.base.c.c(sb2.toString());
            return true;
        } catch (Exception unused) {
            com.tencent.qmsp.sdk.base.c.c(f + " isSupport error, RemoteException!");
            return false;
        }
    }

    public void e() {
        try {
            this.c.unbindService(this.b);
            StringBuilder sb = new StringBuilder();
            sb.append(f);
            sb.append(" unBind Service successful");
            com.tencent.qmsp.sdk.base.c.c(sb.toString());
        } catch (IllegalArgumentException unused) {
            com.tencent.qmsp.sdk.base.c.c(f + " unBind Service exception");
        }
        this.d = null;
    }
}
