package com.tencent.qmsp.sdk.g.h;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface a extends IInterface {

    /* renamed from: com.tencent.qmsp.sdk.g.h.a$a, reason: collision with other inner class name */
    public static abstract class AbstractBinderC0046a extends Binder implements a {

        /* renamed from: com.tencent.qmsp.sdk.g.h.a$a$a, reason: collision with other inner class name */
        private static class C0047a implements a {
            private IBinder a;

            C0047a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // com.tencent.qmsp.sdk.g.h.a
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

            @Override // com.tencent.qmsp.sdk.g.h.a
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
        }

        public static a a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.samsung.android.deviceidservice.IDeviceIdService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof a)) ? new C0047a(iBinder) : (a) queryLocalInterface;
        }
    }

    String a();

    String a(String str);
}
