package com.tencent.beacon.base.net.call;

import com.tencent.beacon.base.net.NetException;
import com.tencent.beacon.base.net.RequestType;
import com.tencent.beacon.pack.ResponsePackage;

/* compiled from: JceCall.java */
/* loaded from: classes.dex */
class i implements Callback<byte[]> {
    final /* synthetic */ Callback a;
    final /* synthetic */ j b;

    i(j jVar, Callback callback) {
        this.b = jVar;
        this.a = callback;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(byte[] bArr) throws NetException {
        JceRequestEntity jceRequestEntity;
        ResponsePackage a;
        JceRequestEntity jceRequestEntity2;
        byte[] bArr2;
        long j;
        com.tencent.beacon.base.util.c.a("[BeaconNet]", "raw response size: " + bArr.length, new Object[0]);
        jceRequestEntity = this.b.a;
        if (jceRequestEntity.getType() == RequestType.EVENT) {
            a = com.tencent.beacon.base.net.c.c().e.b().a(bArr);
            if (a == null) {
                throw new NetException("ResponsePackageV2 == null");
            }
            bArr2 = null;
        } else {
            a = com.tencent.beacon.base.net.c.c().d.b().a(bArr);
            if (a == null) {
                throw new NetException("responsePackage == null");
            }
            ResponsePackage responsePackage = a;
            int i = responsePackage.cmd;
            jceRequestEntity2 = this.b.a;
            if (i != jceRequestEntity2.getResponseCmd()) {
                throw new NetException("responsePackage.cmd != requestEntity.responseCmd");
            }
            if (responsePackage.result != 0) {
                throw new NetException("responsePackage.result != OK(0)");
            }
            bArr2 = responsePackage.sBuffer;
            if (bArr2 == null || bArr2.length <= 0) {
                throw new NetException("responsePackage.buffer == null");
            }
        }
        j = this.b.b;
        com.tencent.beacon.base.net.b.d.a(j, a.serverTime, a.srcGatewayIp);
        Callback callback = this.a;
        if (callback != null) {
            callback.onResponse(bArr2);
        }
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(com.tencent.beacon.base.net.d dVar) {
        Callback callback = this.a;
        if (callback != null) {
            callback.onFailure(dVar);
        }
    }
}
