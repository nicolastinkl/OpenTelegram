package com.google.android.gms.internal.play_billing;

/* compiled from: com.android.billingclient:billing@@5.1.0 */
/* loaded from: classes.dex */
final class zzs extends zzo {
    private final zzu zza;

    zzs(zzu zzuVar, int i) {
        super(zzuVar.size(), i);
        this.zza = zzuVar;
    }

    @Override // com.google.android.gms.internal.play_billing.zzo
    protected final Object zza(int i) {
        return this.zza.get(i);
    }
}
