package com.google.firebase.appindexing.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation$ResultHolder;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.icing.zzaa;
import com.google.android.gms.internal.icing.zzae;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.firebase:firebase-appindexing@@20.0.0 */
/* loaded from: classes.dex */
abstract class zzs extends TaskApiCall<zzae, Void> implements BaseImplementation$ResultHolder<Status> {
    protected TaskCompletionSource<Void> zzb;

    zzs() {
        super(null, false, 9004);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    protected final /* bridge */ /* synthetic */ void doExecute(zzae zzaeVar, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        this.zzb = taskCompletionSource;
        zza((zzaa) zzaeVar.getService());
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation$ResultHolder
    public final void setFailedResult(Status status) {
        Preconditions.checkArgument(!status.isSuccess(), "Failed result must not be success.");
        String statusMessage = status.getStatusMessage();
        if (statusMessage == null) {
            statusMessage = "";
        }
        this.zzb.setException(zzaf.zza(status, statusMessage));
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation$ResultHolder
    public final /* bridge */ /* synthetic */ void setResult(Object obj) {
        Status status = (Status) obj;
        if (status.isSuccess()) {
            this.zzb.setResult(null);
        } else {
            this.zzb.setException(zzaf.zza(status, "User Action indexing error, please try again."));
        }
    }

    protected abstract void zza(zzaa zzaaVar) throws RemoteException;
}
