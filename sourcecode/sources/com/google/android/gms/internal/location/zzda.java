package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import androidx.collection.SimpleArrayMap;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LastLocationRequest;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
/* loaded from: classes.dex */
public final class zzda extends GmsClient {
    private final SimpleArrayMap zzf;
    private final SimpleArrayMap zzg;
    private final SimpleArrayMap zzh;

    public zzda(Context context, Looper looper, ClientSettings clientSettings, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 23, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzf = new SimpleArrayMap();
        this.zzg = new SimpleArrayMap();
        this.zzh = new SimpleArrayMap();
    }

    private final boolean zzE(Feature feature) {
        Feature feature2;
        Feature[] availableFeatures = getAvailableFeatures();
        if (availableFeatures == null) {
            return false;
        }
        int length = availableFeatures.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                feature2 = null;
                break;
            }
            feature2 = availableFeatures[i];
            if (feature.getName().equals(feature2.getName())) {
                break;
            }
            i++;
        }
        return feature2 != null && feature2.getVersion() >= feature.getVersion();
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
        return queryLocalInterface instanceof zzo ? (zzo) queryLocalInterface : new zzn(iBinder);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final Feature[] getApiFeatures() {
        return com.google.android.gms.location.zzm.zzl;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final int getMinApkVersion() {
        return 11717000;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final String getStartServiceAction() {
        return "com.google.android.location.internal.GoogleLocationManagerService.START";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final void onConnectionSuspended(int i) {
        super.onConnectionSuspended(i);
        synchronized (this.zzf) {
            this.zzf.clear();
        }
        synchronized (this.zzg) {
            this.zzg.clear();
        }
        synchronized (this.zzh) {
            this.zzh.clear();
        }
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final boolean usesClientTelemetry() {
        return true;
    }

    public final void zzB(ListenerHolder.ListenerKey listenerKey, boolean z, TaskCompletionSource taskCompletionSource) throws RemoteException {
        synchronized (this.zzg) {
            zzcw zzcwVar = (zzcw) this.zzg.remove(listenerKey);
            if (zzcwVar == null) {
                taskCompletionSource.setResult(Boolean.FALSE);
                return;
            }
            zzcwVar.zzh();
            if (!z) {
                taskCompletionSource.setResult(Boolean.TRUE);
            } else if (zzE(com.google.android.gms.location.zzm.zzj)) {
                ((zzo) getService()).zzy(zzdb.zzb(null, zzcwVar, null, null), new zzcl(this, Boolean.TRUE, taskCompletionSource));
            } else {
                ((zzo) getService()).zzz(new zzdf(2, null, null, zzcwVar, null, new zzcn(Boolean.TRUE, taskCompletionSource), null));
            }
        }
    }

    public final void zzC(ListenerHolder.ListenerKey listenerKey, boolean z, TaskCompletionSource taskCompletionSource) throws RemoteException {
        synchronized (this.zzf) {
            zzcz zzczVar = (zzcz) this.zzf.remove(listenerKey);
            if (zzczVar == null) {
                taskCompletionSource.setResult(Boolean.FALSE);
                return;
            }
            zzczVar.zzg();
            if (!z) {
                taskCompletionSource.setResult(Boolean.TRUE);
            } else if (zzE(com.google.android.gms.location.zzm.zzj)) {
                ((zzo) getService()).zzy(zzdb.zzc(null, zzczVar, null, null), new zzcl(this, Boolean.TRUE, taskCompletionSource));
            } else {
                ((zzo) getService()).zzz(new zzdf(2, null, zzczVar, null, null, new zzcn(Boolean.TRUE, taskCompletionSource), null));
            }
        }
    }

    public final void zzq(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, TaskCompletionSource taskCompletionSource) throws RemoteException {
        Preconditions.checkNotNull(geofencingRequest, "geofencingRequest can't be null.");
        Preconditions.checkNotNull(pendingIntent, "PendingIntent must be specified.");
        ((zzo) getService()).zzg(geofencingRequest, pendingIntent, new zzci(taskCompletionSource));
    }

    public final void zzt(LastLocationRequest lastLocationRequest, TaskCompletionSource taskCompletionSource) throws RemoteException {
        getContext();
        if (zzE(com.google.android.gms.location.zzm.zzf)) {
            ((zzo) getService()).zzj(lastLocationRequest, new zzcm(this, taskCompletionSource));
        } else {
            taskCompletionSource.setResult(((zzo) getService()).zzd());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0044 A[Catch: all -> 0x0082, TryCatch #0 {, blocks: (B:4:0x001a, B:8:0x0028, B:9:0x003b, B:11:0x0044, B:12:0x0080, B:16:0x0057, B:17:0x002e), top: B:3:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0057 A[Catch: all -> 0x0082, TryCatch #0 {, blocks: (B:4:0x001a, B:8:0x0028, B:9:0x003b, B:11:0x0044, B:12:0x0080, B:16:0x0057, B:17:0x002e), top: B:3:0x001a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzu(com.google.android.gms.internal.location.zzcs r18, com.google.android.gms.location.LocationRequest r19, com.google.android.gms.tasks.TaskCompletionSource r20) throws android.os.RemoteException {
        /*
            r17 = this;
            r1 = r17
            r0 = r19
            r2 = r20
            com.google.android.gms.common.api.internal.ListenerHolder r3 = r18.zza()
            com.google.android.gms.common.api.internal.ListenerHolder$ListenerKey r4 = r3.getListenerKey()
            r4.getClass()
            com.google.android.gms.common.Feature r5 = com.google.android.gms.location.zzm.zzj
            boolean r5 = r1.zzE(r5)
            androidx.collection.SimpleArrayMap r6 = r1.zzg
            monitor-enter(r6)
            androidx.collection.SimpleArrayMap r7 = r1.zzg     // Catch: java.lang.Throwable -> L82
            java.lang.Object r7 = r7.get(r4)     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzcw r7 = (com.google.android.gms.internal.location.zzcw) r7     // Catch: java.lang.Throwable -> L82
            r8 = 0
            if (r7 == 0) goto L2e
            if (r5 == 0) goto L28
            goto L2e
        L28:
            r7.zzg(r3)     // Catch: java.lang.Throwable -> L82
            r13 = r7
            r7 = r8
            goto L3b
        L2e:
            com.google.android.gms.internal.location.zzcw r3 = new com.google.android.gms.internal.location.zzcw     // Catch: java.lang.Throwable -> L82
            r9 = r18
            r3.<init>(r9)     // Catch: java.lang.Throwable -> L82
            androidx.collection.SimpleArrayMap r9 = r1.zzg     // Catch: java.lang.Throwable -> L82
            r9.put(r4, r3)     // Catch: java.lang.Throwable -> L82
            r13 = r3
        L3b:
            r17.getContext()     // Catch: java.lang.Throwable -> L82
            java.lang.String r3 = r4.toIdString()     // Catch: java.lang.Throwable -> L82
            if (r5 == 0) goto L57
            android.os.IInterface r4 = r17.getService()     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzo r4 = (com.google.android.gms.internal.location.zzo) r4     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzdb r3 = com.google.android.gms.internal.location.zzdb.zzb(r7, r13, r8, r3)     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzcl r5 = new com.google.android.gms.internal.location.zzcl     // Catch: java.lang.Throwable -> L82
            r5.<init>(r1, r8, r2)     // Catch: java.lang.Throwable -> L82
            r4.zzk(r3, r0, r5)     // Catch: java.lang.Throwable -> L82
            goto L80
        L57:
            android.os.IInterface r4 = r17.getService()     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzo r4 = (com.google.android.gms.internal.location.zzo) r4     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.location.LocationRequest$Builder r5 = new com.google.android.gms.location.LocationRequest$Builder     // Catch: java.lang.Throwable -> L82
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L82
            r5.zzb(r8)     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.location.LocationRequest r0 = r5.build()     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzdd r11 = com.google.android.gms.internal.location.zzdd.zza(r8, r0)     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzcp r15 = new com.google.android.gms.internal.location.zzcp     // Catch: java.lang.Throwable -> L82
            r15.<init>(r2, r13)     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzdf r0 = new com.google.android.gms.internal.location.zzdf     // Catch: java.lang.Throwable -> L82
            r10 = 1
            r12 = 0
            r14 = 0
            r9 = r0
            r16 = r3
            r9.<init>(r10, r11, r12, r13, r14, r15, r16)     // Catch: java.lang.Throwable -> L82
            r4.zzz(r0)     // Catch: java.lang.Throwable -> L82
        L80:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L82
            return
        L82:
            r0 = move-exception
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L82
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.location.zzda.zzu(com.google.android.gms.internal.location.zzcs, com.google.android.gms.location.LocationRequest, com.google.android.gms.tasks.TaskCompletionSource):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0044 A[Catch: all -> 0x0082, TryCatch #0 {, blocks: (B:4:0x001a, B:8:0x0028, B:9:0x003b, B:11:0x0044, B:12:0x0080, B:16:0x0057, B:17:0x002e), top: B:3:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0057 A[Catch: all -> 0x0082, TryCatch #0 {, blocks: (B:4:0x001a, B:8:0x0028, B:9:0x003b, B:11:0x0044, B:12:0x0080, B:16:0x0057, B:17:0x002e), top: B:3:0x001a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzv(com.google.android.gms.internal.location.zzcs r18, com.google.android.gms.location.LocationRequest r19, com.google.android.gms.tasks.TaskCompletionSource r20) throws android.os.RemoteException {
        /*
            r17 = this;
            r1 = r17
            r0 = r19
            r2 = r20
            com.google.android.gms.common.api.internal.ListenerHolder r3 = r18.zza()
            com.google.android.gms.common.api.internal.ListenerHolder$ListenerKey r4 = r3.getListenerKey()
            r4.getClass()
            com.google.android.gms.common.Feature r5 = com.google.android.gms.location.zzm.zzj
            boolean r5 = r1.zzE(r5)
            androidx.collection.SimpleArrayMap r6 = r1.zzf
            monitor-enter(r6)
            androidx.collection.SimpleArrayMap r7 = r1.zzf     // Catch: java.lang.Throwable -> L82
            java.lang.Object r7 = r7.get(r4)     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzcz r7 = (com.google.android.gms.internal.location.zzcz) r7     // Catch: java.lang.Throwable -> L82
            r8 = 0
            if (r7 == 0) goto L2e
            if (r5 == 0) goto L28
            goto L2e
        L28:
            r7.zzf(r3)     // Catch: java.lang.Throwable -> L82
            r12 = r7
            r7 = r8
            goto L3b
        L2e:
            com.google.android.gms.internal.location.zzcz r3 = new com.google.android.gms.internal.location.zzcz     // Catch: java.lang.Throwable -> L82
            r9 = r18
            r3.<init>(r9)     // Catch: java.lang.Throwable -> L82
            androidx.collection.SimpleArrayMap r9 = r1.zzf     // Catch: java.lang.Throwable -> L82
            r9.put(r4, r3)     // Catch: java.lang.Throwable -> L82
            r12 = r3
        L3b:
            r17.getContext()     // Catch: java.lang.Throwable -> L82
            java.lang.String r3 = r4.toIdString()     // Catch: java.lang.Throwable -> L82
            if (r5 == 0) goto L57
            android.os.IInterface r4 = r17.getService()     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzo r4 = (com.google.android.gms.internal.location.zzo) r4     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzdb r3 = com.google.android.gms.internal.location.zzdb.zzc(r7, r12, r8, r3)     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzcl r5 = new com.google.android.gms.internal.location.zzcl     // Catch: java.lang.Throwable -> L82
            r5.<init>(r1, r8, r2)     // Catch: java.lang.Throwable -> L82
            r4.zzk(r3, r0, r5)     // Catch: java.lang.Throwable -> L82
            goto L80
        L57:
            android.os.IInterface r4 = r17.getService()     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzo r4 = (com.google.android.gms.internal.location.zzo) r4     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.location.LocationRequest$Builder r5 = new com.google.android.gms.location.LocationRequest$Builder     // Catch: java.lang.Throwable -> L82
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L82
            r5.zzb(r8)     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.location.LocationRequest r0 = r5.build()     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzdd r11 = com.google.android.gms.internal.location.zzdd.zza(r8, r0)     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzco r15 = new com.google.android.gms.internal.location.zzco     // Catch: java.lang.Throwable -> L82
            r15.<init>(r2, r12)     // Catch: java.lang.Throwable -> L82
            com.google.android.gms.internal.location.zzdf r0 = new com.google.android.gms.internal.location.zzdf     // Catch: java.lang.Throwable -> L82
            r10 = 1
            r13 = 0
            r14 = 0
            r9 = r0
            r16 = r3
            r9.<init>(r10, r11, r12, r13, r14, r15, r16)     // Catch: java.lang.Throwable -> L82
            r4.zzz(r0)     // Catch: java.lang.Throwable -> L82
        L80:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L82
            return
        L82:
            r0 = move-exception
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L82
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.location.zzda.zzv(com.google.android.gms.internal.location.zzcs, com.google.android.gms.location.LocationRequest, com.google.android.gms.tasks.TaskCompletionSource):void");
    }

    public final void zzy(List list, TaskCompletionSource taskCompletionSource) throws RemoteException {
        Preconditions.checkArgument((list == null || list.isEmpty()) ? false : true, "geofenceRequestIds can't be null nor empty.");
        ((zzo) getService()).zzo((String[]) list.toArray(new String[0]), new zzci(taskCompletionSource), getContext().getPackageName());
    }
}
