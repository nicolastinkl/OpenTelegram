package com.tencent.qmsp.sdk.u;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.tencent.beacon.event.open.BeaconReport;
import com.tencent.qmsp.sdk.app.QmspSDK;
import com.tencent.qmsp.sdk.base.IVendorCallback;
import com.tencent.qmsp.sdk.base.e;

/* loaded from: classes.dex */
public class U {
    static {
        try {
            System.loadLibrary("qmp");
        } catch (Throwable th) {
            th.printStackTrace();
            load_so();
        }
    }

    public static native String a(Context context, int i, Activity activity, String str);

    public static void getOAID(IVendorCallback iVendorCallback) {
        new e().a(iVendorCallback);
    }

    public static String getOAIDSync(Context context) {
        return e.a(context, (String) null, 0);
    }

    private static String getP() {
        try {
            return (String) BeaconReport.class.getDeclaredMethod("getSoPath", new Class[0]).invoke(null, new Object[0]);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static boolean getSDKIsAlive() {
        return QmspSDK.getSDKIsAlive();
    }

    public static void init_o(Context context, boolean z, boolean z2) {
        e.a(context, z, z2);
    }

    private static boolean load_so() {
        String p = getP();
        try {
            if (TextUtils.isEmpty(p)) {
                return false;
            }
            System.load(p);
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static int startQ(Context context, String str, String str2, String str3, String str4, boolean z) {
        QmspSDK.setLogcat(z);
        return QmspSDK.startQmsp(context, str, str2, str3, str4);
    }

    public static void stopQ() {
        QmspSDK.stopQmsp();
    }
}
