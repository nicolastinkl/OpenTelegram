package com.google.android.gms.internal.mlkit_language_id;

/* compiled from: com.google.mlkit:language-id@@16.1.1 */
/* loaded from: classes.dex */
final class zzfu {
    private static final zzfs zza = zzc();
    private static final zzfs zzb = new zzfv();

    static zzfs zza() {
        return zza;
    }

    static zzfs zzb() {
        return zzb;
    }

    private static zzfs zzc() {
        try {
            return (zzfs) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
