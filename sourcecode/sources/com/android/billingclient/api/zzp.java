package com.android.billingclient.api;

import android.content.Context;
import android.content.IntentFilter;

/* compiled from: com.android.billingclient:billing@@5.1.0 */
/* loaded from: classes.dex */
final class zzp {
    private final Context zza;
    private final zzo zzb;

    zzp(Context context, zzbf zzbfVar) {
        this.zza = context;
        this.zzb = new zzo(this, null, 0 == true ? 1 : 0);
    }

    final zzbf zzb() {
        zzo.zza(this.zzb);
        return null;
    }

    final PurchasesUpdatedListener zzc() {
        PurchasesUpdatedListener purchasesUpdatedListener;
        purchasesUpdatedListener = this.zzb.zzb;
        return purchasesUpdatedListener;
    }

    final void zze() {
        IntentFilter intentFilter = new IntentFilter("com.android.vending.billing.PURCHASES_UPDATED");
        intentFilter.addAction("com.android.vending.billing.ALTERNATIVE_BILLING");
        this.zzb.zzc(this.zza, intentFilter);
    }

    zzp(Context context, PurchasesUpdatedListener purchasesUpdatedListener, zzc zzcVar) {
        this.zza = context;
        this.zzb = new zzo(this, purchasesUpdatedListener, zzcVar, null);
    }
}
