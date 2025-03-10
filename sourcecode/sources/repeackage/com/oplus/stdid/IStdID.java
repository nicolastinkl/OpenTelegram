package repeackage.com.oplus.stdid;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import repeackage.com.heytap.openid.IOpenID;

/* loaded from: classes4.dex */
public interface IStdID extends IOpenID {

    public static abstract class Stub extends IOpenID.Stub {
        private static final String DESCRIPTOR = "com.oplus.stdid.IStdID";
        static final int TRANSACTION_getSerID = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStdID asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IStdID)) {
                return (IStdID) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        private static class Proxy implements IStdID {
            public static IOpenID sDefaultImpl;
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // repeackage.com.heytap.openid.IOpenID
            public String getSerID(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSerID(str, str2, str3);
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IOpenID iOpenID) {
            if (Proxy.sDefaultImpl != null || iOpenID == null) {
                return false;
            }
            Proxy.sDefaultImpl = iOpenID;
            return true;
        }

        public static IOpenID getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
