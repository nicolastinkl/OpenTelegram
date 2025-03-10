package com.google.android.gms.internal.clearcut;

import com.google.android.gms.internal.clearcut.zzcg;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes.dex */
final class zzbv extends zzbu<zzcg.zze> {
    zzbv() {
    }

    @Override // com.google.android.gms.internal.clearcut.zzbu
    final int zza(Map.Entry<?, ?> entry) {
        return ((zzcg.zze) entry.getKey()).number;
    }

    @Override // com.google.android.gms.internal.clearcut.zzbu
    final zzby<zzcg.zze> zza(Object obj) {
        return ((zzcg.zzd) obj).zzjv;
    }

    @Override // com.google.android.gms.internal.clearcut.zzbu
    final void zza(zzfr zzfrVar, Map.Entry<?, ?> entry) throws IOException {
        zzcg.zze zzeVar = (zzcg.zze) entry.getKey();
        switch (zzbw.zzgq[zzeVar.zzjx.ordinal()]) {
            case 1:
                zzfrVar.zza(zzeVar.number, ((Double) entry.getValue()).doubleValue());
                break;
            case 2:
                zzfrVar.zza(zzeVar.number, ((Float) entry.getValue()).floatValue());
                break;
            case 3:
                zzfrVar.zzi(zzeVar.number, ((Long) entry.getValue()).longValue());
                break;
            case 4:
                zzfrVar.zza(zzeVar.number, ((Long) entry.getValue()).longValue());
                break;
            case 5:
                zzfrVar.zzc(zzeVar.number, ((Integer) entry.getValue()).intValue());
                break;
            case 6:
                zzfrVar.zzc(zzeVar.number, ((Long) entry.getValue()).longValue());
                break;
            case 7:
                zzfrVar.zzf(zzeVar.number, ((Integer) entry.getValue()).intValue());
                break;
            case 8:
                zzfrVar.zzb(zzeVar.number, ((Boolean) entry.getValue()).booleanValue());
                break;
            case 9:
                zzfrVar.zzd(zzeVar.number, ((Integer) entry.getValue()).intValue());
                break;
            case 10:
                zzfrVar.zzm(zzeVar.number, ((Integer) entry.getValue()).intValue());
                break;
            case 11:
                zzfrVar.zzj(zzeVar.number, ((Long) entry.getValue()).longValue());
                break;
            case 12:
                zzfrVar.zze(zzeVar.number, ((Integer) entry.getValue()).intValue());
                break;
            case 13:
                zzfrVar.zzb(zzeVar.number, ((Long) entry.getValue()).longValue());
                break;
            case 14:
                zzfrVar.zzc(zzeVar.number, ((Integer) entry.getValue()).intValue());
                break;
            case 15:
                zzfrVar.zza(zzeVar.number, (zzbb) entry.getValue());
                break;
            case 16:
                zzfrVar.zza(zzeVar.number, (String) entry.getValue());
                break;
            case 17:
                zzfrVar.zzb(zzeVar.number, entry.getValue(), zzea.zzcm().zze(entry.getValue().getClass()));
                break;
            case 18:
                zzfrVar.zza(zzeVar.number, entry.getValue(), zzea.zzcm().zze(entry.getValue().getClass()));
                break;
        }
    }

    @Override // com.google.android.gms.internal.clearcut.zzbu
    final void zza(Object obj, zzby<zzcg.zze> zzbyVar) {
        ((zzcg.zzd) obj).zzjv = zzbyVar;
    }

    @Override // com.google.android.gms.internal.clearcut.zzbu
    final zzby<zzcg.zze> zzb(Object obj) {
        zzby<zzcg.zze> zza = zza(obj);
        if (!zza.isImmutable()) {
            return zza;
        }
        zzby<zzcg.zze> zzbyVar = (zzby) zza.clone();
        zza(obj, zzbyVar);
        return zzbyVar;
    }

    @Override // com.google.android.gms.internal.clearcut.zzbu
    final void zzc(Object obj) {
        zza(obj).zzv();
    }

    @Override // com.google.android.gms.internal.clearcut.zzbu
    final boolean zze(zzdo zzdoVar) {
        return zzdoVar instanceof zzcg.zzd;
    }
}
