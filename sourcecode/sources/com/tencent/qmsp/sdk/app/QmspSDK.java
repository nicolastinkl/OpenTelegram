package com.tencent.qmsp.sdk.app;

import android.content.Context;
import com.tencent.qmsp.sdk.f.g;

/* loaded from: classes.dex */
public class QmspSDK extends a {
    public static boolean getSDKIsAlive() {
        if (a.getTaskStatus()) {
            return b.e().a();
        }
        return false;
    }

    public static void setLogcat(boolean z) {
        g.a(z);
    }

    public static int startQmsp(Context context, String str, String str2, String str3, String str4) {
        return a.login(context, str, str2, str3, str4);
    }

    public static void stopQmsp() {
        a.logout();
    }
}
