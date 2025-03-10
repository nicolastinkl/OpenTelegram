package com.tencent.qmsp.sdk.g.d;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.IBinder;
import com.tencent.qmsp.sdk.g.d.a;
import java.util.Objects;

/* loaded from: classes.dex */
public class b {
    private static String e = "com.mdid.msa";
    private c a;
    private ServiceConnection b;
    private Context c;
    private com.tencent.qmsp.sdk.g.d.a d;

    class a implements ServiceConnection {
        c a;

        a(b bVar, c cVar) {
            this.a = cVar;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (this) {
                b.this.d = a.AbstractBinderC0043a.a(iBinder);
                new d(b.this.d, this.a).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            b.this.d = null;
            b.this.d = null;
        }
    }

    public b(Context context, c cVar) {
        Objects.requireNonNull(context, "Context can not be null.");
        this.c = context;
        this.a = cVar;
        this.b = new a(this, cVar);
    }

    public static void a(Context context, String str) {
        Intent intent = new Intent();
        intent.setClassName(e, "com.mdid.msa.service.MsaKlService");
        intent.setAction("com.bun.msa.action.start.service");
        intent.putExtra("com.bun.msa.param.pkgname", str);
        try {
            intent.putExtra("com.bun.msa.param.runinset", true);
            context.startService(intent);
        } catch (Exception unused) {
        }
    }

    public static boolean a(Context context) {
        try {
            context.getPackageManager().getPackageInfo(e, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public String a() {
        try {
            com.tencent.qmsp.sdk.g.d.a aVar = this.d;
            return aVar == null ? "" : aVar.b();
        } catch (Exception unused) {
            return "";
        }
    }

    public void a(String str) {
        c cVar;
        Intent intent = new Intent();
        intent.setClassName("com.mdid.msa", "com.mdid.msa.service.MsaIdService");
        intent.setAction("com.bun.msa.action.bindto.service");
        intent.putExtra("com.bun.msa.param.pkgname", str);
        if (this.c.bindService(intent, this.b, 1) || (cVar = this.a) == null) {
            return;
        }
        cVar.g();
    }

    public String b() {
        try {
            com.tencent.qmsp.sdk.g.d.a aVar = this.d;
            return aVar == null ? "" : aVar.a();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public boolean c() {
        try {
            com.tencent.qmsp.sdk.g.d.a aVar = this.d;
            if (aVar == null) {
                return false;
            }
            return aVar.d();
        } catch (Exception unused) {
            return false;
        }
    }

    public void d() {
        com.tencent.qmsp.sdk.g.d.a aVar = this.d;
        if (aVar != null) {
            try {
                aVar.e();
                ServiceConnection serviceConnection = this.b;
                if (serviceConnection != null) {
                    this.c.unbindService(serviceConnection);
                }
            } catch (Exception unused) {
            }
            this.b = null;
            this.d = null;
        }
    }
}
