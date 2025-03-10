package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Status;

/* compiled from: com.google.android.gms:play-services-base@@18.1.0 */
/* loaded from: classes.dex */
public interface BaseImplementation$ResultHolder<R> {
    void setFailedResult(Status status);

    void setResult(R r);
}
