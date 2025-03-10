package com.google.android.gms.internal.clearcut;

import com.google.android.gms.internal.clearcut.zzcg;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class zzgw$zza extends zzcg<zzgw$zza, zza> implements zzdq {
    private static volatile zzdz<zzgw$zza> zzbg;
    private static final zzgw$zza zzbir;
    private zzcn<zzb> zzbiq = zzcg.zzbb();

    public static final class zza extends zzcg.zza<zzgw$zza, zza> implements zzdq {
        private zza() {
            super(zzgw$zza.zzbir);
        }

        /* synthetic */ zza(zzgx zzgxVar) {
            this();
        }
    }

    public static final class zzb extends zzcg<zzb, zza> implements zzdq {
        private static volatile zzdz<zzb> zzbg;
        private static final zzb zzbiv;
        private int zzbb;
        private String zzbis = "";
        private long zzbit;
        private long zzbiu;
        private int zzya;

        public static final class zza extends zzcg.zza<zzb, zza> implements zzdq {
            private zza() {
                super(zzb.zzbiv);
            }

            /* synthetic */ zza(zzgx zzgxVar) {
                this();
            }

            public final zza zzn(String str) {
                zzbf();
                ((zzb) this.zzjt).zzm(str);
                return this;
            }

            public final zza zzr(long j) {
                zzbf();
                ((zzb) this.zzjt).zzp(j);
                return this;
            }

            public final zza zzs(long j) {
                zzbf();
                ((zzb) this.zzjt).zzq(j);
                return this;
            }
        }

        static {
            zzb zzbVar = new zzb();
            zzbiv = zzbVar;
            zzcg.zza((Class<zzb>) zzb.class, zzbVar);
        }

        private zzb() {
        }

        public static zza zzfz() {
            return (zza) ((zzcg.zza) zzbiv.zza(zzcg.zzg.zzkh, (Object) null, (Object) null));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzm(String str) {
            Objects.requireNonNull(str);
            this.zzbb |= 2;
            this.zzbis = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzp(long j) {
            this.zzbb |= 4;
            this.zzbit = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzq(long j) {
            this.zzbb |= 8;
            this.zzbiu = j;
        }

        public final int getEventCode() {
            return this.zzya;
        }

        /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.clearcut.zzcg$zzb, com.google.android.gms.internal.clearcut.zzdz<com.google.android.gms.internal.clearcut.zzgw$zza$zzb>] */
        @Override // com.google.android.gms.internal.clearcut.zzcg
        protected final Object zza(int i, Object obj, Object obj2) {
            zzdz<zzb> zzdzVar;
            zzgx zzgxVar = null;
            switch (zzgx.zzba[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzgxVar);
                case 3:
                    return zzcg.zza(zzbiv, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0005\u0000\u0000\u0000\u0001\u0004\u0000\u0002\b\u0001\u0003\u0002\u0002\u0004\u0002\u0003", new Object[]{"zzbb", "zzya", "zzbis", "zzbit", "zzbiu"});
                case 4:
                    return zzbiv;
                case 5:
                    zzdz<zzb> zzdzVar2 = zzbg;
                    zzdz<zzb> zzdzVar3 = zzdzVar2;
                    if (zzdzVar2 == null) {
                        synchronized (zzb.class) {
                            zzdz<zzb> zzdzVar4 = zzbg;
                            zzdzVar = zzdzVar4;
                            if (zzdzVar4 == null) {
                                ?? zzbVar = new zzcg.zzb(zzbiv);
                                zzbg = zzbVar;
                                zzdzVar = zzbVar;
                            }
                        }
                        zzdzVar3 = zzdzVar;
                    }
                    return zzdzVar3;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public final boolean zzfv() {
            return (this.zzbb & 1) == 1;
        }

        public final String zzfw() {
            return this.zzbis;
        }

        public final long zzfx() {
            return this.zzbit;
        }

        public final long zzfy() {
            return this.zzbiu;
        }
    }

    static {
        zzgw$zza zzgw_zza = new zzgw$zza();
        zzbir = zzgw_zza;
        zzcg.zza((Class<zzgw$zza>) zzgw$zza.class, zzgw_zza);
    }

    private zzgw$zza() {
    }

    public static zzgw$zza zzft() {
        return zzbir;
    }

    public static zzgw$zza zzi(byte[] bArr) throws zzco {
        return (zzgw$zza) zzcg.zzb(zzbir, bArr);
    }

    /* JADX WARN: Type inference failed for: r2v14, types: [com.google.android.gms.internal.clearcut.zzcg$zzb, com.google.android.gms.internal.clearcut.zzdz<com.google.android.gms.internal.clearcut.zzgw$zza>] */
    @Override // com.google.android.gms.internal.clearcut.zzcg
    protected final Object zza(int i, Object obj, Object obj2) {
        zzdz<zzgw$zza> zzdzVar;
        zzgx zzgxVar = null;
        switch (zzgx.zzba[i - 1]) {
            case 1:
                return new zzgw$zza();
            case 2:
                return new zza(zzgxVar);
            case 3:
                return zzcg.zza(zzbir, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0002\u0000\u0001\u0000\u0001\u001b", new Object[]{"zzbiq", zzb.class});
            case 4:
                return zzbir;
            case 5:
                zzdz<zzgw$zza> zzdzVar2 = zzbg;
                zzdz<zzgw$zza> zzdzVar3 = zzdzVar2;
                if (zzdzVar2 == null) {
                    synchronized (zzgw$zza.class) {
                        zzdz<zzgw$zza> zzdzVar4 = zzbg;
                        zzdzVar = zzdzVar4;
                        if (zzdzVar4 == null) {
                            ?? zzbVar = new zzcg.zzb(zzbir);
                            zzbg = zzbVar;
                            zzdzVar = zzbVar;
                        }
                    }
                    zzdzVar3 = zzdzVar;
                }
                return zzdzVar3;
            case 6:
                return (byte) 1;
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public final List<zzb> zzfs() {
        return this.zzbiq;
    }
}
