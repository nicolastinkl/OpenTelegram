package com.google.android.gms.safetynet;

import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.internal.safetynet.zzae;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.android.gms:play-services-safetynet@@17.0.1 */
/* loaded from: classes.dex */
public class SafetyNetClient extends GoogleApi<Api.ApiOptions.NoOptions> {
    public Task<SafetyNetApi$AttestationResponse> attest(byte[] bArr, String str) {
        return PendingResultUtil.toResponseTask(zzae.zza(asGoogleApiClient(), bArr, str), new SafetyNetApi$AttestationResponse());
    }

    SafetyNetClient(Context context) {
        super(context, SafetyNet.API, (Api.ApiOptions) null, new ApiExceptionMapper());
    }
}
