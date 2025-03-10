package com.google.android.gms.internal.play_billing;

/* compiled from: com.android.billingclient:billing@@5.1.0 */
/* loaded from: classes.dex */
final class zzaa extends zzu {
    static final zzu zza = new zzaa(new Object[0], 0);
    final transient Object[] zzb;
    private final transient int zzc;

    zzaa(Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    @Override // java.util.List
    public final Object get(int i) {
        zzm.zza(i, this.zzc, "index");
        Object obj = this.zzb[i];
        obj.getClass();
        return obj;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.play_billing.zzu, com.google.android.gms.internal.play_billing.zzr
    final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzc);
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.play_billing.zzr
    final int zzb() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.play_billing.zzr
    final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.play_billing.zzr
    final boolean zzf() {
        return false;
    }

    @Override // com.google.android.gms.internal.play_billing.zzr
    final Object[] zzg() {
        return this.zzb;
    }
}
