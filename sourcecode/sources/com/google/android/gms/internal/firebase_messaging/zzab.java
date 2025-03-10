package com.google.android.gms.internal.firebase_messaging;

import com.google.firebase.encoders.EncodingException;
import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import com.google.firebase.encoders.ValueEncoder;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-messaging@@22.0.0 */
/* loaded from: classes.dex */
final class zzab implements ObjectEncoderContext {
    private static final Charset zza = Charset.forName("UTF-8");
    private static final FieldDescriptor zzg;
    private static final FieldDescriptor zzh;
    private static final ObjectEncoder<Map.Entry<Object, Object>> zzi;
    private OutputStream zzb;
    private final Map<Class<?>, ObjectEncoder<?>> zzc;
    private final Map<Class<?>, ValueEncoder<?>> zzd;
    private final ObjectEncoder<Object> zze;
    private final zzaf zzf = new zzaf(this);

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder("key");
        zzv zzvVar = new zzv();
        zzvVar.zza(1);
        zzg = builder.withProperty(zzvVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("value");
        zzv zzvVar2 = new zzv();
        zzvVar2.zza(2);
        zzh = builder2.withProperty(zzvVar2.zzb()).build();
        zzi = zzaa.zza;
    }

    zzab(OutputStream outputStream, Map<Class<?>, ObjectEncoder<?>> map, Map<Class<?>, ValueEncoder<?>> map2, ObjectEncoder<Object> objectEncoder) {
        this.zzb = outputStream;
        this.zzc = map;
        this.zzd = map2;
        this.zze = objectEncoder;
    }

    static final /* synthetic */ void zzg(Map.Entry entry, ObjectEncoderContext objectEncoderContext) throws IOException {
        objectEncoderContext.add(zzg, entry.getKey());
        objectEncoderContext.add(zzh, entry.getValue());
    }

    private final <T> zzab zzh(ObjectEncoder<T> objectEncoder, FieldDescriptor fieldDescriptor, T t, boolean z) throws IOException {
        long zzi2 = zzi(objectEncoder, t);
        if (z && zzi2 == 0) {
            return this;
        }
        zzn((zzl(fieldDescriptor) << 3) | 2);
        zzo(zzi2);
        objectEncoder.encode(t, this);
        return this;
    }

    private final <T> long zzi(ObjectEncoder<T> objectEncoder, T t) throws IOException {
        zzw zzwVar = new zzw();
        try {
            OutputStream outputStream = this.zzb;
            this.zzb = zzwVar;
            try {
                objectEncoder.encode(t, this);
                this.zzb = outputStream;
                long zza2 = zzwVar.zza();
                zzwVar.close();
                return zza2;
            } catch (Throwable th) {
                this.zzb = outputStream;
                throw th;
            }
        } catch (Throwable th2) {
            try {
                zzwVar.close();
            } catch (Throwable th3) {
                zzt.zza(th2, th3);
            }
            throw th2;
        }
    }

    private final <T> zzab zzj(ValueEncoder<T> valueEncoder, FieldDescriptor fieldDescriptor, T t, boolean z) throws IOException {
        this.zzf.zza(fieldDescriptor, z);
        valueEncoder.encode(t, this.zzf);
        return this;
    }

    private static ByteBuffer zzk(int i) {
        return ByteBuffer.allocate(i).order(ByteOrder.LITTLE_ENDIAN);
    }

    private static int zzl(FieldDescriptor fieldDescriptor) {
        zzz zzzVar = (zzz) fieldDescriptor.getProperty(zzz.class);
        if (zzzVar != null) {
            return zzzVar.zza();
        }
        throw new EncodingException("Field has no @Protobuf config");
    }

    private static zzz zzm(FieldDescriptor fieldDescriptor) {
        zzz zzzVar = (zzz) fieldDescriptor.getProperty(zzz.class);
        if (zzzVar != null) {
            return zzzVar;
        }
        throw new EncodingException("Field has no @Protobuf config");
    }

    private final void zzn(int i) throws IOException {
        while ((i & (-128)) != 0) {
            this.zzb.write((i & 127) | 128);
            i >>>= 7;
        }
        this.zzb.write(i & 127);
    }

    private final void zzo(long j) throws IOException {
        while (((-128) & j) != 0) {
            this.zzb.write((((int) j) & 127) | 128);
            j >>>= 7;
        }
        this.zzb.write(((int) j) & 127);
    }

    final ObjectEncoderContext zza(FieldDescriptor fieldDescriptor, Object obj, boolean z) throws IOException {
        if (obj == null) {
            return this;
        }
        if (obj instanceof CharSequence) {
            CharSequence charSequence = (CharSequence) obj;
            if (z && charSequence.length() == 0) {
                return this;
            }
            zzn((zzl(fieldDescriptor) << 3) | 2);
            byte[] bytes = charSequence.toString().getBytes(zza);
            zzn(bytes.length);
            this.zzb.write(bytes);
            return this;
        }
        if (obj instanceof Collection) {
            Iterator it = ((Collection) obj).iterator();
            while (it.hasNext()) {
                zza(fieldDescriptor, it.next(), false);
            }
            return this;
        }
        if (obj instanceof Map) {
            Iterator it2 = ((Map) obj).entrySet().iterator();
            while (it2.hasNext()) {
                zzh(zzi, fieldDescriptor, (Map.Entry) it2.next(), false);
            }
            return this;
        }
        if (obj instanceof Double) {
            zzb(fieldDescriptor, ((Double) obj).doubleValue(), z);
            return this;
        }
        if (obj instanceof Float) {
            zzc(fieldDescriptor, ((Float) obj).floatValue(), z);
            return this;
        }
        if (obj instanceof Number) {
            zze(fieldDescriptor, ((Number) obj).longValue(), z);
            return this;
        }
        if (obj instanceof Boolean) {
            zzd(fieldDescriptor, ((Boolean) obj).booleanValue() ? 1 : 0, z);
            return this;
        }
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            if (z && bArr.length == 0) {
                return this;
            }
            zzn((zzl(fieldDescriptor) << 3) | 2);
            zzn(bArr.length);
            this.zzb.write(bArr);
            return this;
        }
        ObjectEncoder<?> objectEncoder = this.zzc.get(obj.getClass());
        if (objectEncoder != null) {
            zzh(objectEncoder, fieldDescriptor, obj, z);
            return this;
        }
        ValueEncoder<?> valueEncoder = this.zzd.get(obj.getClass());
        if (valueEncoder != null) {
            zzj(valueEncoder, fieldDescriptor, obj, z);
            return this;
        }
        if (obj instanceof zzx) {
            zzd(fieldDescriptor, ((zzx) obj).getNumber(), true);
            return this;
        }
        if (obj instanceof Enum) {
            zzd(fieldDescriptor, ((Enum) obj).ordinal(), true);
            return this;
        }
        zzh(this.zze, fieldDescriptor, obj, z);
        return this;
    }

    final ObjectEncoderContext zzb(FieldDescriptor fieldDescriptor, double d, boolean z) throws IOException {
        if (z && d == 0.0d) {
            return this;
        }
        zzn((zzl(fieldDescriptor) << 3) | 1);
        this.zzb.write(zzk(8).putDouble(d).array());
        return this;
    }

    final ObjectEncoderContext zzc(FieldDescriptor fieldDescriptor, float f, boolean z) throws IOException {
        if (z && f == 0.0f) {
            return this;
        }
        zzn((zzl(fieldDescriptor) << 3) | 5);
        this.zzb.write(zzk(4).putFloat(f).array());
        return this;
    }

    final zzab zzd(FieldDescriptor fieldDescriptor, int i, boolean z) throws IOException {
        if (z && i == 0) {
            return this;
        }
        zzz zzm = zzm(fieldDescriptor);
        zzy zzyVar = zzy.DEFAULT;
        int ordinal = zzm.zzb().ordinal();
        if (ordinal == 0) {
            zzn(zzm.zza() << 3);
            zzn(i);
        } else if (ordinal == 1) {
            zzn(zzm.zza() << 3);
            zzn((i + i) ^ (i >> 31));
        } else if (ordinal == 2) {
            zzn((zzm.zza() << 3) | 5);
            this.zzb.write(zzk(4).putInt(i).array());
        }
        return this;
    }

    final zzab zze(FieldDescriptor fieldDescriptor, long j, boolean z) throws IOException {
        if (z && j == 0) {
            return this;
        }
        zzz zzm = zzm(fieldDescriptor);
        zzy zzyVar = zzy.DEFAULT;
        int ordinal = zzm.zzb().ordinal();
        if (ordinal == 0) {
            zzn(zzm.zza() << 3);
            zzo(j);
        } else if (ordinal == 1) {
            zzn(zzm.zza() << 3);
            zzo((j >> 63) ^ (j + j));
        } else if (ordinal == 2) {
            zzn((zzm.zza() << 3) | 1);
            this.zzb.write(zzk(8).putLong(j).array());
        }
        return this;
    }

    final zzab zzf(Object obj) throws IOException {
        if (obj == null) {
            return this;
        }
        ObjectEncoder<?> objectEncoder = this.zzc.get(obj.getClass());
        if (objectEncoder != null) {
            objectEncoder.encode(obj, this);
            return this;
        }
        String valueOf = String.valueOf(obj.getClass());
        StringBuilder sb = new StringBuilder(valueOf.length() + 15);
        sb.append("No encoder for ");
        sb.append(valueOf);
        throw new EncodingException(sb.toString());
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final /* bridge */ /* synthetic */ ObjectEncoderContext add(FieldDescriptor fieldDescriptor, int i) throws IOException {
        zzd(fieldDescriptor, i, true);
        return this;
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final /* bridge */ /* synthetic */ ObjectEncoderContext add(FieldDescriptor fieldDescriptor, long j) throws IOException {
        zze(fieldDescriptor, j, true);
        return this;
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final ObjectEncoderContext add(FieldDescriptor fieldDescriptor, Object obj) throws IOException {
        zza(fieldDescriptor, obj, true);
        return this;
    }
}
