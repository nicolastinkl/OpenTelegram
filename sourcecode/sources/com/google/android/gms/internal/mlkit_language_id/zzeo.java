package com.google.android.gms.internal.mlkit_language_id;

import com.google.android.gms.internal.mlkit_language_id.zzeo;
import com.google.android.gms.internal.mlkit_language_id.zzeo.zzb;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/* compiled from: com.google.mlkit:language-id@@16.1.1 */
/* loaded from: classes.dex */
public abstract class zzeo<MessageType extends zzeo<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zzde<MessageType, BuilderType> {
    private static Map<Object, zzeo<?, ?>> zzd = new ConcurrentHashMap();
    protected zzhg zzb = zzhg.zza();
    private int zzc = -1;

    /* compiled from: com.google.mlkit:language-id@@16.1.1 */
    protected static class zza<T extends zzeo<T, ?>> extends zzdj<T> {
        public zza(T t) {
        }
    }

    /* compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static abstract class zzc<MessageType extends zzc<MessageType, BuilderType>, BuilderType extends zzd<MessageType, BuilderType>> extends zzeo<MessageType, BuilderType> implements zzgb {
        protected zzej<zzf> zzc = zzej.zza();
    }

    /* compiled from: com.google.mlkit:language-id@@16.1.1 */
    public enum zze {
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

    /* compiled from: com.google.mlkit:language-id@@16.1.1 */
    static final class zzf implements zzel<zzf> {
        @Override // com.google.android.gms.internal.mlkit_language_id.zzel
        public final int zza() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_language_id.zzel
        public final zzhv zzb() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_language_id.zzel
        public final zzhy zzc() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_language_id.zzel
        public final boolean zzd() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_language_id.zzel
        public final boolean zze() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_language_id.zzel
        public final zzfy zza(zzfy zzfyVar, zzfz zzfzVar) {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_language_id.zzel
        public final zzgf zza(zzgf zzgfVar, zzgf zzgfVar2) {
            throw new NoSuchMethodError();
        }

        @Override // java.lang.Comparable
        public final /* synthetic */ int compareTo(Object obj) {
            throw new NoSuchMethodError();
        }
    }

    protected abstract Object zza(int i, Object obj, Object obj2);

    /* compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static abstract class zzd<MessageType extends zzc<MessageType, BuilderType>, BuilderType extends zzd<MessageType, BuilderType>> extends zzb<MessageType, BuilderType> implements zzgb {
        protected zzd(MessageType messagetype) {
            super(messagetype);
        }

        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo.zzb
        protected void zzc() {
            super.zzc();
            MessageType messagetype = this.zza;
            ((zzc) messagetype).zzc = (zzej) ((zzc) messagetype).zzc.clone();
        }

        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo.zzb
        /* renamed from: zzd */
        public /* synthetic */ zzeo zzf() {
            return (zzc) zzf();
        }

        @Override // com.google.android.gms.internal.mlkit_language_id.zzeo.zzb, com.google.android.gms.internal.mlkit_language_id.zzfy
        public /* synthetic */ zzfz zzf() {
            if (this.zzb) {
                return (zzc) this.zza;
            }
            ((zzc) this.zza).zzc.zzb();
            return (zzc) super.zzf();
        }
    }

    public String toString() {
        return zzga.zza(this, super.toString());
    }

    public int hashCode() {
        int i = this.zza;
        if (i != 0) {
            return i;
        }
        int zza2 = zzgk.zza().zza((zzgk) this).zza(this);
        this.zza = zza2;
        return zza2;
    }

    /* compiled from: com.google.mlkit:language-id@@16.1.1 */
    public static abstract class zzb<MessageType extends zzeo<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zzdh<MessageType, BuilderType> {
        protected MessageType zza;
        protected boolean zzb = false;
        private final MessageType zzc;

        protected zzb(MessageType messagetype) {
            this.zzc = messagetype;
            this.zza = (MessageType) messagetype.zza(zze.zzd, null, null);
        }

        protected void zzc() {
            MessageType messagetype = (MessageType) this.zza.zza(zze.zzd, null, null);
            zza(messagetype, this.zza);
            this.zza = messagetype;
        }

        @Override // com.google.android.gms.internal.mlkit_language_id.zzfy
        /* renamed from: zzd, reason: merged with bridge method [inline-methods] */
        public MessageType zzf() {
            if (this.zzb) {
                return this.zza;
            }
            MessageType messagetype = this.zza;
            zzgk.zza().zza((zzgk) messagetype).zzb(messagetype);
            this.zzb = true;
            return this.zza;
        }

        @Override // com.google.android.gms.internal.mlkit_language_id.zzfy
        /* renamed from: zze, reason: merged with bridge method [inline-methods] */
        public final MessageType zzg() {
            MessageType messagetype = (MessageType) zzf();
            if (messagetype.zzi()) {
                return messagetype;
            }
            throw new zzhe(messagetype);
        }

        @Override // com.google.android.gms.internal.mlkit_language_id.zzdh
        public final BuilderType zza(MessageType messagetype) {
            if (this.zzb) {
                zzc();
                this.zzb = false;
            }
            zza(this.zza, messagetype);
            return this;
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzgk.zza().zza((zzgk) messagetype).zzb(messagetype, messagetype2);
        }

        @Override // com.google.android.gms.internal.mlkit_language_id.zzgb
        public final /* synthetic */ zzfz zzn() {
            return this.zzc;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zzb zzbVar = (zzb) this.zzc.zza(zze.zze, null, null);
            zzbVar.zza((zzb) zzf());
            return zzbVar;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return zzgk.zza().zza((zzgk) this).zza(this, (zzeo<MessageType, BuilderType>) obj);
        }
        return false;
    }

    protected final <MessageType extends zzeo<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> BuilderType zzh() {
        return (BuilderType) zza(zze.zze, (Object) null, (Object) null);
    }

    protected final <MessageType extends zzeo<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> BuilderType zza(MessageType messagetype) {
        return (BuilderType) zzh().zza(messagetype);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgb
    public final boolean zzi() {
        return zza(this, true);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzde
    final int zzg() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzde
    final void zza(int i) {
        this.zzc = i;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzfz
    public final void zza(zzea zzeaVar) throws IOException {
        zzgk.zza().zza((zzgk) this).zza((zzgp) this, (zzib) zzed.zza(zzeaVar));
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzfz
    public final int zzj() {
        if (this.zzc == -1) {
            this.zzc = zzgk.zza().zza((zzgk) this).zzd(this);
        }
        return this.zzc;
    }

    static <T extends zzeo<?, ?>> T zza(Class<T> cls) {
        zzeo<?, ?> zzeoVar = zzd.get(cls);
        if (zzeoVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzeoVar = zzd.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzeoVar == null) {
            zzeoVar = (T) ((zzeo) zzhn.zza(cls)).zza(zze.zzf, (Object) null, (Object) null);
            if (zzeoVar == null) {
                throw new IllegalStateException();
            }
            zzd.put(cls, zzeoVar);
        }
        return (T) zzeoVar;
    }

    protected static <T extends zzeo<?, ?>> void zza(Class<T> cls, T t) {
        zzd.put(cls, t);
    }

    protected static Object zza(zzfz zzfzVar, String str, Object[] objArr) {
        return new zzgm(zzfzVar, str, objArr);
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

    protected static final <T extends zzeo<T, ?>> boolean zza(T t, boolean z) {
        byte byteValue = ((Byte) t.zza(zze.zza, null, null)).byteValue();
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        boolean zzc2 = zzgk.zza().zza((zzgk) t).zzc(t);
        if (z) {
            t.zza(zze.zzb, zzc2 ? t : null, null);
        }
        return zzc2;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_language_id.zzer, com.google.android.gms.internal.mlkit_language_id.zzeu] */
    protected static zzeu zzk() {
        return zzer.zzd();
    }

    protected static <E> zzew<E> zzl() {
        return zzgn.zzd();
    }

    protected static <E> zzew<E> zza(zzew<E> zzewVar) {
        int size = zzewVar.size();
        return zzewVar.zzb(size == 0 ? 10 : size << 1);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzfz
    public final /* synthetic */ zzfy zzm() {
        zzb zzbVar = (zzb) zza(zze.zze, (Object) null, (Object) null);
        zzbVar.zza((zzb) this);
        return zzbVar;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgb
    public final /* synthetic */ zzfz zzn() {
        return (zzeo) zza(zze.zzf, (Object) null, (Object) null);
    }
}
