package com.tencent.qmsp.sdk.g.c;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.tencent.qmsp.sdk.g.c.a;
import java.util.Objects;

/* loaded from: classes.dex */
public class c {
    private static String e = "LXOP";
    private Context a;
    public com.tencent.qmsp.sdk.g.c.a b;
    private ServiceConnection c;
    public b d;

    class a implements ServiceConnection {
        a() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (this) {
                c.this.b = a.AbstractBinderC0041a.a(iBinder);
                c cVar = c.this;
                b bVar = cVar.d;
                if (bVar != null) {
                    bVar.a(cVar);
                }
                c.this.b("Service onServiceConnected");
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            c cVar = c.this;
            cVar.b = null;
            cVar.b("Service onServiceDisconnected");
        }
    }

    public interface b {
        void a(c cVar);
    }

    public c(Context context, b bVar) {
        String str;
        this.a = null;
        this.d = null;
        Objects.requireNonNull(context, "Context can not be null.");
        this.a = context;
        this.d = bVar;
        this.c = new a();
        Intent intent = new Intent();
        intent.setClassName("com.zui.deviceidservice", "com.zui.deviceidservice.DeviceidService");
        if (this.a.bindService(intent, this.c, 1)) {
            str = "bindService Successful!";
        } else {
            b bVar2 = this.d;
            if (bVar2 != null) {
                bVar2.a(this);
            }
            str = "bindService Failed!!!";
        }
        b(str);
    }

    private void a(String str) {
        com.tencent.qmsp.sdk.base.c.b(e + " " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        com.tencent.qmsp.sdk.base.c.a(e + " " + str);
    }

    public String a() {
        if (this.a == null) {
            a("Context is null.");
            throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
        }
        try {
            com.tencent.qmsp.sdk.g.c.a aVar = this.b;
            if (aVar != null) {
                return aVar.g();
            }
            return null;
        } catch (Exception e2) {
            a("getOAID error, RemoteException!");
            e2.printStackTrace();
            return null;
        }
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
            com.tencent.qmsp.sdk.g.c.a aVar = this.b;
            if (aVar != null) {
                return aVar.b(packageName);
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
}
