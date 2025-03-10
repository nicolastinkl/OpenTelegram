package io.openinstall.sdk;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;

/* loaded from: classes.dex */
public class af implements aa {

    private static final class a implements IInterface {
        private final IBinder a;

        public a(IBinder iBinder) {
            this.a = iBinder;
        }

        public String a() throws RemoteException {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.uodis.opendevice.aidl.OpenDeviceIdentifierService");
                this.a.transact(1, obtain, obtain2, 0);
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
        String str = null;
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                str = Settings.Global.getString(context.getContentResolver(), "pps_oaid");
                if (!TextUtils.isEmpty(str)) {
                    return str;
                }
            } catch (Throwable unused) {
            }
        }
        Intent intent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
        intent.setPackage("com.huawei.hwid");
        y yVar = new y();
        if (context.bindService(intent, yVar, 1)) {
            try {
                str = new a(yVar.a()).a();
            } catch (RemoteException | InterruptedException unused2) {
            } catch (Throwable th) {
                context.unbindService(yVar);
                throw th;
            }
            context.unbindService(yVar);
        }
        return str;
    }
}
