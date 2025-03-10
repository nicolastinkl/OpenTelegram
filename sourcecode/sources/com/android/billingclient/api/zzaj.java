package com.android.billingclient.api;

import java.util.concurrent.Callable;

/* compiled from: com.android.billingclient:billing@@5.1.0 */
/* loaded from: classes.dex */
final class zzaj implements Callable {
    final /* synthetic */ String zza;
    final /* synthetic */ PurchasesResponseListener zzb;
    final /* synthetic */ BillingClientImpl zzc;

    zzaj(BillingClientImpl billingClientImpl, String str, PurchasesResponseListener purchasesResponseListener) {
        this.zzc = billingClientImpl;
        this.zza = str;
        this.zzb = purchasesResponseListener;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        zzbj zzi = BillingClientImpl.zzi(this.zzc, this.zza);
        if (zzi.zzb() != null) {
            this.zzb.onQueryPurchasesResponse(zzi.zza(), zzi.zzb());
            return null;
        }
        this.zzb.onQueryPurchasesResponse(zzi.zza(), com.google.android.gms.internal.play_billing.zzu.zzl());
        return null;
    }
}
