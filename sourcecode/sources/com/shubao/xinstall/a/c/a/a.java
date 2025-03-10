package com.shubao.xinstall.a.c.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import com.shubao.xinstall.a.f.o;
import java.util.concurrent.LinkedBlockingDeque;

/* loaded from: classes.dex */
public final class a {

    /* renamed from: com.shubao.xinstall.a.c.a.a$a, reason: collision with other inner class name */
    static final class ServiceConnectionC0012a implements ServiceConnection {
        boolean a;
        final LinkedBlockingDeque<IBinder> b;

        private ServiceConnectionC0012a() {
            this.a = false;
            this.b = new LinkedBlockingDeque<>(1);
        }

        /* synthetic */ ServiceConnectionC0012a(byte b) {
            this();
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.b.put(iBinder);
            } catch (InterruptedException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
        }
    }

    static final class b implements IInterface {
        private IBinder a;

        public b(IBinder iBinder) {
            this.a = iBinder;
        }

        public final String a() {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.a.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                return obtain2.readString();
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this.a;
        }

        public final boolean b() {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                obtain.writeInt(1);
                this.a.transact(2, obtain, obtain2, 0);
                obtain2.readException();
                return obtain2.readInt() != 0;
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }
    }

    public static final class c {
        private final String a;
        private final boolean b;

        c(String str, boolean z) {
            this.a = str;
            this.b = z;
        }

        public final String a() {
            return this.b ? "" : this.a;
        }
    }

    public static c a(Context context) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            o.a("Cannot call in the main thread, You must call in the other thread");
            return null;
        }
        byte b2 = 0;
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ServiceConnectionC0012a serviceConnectionC0012a = new ServiceConnectionC0012a(b2);
        Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
        intent.setPackage("com.google.android.gms");
        try {
            if (context.bindService(intent, serviceConnectionC0012a, 1)) {
                if (serviceConnectionC0012a.a) {
                    throw new IllegalStateException();
                }
                serviceConnectionC0012a.a = true;
                b bVar = new b(serviceConnectionC0012a.b.take());
                return new c(bVar.a(), bVar.b());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            context.unbindService(serviceConnectionC0012a);
        }
        return null;
    }
}
