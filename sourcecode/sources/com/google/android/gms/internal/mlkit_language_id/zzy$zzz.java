package com.google.android.gms.internal.mlkit_language_id;

import com.google.android.gms.internal.mlkit_language_id.zzeo;

/* compiled from: com.google.mlkit:language-id@@16.1.1 */
/* loaded from: classes.dex */
public final class zzy$zzz extends zzeo<zzy$zzz, zza> implements zzgb {
    private static final zzex<Integer, zzai> zzg = new zzag();
    private static final zzy$zzz zzj;
    private static volatile zzgj<zzy$zzz> zzk;
    private int zzc;
    private zzy$zzam zzd;
    private zzy$zzam zze;
    private zzeu zzf = zzeo.zzk();
    private long zzh;
    private boolean zzi;

    private zzy$zzz() {
    }

    /* compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static final class zza extends zzeo.zzb<zzy$zzz, zza> implements zzgb {
        private zza() {
            super(zzy$zzz.zzj);
        }

        /* synthetic */ zza(zzx zzxVar) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.mlkit_language_id.zzeo$zza, com.google.android.gms.internal.mlkit_language_id.zzgj<com.google.android.gms.internal.mlkit_language_id.zzy$zzz>] */
    @Override // com.google.android.gms.internal.mlkit_language_id.zzeo
    protected final Object zza(int i, Object obj, Object obj2) {
        zzgj<zzy$zzz> zzgjVar;
        zzx zzxVar = null;
        switch (zzx.zza[i - 1]) {
            case 1:
                return new zzy$zzz();
            case 2:
                return new zza(zzxVar);
            case 3:
                return zzeo.zza(zzj, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u001e\u0004ဃ\u0002\u0005ဇ\u0003", new Object[]{"zzc", "zzd", "zze", "zzf", zzai.zzb(), "zzh", "zzi"});
            case 4:
                return zzj;
            case 5:
                zzgj<zzy$zzz> zzgjVar2 = zzk;
                zzgj<zzy$zzz> zzgjVar3 = zzgjVar2;
                if (zzgjVar2 == null) {
                    synchronized (zzy$zzz.class) {
                        zzgj<zzy$zzz> zzgjVar4 = zzk;
                        zzgjVar = zzgjVar4;
                        if (zzgjVar4 == null) {
                            ?? zzaVar = new zzeo.zza(zzj);
                            zzk = zzaVar;
                            zzgjVar = zzaVar;
                        }
                    }
                    zzgjVar3 = zzgjVar;
                }
                return zzgjVar3;
            case 6:
                return (byte) 1;
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_language_id.zzag, com.google.android.gms.internal.mlkit_language_id.zzex<java.lang.Integer, com.google.android.gms.internal.mlkit_language_id.zzai>] */
    static {
        zzy$zzz zzy_zzz = new zzy$zzz();
        zzj = zzy_zzz;
        zzeo.zza((Class<zzy$zzz>) zzy$zzz.class, zzy_zzz);
    }
}
