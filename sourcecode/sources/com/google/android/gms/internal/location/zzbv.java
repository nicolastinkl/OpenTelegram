package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation$ResultHolder;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
/* loaded from: classes.dex */
public final class zzbv implements GeofencingApi {
    static /* bridge */ /* synthetic */ TaskCompletionSource zza(final BaseImplementation$ResultHolder baseImplementation$ResultHolder) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        taskCompletionSource.getTask().addOnCompleteListener(new OnCompleteListener() { // from class: com.google.android.gms.internal.location.zzbq
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

    @Override // com.google.android.gms.location.GeofencingApi
    @Deprecated
    public final PendingResult<Status> addGeofences(GoogleApiClient googleApiClient, List<Geofence> list, PendingIntent pendingIntent) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.addGeofences(list);
        builder.setInitialTrigger(5);
        return googleApiClient.execute(new zzbr(this, googleApiClient, builder.build(), pendingIntent));
    }

    @Override // com.google.android.gms.location.GeofencingApi
    public final PendingResult<Status> removeGeofences(GoogleApiClient googleApiClient, List<String> list) {
        return googleApiClient.execute(new zzbt(this, googleApiClient, list));
    }
}
