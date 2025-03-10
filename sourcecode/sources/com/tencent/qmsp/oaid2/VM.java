package com.tencent.qmsp.oaid2;

import android.content.Context;

/* loaded from: classes.dex */
public class VM {
    public static int getVendorInfo(Context context, IVendorCallback iVendorCallback) {
        return new VendorManager().getVendorInfo(context, iVendorCallback);
    }
}
