package com.tencent.beacon.a.c;

import android.content.Context;
import com.tencent.qimei.sdk.IAsyncQimeiListener;
import com.tencent.qimei.sdk.QimeiSDK;

/* compiled from: QimeiWrapper.java */
/* loaded from: classes.dex */
class i implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Context b;
    final /* synthetic */ IAsyncQimeiListener c;

    i(String str, Context context, IAsyncQimeiListener iAsyncQimeiListener) {
        this.a = str;
        this.b = context;
        this.c = iAsyncQimeiListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.tencent.beacon.base.util.c.a("QimeiWrapper", "async getQimeiWithAppkey  appkey is %s", this.a);
        j.c(this.b.getApplicationContext(), this.a);
        QimeiSDK.getInstance(this.a).getQimei(this.c);
    }
}
