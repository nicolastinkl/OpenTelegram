package com.google.android.gms.internal.mlkit_language_id;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'zzc' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* compiled from: com.google.mlkit:language-id@@16.1.1 */
/* loaded from: classes.dex */
public class zzhv {
    public static final zzhv zza;
    public static final zzhv zzb;
    public static final zzhv zzc;
    public static final zzhv zzd;
    public static final zzhv zze;
    public static final zzhv zzf;
    public static final zzhv zzg;
    public static final zzhv zzh;
    public static final zzhv zzi;
    public static final zzhv zzj;
    public static final zzhv zzk;
    public static final zzhv zzl;
    public static final zzhv zzm;
    public static final zzhv zzn;
    public static final zzhv zzo;
    public static final zzhv zzp;
    public static final zzhv zzq;
    public static final zzhv zzr;
    private static final /* synthetic */ zzhv[] zzu;
    private final zzhy zzs;
    private final int zzt;

    public static zzhv[] values() {
        return (zzhv[]) zzu.clone();
    }

    private zzhv(String str, int i, zzhy zzhyVar, int i2) {
        this.zzs = zzhyVar;
        this.zzt = i2;
    }

    public final zzhy zza() {
        return this.zzs;
    }

    static {
        zzhv zzhvVar = new zzhv("DOUBLE", 0, zzhy.DOUBLE, 1);
        zza = zzhvVar;
        zzhv zzhvVar2 = new zzhv("FLOAT", 1, zzhy.FLOAT, 5);
        zzb = zzhvVar2;
        zzhy zzhyVar = zzhy.LONG;
        final int i = 2;
        zzhv zzhvVar3 = new zzhv("INT64", 2, zzhyVar, 0);
        zzc = zzhvVar3;
        final int i2 = 3;
        zzhv zzhvVar4 = new zzhv("UINT64", 3, zzhyVar, 0);
        zzd = zzhvVar4;
        zzhy zzhyVar2 = zzhy.INT;
        zzhv zzhvVar5 = new zzhv("INT32", 4, zzhyVar2, 0);
        zze = zzhvVar5;
        zzhv zzhvVar6 = new zzhv("FIXED64", 5, zzhyVar, 1);
        zzf = zzhvVar6;
        zzhv zzhvVar7 = new zzhv("FIXED32", 6, zzhyVar2, 5);
        zzg = zzhvVar7;
        zzhv zzhvVar8 = new zzhv("BOOL", 7, zzhy.BOOLEAN, 0);
        zzh = zzhvVar8;
        final zzhy zzhyVar3 = zzhy.STRING;
        final String str = "STRING";
        final int i3 = 8;
        zzhv zzhvVar9 = new zzhv(str, i3, zzhyVar3, i) { // from class: com.google.android.gms.internal.mlkit_language_id.zzhu
            {
                int i4 = 8;
                int i5 = 2;
            }
        };
        zzi = zzhvVar9;
        final zzhy zzhyVar4 = zzhy.MESSAGE;
        final String str2 = "GROUP";
        final int i4 = 9;
        zzhv zzhvVar10 = new zzhv(str2, i4, zzhyVar4, i2) { // from class: com.google.android.gms.internal.mlkit_language_id.zzhx
            {
                int i5 = 9;
                int i6 = 3;
            }
        };
        zzj = zzhvVar10;
        final String str3 = "MESSAGE";
        final int i5 = 10;
        final int i6 = 2;
        zzhv zzhvVar11 = new zzhv(str3, i5, zzhyVar4, i6) { // from class: com.google.android.gms.internal.mlkit_language_id.zzhw
            {
                int i7 = 10;
                int i8 = 2;
            }
        };
        zzk = zzhvVar11;
        final zzhy zzhyVar5 = zzhy.BYTE_STRING;
        final String str4 = "BYTES";
        final int i7 = 11;
        zzhv zzhvVar12 = new zzhv(str4, i7, zzhyVar5, i6) { // from class: com.google.android.gms.internal.mlkit_language_id.zzhz
            {
                int i8 = 11;
                int i9 = 2;
            }
        };
        zzl = zzhvVar12;
        zzhv zzhvVar13 = new zzhv("UINT32", 12, zzhyVar2, 0);
        zzm = zzhvVar13;
        zzhv zzhvVar14 = new zzhv("ENUM", 13, zzhy.ENUM, 0);
        zzn = zzhvVar14;
        zzhv zzhvVar15 = new zzhv("SFIXED32", 14, zzhyVar2, 5);
        zzo = zzhvVar15;
        zzhv zzhvVar16 = new zzhv("SFIXED64", 15, zzhyVar, 1);
        zzp = zzhvVar16;
        zzhv zzhvVar17 = new zzhv("SINT32", 16, zzhyVar2, 0);
        zzq = zzhvVar17;
        zzhv zzhvVar18 = new zzhv("SINT64", 17, zzhyVar, 0);
        zzr = zzhvVar18;
        zzu = new zzhv[]{zzhvVar, zzhvVar2, zzhvVar3, zzhvVar4, zzhvVar5, zzhvVar6, zzhvVar7, zzhvVar8, zzhvVar9, zzhvVar10, zzhvVar11, zzhvVar12, zzhvVar13, zzhvVar14, zzhvVar15, zzhvVar16, zzhvVar17, zzhvVar18};
    }
}
