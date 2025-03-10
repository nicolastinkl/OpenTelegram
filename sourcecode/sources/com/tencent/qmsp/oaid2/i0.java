package com.tencent.qmsp.oaid2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface i0 extends IInterface {
    String a();

    String a(String str);

    public static abstract class a extends Binder implements i0 {
        public static i0 a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.samsung.android.deviceidservice.IDeviceIdService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof i0)) ? new C0026a(iBinder) : (i0) queryLocalInterface;
        }

        /* renamed from: com.tencent.qmsp.oaid2.i0$a$a, reason: collision with other inner class name */
        public static class C0026a implements i0 {
            public IBinder a;

            public C0026a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // com.tencent.qmsp.oaid2.i0
            public String a(String str) {
                String str2;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    try {
                        obtain.writeInterfaceToken("com.samsung.android.deviceidservice.IDeviceIdService");
                        obtain.writeString(str);
                        this.a.transact(3, obtain, obtain2, 0);
                        obtain2.readException();
                        str2 = obtain2.readString();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        obtain2.recycle();
                        obtain.recycle();
                        str2 = null;
                    }
                    return str2;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.tencent.qmsp.oaid2.i0
            public String a() {
                String str;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    try {
                        obtain.writeInterfaceToken("com.samsung.android.deviceidservice.IDeviceIdService");
                        this.a.transact(1, obtain, obtain2, 0);
                        obtain2.readException();
                        str = obtain2.readString();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        obtain2.recycle();
                        obtain.recycle();
                        str = null;
                    }
                    return str;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
