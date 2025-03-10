package com.google.android.gms.internal.mlkit_language_id;

import java.util.Objects;

/* compiled from: com.google.mlkit:language-id@@16.1.1 */
/* loaded from: classes.dex */
final class zzu extends zzt {
    private final zzs zza = new zzs();

    zzu() {
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzt
    public final void zza(Throwable th, Throwable th2) {
        if (th2 == th) {
            throw new IllegalArgumentException("Self suppression is not allowed.", th2);
        }
        Objects.requireNonNull(th2, "The suppressed exception cannot be null.");
        this.zza.zza(th, true).add(th2);
    }
}
