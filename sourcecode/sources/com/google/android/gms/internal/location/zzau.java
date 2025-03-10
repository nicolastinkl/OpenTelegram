package com.google.android.gms.internal.location;

import android.os.Looper;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation$ResultHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
/* loaded from: classes.dex */
public final class zzau implements FusedLocationProviderApi {
    static /* bridge */ /* synthetic */ TaskCompletionSource zza(final BaseImplementation$ResultHolder baseImplementation$ResultHolder) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        taskCompletionSource.getTask().addOnCompleteListener(new OnCompleteListener() { // from class: com.google.android.gms.internal.location.zzah
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                BaseImplementation$ResultHolder baseImplementation$ResultHolder2 = BaseImplementation$ResultHolder.this;
                if (task.isSuccessful()) {
                    baseImplementation$ResultHolder2.setResult(Status.RESULT_SUCCESS);
                    return;
                }
                if (task.isCanceled()) {
                    baseImplementation$ResultHolder2.setFailedResult(Status.RESULT_CANCELED);
                    return;
                }
                Exception exception = task.getException();
                if (exception instanceof ApiException) {
                    baseImplementation$ResultHolder2.setFailedResult(((ApiException) exception).getStatus());
                } else {
                    baseImplementation$ResultHolder2.setFailedResult(Status.RESULT_INTERNAL_ERROR);
                }
            }
        });
        return taskCompletionSource;
    }

    @Override // com.google.android.gms.location.FusedLocationProviderApi
    public final PendingResult<Status> removeLocationUpdates(GoogleApiClient googleApiClient, LocationListener locationListener) {
        return googleApiClient.execute(new zzan(this, googleApiClient, locationListener));
    }

    @Override // com.google.android.gms.location.FusedLocationProviderApi
    public final PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        if (looper == null) {
            looper = Looper.myLooper();
            Preconditions.checkNotNull(looper, "invalid null looper");
        }
        return googleApiClient.execute(new zzak(this, googleApiClient, ListenerHolders.createListenerHolder(locationListener, looper, LocationListener.class.getSimpleName()), locationRequest));
    }
}
