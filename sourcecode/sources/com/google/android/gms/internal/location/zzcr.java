package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.internal.BaseImplementation$ResultHolder;
import com.google.android.gms.location.LocationSettingsResult;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
/* loaded from: classes.dex */
final class zzcr extends zzr {
    final /* synthetic */ BaseImplementation$ResultHolder zza;

    zzcr(BaseImplementation$ResultHolder baseImplementation$ResultHolder) {
        this.zza = baseImplementation$ResultHolder;
    }

    @Override // com.google.android.gms.internal.location.zzs
    public final void zzb(LocationSettingsResult locationSettingsResult) {
        this.zza.setResult(locationSettingsResult);
    }
}
