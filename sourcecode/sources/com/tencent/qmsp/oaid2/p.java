package com.tencent.qmsp.oaid2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.tencent.qmsp.oaid2.n;
import java.util.Objects;

/* loaded from: classes.dex */
public class p {
    public static String e = "LXOP";
    public Context a;
    public n b;
    public ServiceConnection c;
    public b d;

    public class a implements ServiceConnection {
        public a() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (this) {
                p.this.b = n.a.a(iBinder);
                p pVar = p.this;
                b bVar = pVar.d;
                if (bVar != null) {
                    bVar.a(pVar);
                }
                p.this.b("Service onServiceConnected");
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            p pVar = p.this;
            pVar.b = null;
            pVar.b("Service onServiceDisconnected");
        }
    }

    public interface b {
        void a(p pVar);
    }

    public p(Context context, b bVar) {
        this.a = null;
        this.d = null;
        Objects.requireNonNull(context, "Context can not be null.");
        this.a = context;
        this.d = bVar;
        this.c = new a();
        Intent intent = new Intent();
        intent.setClassName("com.zui.deviceidservice", "com.zui.deviceidservice.DeviceidService");
        if (this.a.bindService(intent, this.c, 1)) {
            b("bindService Successful!");
            return;
        }
        b bVar2 = this.d;
        if (bVar2 != null) {
            bVar2.a(this);
        }
        b("bindService Failed!!!");
    }

    public final void b(String str) {
        c.a(e + " " + str);
    }

    public String c() {
        Context context = this.a;
        if (context == null) {
            b("Context is null.");
            throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
        }
        String packageName = context.getPackageName();
        b("liufeng, getAAID package：" + packageName);
        if (packageName == null || packageName.equals("")) {
            b("input package is null!");
            return null;
        }
        try {
            n nVar = this.b;
            if (nVar != null) {
                return nVar.b(packageName);
            }
            return null;
        } catch (Exception unused) {
            a("getAAID error, RemoteException!");
            return null;
        }
    }

    public void d() {
        try {
            this.a.unbindService(this.c);
            b("unBind Service successful");
        } catch (IllegalArgumentException unused) {
            a("unBind Service exception");
        }
        this.b = null;
    }

    public final void a(String str) {
        c.b(e + " " + str);
    }

    public boolean b() {
        try {
            if (this.b == null) {
                return false;
            }
            b("Device support opendeviceid");
            return this.b.c();
        } catch (Exception unused) {
            a("isSupport error, RemoteException!");
            return false;
        }
    }

    public String a() {
        if (this.a != null) {
            try {
                n nVar = this.b;
                if (nVar != null) {
                    return nVar.b();
                }
                return null;
            } catch (Exception e2) {
                a("getOAID error, RemoteException!");
                e2.printStackTrace();
                return null;
            }
        }
        a("Context is null.");
        throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
    }
}
