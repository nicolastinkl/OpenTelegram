package com.github.gzuliyujiang.oaid.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.github.gzuliyujiang.oaid.IGetter;
import com.github.gzuliyujiang.oaid.OAIDException;
import com.github.gzuliyujiang.oaid.OAIDLog;
import com.github.gzuliyujiang.oaid.impl.OAIDService;
import repeackage.com.oplus.stdid.IStdID;

/* loaded from: classes.dex */
public class OppoExtImpl extends OppoImpl {
    private final Context context;

    public OppoExtImpl(Context context) {
        super(context);
        this.context = context;
    }

    @Override // com.github.gzuliyujiang.oaid.impl.OppoImpl, com.github.gzuliyujiang.oaid.IOAID
    public boolean supported() {
        Context context = this.context;
        if (context == null) {
            return false;
        }
        try {
            return context.getPackageManager().getPackageInfo("com.coloros.mcs", 0) != null;
        } catch (Exception e) {
            OAIDLog.print(e);
            return false;
        }
    }

    @Override // com.github.gzuliyujiang.oaid.impl.OppoImpl, com.github.gzuliyujiang.oaid.IOAID
    public void doGet(IGetter iGetter) {
        if (this.context == null || iGetter == null) {
            return;
        }
        Intent intent = new Intent("action.com.oplus.stdid.ID_SERVICE");
        intent.setComponent(new ComponentName("com.coloros.mcs", "com.oplus.stdid.IdentifyService"));
        OAIDService.bind(this.context, intent, iGetter, new OAIDService.RemoteCaller() { // from class: com.github.gzuliyujiang.oaid.impl.OppoExtImpl.1
            @Override // com.github.gzuliyujiang.oaid.impl.OAIDService.RemoteCaller
            public String callRemoteInterface(IBinder iBinder) throws OAIDException, RemoteException {
                try {
                    return OppoExtImpl.this.realGetOUID(iBinder);
                } catch (RemoteException e) {
                    throw e;
                } catch (OAIDException e2) {
                    throw e2;
                } catch (Exception e3) {
                    throw new OAIDException(e3);
                }
            }
        });
    }

    @Override // com.github.gzuliyujiang.oaid.impl.OppoImpl
    protected String getSerId(IBinder iBinder, String str, String str2) throws RemoteException, OAIDException {
        IStdID asInterface = IStdID.Stub.asInterface(iBinder);
        if (asInterface == null) {
            throw new OAIDException("IStdID is null");
        }
        return asInterface.getSerID(str, str2, "OUID");
    }
}
