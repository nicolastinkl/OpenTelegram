package com.google.android.gms.internal.safetynet;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNetApi$AttestationResult;

/* compiled from: com.google.android.gms:play-services-safetynet@@17.0.1 */
/* loaded from: classes.dex */
abstract class zzr extends zze<SafetyNetApi$AttestationResult> {
    protected final zzg zzc;

    public zzr(GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzc = new zzq(this);
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* bridge */ /* synthetic */ Result createFailedResult(Status status) {
        return new zzp(status, null);
    }
}
