package com.google.android.gms.internal.play_billing;

/* compiled from: com.android.billingclient:billing@@5.1.0 */
/* loaded from: classes.dex */
final class zzv {
    private final Object zza;
    private final Object zzb;
    private final Object zzc;

    zzv(Object obj, Object obj2, Object obj3) {
        this.zza = obj;
        this.zzb = obj2;
        this.zzc = obj3;
    }

    final IllegalArgumentException zza() {
        return new IllegalArgumentException("Multiple entries with same key: " + this.zza + "=" + this.zzb + " and " + this.zza + "=" + this.zzc);
    }
}
