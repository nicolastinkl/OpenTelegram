package com.github.gzuliyujiang.oaid.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.github.gzuliyujiang.oaid.IGetter;
import com.github.gzuliyujiang.oaid.IOAID;
import com.github.gzuliyujiang.oaid.OAIDException;
import com.github.gzuliyujiang.oaid.OAIDLog;
import com.github.gzuliyujiang.oaid.impl.OAIDService;
import repeackage.com.asus.msa.SupplementaryDID.IDidAidlInterface;

/* loaded from: classes.dex */
class AsusImpl implements IOAID {
    private final Context context;

    public AsusImpl(Context context) {
        this.context = context;
    }

    @Override // com.github.gzuliyujiang.oaid.IOAID
    public boolean supported() {
        Context context = this.context;
        if (context == null) {
            return false;
        }
        try {
            return context.getPackageManager().getPackageInfo("com.asus.msa.SupplementaryDID", 0) != null;
        } catch (Exception e) {
            OAIDLog.print(e);
            return false;
        }
    }

    @Override // com.github.gzuliyujiang.oaid.IOAID
    public void doGet(IGetter iGetter) {
        if (this.context == null || iGetter == null) {
            return;
        }
        Intent intent = new Intent("com.asus.msa.action.ACCESS_DID");
        intent.setComponent(new ComponentName("com.asus.msa.SupplementaryDID", "com.asus.msa.SupplementaryDID.SupplementaryDIDService"));
        OAIDService.bind(this.context, intent, iGetter, new OAIDService.RemoteCaller(this) { // from class: com.github.gzuliyujiang.oaid.impl.AsusImpl.1
            @Override // com.github.gzuliyujiang.oaid.impl.OAIDService.RemoteCaller
            public String callRemoteInterface(IBinder iBinder) throws OAIDException, RemoteException {
                IDidAidlInterface asInterface = IDidAidlInterface.Stub.asInterface(iBinder);
                if (asInterface == null) {
                    throw new OAIDException("IDidAidlInterface is null");
                }
                if (!asInterface.isSupport()) {
                    throw new OAIDException("IDidAidlInterface#isSupport return false");
                }
                return asInterface.getOAID();
            }
        });
    }
}
