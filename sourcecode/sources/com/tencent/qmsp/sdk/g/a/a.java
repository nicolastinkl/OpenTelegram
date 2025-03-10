package com.tencent.qmsp.sdk.g.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes.dex */
public interface a extends IInterface {

    /* renamed from: com.tencent.qmsp.sdk.g.a.a$a, reason: collision with other inner class name */
    public static abstract class AbstractBinderC0037a extends Binder implements a {

        /* renamed from: com.tencent.qmsp.sdk.g.a.a$a$a, reason: collision with other inner class name */
        public static class C0038a implements a {
            public IBinder a;

            public C0038a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // com.tencent.qmsp.sdk.g.a.a
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

            /* JADX WARN: Code restructure failed: missing block: B:4:0x001b, code lost:
            
                if (r1.readInt() == 0) goto L10;
             */
            @Override // com.tencent.qmsp.sdk.g.a.a
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean g() {
                /*
                    r5 = this;
                    android.os.Parcel r0 = android.os.Parcel.obtain()
                    android.os.Parcel r1 = android.os.Parcel.obtain()
                    java.lang.String r2 = "com.asus.msa.SupplementaryDID.IDidAidlInterface"
                    r3 = 0
                    r4 = 1
                    r0.writeInterfaceToken(r2)     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L26
                    android.os.IBinder r2 = r5.a     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L26
                    r2.transact(r4, r0, r1, r3)     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L26
                    r1.readException()     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L26
                    int r2 = r1.readInt()     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L26
                    if (r2 != 0) goto L26
                    goto L27
                L1e:
                    r2 = move-exception
                    r1.recycle()
                    r0.recycle()
                    throw r2
                L26:
                    r3 = 1
                L27:
                    r1.recycle()
                    r0.recycle()
                    return r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.g.a.a.AbstractBinderC0037a.C0038a.g():boolean");
            }

            @Override // com.tencent.qmsp.sdk.g.a.a
            public String h() {
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

        public static a a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.asus.msa.SupplementaryDID.IDidAidlInterface");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof a)) ? new C0038a(iBinder) : (a) queryLocalInterface;
        }
    }

    String c();

    boolean g();

    String h();
}
