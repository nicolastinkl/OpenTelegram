package com.tencent.beacon.base.net.call;

import com.tencent.beacon.base.net.NetException;

/* loaded from: classes.dex */
public interface Callback<T> {
    void onFailure(com.tencent.beacon.base.net.d dVar);

    void onResponse(T t) throws NetException;
}
