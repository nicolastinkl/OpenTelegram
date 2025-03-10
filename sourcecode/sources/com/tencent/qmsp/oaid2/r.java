package com.tencent.qmsp.oaid2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.IBinder;
import com.tencent.qmsp.oaid2.q;
import java.util.Objects;

/* loaded from: classes.dex */
public class r {
    public static String e = "com.mdid.msa";
    public s a;
    public ServiceConnection b;
    public Context c;
    public q d;

    public class a implements ServiceConnection {
        public s a;

        public a(r rVar, s sVar) {
            this.a = sVar;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (this) {
                r.this.d = q.a.a(iBinder);
                new t(r.this.d, this.a).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            r.this.d = null;
            r.this.d = null;
        }
    }

    public r(Context context, s sVar) {
        Objects.requireNonNull(context, "Context can not be null.");
        this.c = context;
        this.a = sVar;
        this.b = new a(this, sVar);
    }

    public String b() {
        try {
            q qVar = this.d;
            return qVar == null ? "" : qVar.a();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public boolean c() {
        try {
            q qVar = this.d;
            if (qVar == null) {
                return false;
            }
            return qVar.g();
        } catch (Exception unused) {
            return false;
        }
    }

    public void d() {
        q qVar = this.d;
        if (qVar != null) {
            try {
                qVar.f();
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

    public void a(String str) {
        s sVar;
        Intent intent = new Intent();
        intent.setClassName("com.mdid.msa", "com.mdid.msa.service.MsaIdService");
        intent.setAction("com.bun.msa.action.bindto.service");
        intent.putExtra("com.bun.msa.param.pkgname", str);
        if (this.c.bindService(intent, this.b, 1) || (sVar = this.a) == null) {
            return;
        }
        sVar.b();
    }

    public static boolean a(Context context) {
        try {
            context.getPackageManager().getPackageInfo(e, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
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

    public String a() {
        try {
            q qVar = this.d;
            return qVar == null ? "" : qVar.d();
        } catch (Exception unused) {
            return "";
        }
    }
}
