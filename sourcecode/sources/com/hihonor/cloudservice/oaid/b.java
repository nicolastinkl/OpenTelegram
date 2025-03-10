package com.hihonor.cloudservice.oaid;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.hihonor.cloudservice.oaid.a;

/* loaded from: classes.dex */
public interface b extends IInterface {

    public static abstract class a extends Binder implements b {
        public static final /* synthetic */ int a = 0;

        /* renamed from: com.hihonor.cloudservice.oaid.b$a$a, reason: collision with other inner class name */
        public static class C0007a implements b {
            public IBinder a;

            public C0007a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // com.hihonor.cloudservice.oaid.b
            public void a(com.hihonor.cloudservice.oaid.a aVar) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.hihonor.cloudservice.oaid.IOAIDService");
                    obtain.writeStrongBinder(aVar != null ? (a.AbstractBinderC0006a) aVar : null);
                    if (!this.a.transact(3, obtain, obtain2, 0)) {
                        int i = a.a;
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.hihonor.cloudservice.oaid.b
            public void b(com.hihonor.cloudservice.oaid.a aVar) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.hihonor.cloudservice.oaid.IOAIDService");
                    obtain.writeStrongBinder(aVar != null ? (a.AbstractBinderC0006a) aVar : null);
                    if (!this.a.transact(2, obtain, obtain2, 0)) {
                        int i = a.a;
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }

    void a(com.hihonor.cloudservice.oaid.a aVar);

    void b(com.hihonor.cloudservice.oaid.a aVar);
}
