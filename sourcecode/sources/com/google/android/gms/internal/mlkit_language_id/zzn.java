package com.google.android.gms.internal.mlkit_language_id;

/* compiled from: com.google.mlkit:language-id@@16.1.1 */
/* loaded from: classes.dex */
final class zzn<E> extends zzi<E> {
    private final zzk<E> zza;

    zzn(zzk<E> zzkVar, int i) {
        super(zzkVar.size(), i);
        this.zza = zzkVar;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzi
    protected final E zza(int i) {
        return this.zza.get(i);
    }
}
