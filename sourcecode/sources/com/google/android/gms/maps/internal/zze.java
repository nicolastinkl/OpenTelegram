package com.google.android.gms.maps.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zzh;
import com.google.android.gms.internal.maps.zzi;
import com.google.android.gms.maps.GoogleMapOptions;

/* compiled from: com.google.android.gms:play-services-maps@@18.1.0 */
/* loaded from: classes.dex */
public final class zze extends com.google.android.gms.internal.maps.zza implements zzf {
    zze(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.internal.ICreator");
    }

    @Override // com.google.android.gms.maps.internal.zzf
    public final int zzd() throws RemoteException {
        Parcel zzH = zzH(9, zza());
        int readInt = zzH.readInt();
        zzH.recycle();
        return readInt;
    }

    @Override // com.google.android.gms.maps.internal.zzf
    public final ICameraUpdateFactoryDelegate zze() throws RemoteException {
        ICameraUpdateFactoryDelegate zzbVar;
        Parcel zzH = zzH(4, zza());
        IBinder readStrongBinder = zzH.readStrongBinder();
        if (readStrongBinder == null) {
            zzbVar = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
            zzbVar = queryLocalInterface instanceof ICameraUpdateFactoryDelegate ? (ICameraUpdateFactoryDelegate) queryLocalInterface : new zzb(readStrongBinder);
        }
        zzH.recycle();
        return zzbVar;
    }

    @Override // com.google.android.gms.maps.internal.zzf
    public final IMapViewDelegate zzg(IObjectWrapper iObjectWrapper, GoogleMapOptions googleMapOptions) throws RemoteException {
        IMapViewDelegate zzlVar;
        Parcel zza = zza();
        com.google.android.gms.internal.maps.zzc.zzg(zza, iObjectWrapper);
        com.google.android.gms.internal.maps.zzc.zze(zza, googleMapOptions);
        Parcel zzH = zzH(3, zza);
        IBinder readStrongBinder = zzH.readStrongBinder();
        if (readStrongBinder == null) {
            zzlVar = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
            zzlVar = queryLocalInterface instanceof IMapViewDelegate ? (IMapViewDelegate) queryLocalInterface : new zzl(readStrongBinder);
        }
        zzH.recycle();
        return zzlVar;
    }

    @Override // com.google.android.gms.maps.internal.zzf
    public final zzi zzj() throws RemoteException {
        Parcel zzH = zzH(5, zza());
        zzi zzb = zzh.zzb(zzH.readStrongBinder());
        zzH.recycle();
        return zzb;
    }

    @Override // com.google.android.gms.maps.internal.zzf
    public final void zzk(IObjectWrapper iObjectWrapper, int i) throws RemoteException {
        Parcel zza = zza();
        com.google.android.gms.internal.maps.zzc.zzg(zza, iObjectWrapper);
        zza.writeInt(i);
        zzc(6, zza);
    }

    @Override // com.google.android.gms.maps.internal.zzf
    public final void zzl(IObjectWrapper iObjectWrapper, int i) throws RemoteException {
        Parcel zza = zza();
        com.google.android.gms.internal.maps.zzc.zzg(zza, iObjectWrapper);
        zza.writeInt(i);
        zzc(10, zza);
    }
}
