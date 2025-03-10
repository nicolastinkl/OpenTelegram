package com.google.android.gms.internal.clearcut;

import com.google.android.gms.internal.clearcut.zzca;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes.dex */
abstract class zzbu<T extends zzca<T>> {
    zzbu() {
    }

    abstract int zza(Map.Entry<?, ?> entry);

    abstract zzby<T> zza(Object obj);

    abstract void zza(zzfr zzfrVar, Map.Entry<?, ?> entry) throws IOException;

    abstract void zza(Object obj, zzby<T> zzbyVar);

    abstract zzby<T> zzb(Object obj);

    abstract void zzc(Object obj);

    abstract boolean zze(zzdo zzdoVar);
}
