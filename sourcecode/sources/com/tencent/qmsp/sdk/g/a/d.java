package com.tencent.qmsp.sdk.g.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.tencent.qmsp.sdk.g.a.a;

/* loaded from: classes.dex */
public class d {
    public Context b;
    public com.tencent.qmsp.sdk.g.a.a c;
    public b d;
    public boolean a = false;
    public ServiceConnection e = new a();

    class a implements ServiceConnection {
        a() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            com.tencent.qmsp.sdk.base.c.a("HSDID did service binded");
            d.this.c = a.AbstractBinderC0037a.a(iBinder);
            d.this.a(true);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            d.this.a(false);
        }
    }

    public d(Context context) {
        this.b = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        if (!z) {
            this.d.g();
            return;
        }
        try {
            this.d.a(this.c);
        } catch (Exception e) {
            com.tencent.qmsp.sdk.base.c.b("HSDID notify did bind status error :" + e.getMessage());
        }
    }

    public void a() {
        try {
            if (!this.a || this.e == null || this.b == null) {
                return;
            }
            com.tencent.qmsp.sdk.base.c.a("HSDID start to unbind did service");
            this.a = false;
            this.b.unbindService(this.e);
        } catch (Exception e) {
            com.tencent.qmsp.sdk.base.c.b("HSDID error:" + e.getMessage());
        }
    }

    public void a(b bVar) {
        try {
            this.d = bVar;
            Intent intent = new Intent("com.asus.msa.action.ACCESS_DID");
            ComponentName componentName = new ComponentName("com.asus.msa.SupplementaryDID", "com.asus.msa.SupplementaryDID.SupplementaryDIDService");
            Intent intent2 = new Intent(intent);
            intent2.setComponent(componentName);
            com.tencent.qmsp.sdk.base.c.a("HSDID start to bind did service");
            boolean bindService = this.b.bindService(intent2, this.e, 1);
            this.a = bindService;
            if (bindService) {
                return;
            }
            this.d.g();
        } catch (Exception unused) {
            a(false);
        }
    }
}
