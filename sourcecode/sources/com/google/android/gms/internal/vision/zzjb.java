package com.google.android.gms.internal.vision;

import com.google.android.gms.internal.vision.zzjb;
import com.google.android.gms.internal.vision.zzjb.zzb;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
/* loaded from: classes.dex */
public abstract class zzjb<MessageType extends zzjb<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zzhf<MessageType, BuilderType> {
    private static Map<Object, zzjb<?, ?>> zzd = new ConcurrentHashMap();
    protected zzlx zzb = zzlx.zza();
    private int zzc = -1;

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    protected static class zza<T extends zzjb<T, ?>> extends zzhg<T> {
        public zza(T t) {
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static class zze<ContainingType extends zzkk, Type> extends zzim<ContainingType, Type> {
        final zzkk zzc;
        final zzf zzd;
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public enum zzg {
        public static final int zza = 1;
        public static final int zzb = 2;
        public static final int zzc = 3;
        public static final int zzd = 4;
        public static final int zze = 5;
        public static final int zzf = 6;
        public static final int zzg = 7;
        private static final /* synthetic */ int[] zzh = {1, 2, 3, 4, 5, 6, 7};

        public static int[] zza() {
            return (int[]) zzh.clone();
        }
    }

    protected abstract Object zza(int i, Object obj, Object obj2);

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static abstract class zzc<MessageType extends zzc<MessageType, BuilderType>, BuilderType> extends zzjb<MessageType, BuilderType> implements zzkm {
        protected zziu<zzf> zzc = zziu.zza();

        final zziu<zzf> zza() {
            if (this.zzc.zzc()) {
                this.zzc = (zziu) this.zzc.clone();
            }
            return this.zzc;
        }
    }

    public String toString() {
        return zzkp.zza(this, super.toString());
    }

    public int hashCode() {
        int i = this.zza;
        if (i != 0) {
            return i;
        }
        int zza2 = zzky.zza().zza((zzky) this).zza(this);
        this.zza = zza2;
        return zza2;
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static abstract class zzb<MessageType extends zzjb<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zzhe<MessageType, BuilderType> {
        protected MessageType zza;
        protected boolean zzb = false;
        private final MessageType zzc;

        protected zzb(MessageType messagetype) {
            this.zzc = messagetype;
            this.zza = (MessageType) messagetype.zza(zzg.zzd, null, null);
        }

        protected void zzb() {
            MessageType messagetype = (MessageType) this.zza.zza(zzg.zzd, null, null);
            zza(messagetype, this.zza);
            this.zza = messagetype;
        }

        @Override // com.google.android.gms.internal.vision.zzkn
        /* renamed from: zzc, reason: merged with bridge method [inline-methods] */
        public MessageType zze() {
            if (this.zzb) {
                return this.zza;
            }
            MessageType messagetype = this.zza;
            zzky.zza().zza((zzky) messagetype).zzc(messagetype);
            this.zzb = true;
            return this.zza;
        }

        @Override // com.google.android.gms.internal.vision.zzkn
        /* renamed from: zzd, reason: merged with bridge method [inline-methods] */
        public final MessageType zzf() {
            MessageType messagetype = (MessageType) zze();
            if (messagetype.zzk()) {
                return messagetype;
            }
            throw new zzlv(messagetype);
        }

        @Override // com.google.android.gms.internal.vision.zzhe
        public final BuilderType zza(MessageType messagetype) {
            if (this.zzb) {
                zzb();
                this.zzb = false;
            }
            zza(this.zza, messagetype);
            return this;
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzky.zza().zza((zzky) messagetype).zzb(messagetype, messagetype2);
        }

        private final BuilderType zzb(byte[] bArr, int i, int i2, zzio zzioVar) throws zzjk {
            if (this.zzb) {
                zzb();
                this.zzb = false;
            }
            try {
                zzky.zza().zza((zzky) this.zza).zza(this.zza, bArr, 0, i2, new zzhn(zzioVar));
                return this;
            } catch (zzjk e) {
                throw e;
            } catch (IOException e2) {
                throw new RuntimeException("Reading from byte array should not throw IOException.", e2);
            } catch (IndexOutOfBoundsException unused) {
                throw zzjk.zza();
            }
        }

        @Override // com.google.android.gms.internal.vision.zzhe
        public final /* synthetic */ zzhe zza(byte[] bArr, int i, int i2, zzio zzioVar) throws zzjk {
            return zzb(bArr, 0, i2, zzioVar);
        }

        @Override // com.google.android.gms.internal.vision.zzkm
        public final /* synthetic */ zzkk zzr() {
            return this.zzc;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zzb zzbVar = (zzb) this.zzc.zza(zzg.zze, null, null);
            zzbVar.zza((zzb) zze());
            return zzbVar;
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    static final class zzf implements zziw<zzf> {
        final int zzb;
        final zzml zzc;
        final boolean zzd;

        @Override // com.google.android.gms.internal.vision.zziw
        public final boolean zze() {
            return false;
        }

        @Override // com.google.android.gms.internal.vision.zziw
        public final int zza() {
            return this.zzb;
        }

        @Override // com.google.android.gms.internal.vision.zziw
        public final zzml zzb() {
            return this.zzc;
        }

        @Override // com.google.android.gms.internal.vision.zziw
        public final zzmo zzc() {
            return this.zzc.zza();
        }

        @Override // com.google.android.gms.internal.vision.zziw
        public final boolean zzd() {
            return this.zzd;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.internal.vision.zziw
        public final zzkn zza(zzkn zzknVar, zzkk zzkkVar) {
            return ((zzb) zzknVar).zza((zzb) zzkkVar);
        }

        @Override // com.google.android.gms.internal.vision.zziw
        public final zzkt zza(zzkt zzktVar, zzkt zzktVar2) {
            throw new UnsupportedOperationException();
        }

        @Override // java.lang.Comparable
        public final /* synthetic */ int compareTo(Object obj) {
            return this.zzb - ((zzf) obj).zzb;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return zzky.zza().zza((zzky) this).zza(this, (zzjb<MessageType, BuilderType>) obj);
        }
        return false;
    }

    protected final <MessageType extends zzjb<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> BuilderType zzj() {
        return (BuilderType) zza(zzg.zze, (Object) null, (Object) null);
    }

    @Override // com.google.android.gms.internal.vision.zzkm
    public final boolean zzk() {
        return zza(this, true);
    }

    @Override // com.google.android.gms.internal.vision.zzhf
    final int zzi() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.vision.zzhf
    final void zzb(int i) {
        this.zzc = i;
    }

    @Override // com.google.android.gms.internal.vision.zzkk
    public final void zza(zzii zziiVar) throws IOException {
        zzky.zza().zza((zzky) this).zza((zzlc) this, (zzmr) zzil.zza(zziiVar));
    }

    @Override // com.google.android.gms.internal.vision.zzkk
    public final int zzm() {
        if (this.zzc == -1) {
            this.zzc = zzky.zza().zza((zzky) this).zzb(this);
        }
        return this.zzc;
    }

    static <T extends zzjb<?, ?>> T zza(Class<T> cls) {
        zzjb<?, ?> zzjbVar = zzd.get(cls);
        if (zzjbVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzjbVar = zzd.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzjbVar == null) {
            zzjbVar = (T) ((zzjb) zzma.zza(cls)).zza(zzg.zzf, (Object) null, (Object) null);
            if (zzjbVar == null) {
                throw new IllegalStateException();
            }
            zzd.put(cls, zzjbVar);
        }
        return (T) zzjbVar;
    }

    protected static <T extends zzjb<?, ?>> void zza(Class<T> cls, T t) {
        zzd.put(cls, t);
    }

    protected static Object zza(zzkk zzkkVar, String str, Object[] objArr) {
        return new zzla(zzkkVar, str, objArr);
    }

    static Object zza(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    protected static final <T extends zzjb<T, ?>> boolean zza(T t, boolean z) {
        byte byteValue = ((Byte) t.zza(zzg.zza, null, null)).byteValue();
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        boolean zzd2 = zzky.zza().zza((zzky) t).zzd(t);
        if (z) {
            t.zza(zzg.zzb, zzd2 ? t : null, null);
        }
        return zzd2;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.vision.zzjd, com.google.android.gms.internal.vision.zzjj] */
    protected static zzjj zzn() {
        return zzjd.zzd();
    }

    protected static <E> zzjl<E> zzo() {
        return zzlb.zzd();
    }

    protected static <E> zzjl<E> zza(zzjl<E> zzjlVar) {
        int size = zzjlVar.size();
        return zzjlVar.zza(size == 0 ? 10 : size << 1);
    }

    @Override // com.google.android.gms.internal.vision.zzkk
    public final /* synthetic */ zzkn zzp() {
        zzb zzbVar = (zzb) zza(zzg.zze, (Object) null, (Object) null);
        zzbVar.zza((zzb) this);
        return zzbVar;
    }

    @Override // com.google.android.gms.internal.vision.zzkk
    public final /* synthetic */ zzkn zzq() {
        return (zzb) zza(zzg.zze, (Object) null, (Object) null);
    }

    @Override // com.google.android.gms.internal.vision.zzkm
    public final /* synthetic */ zzkk zzr() {
        return (zzjb) zza(zzg.zzf, (Object) null, (Object) null);
    }
}
