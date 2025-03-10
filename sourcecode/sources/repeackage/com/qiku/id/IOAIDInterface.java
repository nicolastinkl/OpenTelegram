package repeackage.com.qiku.id;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IOAIDInterface extends IInterface {
    String getOAID() throws RemoteException;

    public static abstract class Stub extends Binder implements IOAIDInterface {
        public static IOAIDInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.qiku.id.IOAIDInterface");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOAIDInterface)) {
                return (IOAIDInterface) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        private static class Proxy implements IOAIDInterface {
            public static IOAIDInterface sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // repeackage.com.qiku.id.IOAIDInterface
            public String getOAID() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.qiku.id.IOAIDInterface");
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getOAID();
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IOAIDInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
