package com.google.android.gms.internal.mlkit_language_id;

/* compiled from: com.google.mlkit:language-id@@16.1.1 */
/* loaded from: classes.dex */
final class zzdq extends zzdx {
    private final int zzc;
    private final int zzd;

    zzdq(byte[] bArr, int i, int i2) {
        super(bArr);
        zzdn.zzb(i, i + i2, bArr.length);
        this.zzc = i;
        this.zzd = i2;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdx, com.google.android.gms.internal.mlkit_language_id.zzdn
    public final byte zza(int i) {
        int zza = zza();
        if (((zza - (i + 1)) | i) >= 0) {
            return this.zzb[this.zzc + i];
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(22);
            sb.append("Index < 0: ");
            sb.append(i);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder(40);
        sb2.append("Index > length: ");
        sb2.append(i);
        sb2.append(", ");
        sb2.append(zza);
        throw new ArrayIndexOutOfBoundsException(sb2.toString());
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdx, com.google.android.gms.internal.mlkit_language_id.zzdn
    final byte zzb(int i) {
        return this.zzb[this.zzc + i];
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdx, com.google.android.gms.internal.mlkit_language_id.zzdn
    public final int zza() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdx
    protected final int zze() {
        return this.zzc;
    }
}
