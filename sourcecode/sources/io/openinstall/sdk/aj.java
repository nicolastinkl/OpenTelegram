package io.openinstall.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes.dex */
public class aj implements aa {
    private final Context a;
    private String b;

    private static final class a implements IInterface {
        private final IBinder a;

        public a(IBinder iBinder) {
            this.a = iBinder;
        }

        public String a(String str, String str2, String str3) throws RemoteException {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.heytap.openid.IOpenID");
                obtain.writeString(str);
                obtain.writeString(str2);
                obtain.writeString(str3);
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

    public aj(Context context) {
        this.a = context;
    }

    private String a() throws NoSuchAlgorithmException, PackageManager.NameNotFoundException {
        if (this.b == null) {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(this.a.getPackageManager().getPackageInfo(this.a.getPackageName(), 64).signatures[0].toByteArray());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(Integer.toHexString((b & 255) | 256).substring(1, 3));
            }
            this.b = sb.toString();
        }
        return this.b;
    }

    @Override // io.openinstall.sdk.aa
    public String a(Context context) {
        Intent intent = new Intent("action.com.heytap.openid.OPEN_ID_SERVICE");
        intent.setComponent(new ComponentName("com.heytap.openid", "com.heytap.openid.IdentifyService"));
        y yVar = new y();
        if (context.bindService(intent, yVar, 1)) {
            try {
                return new a(yVar.a()).a(context.getPackageName(), a(), "OUID");
            } catch (PackageManager.NameNotFoundException | RemoteException | InterruptedException | NoSuchAlgorithmException unused) {
            } finally {
                context.unbindService(yVar);
            }
        }
        return null;
    }
}
