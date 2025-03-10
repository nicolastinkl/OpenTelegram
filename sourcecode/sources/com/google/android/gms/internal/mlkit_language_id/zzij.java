package com.google.android.gms.internal.mlkit_language_id;

/* compiled from: com.google.mlkit:language-id@@16.1.1 */
/* loaded from: classes.dex */
public enum zzij implements zzet {
    UNKNOWN_EVENT_TYPE(0),
    VALIDATION_TEST(1),
    CONTINUOUS_FEEDBACK(2);

    private final int zze;

    @Override // com.google.android.gms.internal.mlkit_language_id.zzet
    public final int zza() {
        return this.zze;
    }

    public static zzev zzb() {
        return zzil.zza;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return "<" + zzij.class.getName() + '@' + Integer.toHexString(System.identityHashCode(this)) + " number=" + this.zze + " name=" + name() + '>';
    }

    zzij(int i) {
        this.zze = i;
    }

    static {
        new Object() { // from class: com.google.android.gms.internal.mlkit_language_id.zzim
        };
    }
}
