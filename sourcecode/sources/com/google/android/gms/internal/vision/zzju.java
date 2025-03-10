package com.google.android.gms.internal.vision;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
/* loaded from: classes.dex */
abstract class zzju {
    private static final zzju zza;
    private static final zzju zzb;

    private zzju() {
    }

    abstract <L> void zza(Object obj, Object obj2, long j);

    abstract void zzb(Object obj, long j);

    static zzju zza() {
        return zza;
    }

    static zzju zzb() {
        return zzb;
    }

    static {
        zzjx zzjxVar = null;
        zza = new zzjw();
        zzb = new zzjz();
    }
}
