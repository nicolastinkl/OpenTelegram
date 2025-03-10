package com.tencent.beacon.a.c;

import com.tencent.beacon.base.util.BeaconLogger;
import com.tencent.qimei.log.IObservableLog;
import com.tencent.qimei.upload.BuildConfig;

/* compiled from: QimeiWrapper.java */
/* loaded from: classes.dex */
class h implements IObservableLog {
    final /* synthetic */ BeaconLogger a;

    h(BeaconLogger beaconLogger) {
        this.a = beaconLogger;
    }

    @Override // com.tencent.qimei.log.IObservableLog
    public void onLog(String str) {
        this.a.d(BuildConfig.SDK_ID, str);
    }
}
