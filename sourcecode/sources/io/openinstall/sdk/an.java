package io.openinstall.sdk;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public class an implements aa {

    private static final class a implements IInterface {
        private final IBinder a;

        public a(IBinder iBinder) {
            this.a = iBinder;
        }

        public String a() throws RemoteException {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.bun.lib.MsaIdInterface");
                this.a.transact(3, obtain, obtain2, 0);
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
        intent.setClassName("com.mdid.msa", "com.mdid.msa.service.MsaKlService");
        intent.setAction("com.bun.msa.action.start.service");
        intent.putExtra("com.bun.msa.param.pkgname", context.getPackageName());
        intent.putExtra("com.bun.msa.param.runinset", true);
        try {
            context.startService(intent);
        } catch (Exception unused) {
        }
        y yVar = new y();
        Intent intent2 = new Intent();
        intent2.setClassName("com.mdid.msa", "com.mdid.msa.service.MsaIdService");
        intent2.setAction("com.bun.msa.action.bindto.service");
        intent2.putExtra("com.bun.msa.param.pkgname", context.getPackageName());
        if (context.bindService(intent2, yVar, 1)) {
            try {
                return new a(yVar.a()).a();
            } catch (RemoteException | InterruptedException unused2) {
            } finally {
                context.unbindService(yVar);
            }
        }
        return null;
    }
}
