package com.google.android.gms.internal.location;

import android.location.Location;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.zzt;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
/* loaded from: classes.dex */
final class zzcz extends zzt {
    private final zzcs zza;

    zzcz(zzcs zzcsVar) {
        this.zza = zzcsVar;
    }

    @Override // com.google.android.gms.location.zzu
    public final void zzd(Location location) {
        this.zza.zza().notifyListener(new zzcx(this, location));
    }

    @Override // com.google.android.gms.location.zzu
    public final void zze() {
        this.zza.zza().notifyListener(new zzcy(this));
    }

    final zzcz zzf(ListenerHolder listenerHolder) {
        this.zza.zzc(listenerHolder);
        return this;
    }

    final void zzg() {
        this.zza.zza().clear();
    }
}
