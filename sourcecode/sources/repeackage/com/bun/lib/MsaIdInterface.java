package repeackage.com.bun.lib;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface MsaIdInterface extends IInterface {

    public static class Default implements MsaIdInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // repeackage.com.bun.lib.MsaIdInterface
        public String getAAID() throws RemoteException {
            return null;
        }

        @Override // repeackage.com.bun.lib.MsaIdInterface
        public String getOAID() throws RemoteException {
            return null;
        }

        @Override // repeackage.com.bun.lib.MsaIdInterface
        public String getVAID() throws RemoteException {
            return null;
        }

        @Override // repeackage.com.bun.lib.MsaIdInterface
        public boolean isDataArrived() throws RemoteException {
            return false;
        }

        @Override // repeackage.com.bun.lib.MsaIdInterface
        public boolean isSupported() throws RemoteException {
            return false;
        }

        @Override // repeackage.com.bun.lib.MsaIdInterface
        public void shutDown() throws RemoteException {
        }
    }

    String getAAID() throws RemoteException;

    String getOAID() throws RemoteException;

    String getVAID() throws RemoteException;

    boolean isDataArrived() throws RemoteException;

    boolean isSupported() throws RemoteException;

    void shutDown() throws RemoteException;

    public static abstract class Stub extends Binder implements MsaIdInterface {
        private static final String DESCRIPTOR = "com.bun.lib.MsaIdInterface";
        static final int TRANSACTION_getAAID = 5;
        static final int TRANSACTION_getOAID = 3;
        static final int TRANSACTION_getVAID = 4;
        static final int TRANSACTION_isDataArrived = 2;
        static final int TRANSACTION_isSupported = 1;
        static final int TRANSACTION_shutDown = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static MsaIdInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof MsaIdInterface)) {
                return (MsaIdInterface) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isSupported = isSupported();
                    parcel2.writeNoException();
                    parcel2.writeInt(isSupported ? 1 : 0);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isDataArrived = isDataArrived();
                    parcel2.writeNoException();
                    parcel2.writeInt(isDataArrived ? 1 : 0);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    String oaid = getOAID();
                    parcel2.writeNoException();
                    parcel2.writeString(oaid);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    String vaid = getVAID();
                    parcel2.writeNoException();
                    parcel2.writeString(vaid);
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    String aaid = getAAID();
                    parcel2.writeNoException();
                    parcel2.writeString(aaid);
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    shutDown();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements MsaIdInterface {
            public static MsaIdInterface sDefaultImpl;
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

            @Override // repeackage.com.bun.lib.MsaIdInterface
            public boolean isSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSupported();
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // repeackage.com.bun.lib.MsaIdInterface
            public boolean isDataArrived() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDataArrived();
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // repeackage.com.bun.lib.MsaIdInterface
            public String getOAID() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
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

            @Override // repeackage.com.bun.lib.MsaIdInterface
            public String getVAID() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVAID();
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // repeackage.com.bun.lib.MsaIdInterface
            public String getAAID() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAAID();
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // repeackage.com.bun.lib.MsaIdInterface
            public void shutDown() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().shutDown();
                    } else {
                        obtain2.readException();
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(MsaIdInterface msaIdInterface) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (msaIdInterface == null) {
                return false;
            }
            Proxy.sDefaultImpl = msaIdInterface;
            return true;
        }

        public static MsaIdInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
