package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.zzan;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
/* loaded from: classes.dex */
final class zzj extends zzan {
    final /* synthetic */ GoogleMap.OnMapLoadedCallback zza;

    zzj(GoogleMap googleMap, GoogleMap.OnMapLoadedCallback onMapLoadedCallback) {
        this.zza = onMapLoadedCallback;
    }

    @Override // com.google.android.gms.maps.internal.zzao
    public final void zzb() throws RemoteException {
        this.zza.onMapLoaded();
    }
}
