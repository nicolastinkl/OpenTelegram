package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
/* loaded from: classes.dex */
public final class zzl extends com.google.android.gms.internal.maps.zza implements IMapViewDelegate {
    zzl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.IMapViewDelegate");
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void getMapAsync(zzas zzasVar) throws RemoteException {
        Parcel zza = zza();
        com.google.android.gms.internal.maps.zzc.zzg(zza, zzasVar);
        zzc(9, zza);
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final IObjectWrapper getView() throws RemoteException {
        Parcel zzH = zzH(8, zza());
        IObjectWrapper asInterface = IObjectWrapper.Stub.asInterface(zzH.readStrongBinder());
        zzH.recycle();
        return asInterface;
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onCreate(Bundle bundle) throws RemoteException {
        Parcel zza = zza();
        com.google.android.gms.internal.maps.zzc.zze(zza, bundle);
        zzc(2, zza);
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onDestroy() throws RemoteException {
        zzc(5, zza());
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onEnterAmbient(Bundle bundle) throws RemoteException {
        Parcel zza = zza();
        com.google.android.gms.internal.maps.zzc.zze(zza, bundle);
        zzc(10, zza);
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onExitAmbient() throws RemoteException {
        zzc(11, zza());
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onLowMemory() throws RemoteException {
        zzc(6, zza());
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onPause() throws RemoteException {
        zzc(4, zza());
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onResume() throws RemoteException {
        zzc(3, zza());
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onSaveInstanceState(Bundle bundle) throws RemoteException {
        Parcel zza = zza();
        com.google.android.gms.internal.maps.zzc.zze(zza, bundle);
        Parcel zzH = zzH(7, zza);
        if (zzH.readInt() != 0) {
            bundle.readFromParcel(zzH);
        }
        zzH.recycle();
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onStart() throws RemoteException {
        zzc(12, zza());
    }

    @Override // com.google.android.gms.maps.internal.IMapViewDelegate
    public final void onStop() throws RemoteException {
        zzc(13, zza());
    }
}
