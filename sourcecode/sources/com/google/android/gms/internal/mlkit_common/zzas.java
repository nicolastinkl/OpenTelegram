package com.google.android.gms.internal.mlkit_common;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* compiled from: com.google.mlkit:common@@17.0.0 */
/* loaded from: classes.dex */
final class zzas extends WeakReference<Throwable> {
    private final int zza;

    public zzas(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, referenceQueue);
        Objects.requireNonNull(th, "The referent cannot be null");
        this.zza = System.identityHashCode(th);
    }

    public final int hashCode() {
        return this.zza;
    }

    public final boolean equals(Object obj) {
        if (obj != null && obj.getClass() == zzas.class) {
            if (this == obj) {
                return true;
            }
            zzas zzasVar = (zzas) obj;
            if (this.zza == zzasVar.zza && get() == zzasVar.get()) {
                return true;
            }
        }
        return false;
    }
}
