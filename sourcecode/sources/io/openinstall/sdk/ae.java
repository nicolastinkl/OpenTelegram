package io.openinstall.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class ae implements aa {

    private static final class a extends Binder implements IInterface {
        private final IBinder a;

        public a(IBinder iBinder) {
            this.a = iBinder;
        }

        public void a(c cVar) throws RemoteException {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.hihonor.cloudservice.oaid.IOAIDService");
                obtain.writeStrongBinder(cVar);
                this.a.transact(2, obtain, obtain2, 0);
                obtain2.readException();
            } finally {
                obtain.recycle();
                obtain2.recycle();
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this.a;
        }
    }

    private class b extends c {
        private BlockingQueue<String> b;

        b(ae aeVar, BlockingQueue<String> blockingQueue) {
            this.b = blockingQueue;
        }

        @Override // io.openinstall.sdk.ae.c
        void a(int i, long j, boolean z, float f, double d, String str) {
        }

        @Override // io.openinstall.sdk.ae.c
        void a(int i, Bundle bundle) {
            try {
                this.b.put((i != 0 || bundle == null) ? "" : bundle.getString("oa_id_flag"));
            } catch (InterruptedException unused) {
            }
        }
    }

    private static abstract class c extends Binder implements IInterface {
        public c() {
            attachInterface(this, "com.hihonor.cloudservice.oaid.IOAIDCallBack");
        }

        abstract void a(int i, long j, boolean z, float f, double d, String str);

        abstract void a(int i, Bundle bundle);

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        protected boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("com.hihonor.cloudservice.oaid.IOAIDCallBack");
                a(parcel.readInt(), parcel.readLong(), parcel.readInt() != 0, parcel.readFloat(), parcel.readDouble(), parcel.readString());
            } else {
                if (i != 2) {
                    if (i != 1598968902) {
                        return super.onTransact(i, parcel, parcel2, i2);
                    }
                    parcel2.writeString("com.hihonor.cloudservice.oaid.IOAIDCallBack");
                    return true;
                }
                parcel.enforceInterface("com.hihonor.cloudservice.oaid.IOAIDCallBack");
                a(parcel.readInt(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
            }
            parcel2.writeNoException();
            return true;
        }
    }

    public static boolean b(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.hihonor.id", 0);
            new Intent("com.hihonor.id.HnOaIdService").setPackage("com.hihonor.id");
            return !r4.queryIntentServices(r2, 0).isEmpty();
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // io.openinstall.sdk.aa
    public String a(Context context) {
        String str = null;
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                str = Settings.Global.getString(context.getContentResolver(), "oaid");
                if (!TextUtils.isEmpty(str)) {
                    return str;
                }
            } catch (Exception unused) {
            }
        }
        Intent intent = new Intent();
        intent.setAction("com.hihonor.id.HnOaIdService");
        intent.setPackage("com.hihonor.id");
        y yVar = new y();
        if (!context.bindService(intent, yVar, 1)) {
            return str;
        }
        try {
            a aVar = new a(yVar.a());
            LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
            aVar.a(new b(this, linkedBlockingQueue));
            return (String) linkedBlockingQueue.poll(3L, TimeUnit.SECONDS);
        } catch (RemoteException | InterruptedException unused2) {
            return str;
        } finally {
            context.unbindService(yVar);
        }
    }
}
