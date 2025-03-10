package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzjb;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
/* loaded from: classes.dex */
public final class zzfi$zze extends zzjb<zzfi$zze, zza> implements zzkm {
    private static final zzfi$zze zzl;
    private static volatile zzkx<zzfi$zze> zzm;
    private int zzc;
    private boolean zze;
    private int zzf;
    private long zzg;
    private long zzh;
    private long zzi;
    private boolean zzk;
    private String zzd = "";
    private String zzj = "";

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public enum zzb implements zzje {
        REASON_UNKNOWN(0),
        REASON_MISSING(1),
        REASON_UPGRADE(2),
        REASON_INVALID(3);

        private final int zzf;

        @Override // com.google.android.gms.internal.vision.zzje
        public final int zza() {
            return this.zzf;
        }

        public static zzb zza(int i) {
            if (i == 0) {
                return REASON_UNKNOWN;
            }
            if (i == 1) {
                return REASON_MISSING;
            }
            if (i == 2) {
                return REASON_UPGRADE;
            }
            if (i != 3) {
                return null;
            }
            return REASON_INVALID;
        }

        public static zzjg zzb() {
            return zzfn.zza;
        }

        @Override // java.lang.Enum
        public final String toString() {
            return "<" + zzb.class.getName() + '@' + Integer.toHexString(System.identityHashCode(this)) + " number=" + this.zzf + " name=" + name() + '>';
        }

        zzb(int i) {
            this.zzf = i;
        }

        static {
            new zzfm();
        }
    }

    private zzfi$zze() {
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static final class zza extends zzjb.zzb<zzfi$zze, zza> implements zzkm {
        private zza() {
            super(zzfi$zze.zzl);
        }

        /* synthetic */ zza(zzfk zzfkVar) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.vision.zzjb$zza, com.google.android.gms.internal.vision.zzkx<com.google.android.gms.internal.vision.zzfi$zze>] */
    @Override // com.google.android.gms.internal.vision.zzjb
    protected final Object zza(int i, Object obj, Object obj2) {
        zzkx<zzfi$zze> zzkxVar;
        zzfk zzfkVar = null;
        switch (zzfk.zza[i - 1]) {
            case 1:
                return new zzfi$zze();
            case 2:
                return new zza(zzfkVar);
            case 3:
                return zzjb.zza(zzl, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဇ\u0001\u0003ဌ\u0002\u0004ဂ\u0003\u0005ဂ\u0004\u0006ဂ\u0005\u0007ဈ\u0006\bဇ\u0007", new Object[]{"zzc", "zzd", "zze", "zzf", zzb.zzb(), "zzg", "zzh", "zzi", "zzj", "zzk"});
            case 4:
                return zzl;
            case 5:
                zzkx<zzfi$zze> zzkxVar2 = zzm;
                zzkx<zzfi$zze> zzkxVar3 = zzkxVar2;
                if (zzkxVar2 == null) {
                    synchronized (zzfi$zze.class) {
                        zzkx<zzfi$zze> zzkxVar4 = zzm;
                        zzkxVar = zzkxVar4;
                        if (zzkxVar4 == null) {
                            ?? zzaVar = new zzjb.zza(zzl);
                            zzm = zzaVar;
                            zzkxVar = zzaVar;
                        }
                    }
                    zzkxVar3 = zzkxVar;
                }
                return zzkxVar3;
            case 6:
                return (byte) 1;
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    static {
        zzfi$zze zzfi_zze = new zzfi$zze();
        zzl = zzfi_zze;
        zzjb.zza((Class<zzfi$zze>) zzfi$zze.class, zzfi_zze);
    }
}
