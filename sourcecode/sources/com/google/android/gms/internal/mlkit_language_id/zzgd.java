package com.google.android.gms.internal.mlkit_language_id;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import sun.misc.Unsafe;

/* compiled from: com.google.mlkit:language-id@@16.1.1 */
/* loaded from: classes.dex */
final class zzgd<T> implements zzgp<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzhn.zzc();
    private final int[] zzc;
    private final Object[] zzd;
    private final zzfz zzg;
    private final boolean zzh;
    private final boolean zzj;
    private final int[] zzl;
    private final int zzm;
    private final int zzn;
    private final zzge zzo;
    private final zzfj zzp;
    private final zzhh<?, ?> zzq;
    private final zzee<?> zzr;
    private final zzfs zzs;

    private zzgd(int[] iArr, Object[] objArr, int i, int i2, zzfz zzfzVar, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzge zzgeVar, zzfj zzfjVar, zzhh<?, ?> zzhhVar, zzee<?> zzeeVar, zzfs zzfsVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        boolean z3 = zzfzVar instanceof zzeo;
        this.zzj = z;
        this.zzh = zzeeVar != null && zzeeVar.zza(zzfzVar);
        this.zzl = iArr2;
        this.zzm = i3;
        this.zzn = i4;
        this.zzo = zzgeVar;
        this.zzp = zzfjVar;
        this.zzq = zzhhVar;
        this.zzr = zzeeVar;
        this.zzg = zzfzVar;
        this.zzs = zzfsVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x033a  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x039c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static <T> com.google.android.gms.internal.mlkit_language_id.zzgd<T> zza(java.lang.Class<T> r33, com.google.android.gms.internal.mlkit_language_id.zzfx r34, com.google.android.gms.internal.mlkit_language_id.zzge r35, com.google.android.gms.internal.mlkit_language_id.zzfj r36, com.google.android.gms.internal.mlkit_language_id.zzhh<?, ?> r37, com.google.android.gms.internal.mlkit_language_id.zzee<?> r38, com.google.android.gms.internal.mlkit_language_id.zzfs r39) {
        /*
            Method dump skipped, instructions count: 1052
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.mlkit_language_id.zzgd.zza(java.lang.Class, com.google.android.gms.internal.mlkit_language_id.zzfx, com.google.android.gms.internal.mlkit_language_id.zzge, com.google.android.gms.internal.mlkit_language_id.zzfj, com.google.android.gms.internal.mlkit_language_id.zzhh, com.google.android.gms.internal.mlkit_language_id.zzee, com.google.android.gms.internal.mlkit_language_id.zzfs):com.google.android.gms.internal.mlkit_language_id.zzgd");
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String arrays = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + name.length() + String.valueOf(arrays).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(arrays);
            throw new RuntimeException(sb.toString());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x006a, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzgr.zza(com.google.android.gms.internal.mlkit_language_id.zzhn.zzf(r10, r6), com.google.android.gms.internal.mlkit_language_id.zzhn.zzf(r11, r6)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x007e, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzhn.zzb(r10, r6) == com.google.android.gms.internal.mlkit_language_id.zzhn.zzb(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0090, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzhn.zza(r10, r6) == com.google.android.gms.internal.mlkit_language_id.zzhn.zza(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00a4, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzhn.zzb(r10, r6) == com.google.android.gms.internal.mlkit_language_id.zzhn.zzb(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b6, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzhn.zza(r10, r6) == com.google.android.gms.internal.mlkit_language_id.zzhn.zza(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00c8, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzhn.zza(r10, r6) == com.google.android.gms.internal.mlkit_language_id.zzhn.zza(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00da, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzhn.zza(r10, r6) == com.google.android.gms.internal.mlkit_language_id.zzhn.zza(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f0, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzgr.zza(com.google.android.gms.internal.mlkit_language_id.zzhn.zzf(r10, r6), com.google.android.gms.internal.mlkit_language_id.zzhn.zzf(r11, r6)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0106, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzgr.zza(com.google.android.gms.internal.mlkit_language_id.zzhn.zzf(r10, r6), com.google.android.gms.internal.mlkit_language_id.zzhn.zzf(r11, r6)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x011c, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzgr.zza(com.google.android.gms.internal.mlkit_language_id.zzhn.zzf(r10, r6), com.google.android.gms.internal.mlkit_language_id.zzhn.zzf(r11, r6)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x012e, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzhn.zzc(r10, r6) == com.google.android.gms.internal.mlkit_language_id.zzhn.zzc(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0140, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzhn.zza(r10, r6) == com.google.android.gms.internal.mlkit_language_id.zzhn.zza(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0154, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzhn.zzb(r10, r6) == com.google.android.gms.internal.mlkit_language_id.zzhn.zzb(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0165, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzhn.zza(r10, r6) == com.google.android.gms.internal.mlkit_language_id.zzhn.zza(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0178, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzhn.zzb(r10, r6) == com.google.android.gms.internal.mlkit_language_id.zzhn.zzb(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x018b, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzhn.zzb(r10, r6) == com.google.android.gms.internal.mlkit_language_id.zzhn.zzb(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01a4, code lost:
    
        if (java.lang.Float.floatToIntBits(com.google.android.gms.internal.mlkit_language_id.zzhn.zzd(r10, r6)) == java.lang.Float.floatToIntBits(com.google.android.gms.internal.mlkit_language_id.zzhn.zzd(r11, r6))) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01bf, code lost:
    
        if (java.lang.Double.doubleToLongBits(com.google.android.gms.internal.mlkit_language_id.zzhn.zze(r10, r6)) == java.lang.Double.doubleToLongBits(com.google.android.gms.internal.mlkit_language_id.zzhn.zze(r11, r6))) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0038, code lost:
    
        if (com.google.android.gms.internal.mlkit_language_id.zzgr.zza(com.google.android.gms.internal.mlkit_language_id.zzhn.zzf(r10, r6), com.google.android.gms.internal.mlkit_language_id.zzhn.zzf(r11, r6)) != false) goto L105;
     */
    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zza(T r10, T r11) {
        /*
            Method dump skipped, instructions count: 640
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.mlkit_language_id.zzgd.zza(java.lang.Object, java.lang.Object):boolean");
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final int zza(T t) {
        int i;
        int zza2;
        int length = this.zzc.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int zzc = zzc(i3);
            int i4 = this.zzc[i3];
            long j = 1048575 & zzc;
            int i5 = 37;
            switch ((zzc & 267386880) >>> 20) {
                case 0:
                    i = i2 * 53;
                    zza2 = zzeq.zza(Double.doubleToLongBits(zzhn.zze(t, j)));
                    i2 = i + zza2;
                    break;
                case 1:
                    i = i2 * 53;
                    zza2 = Float.floatToIntBits(zzhn.zzd(t, j));
                    i2 = i + zza2;
                    break;
                case 2:
                    i = i2 * 53;
                    zza2 = zzeq.zza(zzhn.zzb(t, j));
                    i2 = i + zza2;
                    break;
                case 3:
                    i = i2 * 53;
                    zza2 = zzeq.zza(zzhn.zzb(t, j));
                    i2 = i + zza2;
                    break;
                case 4:
                    i = i2 * 53;
                    zza2 = zzhn.zza(t, j);
                    i2 = i + zza2;
                    break;
                case 5:
                    i = i2 * 53;
                    zza2 = zzeq.zza(zzhn.zzb(t, j));
                    i2 = i + zza2;
                    break;
                case 6:
                    i = i2 * 53;
                    zza2 = zzhn.zza(t, j);
                    i2 = i + zza2;
                    break;
                case 7:
                    i = i2 * 53;
                    zza2 = zzeq.zza(zzhn.zzc(t, j));
                    i2 = i + zza2;
                    break;
                case 8:
                    i = i2 * 53;
                    zza2 = ((String) zzhn.zzf(t, j)).hashCode();
                    i2 = i + zza2;
                    break;
                case 9:
                    Object zzf = zzhn.zzf(t, j);
                    if (zzf != null) {
                        i5 = zzf.hashCode();
                    }
                    i2 = (i2 * 53) + i5;
                    break;
                case 10:
                    i = i2 * 53;
                    zza2 = zzhn.zzf(t, j).hashCode();
                    i2 = i + zza2;
                    break;
                case 11:
                    i = i2 * 53;
                    zza2 = zzhn.zza(t, j);
                    i2 = i + zza2;
                    break;
                case 12:
                    i = i2 * 53;
                    zza2 = zzhn.zza(t, j);
                    i2 = i + zza2;
                    break;
                case 13:
                    i = i2 * 53;
                    zza2 = zzhn.zza(t, j);
                    i2 = i + zza2;
                    break;
                case 14:
                    i = i2 * 53;
                    zza2 = zzeq.zza(zzhn.zzb(t, j));
                    i2 = i + zza2;
                    break;
                case 15:
                    i = i2 * 53;
                    zza2 = zzhn.zza(t, j);
                    i2 = i + zza2;
                    break;
                case 16:
                    i = i2 * 53;
                    zza2 = zzeq.zza(zzhn.zzb(t, j));
                    i2 = i + zza2;
                    break;
                case 17:
                    Object zzf2 = zzhn.zzf(t, j);
                    if (zzf2 != null) {
                        i5 = zzf2.hashCode();
                    }
                    i2 = (i2 * 53) + i5;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = i2 * 53;
                    zza2 = zzhn.zzf(t, j).hashCode();
                    i2 = i + zza2;
                    break;
                case 50:
                    i = i2 * 53;
                    zza2 = zzhn.zzf(t, j).hashCode();
                    i2 = i + zza2;
                    break;
                case 51:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzeq.zza(Double.doubleToLongBits(zzb(t, j)));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = Float.floatToIntBits(zzc(t, j));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzeq.zza(zze(t, j));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzeq.zza(zze(t, j));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzd(t, j);
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzeq.zza(zze(t, j));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzd(t, j);
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzeq.zza(zzf(t, j));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = ((String) zzhn.zzf(t, j)).hashCode();
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzhn.zzf(t, j).hashCode();
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzhn.zzf(t, j).hashCode();
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzd(t, j);
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzd(t, j);
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzd(t, j);
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzeq.zza(zze(t, j));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzd(t, j);
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzeq.zza(zze(t, j));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zza((zzgd<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzhn.zzf(t, j).hashCode();
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int hashCode = (i2 * 53) + this.zzq.zza(t).hashCode();
        return this.zzh ? (hashCode * 53) + this.zzr.zza(t).hashCode() : hashCode;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final void zzb(T t, T t2) {
        Objects.requireNonNull(t2);
        for (int i = 0; i < this.zzc.length; i += 3) {
            int zzc = zzc(i);
            long j = 1048575 & zzc;
            int i2 = this.zzc[i];
            switch ((zzc & 267386880) >>> 20) {
                case 0:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza(t, j, zzhn.zze(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zzd(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zzb(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zzb(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zza(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zzb(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zza(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza(t, j, zzhn.zzc(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza(t, j, zzhn.zzf(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zza(t, t2, i);
                    break;
                case 10:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza(t, j, zzhn.zzf(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zza(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zza(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zza(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zzb(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zza(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zza((zzgd<T>) t2, i)) {
                        zzhn.zza((Object) t, j, zzhn.zzb(t2, j));
                        zzb((zzgd<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zza(t, t2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.zzp.zza(t, t2, j);
                    break;
                case 50:
                    zzgr.zza(this.zzs, t, t2, j);
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zza((zzgd<T>) t2, i2, i)) {
                        zzhn.zza(t, j, zzhn.zzf(t2, j));
                        zzb((zzgd<T>) t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzb(t, t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zza((zzgd<T>) t2, i2, i)) {
                        zzhn.zza(t, j, zzhn.zzf(t2, j));
                        zzb((zzgd<T>) t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzb(t, t2, i);
                    break;
            }
        }
        zzgr.zza(this.zzq, t, t2);
        if (this.zzh) {
            zzgr.zza(this.zzr, t, t2);
        }
    }

    private final void zza(T t, T t2, int i) {
        long zzc = zzc(i) & 1048575;
        if (zza((zzgd<T>) t2, i)) {
            Object zzf = zzhn.zzf(t, zzc);
            Object zzf2 = zzhn.zzf(t2, zzc);
            if (zzf != null && zzf2 != null) {
                zzhn.zza(t, zzc, zzeq.zza(zzf, zzf2));
                zzb((zzgd<T>) t, i);
            } else if (zzf2 != null) {
                zzhn.zza(t, zzc, zzf2);
                zzb((zzgd<T>) t, i);
            }
        }
    }

    private final void zzb(T t, T t2, int i) {
        int zzc = zzc(i);
        int i2 = this.zzc[i];
        long j = zzc & 1048575;
        if (zza((zzgd<T>) t2, i2, i)) {
            Object zzf = zzhn.zzf(t, j);
            Object zzf2 = zzhn.zzf(t2, j);
            if (zzf != null && zzf2 != null) {
                zzhn.zza(t, j, zzeq.zza(zzf, zzf2));
                zzb((zzgd<T>) t, i2, i);
            } else if (zzf2 != null) {
                zzhn.zza(t, j, zzf2);
                zzb((zzgd<T>) t, i2, i);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final int zzd(T t) {
        int i;
        long j;
        int zzd;
        int zzb2;
        int zzj;
        int zzh;
        int zzi;
        int zze;
        int zzg;
        int zzb3;
        int zzi2;
        int zze2;
        int zzg2;
        int i2 = 267386880;
        int i3 = 1048575;
        int i4 = 1;
        if (this.zzj) {
            Unsafe unsafe = zzb;
            int i5 = 0;
            int i6 = 0;
            while (i5 < this.zzc.length) {
                int zzc = zzc(i5);
                int i7 = (zzc & i2) >>> 20;
                int i8 = this.zzc[i5];
                long j2 = zzc & 1048575;
                if (i7 >= zzek.zza.zza() && i7 <= zzek.zzb.zza()) {
                    int i9 = this.zzc[i5 + 2];
                }
                switch (i7) {
                    case 0:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzb(i8, 0.0d);
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 1:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzb(i8, 0.0f);
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzd(i8, zzhn.zzb(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zze(i8, zzhn.zzb(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzf(i8, zzhn.zza(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 5:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzg(i8, 0L);
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 6:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzi(i8, 0);
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 7:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzb(i8, true);
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 8:
                        if (zza((zzgd<T>) t, i5)) {
                            Object zzf = zzhn.zzf(t, j2);
                            if (zzf instanceof zzdn) {
                                zzb3 = zzea.zzc(i8, (zzdn) zzf);
                            } else {
                                zzb3 = zzea.zzb(i8, (String) zzf);
                            }
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 9:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzgr.zza(i8, zzhn.zzf(t, j2), zza(i5));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 10:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzc(i8, (zzdn) zzhn.zzf(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 11:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzg(i8, zzhn.zza(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 12:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzk(i8, zzhn.zza(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 13:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzj(i8, 0);
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 14:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzh(i8, 0L);
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 15:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzh(i8, zzhn.zza(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzf(i8, zzhn.zzb(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 17:
                        if (zza((zzgd<T>) t, i5)) {
                            zzb3 = zzea.zzc(i8, (zzfz) zzhn.zzf(t, j2), zza(i5));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        zzb3 = zzgr.zzi(i8, zza(t, j2), false);
                        i6 += zzb3;
                        break;
                    case 19:
                        zzb3 = zzgr.zzh(i8, zza(t, j2), false);
                        i6 += zzb3;
                        break;
                    case 20:
                        zzb3 = zzgr.zza(i8, (List<Long>) zza(t, j2), false);
                        i6 += zzb3;
                        break;
                    case 21:
                        zzb3 = zzgr.zzb(i8, (List<Long>) zza(t, j2), false);
                        i6 += zzb3;
                        break;
                    case 22:
                        zzb3 = zzgr.zze(i8, zza(t, j2), false);
                        i6 += zzb3;
                        break;
                    case 23:
                        zzb3 = zzgr.zzi(i8, zza(t, j2), false);
                        i6 += zzb3;
                        break;
                    case 24:
                        zzb3 = zzgr.zzh(i8, zza(t, j2), false);
                        i6 += zzb3;
                        break;
                    case 25:
                        zzb3 = zzgr.zzj(i8, zza(t, j2), false);
                        i6 += zzb3;
                        break;
                    case 26:
                        zzb3 = zzgr.zza(i8, zza(t, j2));
                        i6 += zzb3;
                        break;
                    case 27:
                        zzb3 = zzgr.zza(i8, zza(t, j2), zza(i5));
                        i6 += zzb3;
                        break;
                    case 28:
                        zzb3 = zzgr.zzb(i8, zza(t, j2));
                        i6 += zzb3;
                        break;
                    case 29:
                        zzb3 = zzgr.zzf(i8, zza(t, j2), false);
                        i6 += zzb3;
                        break;
                    case 30:
                        zzb3 = zzgr.zzd(i8, zza(t, j2), false);
                        i6 += zzb3;
                        break;
                    case 31:
                        zzb3 = zzgr.zzh(i8, zza(t, j2), false);
                        i6 += zzb3;
                        break;
                    case 32:
                        zzb3 = zzgr.zzi(i8, zza(t, j2), false);
                        i6 += zzb3;
                        break;
                    case 33:
                        zzb3 = zzgr.zzg(i8, zza(t, j2), false);
                        i6 += zzb3;
                        break;
                    case 34:
                        zzb3 = zzgr.zzc(i8, zza(t, j2), false);
                        i6 += zzb3;
                        break;
                    case 35:
                        zzi2 = zzgr.zzi((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            zze2 = zzea.zze(i8);
                            zzg2 = zzea.zzg(zzi2);
                            zzb3 = zze2 + zzg2 + zzi2;
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 36:
                        zzi2 = zzgr.zzh((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            zze2 = zzea.zze(i8);
                            zzg2 = zzea.zzg(zzi2);
                            zzb3 = zze2 + zzg2 + zzi2;
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 37:
                        zzi2 = zzgr.zza((List<Long>) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            zze2 = zzea.zze(i8);
                            zzg2 = zzea.zzg(zzi2);
                            zzb3 = zze2 + zzg2 + zzi2;
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 38:
                        zzi2 = zzgr.zzb((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            zze2 = zzea.zze(i8);
                            zzg2 = zzea.zzg(zzi2);
                            zzb3 = zze2 + zzg2 + zzi2;
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 39:
                        zzi2 = zzgr.zze((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            zze2 = zzea.zze(i8);
                            zzg2 = zzea.zzg(zzi2);
                            zzb3 = zze2 + zzg2 + zzi2;
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 40:
                        zzi2 = zzgr.zzi((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            zze2 = zzea.zze(i8);
                            zzg2 = zzea.zzg(zzi2);
                            zzb3 = zze2 + zzg2 + zzi2;
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 41:
                        zzi2 = zzgr.zzh((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            zze2 = zzea.zze(i8);
                            zzg2 = zzea.zzg(zzi2);
                            zzb3 = zze2 + zzg2 + zzi2;
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 42:
                        zzi2 = zzgr.zzj((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            zze2 = zzea.zze(i8);
                            zzg2 = zzea.zzg(zzi2);
                            zzb3 = zze2 + zzg2 + zzi2;
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 43:
                        zzi2 = zzgr.zzf((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            zze2 = zzea.zze(i8);
                            zzg2 = zzea.zzg(zzi2);
                            zzb3 = zze2 + zzg2 + zzi2;
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 44:
                        zzi2 = zzgr.zzd((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            zze2 = zzea.zze(i8);
                            zzg2 = zzea.zzg(zzi2);
                            zzb3 = zze2 + zzg2 + zzi2;
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 45:
                        zzi2 = zzgr.zzh((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            zze2 = zzea.zze(i8);
                            zzg2 = zzea.zzg(zzi2);
                            zzb3 = zze2 + zzg2 + zzi2;
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 46:
                        zzi2 = zzgr.zzi((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            zze2 = zzea.zze(i8);
                            zzg2 = zzea.zzg(zzi2);
                            zzb3 = zze2 + zzg2 + zzi2;
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 47:
                        zzi2 = zzgr.zzg((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            zze2 = zzea.zze(i8);
                            zzg2 = zzea.zzg(zzi2);
                            zzb3 = zze2 + zzg2 + zzi2;
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 48:
                        zzi2 = zzgr.zzc((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            zze2 = zzea.zze(i8);
                            zzg2 = zzea.zzg(zzi2);
                            zzb3 = zze2 + zzg2 + zzi2;
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 49:
                        zzb3 = zzgr.zzb(i8, (List<zzfz>) zza(t, j2), zza(i5));
                        i6 += zzb3;
                        break;
                    case 50:
                        zzb3 = this.zzs.zza(i8, zzhn.zzf(t, j2), zzb(i5));
                        i6 += zzb3;
                        break;
                    case 51:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzb(i8, 0.0d);
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 52:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzb(i8, 0.0f);
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 53:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzd(i8, zze(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 54:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zze(i8, zze(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 55:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzf(i8, zzd(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 56:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzg(i8, 0L);
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 57:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzi(i8, 0);
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 58:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzb(i8, true);
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 59:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            Object zzf2 = zzhn.zzf(t, j2);
                            if (zzf2 instanceof zzdn) {
                                zzb3 = zzea.zzc(i8, (zzdn) zzf2);
                            } else {
                                zzb3 = zzea.zzb(i8, (String) zzf2);
                            }
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 60:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzgr.zza(i8, zzhn.zzf(t, j2), zza(i5));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 61:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzc(i8, (zzdn) zzhn.zzf(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 62:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzg(i8, zzd(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 63:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzk(i8, zzd(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 64:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzj(i8, 0);
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 65:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzh(i8, 0L);
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 66:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzh(i8, zzd(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 67:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzf(i8, zze(t, j2));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                    case 68:
                        if (zza((zzgd<T>) t, i8, i5)) {
                            zzb3 = zzea.zzc(i8, (zzfz) zzhn.zzf(t, j2), zza(i5));
                            i6 += zzb3;
                            break;
                        } else {
                            break;
                        }
                }
                i5 += 3;
                i2 = 267386880;
            }
            return i6 + zza((zzhh) this.zzq, (Object) t);
        }
        Unsafe unsafe2 = zzb;
        int i10 = 0;
        int i11 = 0;
        int i12 = 1048575;
        int i13 = 0;
        while (i10 < this.zzc.length) {
            int zzc2 = zzc(i10);
            int[] iArr = this.zzc;
            int i14 = iArr[i10];
            int i15 = (zzc2 & 267386880) >>> 20;
            if (i15 <= 17) {
                int i16 = iArr[i10 + 2];
                int i17 = i16 & i3;
                i = i4 << (i16 >>> 20);
                if (i17 != i12) {
                    i13 = unsafe2.getInt(t, i17);
                    i12 = i17;
                }
            } else {
                i = 0;
            }
            long j3 = zzc2 & i3;
            switch (i15) {
                case 0:
                    j = 0;
                    if ((i13 & i) != 0) {
                        i11 += zzea.zzb(i14, 0.0d);
                        break;
                    }
                    break;
                case 1:
                    j = 0;
                    if ((i13 & i) != 0) {
                        i11 += zzea.zzb(i14, 0.0f);
                    }
                    break;
                case 2:
                    j = 0;
                    if ((i & i13) != 0) {
                        zzd = zzea.zzd(i14, unsafe2.getLong(t, j3));
                        i11 += zzd;
                    }
                    break;
                case 3:
                    j = 0;
                    if ((i & i13) != 0) {
                        zzd = zzea.zze(i14, unsafe2.getLong(t, j3));
                        i11 += zzd;
                    }
                    break;
                case 4:
                    j = 0;
                    if ((i & i13) != 0) {
                        zzd = zzea.zzf(i14, unsafe2.getInt(t, j3));
                        i11 += zzd;
                    }
                    break;
                case 5:
                    j = 0;
                    if ((i13 & i) != 0) {
                        zzd = zzea.zzg(i14, 0L);
                        i11 += zzd;
                    }
                    break;
                case 6:
                    if ((i13 & i) != 0) {
                        i11 += zzea.zzi(i14, 0);
                    }
                    j = 0;
                    break;
                case 7:
                    if ((i13 & i) != 0) {
                        i11 += zzea.zzb(i14, true);
                        j = 0;
                        break;
                    }
                    j = 0;
                case 8:
                    if ((i13 & i) != 0) {
                        Object object = unsafe2.getObject(t, j3);
                        if (object instanceof zzdn) {
                            zzb2 = zzea.zzc(i14, (zzdn) object);
                        } else {
                            zzb2 = zzea.zzb(i14, (String) object);
                        }
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 9:
                    if ((i13 & i) != 0) {
                        zzb2 = zzgr.zza(i14, unsafe2.getObject(t, j3), zza(i10));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 10:
                    if ((i13 & i) != 0) {
                        zzb2 = zzea.zzc(i14, (zzdn) unsafe2.getObject(t, j3));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 11:
                    if ((i13 & i) != 0) {
                        zzb2 = zzea.zzg(i14, unsafe2.getInt(t, j3));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 12:
                    if ((i13 & i) != 0) {
                        zzb2 = zzea.zzk(i14, unsafe2.getInt(t, j3));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 13:
                    if ((i13 & i) != 0) {
                        zzj = zzea.zzj(i14, 0);
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 14:
                    if ((i13 & i) != 0) {
                        zzb2 = zzea.zzh(i14, 0L);
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 15:
                    if ((i13 & i) != 0) {
                        zzb2 = zzea.zzh(i14, unsafe2.getInt(t, j3));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 16:
                    if ((i13 & i) != 0) {
                        zzb2 = zzea.zzf(i14, unsafe2.getLong(t, j3));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 17:
                    if ((i13 & i) != 0) {
                        zzb2 = zzea.zzc(i14, (zzfz) unsafe2.getObject(t, j3), zza(i10));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 18:
                    zzb2 = zzgr.zzi(i14, (List) unsafe2.getObject(t, j3), false);
                    i11 += zzb2;
                    j = 0;
                    break;
                case 19:
                    zzh = zzgr.zzh(i14, (List) unsafe2.getObject(t, j3), false);
                    i11 += zzh;
                    j = 0;
                    break;
                case 20:
                    zzh = zzgr.zza(i14, (List<Long>) unsafe2.getObject(t, j3), false);
                    i11 += zzh;
                    j = 0;
                    break;
                case 21:
                    zzh = zzgr.zzb(i14, (List<Long>) unsafe2.getObject(t, j3), false);
                    i11 += zzh;
                    j = 0;
                    break;
                case 22:
                    zzh = zzgr.zze(i14, (List) unsafe2.getObject(t, j3), false);
                    i11 += zzh;
                    j = 0;
                    break;
                case 23:
                    zzh = zzgr.zzi(i14, (List) unsafe2.getObject(t, j3), false);
                    i11 += zzh;
                    j = 0;
                    break;
                case 24:
                    zzh = zzgr.zzh(i14, (List) unsafe2.getObject(t, j3), false);
                    i11 += zzh;
                    j = 0;
                    break;
                case 25:
                    zzh = zzgr.zzj(i14, (List) unsafe2.getObject(t, j3), false);
                    i11 += zzh;
                    j = 0;
                    break;
                case 26:
                    zzb2 = zzgr.zza(i14, (List<?>) unsafe2.getObject(t, j3));
                    i11 += zzb2;
                    j = 0;
                    break;
                case 27:
                    zzb2 = zzgr.zza(i14, (List<?>) unsafe2.getObject(t, j3), zza(i10));
                    i11 += zzb2;
                    j = 0;
                    break;
                case 28:
                    zzb2 = zzgr.zzb(i14, (List) unsafe2.getObject(t, j3));
                    i11 += zzb2;
                    j = 0;
                    break;
                case 29:
                    zzb2 = zzgr.zzf(i14, (List) unsafe2.getObject(t, j3), false);
                    i11 += zzb2;
                    j = 0;
                    break;
                case 30:
                    zzh = zzgr.zzd(i14, (List) unsafe2.getObject(t, j3), false);
                    i11 += zzh;
                    j = 0;
                    break;
                case 31:
                    zzh = zzgr.zzh(i14, (List) unsafe2.getObject(t, j3), false);
                    i11 += zzh;
                    j = 0;
                    break;
                case 32:
                    zzh = zzgr.zzi(i14, (List) unsafe2.getObject(t, j3), false);
                    i11 += zzh;
                    j = 0;
                    break;
                case 33:
                    zzh = zzgr.zzg(i14, (List) unsafe2.getObject(t, j3), false);
                    i11 += zzh;
                    j = 0;
                    break;
                case 34:
                    zzh = zzgr.zzc(i14, (List) unsafe2.getObject(t, j3), false);
                    i11 += zzh;
                    j = 0;
                    break;
                case 35:
                    zzi = zzgr.zzi((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        zze = zzea.zze(i14);
                        zzg = zzea.zzg(zzi);
                        zzj = zze + zzg + zzi;
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 36:
                    zzi = zzgr.zzh((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        zze = zzea.zze(i14);
                        zzg = zzea.zzg(zzi);
                        zzj = zze + zzg + zzi;
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 37:
                    zzi = zzgr.zza((List<Long>) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        zze = zzea.zze(i14);
                        zzg = zzea.zzg(zzi);
                        zzj = zze + zzg + zzi;
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 38:
                    zzi = zzgr.zzb((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        zze = zzea.zze(i14);
                        zzg = zzea.zzg(zzi);
                        zzj = zze + zzg + zzi;
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 39:
                    zzi = zzgr.zze((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        zze = zzea.zze(i14);
                        zzg = zzea.zzg(zzi);
                        zzj = zze + zzg + zzi;
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 40:
                    zzi = zzgr.zzi((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        zze = zzea.zze(i14);
                        zzg = zzea.zzg(zzi);
                        zzj = zze + zzg + zzi;
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 41:
                    zzi = zzgr.zzh((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        zze = zzea.zze(i14);
                        zzg = zzea.zzg(zzi);
                        zzj = zze + zzg + zzi;
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 42:
                    zzi = zzgr.zzj((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        zze = zzea.zze(i14);
                        zzg = zzea.zzg(zzi);
                        zzj = zze + zzg + zzi;
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 43:
                    zzi = zzgr.zzf((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        zze = zzea.zze(i14);
                        zzg = zzea.zzg(zzi);
                        zzj = zze + zzg + zzi;
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 44:
                    zzi = zzgr.zzd((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        zze = zzea.zze(i14);
                        zzg = zzea.zzg(zzi);
                        zzj = zze + zzg + zzi;
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 45:
                    zzi = zzgr.zzh((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        zze = zzea.zze(i14);
                        zzg = zzea.zzg(zzi);
                        zzj = zze + zzg + zzi;
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 46:
                    zzi = zzgr.zzi((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        zze = zzea.zze(i14);
                        zzg = zzea.zzg(zzi);
                        zzj = zze + zzg + zzi;
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 47:
                    zzi = zzgr.zzg((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        zze = zzea.zze(i14);
                        zzg = zzea.zzg(zzi);
                        zzj = zze + zzg + zzi;
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 48:
                    zzi = zzgr.zzc((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        zze = zzea.zze(i14);
                        zzg = zzea.zzg(zzi);
                        zzj = zze + zzg + zzi;
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 49:
                    zzb2 = zzgr.zzb(i14, (List<zzfz>) unsafe2.getObject(t, j3), zza(i10));
                    i11 += zzb2;
                    j = 0;
                    break;
                case 50:
                    zzb2 = this.zzs.zza(i14, unsafe2.getObject(t, j3), zzb(i10));
                    i11 += zzb2;
                    j = 0;
                    break;
                case 51:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzb2 = zzea.zzb(i14, 0.0d);
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 52:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzj = zzea.zzb(i14, 0.0f);
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 53:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzb2 = zzea.zzd(i14, zze(t, j3));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 54:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzb2 = zzea.zze(i14, zze(t, j3));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 55:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzb2 = zzea.zzf(i14, zzd(t, j3));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 56:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzb2 = zzea.zzg(i14, 0L);
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 57:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzj = zzea.zzi(i14, 0);
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 58:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzj = zzea.zzb(i14, true);
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 59:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        Object object2 = unsafe2.getObject(t, j3);
                        if (object2 instanceof zzdn) {
                            zzb2 = zzea.zzc(i14, (zzdn) object2);
                        } else {
                            zzb2 = zzea.zzb(i14, (String) object2);
                        }
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 60:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzb2 = zzgr.zza(i14, unsafe2.getObject(t, j3), zza(i10));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 61:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzb2 = zzea.zzc(i14, (zzdn) unsafe2.getObject(t, j3));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 62:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzb2 = zzea.zzg(i14, zzd(t, j3));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 63:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzb2 = zzea.zzk(i14, zzd(t, j3));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 64:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzj = zzea.zzj(i14, 0);
                        i11 += zzj;
                    }
                    j = 0;
                    break;
                case 65:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzb2 = zzea.zzh(i14, 0L);
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 66:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzb2 = zzea.zzh(i14, zzd(t, j3));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 67:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzb2 = zzea.zzf(i14, zze(t, j3));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                case 68:
                    if (zza((zzgd<T>) t, i14, i10)) {
                        zzb2 = zzea.zzc(i14, (zzfz) unsafe2.getObject(t, j3), zza(i10));
                        i11 += zzb2;
                    }
                    j = 0;
                    break;
                default:
                    j = 0;
                    break;
            }
            i10 += 3;
            i3 = 1048575;
            i4 = 1;
        }
        int i18 = 0;
        int zza2 = i11 + zza((zzhh) this.zzq, (Object) t);
        if (!this.zzh) {
            return zza2;
        }
        zzej<?> zza3 = this.zzr.zza(t);
        for (int i19 = 0; i19 < zza3.zza.zzc(); i19++) {
            Map.Entry<?, Object> zzb4 = zza3.zza.zzb(i19);
            i18 += zzej.zza((zzel<?>) zzb4.getKey(), zzb4.getValue());
        }
        for (Map.Entry<?, Object> entry : zza3.zza.zzd()) {
            i18 += zzej.zza((zzel<?>) entry.getKey(), entry.getValue());
        }
        return zza2 + i18;
    }

    private static <UT, UB> int zza(zzhh<UT, UB> zzhhVar, T t) {
        return zzhhVar.zzd(zzhhVar.zza(t));
    }

    private static List<?> zza(Object obj, long j) {
        return (List) zzhn.zzf(obj, j);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0513  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x0552  */
    /* JADX WARN: Removed duplicated region for block: B:566:0x0a2a  */
    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r14, com.google.android.gms.internal.mlkit_language_id.zzib r15) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 2916
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.mlkit_language_id.zzgd.zza(java.lang.Object, com.google.android.gms.internal.mlkit_language_id.zzib):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:216:0x0495  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void zzb(T r18, com.google.android.gms.internal.mlkit_language_id.zzib r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.mlkit_language_id.zzgd.zzb(java.lang.Object, com.google.android.gms.internal.mlkit_language_id.zzib):void");
    }

    private final <K, V> void zza(zzib zzibVar, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzibVar.zza(i, this.zzs.zzc(zzb(i2)), this.zzs.zza(obj));
        }
    }

    private static <UT, UB> void zza(zzhh<UT, UB> zzhhVar, T t, zzib zzibVar) throws IOException {
        zzhhVar.zza((zzhh<UT, UB>) zzhhVar.zza(t), zzibVar);
    }

    private final zzgp zza(int i) {
        int i2 = (i / 3) << 1;
        zzgp zzgpVar = (zzgp) this.zzd[i2];
        if (zzgpVar != null) {
            return zzgpVar;
        }
        zzgp<T> zza2 = zzgk.zza().zza((Class) this.zzd[i2 + 1]);
        this.zzd[i2] = zza2;
        return zza2;
    }

    private final Object zzb(int i) {
        return this.zzd[(i / 3) << 1];
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final void zzb(T t) {
        int i;
        int i2 = this.zzm;
        while (true) {
            i = this.zzn;
            if (i2 >= i) {
                break;
            }
            long zzc = zzc(this.zzl[i2]) & 1048575;
            Object zzf = zzhn.zzf(t, zzc);
            if (zzf != null) {
                zzhn.zza(t, zzc, this.zzs.zzb(zzf));
            }
            i2++;
        }
        int length = this.zzl.length;
        while (i < length) {
            this.zzp.zza(t, this.zzl[i]);
            i++;
        }
        this.zzq.zzb(t);
        if (this.zzh) {
            this.zzr.zzc(t);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.mlkit_language_id.zzgp
    public final boolean zzc(T t) {
        int i;
        int i2;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            boolean z = true;
            if (i5 >= this.zzm) {
                return !this.zzh || this.zzr.zza(t).zzf();
            }
            int i6 = this.zzl[i5];
            int i7 = this.zzc[i6];
            int zzc = zzc(i6);
            int i8 = this.zzc[i6 + 2];
            int i9 = i8 & 1048575;
            int i10 = 1 << (i8 >>> 20);
            if (i9 != i3) {
                if (i9 != 1048575) {
                    i4 = zzb.getInt(t, i9);
                }
                i2 = i4;
                i = i9;
            } else {
                i = i3;
                i2 = i4;
            }
            if (((268435456 & zzc) != 0) && !zza(t, i6, i, i2, i10)) {
                return false;
            }
            int i11 = (267386880 & zzc) >>> 20;
            if (i11 == 9 || i11 == 17) {
                if (zza(t, i6, i, i2, i10) && !zza(t, zzc, zza(i6))) {
                    return false;
                }
            } else {
                if (i11 != 27) {
                    if (i11 == 60 || i11 == 68) {
                        if (zza((zzgd<T>) t, i7, i6) && !zza(t, zzc, zza(i6))) {
                            return false;
                        }
                    } else if (i11 != 49) {
                        if (i11 == 50 && !this.zzs.zza(zzhn.zzf(t, zzc & 1048575)).isEmpty()) {
                            this.zzs.zzc(zzb(i6));
                            throw null;
                        }
                    }
                }
                List list = (List) zzhn.zzf(t, zzc & 1048575);
                if (!list.isEmpty()) {
                    zzgp zza2 = zza(i6);
                    int i12 = 0;
                    while (true) {
                        if (i12 >= list.size()) {
                            break;
                        }
                        if (!zza2.zzc(list.get(i12))) {
                            z = false;
                            break;
                        }
                        i12++;
                    }
                }
                if (!z) {
                    return false;
                }
            }
            i5++;
            i3 = i;
            i4 = i2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i, zzgp zzgpVar) {
        return zzgpVar.zzc(zzhn.zzf(obj, i & 1048575));
    }

    private static void zza(int i, Object obj, zzib zzibVar) throws IOException {
        if (obj instanceof String) {
            zzibVar.zza(i, (String) obj);
        } else {
            zzibVar.zza(i, (zzdn) obj);
        }
    }

    private final int zzc(int i) {
        return this.zzc[i + 1];
    }

    private final int zzd(int i) {
        return this.zzc[i + 2];
    }

    private static <T> double zzb(T t, long j) {
        return ((Double) zzhn.zzf(t, j)).doubleValue();
    }

    private static <T> float zzc(T t, long j) {
        return ((Float) zzhn.zzf(t, j)).floatValue();
    }

    private static <T> int zzd(T t, long j) {
        return ((Integer) zzhn.zzf(t, j)).intValue();
    }

    private static <T> long zze(T t, long j) {
        return ((Long) zzhn.zzf(t, j)).longValue();
    }

    private static <T> boolean zzf(T t, long j) {
        return ((Boolean) zzhn.zzf(t, j)).booleanValue();
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza((zzgd<T>) t, i) == zza((zzgd<T>) t2, i);
    }

    private final boolean zza(T t, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zza((zzgd<T>) t, i);
        }
        return (i3 & i4) != 0;
    }

    private final boolean zza(T t, int i) {
        int zzd = zzd(i);
        long j = zzd & 1048575;
        if (j != 1048575) {
            return (zzhn.zza(t, j) & (1 << (zzd >>> 20))) != 0;
        }
        int zzc = zzc(i);
        long j2 = zzc & 1048575;
        switch ((zzc & 267386880) >>> 20) {
            case 0:
                return zzhn.zze(t, j2) != 0.0d;
            case 1:
                return zzhn.zzd(t, j2) != 0.0f;
            case 2:
                return zzhn.zzb(t, j2) != 0;
            case 3:
                return zzhn.zzb(t, j2) != 0;
            case 4:
                return zzhn.zza(t, j2) != 0;
            case 5:
                return zzhn.zzb(t, j2) != 0;
            case 6:
                return zzhn.zza(t, j2) != 0;
            case 7:
                return zzhn.zzc(t, j2);
            case 8:
                Object zzf = zzhn.zzf(t, j2);
                if (zzf instanceof String) {
                    return !((String) zzf).isEmpty();
                }
                if (zzf instanceof zzdn) {
                    return !zzdn.zza.equals(zzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzhn.zzf(t, j2) != null;
            case 10:
                return !zzdn.zza.equals(zzhn.zzf(t, j2));
            case 11:
                return zzhn.zza(t, j2) != 0;
            case 12:
                return zzhn.zza(t, j2) != 0;
            case 13:
                return zzhn.zza(t, j2) != 0;
            case 14:
                return zzhn.zzb(t, j2) != 0;
            case 15:
                return zzhn.zza(t, j2) != 0;
            case 16:
                return zzhn.zzb(t, j2) != 0;
            case 17:
                return zzhn.zzf(t, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final void zzb(T t, int i) {
        int zzd = zzd(i);
        long j = 1048575 & zzd;
        if (j == 1048575) {
            return;
        }
        zzhn.zza((Object) t, j, (1 << (zzd >>> 20)) | zzhn.zza(t, j));
    }

    private final boolean zza(T t, int i, int i2) {
        return zzhn.zza(t, (long) (zzd(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zzhn.zza((Object) t, zzd(i2) & 1048575, i);
    }
}
