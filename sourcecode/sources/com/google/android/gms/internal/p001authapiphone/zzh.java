package com.google.android.gms.internal.p001authapiphone;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: com.google.android.gms:play-services-auth-api-phone@@17.5.1 */
/* loaded from: classes.dex */
public final class zzh extends zza {
    zzh(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.phone.internal.ISmsRetrieverApiService");
    }

    public final void zzg(zzj zzjVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zzb(zza, zzjVar);
        zzb(1, zza);
    }
}
