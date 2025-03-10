package com.google.android.gms.internal.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.location.zzu;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
/* loaded from: classes.dex */
final class zzco extends zzj {
    final /* synthetic */ TaskCompletionSource zza;
    final /* synthetic */ zzu zzb;

    zzco(TaskCompletionSource taskCompletionSource, zzu zzuVar) {
        this.zza = taskCompletionSource;
        this.zzb = zzuVar;
    }

    @Override // com.google.android.gms.internal.location.zzk
    public final void zzd(zzg zzgVar) {
        TaskUtil.setResultOrApiException(zzgVar.getStatus(), this.zza);
    }

    @Override // com.google.android.gms.internal.location.zzk
    public final void zze() throws RemoteException {
        this.zzb.zze();
    }
}
