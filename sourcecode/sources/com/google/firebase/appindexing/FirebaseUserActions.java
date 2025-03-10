package com.google.firebase.appindexing;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.appindexing.internal.zzt;
import java.lang.ref.WeakReference;

/* compiled from: com.google.firebase:firebase-appindexing@@20.0.0 */
/* loaded from: classes.dex */
public abstract class FirebaseUserActions {
    private static WeakReference<FirebaseUserActions> zza;

    public static synchronized FirebaseUserActions getInstance(Context context) {
        synchronized (FirebaseUserActions.class) {
            Preconditions.checkNotNull(context);
            WeakReference<FirebaseUserActions> weakReference = zza;
            FirebaseUserActions firebaseUserActions = weakReference == null ? null : weakReference.get();
            if (firebaseUserActions != null) {
                return firebaseUserActions;
            }
            zzt zztVar = new zzt(context.getApplicationContext());
            zza = new WeakReference<>(zztVar);
            return zztVar;
        }
    }

    public abstract Task<Void> end(Action action);
}
