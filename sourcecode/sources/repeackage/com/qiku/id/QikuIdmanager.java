package repeackage.com.qiku.id;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class QikuIdmanager {
    public static int CODE_GET_OAID = 4;
    public static int CODE_IS_SUPPORTED = 2;
    private IBinder mIBinder;

    public QikuIdmanager() {
        Method declaredMethod;
        this.mIBinder = null;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            if (!((String) cls.getMethod("get", String.class, String.class).invoke(cls, "ro.build.uiversion", "")).contains("360UI") || (declaredMethod = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class)) == null) {
                return;
            }
            this.mIBinder = (IBinder) declaredMethod.invoke(null, "qikuid");
        } catch (Exception e) {
            Log.e("QikuIdmanager", "Failure get qikuid service", e);
        }
    }

    public boolean isSupported() {
        if (this.mIBinder != null) {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                this.mIBinder.transact(CODE_IS_SUPPORTED, obtain, obtain2, 0);
                return obtain2.readInt() == 1;
            } catch (RemoteException e) {
                e.printStackTrace();
            } finally {
                obtain.recycle();
                obtain2.recycle();
            }
        }
        return false;
    }

    public String getOAID() {
        if (this.mIBinder == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            this.mIBinder.transact(CODE_GET_OAID, obtain, obtain2, 0);
            return obtain2.readString();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }
}
