package com.tencent.qmsp.oaid2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.tencent.qmsp.oaid2.m;

/* loaded from: classes.dex */
public class j {

    public static final class a {
        public final String a;
        public final boolean b;

        public a(String str, boolean z) {
            this.a = str;
            this.b = z;
        }

        public final String a() {
            return this.a;
        }

        public final boolean b() {
            return this.b;
        }
    }

    public static a a(Context context) {
        Log.i(a(), "getAdvertisingIdInfo " + System.currentTimeMillis());
        if (Looper.myLooper() == Looper.getMainLooper()) {
            c.b("Cannot be called from the main thread");
            throw new IllegalStateException("Cannot be called from the main thread");
        }
        try {
            context.getPackageManager().getPackageInfo("com.huawei.hwid", 0);
            k kVar = new k();
            Intent intent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
            intent.setPackage("com.huawei.hwid");
            if (!context.bindService(intent, kVar, 1)) {
                c.b("bind failed");
                return null;
            }
            Log.i(a(), "bind ok");
            try {
                try {
                    if (kVar.a) {
                        throw new IllegalStateException();
                    }
                    kVar.a = true;
                    m a2 = m.a.a(kVar.b.take());
                    return new a(a2.m(), a2.h());
                } catch (RemoteException unused) {
                    c.b("bind hms service RemoteException");
                    return null;
                } catch (Throwable unused2) {
                    c.b("bind hms service InterruptedException");
                    return null;
                }
            } finally {
                context.unbindService(kVar);
            }
        } catch (PackageManager.NameNotFoundException unused3) {
            c.b("HMS not found");
            return null;
        }
    }

    public static String a() {
        return "AdId";
    }
}
