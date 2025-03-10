package com.tencent.qmsp.oaid2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.tencent.qmsp.oaid2.f;

/* loaded from: classes.dex */
public class i {
    public Context b;
    public f c;
    public g d;
    public boolean a = false;
    public ServiceConnection e = new a();

    public class a implements ServiceConnection {
        public a() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            c.a("HSDID did service binded");
            i.this.c = f.a.a(iBinder);
            i.this.a(true);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            i.this.a(false);
        }
    }

    public i(Context context) {
        this.b = context;
    }

    public void a() {
        try {
            if (!this.a || this.e == null || this.b == null) {
                return;
            }
            c.a("HSDID start to unbind did service");
            this.a = false;
            this.b.unbindService(this.e);
        } catch (Exception e) {
            c.b("HSDID error:" + e.getMessage());
        }
    }

    public void a(g gVar) {
        try {
            this.d = gVar;
            Intent intent = new Intent("com.asus.msa.action.ACCESS_DID");
            ComponentName componentName = new ComponentName("com.asus.msa.SupplementaryDID", "com.asus.msa.SupplementaryDID.SupplementaryDIDService");
            Intent intent2 = new Intent(intent);
            intent2.setComponent(componentName);
            c.a("HSDID start to bind did service");
            boolean bindService = this.b.bindService(intent2, this.e, 1);
            this.a = bindService;
            if (bindService) {
                return;
            }
            this.d.b();
        } catch (Exception unused) {
            a(false);
        }
    }

    public final void a(boolean z) {
        if (z) {
            try {
                this.d.a(this.c);
                return;
            } catch (Exception e) {
                c.b("HSDID notify did bind status error :" + e.getMessage());
                return;
            }
        }
        this.d.b();
    }
}
