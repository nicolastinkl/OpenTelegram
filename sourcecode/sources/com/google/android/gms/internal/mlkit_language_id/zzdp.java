package com.google.android.gms.internal.mlkit_language_id;

import java.util.Comparator;

/* compiled from: com.google.mlkit:language-id@@16.1.1 */
/* loaded from: classes.dex */
final class zzdp implements Comparator<zzdn> {
    zzdp() {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(zzdn zzdnVar, zzdn zzdnVar2) {
        int zzb;
        int zzb2;
        zzdn zzdnVar3 = zzdnVar;
        zzdn zzdnVar4 = zzdnVar2;
        zzds zzdsVar = (zzds) zzdnVar3.iterator();
        zzds zzdsVar2 = (zzds) zzdnVar4.iterator();
        while (zzdsVar.hasNext() && zzdsVar2.hasNext()) {
            zzb = zzdn.zzb(zzdsVar.zza());
            zzb2 = zzdn.zzb(zzdsVar2.zza());
            int compare = Integer.compare(zzb, zzb2);
            if (compare != 0) {
                return compare;
            }
        }
        return Integer.compare(zzdnVar3.zza(), zzdnVar4.zza());
    }
}
