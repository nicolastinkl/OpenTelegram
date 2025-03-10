package com.tencent.qmsp.oaid2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes.dex */
public interface f extends IInterface {

    public static abstract class a extends Binder implements f {

        /* renamed from: com.tencent.qmsp.oaid2.f$a$a, reason: collision with other inner class name */
        public static class C0024a implements f {
            public IBinder a;

            public C0024a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            /* JADX WARN: Code restructure failed: missing block: B:4:0x001b, code lost:
            
                if (r1.readInt() == 0) goto L10;
             */
            @Override // com.tencent.qmsp.oaid2.f
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean b() {
                /*
                    r5 = this;
                    android.os.Parcel r0 = android.os.Parcel.obtain()
                    android.os.Parcel r1 = android.os.Parcel.obtain()
                    r2 = 0
                    r3 = 1
                    java.lang.String r4 = "com.asus.msa.SupplementaryDID.IDidAidlInterface"
                    r0.writeInterfaceToken(r4)     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L26
                    android.os.IBinder r4 = r5.a     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L26
                    r4.transact(r3, r0, r1, r2)     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L26
                    r1.readException()     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L26
                    int r4 = r1.readInt()     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L26
                    if (r4 != 0) goto L26
                    goto L27
                L1e:
                    r2 = move-exception
                    r1.recycle()
                    r0.recycle()
                    throw r2
                L26:
                    r2 = 1
                L27:
                    r1.recycle()
                    r0.recycle()
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.oaid2.f.a.C0024a.b():boolean");
            }

            @Override // com.tencent.qmsp.oaid2.f
            public String c() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.asus.msa.SupplementaryDID.IDidAidlInterface");
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    obtain2.recycle();
                    obtain.recycle();
                    return readString;
                } catch (Throwable unused) {
                    obtain2.recycle();
                    obtain.recycle();
                    return "";
                }
            }

            @Override // com.tencent.qmsp.oaid2.f
            public String i() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.asus.msa.SupplementaryDID.IDidAidlInterface");
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    obtain2.recycle();
                    obtain.recycle();
                    return readString;
                } catch (Throwable unused) {
                    obtain2.recycle();
                    obtain.recycle();
                    return "";
                }
            }
        }

        public static f a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.asus.msa.SupplementaryDID.IDidAidlInterface");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof f)) ? new C0024a(iBinder) : (f) queryLocalInterface;
        }
    }

    boolean b();

    String c();

    String i();
}
