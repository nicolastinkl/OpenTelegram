package com.google.android.gms.internal.mlkit_language_id;

/* compiled from: com.google.mlkit:language-id@@16.1.1 */
/* loaded from: classes.dex */
final class zzhp {
    private static final zzho zza;

    public static boolean zza(byte[] bArr) {
        return zza.zza(bArr, 0, bArr.length);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzb(int i) {
        if (i > -12) {
            return -1;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzb(int i, int i2) {
        if (i > -12 || i2 > -65) {
            return -1;
        }
        return i ^ (i2 << 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzb(int i, int i2, int i3) {
        if (i > -12 || i2 > -65 || i3 > -65) {
            return -1;
        }
        return (i ^ (i2 << 8)) ^ (i3 << 16);
    }

    public static boolean zza(byte[] bArr, int i, int i2) {
        return zza.zza(bArr, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzc(byte[] bArr, int i, int i2) {
        byte b = bArr[i - 1];
        int i3 = i2 - i;
        if (i3 == 0) {
            return zzb(b);
        }
        if (i3 == 1) {
            return zzb(b, bArr[i]);
        }
        if (i3 == 2) {
            return zzb(b, bArr[i], bArr[i + 1]);
        }
        throw new AssertionError();
    }

    static int zza(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < 128) {
            i2++;
        }
        int i3 = length;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char charAt = charSequence.charAt(i2);
            if (charAt < 2048) {
                i3 += (127 - charAt) >>> 31;
                i2++;
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char charAt2 = charSequence.charAt(i2);
                    if (charAt2 < 2048) {
                        i += (127 - charAt2) >>> 31;
                    } else {
                        i += 2;
                        if (55296 <= charAt2 && charAt2 <= 57343) {
                            if (Character.codePointAt(charSequence, i2) < 65536) {
                                throw new zzhq(i2, length2);
                            }
                            i2++;
                        }
                    }
                    i2++;
                }
                i3 += i;
            }
        }
        if (i3 >= length) {
            return i3;
        }
        long j = i3 + 4294967296L;
        StringBuilder sb = new StringBuilder(54);
        sb.append("UTF-8 length does not fit in int: ");
        sb.append(j);
        throw new IllegalArgumentException(sb.toString());
    }

    static int zza(CharSequence charSequence, byte[] bArr, int i, int i2) {
        return zza.zza(charSequence, bArr, i, i2);
    }

    static {
        zzho zzhrVar;
        if ((zzhn.zza() && zzhn.zzb()) && !zzdl.zza()) {
            zzhrVar = new zzht();
        } else {
            zzhrVar = new zzhr();
        }
        zza = zzhrVar;
    }
}
