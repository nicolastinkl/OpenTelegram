package io.openinstall.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public class ad implements aa {

    private static final class a implements IInterface {
        private final IBinder a;

        public a(IBinder iBinder) {
            this.a = iBinder;
        }

        public String a(String str) throws RemoteException {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.coolpad.deviceidsupport.IDeviceIdManager");
                obtain.writeString(str);
                this.a.transact(2, obtain, obtain2, 0);
                obtain2.readException();
                return obtain2.readString();
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

    @Override // io.openinstall.sdk.aa
    public String a(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.coolpad.deviceidsupport", "com.coolpad.deviceidsupport.DeviceIdService"));
        y yVar = new y();
        if (context.bindService(intent, yVar, 1)) {
            try {
                return new a(yVar.a()).a(context.getPackageName());
            } catch (RemoteException | InterruptedException unused) {
            } finally {
                context.unbindService(yVar);
            }
        }
        return null;
    }
}
