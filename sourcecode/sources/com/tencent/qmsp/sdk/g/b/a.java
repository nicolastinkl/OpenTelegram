package com.tencent.qmsp.sdk.g.b;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.tencent.qmsp.sdk.g.b.d;

/* loaded from: classes.dex */
public class a {

    /* renamed from: com.tencent.qmsp.sdk.g.b.a$a, reason: collision with other inner class name */
    public static final class C0039a {
        private final String a;
        private final boolean b;

        C0039a(String str, boolean z) {
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

    public static C0039a a(Context context) {
        String str;
        b bVar;
        Intent intent;
        String str2;
        Log.i(a(), "getAdvertisingIdInfo " + System.currentTimeMillis());
        if (Looper.myLooper() == Looper.getMainLooper()) {
            com.tencent.qmsp.sdk.base.c.b("Cannot be called from the main thread");
            throw new IllegalStateException("Cannot be called from the main thread");
        }
        try {
            context.getPackageManager().getPackageInfo("com.huawei.hwid", 0);
            bVar = new b();
            intent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
            intent.setPackage("com.huawei.hwid");
        } catch (PackageManager.NameNotFoundException unused) {
            str = "HMS not found";
        }
        if (!context.bindService(intent, bVar, 1)) {
            str = "bind failed";
            com.tencent.qmsp.sdk.base.c.b(str);
            return null;
        }
        Log.i(a(), "bind ok");
        try {
            if (bVar.a) {
                throw new IllegalStateException();
            }
            bVar.a = true;
            d a = d.a.a(bVar.b.take());
            return new C0039a(a.i(), a.f());
        } catch (RemoteException unused2) {
            str2 = "bind hms service RemoteException";
            try {
                com.tencent.qmsp.sdk.base.c.b(str2);
                return null;
            } finally {
                context.unbindService(bVar);
            }
        } catch (Throwable unused3) {
            str2 = "bind hms service InterruptedException";
            com.tencent.qmsp.sdk.base.c.b(str2);
            return null;
        }
    }

    private static String a() {
        return "AdId";
    }
}
