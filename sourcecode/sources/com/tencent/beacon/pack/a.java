package com.tencent.beacon.pack;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: JceInputStream.java */
/* loaded from: classes.dex */
public final class a {
    protected String a = "GBK";
    private ByteBuffer b;

    /* compiled from: JceInputStream.java */
    /* renamed from: com.tencent.beacon.pack.a$a, reason: collision with other inner class name */
    public static class C0020a {
        public byte a;
        public int b;
    }

    public a() {
    }

    public static int a(C0020a c0020a, ByteBuffer byteBuffer) {
        byte b = byteBuffer.get();
        c0020a.a = (byte) (b & 15);
        int i = (b & 240) >> 4;
        c0020a.b = i;
        if (i != 15) {
            return 1;
        }
        c0020a.b = byteBuffer.get() & 255;
        return 2;
    }

    private int b(C0020a c0020a) {
        return a(c0020a, this.b.duplicate());
    }

    private void b(int i) {
        ByteBuffer byteBuffer = this.b;
        byteBuffer.position(byteBuffer.position() + i);
    }

    public a(byte[] bArr) {
        this.b = ByteBuffer.wrap(bArr);
    }

    private void b() {
        C0020a c0020a = new C0020a();
        a(c0020a);
        a(c0020a.a);
    }

    public a(byte[] bArr, int i) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        this.b = wrap;
        wrap.position(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> T[] b(T t, int i, boolean z) {
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        if (c0020a.a == 9) {
            int a = a(0, 0, true);
            if (a >= 0) {
                T[] tArr = (T[]) ((Object[]) Array.newInstance(t.getClass(), a));
                for (int i2 = 0; i2 < a; i2++) {
                    tArr[i2] = a((a) t, 0, true);
                }
                return tArr;
            }
            throw new RuntimeException("size invalid: " + a);
        }
        throw new RuntimeException("type mismatch.");
    }

    public void a(C0020a c0020a) {
        a(c0020a, this.b);
    }

    public void a(byte[] bArr) {
        this.b = ByteBuffer.wrap(bArr);
    }

    public boolean a(int i) {
        try {
            C0020a c0020a = new C0020a();
            while (true) {
                int b = b(c0020a);
                if (c0020a.a == 11) {
                    return false;
                }
                int i2 = c0020a.b;
                if (i <= i2) {
                    return i == i2;
                }
                b(b);
                a(c0020a.a);
            }
        } catch (BufferUnderflowException e) {
            com.tencent.beacon.base.util.c.a(e);
            return false;
        } catch (RuntimeException e2) {
            com.tencent.beacon.base.util.c.a(e2);
            return false;
        }
    }

    public void a() {
        C0020a c0020a = new C0020a();
        do {
            a(c0020a);
            a(c0020a.a);
        } while (c0020a.a != 11);
    }

    private void a(byte b) {
        int i = 0;
        switch (b) {
            case 0:
                b(1);
                return;
            case 1:
                b(2);
                return;
            case 2:
                b(4);
                return;
            case 3:
                b(8);
                return;
            case 4:
                b(4);
                return;
            case 5:
                b(8);
                return;
            case 6:
                int i2 = this.b.get();
                if (i2 < 0) {
                    i2 += 256;
                }
                b(i2);
                return;
            case 7:
                b(this.b.getInt());
                return;
            case 8:
                int a = a(0, 0, true);
                while (i < a * 2) {
                    b();
                    i++;
                }
                return;
            case 9:
                int a2 = a(0, 0, true);
                while (i < a2) {
                    b();
                    i++;
                }
                return;
            case 10:
                a();
                return;
            case 11:
            case 12:
                return;
            case 13:
                C0020a c0020a = new C0020a();
                a(c0020a);
                if (c0020a.a == 0) {
                    b(a(0, 0, true));
                    return;
                }
                throw new RuntimeException("skipField with invalid type, type value: " + ((int) b) + ", " + ((int) c0020a.a));
            default:
                throw new RuntimeException("invalid type.");
        }
    }

    public boolean a(boolean z, int i, boolean z2) {
        return a((byte) 0, i, z2) != 0;
    }

    public byte a(byte b, int i, boolean z) {
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return b;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        byte b2 = c0020a.a;
        if (b2 == 0) {
            return this.b.get();
        }
        if (b2 == 12) {
            return (byte) 0;
        }
        throw new RuntimeException("type mismatch.");
    }

    public short a(short s, int i, boolean z) {
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return s;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        byte b = c0020a.a;
        if (b == 0) {
            return this.b.get();
        }
        if (b == 1) {
            return this.b.getShort();
        }
        if (b == 12) {
            return (short) 0;
        }
        throw new RuntimeException("type mismatch.");
    }

    public int a(int i, int i2, boolean z) {
        if (!a(i2)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return i;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        byte b = c0020a.a;
        if (b == 0) {
            return this.b.get();
        }
        if (b == 1) {
            return this.b.getShort();
        }
        if (b == 2) {
            return this.b.getInt();
        }
        if (b == 12) {
            return 0;
        }
        throw new RuntimeException("type mismatch.");
    }

    public long a(long j, int i, boolean z) {
        int i2;
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return j;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        byte b = c0020a.a;
        if (b == 12) {
            return 0L;
        }
        if (b == 0) {
            i2 = this.b.get();
        } else if (b == 1) {
            i2 = this.b.getShort();
        } else {
            if (b != 2) {
                if (b != 3) {
                    throw new RuntimeException("type mismatch.");
                }
                return this.b.getLong();
            }
            i2 = this.b.getInt();
        }
        return i2;
    }

    public float a(float f, int i, boolean z) {
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return f;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        byte b = c0020a.a;
        if (b == 4) {
            return this.b.getFloat();
        }
        if (b == 12) {
            return 0.0f;
        }
        throw new RuntimeException("type mismatch.");
    }

    public double a(double d, int i, boolean z) {
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return d;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        byte b = c0020a.a;
        if (b == 4) {
            return this.b.getFloat();
        }
        if (b == 5) {
            return this.b.getDouble();
        }
        if (b == 12) {
            return 0.0d;
        }
        throw new RuntimeException("type mismatch.");
    }

    public boolean[] a(boolean[] zArr, int i, boolean z) {
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        if (c0020a.a == 9) {
            int a = a(0, 0, true);
            if (a >= 0) {
                boolean[] zArr2 = new boolean[a];
                for (int i2 = 0; i2 < a; i2++) {
                    zArr2[i2] = a(zArr2[0], 0, true);
                }
                return zArr2;
            }
            throw new RuntimeException("size invalid: " + a);
        }
        throw new RuntimeException("type mismatch.");
    }

    public byte[] a(byte[] bArr, int i, boolean z) {
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        byte b = c0020a.a;
        if (b == 9) {
            int a = a(0, 0, true);
            if (a >= 0 && a <= this.b.capacity()) {
                byte[] bArr2 = new byte[a];
                for (int i2 = 0; i2 < a; i2++) {
                    bArr2[i2] = a(bArr2[0], 0, true);
                }
                return bArr2;
            }
            throw new RuntimeException("size invalid: " + a);
        }
        if (b == 13) {
            C0020a c0020a2 = new C0020a();
            a(c0020a2);
            if (c0020a2.a == 0) {
                int a2 = a(0, 0, true);
                if (a2 >= 0 && a2 <= this.b.capacity()) {
                    byte[] bArr3 = new byte[a2];
                    this.b.get(bArr3);
                    return bArr3;
                }
                throw new RuntimeException("invalid size, tag: " + i + ", type: " + ((int) c0020a.a) + ", " + ((int) c0020a2.a) + ", size: " + a2);
            }
            throw new RuntimeException("type mismatch, tag: " + i + ", type: " + ((int) c0020a.a) + ", " + ((int) c0020a2.a));
        }
        throw new RuntimeException("type mismatch.");
    }

    public short[] a(short[] sArr, int i, boolean z) {
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        if (c0020a.a == 9) {
            int a = a(0, 0, true);
            if (a >= 0) {
                short[] sArr2 = new short[a];
                for (int i2 = 0; i2 < a; i2++) {
                    sArr2[i2] = a(sArr2[0], 0, true);
                }
                return sArr2;
            }
            throw new RuntimeException("size invalid: " + a);
        }
        throw new RuntimeException("type mismatch.");
    }

    public int[] a(int[] iArr, int i, boolean z) {
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        if (c0020a.a == 9) {
            int a = a(0, 0, true);
            if (a >= 0) {
                int[] iArr2 = new int[a];
                for (int i2 = 0; i2 < a; i2++) {
                    iArr2[i2] = a(iArr2[0], 0, true);
                }
                return iArr2;
            }
            throw new RuntimeException("size invalid: " + a);
        }
        throw new RuntimeException("type mismatch.");
    }

    public long[] a(long[] jArr, int i, boolean z) {
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        if (c0020a.a == 9) {
            int a = a(0, 0, true);
            if (a >= 0) {
                long[] jArr2 = new long[a];
                for (int i2 = 0; i2 < a; i2++) {
                    jArr2[i2] = a(jArr2[0], 0, true);
                }
                return jArr2;
            }
            throw new RuntimeException("size invalid: " + a);
        }
        throw new RuntimeException("type mismatch.");
    }

    public float[] a(float[] fArr, int i, boolean z) {
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        if (c0020a.a == 9) {
            int a = a(0, 0, true);
            if (a >= 0) {
                float[] fArr2 = new float[a];
                for (int i2 = 0; i2 < a; i2++) {
                    fArr2[i2] = a(fArr2[0], 0, true);
                }
                return fArr2;
            }
            throw new RuntimeException("size invalid: " + a);
        }
        throw new RuntimeException("type mismatch.");
    }

    public double[] a(double[] dArr, int i, boolean z) {
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        if (c0020a.a == 9) {
            int a = a(0, 0, true);
            if (a >= 0) {
                double[] dArr2 = new double[a];
                for (int i2 = 0; i2 < a; i2++) {
                    dArr2[i2] = a(dArr2[0], 0, true);
                }
                return dArr2;
            }
            throw new RuntimeException("size invalid: " + a);
        }
        throw new RuntimeException("type mismatch.");
    }

    public AbstractJceStruct a(AbstractJceStruct abstractJceStruct, int i, boolean z) {
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        try {
            AbstractJceStruct abstractJceStruct2 = (AbstractJceStruct) abstractJceStruct.getClass().newInstance();
            C0020a c0020a = new C0020a();
            a(c0020a);
            if (c0020a.a == 10) {
                abstractJceStruct2.readFrom(this);
                a();
                return abstractJceStruct2;
            }
            throw new RuntimeException("type mismatch.");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> Object a(T t, int i, boolean z) {
        if (t instanceof Byte) {
            return Byte.valueOf(a((byte) 0, i, z));
        }
        if (t instanceof Boolean) {
            return Boolean.valueOf(a(false, i, z));
        }
        if (t instanceof Short) {
            return Short.valueOf(a((short) 0, i, z));
        }
        if (t instanceof Integer) {
            return Integer.valueOf(a(0, i, z));
        }
        if (t instanceof Long) {
            return Long.valueOf(a(0L, i, z));
        }
        if (t instanceof Float) {
            return Float.valueOf(a(0.0f, i, z));
        }
        if (t instanceof Double) {
            return Double.valueOf(a(0.0d, i, z));
        }
        if (t instanceof String) {
            return a(i, z);
        }
        if (t instanceof Map) {
            return a((Map) t, i, z);
        }
        if (t instanceof List) {
            return a((List) t, i, z);
        }
        if (t instanceof AbstractJceStruct) {
            return a((AbstractJceStruct) t, i, z);
        }
        if (t.getClass().isArray()) {
            if (!(t instanceof byte[]) && !(t instanceof Byte[])) {
                if (t instanceof boolean[]) {
                    return a((boolean[]) null, i, z);
                }
                if (t instanceof short[]) {
                    return a((short[]) null, i, z);
                }
                if (t instanceof int[]) {
                    return a((int[]) null, i, z);
                }
                if (t instanceof long[]) {
                    return a((long[]) null, i, z);
                }
                if (t instanceof float[]) {
                    return a((float[]) null, i, z);
                }
                if (t instanceof double[]) {
                    return a((double[]) null, i, z);
                }
                return a((Object[]) t, i, z);
            }
            return a((byte[]) null, i, z);
        }
        throw new RuntimeException("read object error: unsupport type.");
    }

    public String a(int i, boolean z) {
        if (!a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        C0020a c0020a = new C0020a();
        a(c0020a);
        byte b = c0020a.a;
        if (b == 6) {
            int i2 = this.b.get();
            if (i2 < 0) {
                i2 += 256;
            }
            byte[] bArr = new byte[i2];
            this.b.get(bArr);
            try {
                return new String(bArr, this.a);
            } catch (UnsupportedEncodingException unused) {
                return new String(bArr, Charset.forName("UTF-8"));
            }
        }
        if (b == 7) {
            int i3 = this.b.getInt();
            if (i3 <= 104857600 && i3 >= 0 && i3 <= this.b.capacity()) {
                byte[] bArr2 = new byte[i3];
                this.b.get(bArr2);
                try {
                    return new String(bArr2, this.a);
                } catch (UnsupportedEncodingException unused2) {
                    return new String(bArr2, Charset.forName("UTF-8"));
                }
            }
            throw new RuntimeException("String too long: " + i3);
        }
        throw new RuntimeException("type mismatch.");
    }

    public <K, V> HashMap<K, V> a(Map<K, V> map, int i, boolean z) {
        return (HashMap) a(new HashMap(), map, i, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <K, V> Map<K, V> a(Map<K, V> map, Map<K, V> map2, int i, boolean z) {
        if (map2 != null && !map2.isEmpty()) {
            Map.Entry<K, V> next = map2.entrySet().iterator().next();
            K key = next.getKey();
            V value = next.getValue();
            if (a(i)) {
                C0020a c0020a = new C0020a();
                a(c0020a);
                if (c0020a.a == 8) {
                    int a = a(0, 0, true);
                    if (a < 0) {
                        throw new RuntimeException("size invalid: " + a);
                    }
                    for (int i2 = 0; i2 < a; i2++) {
                        map.put(a((a) key, 0, true), a((a) value, 1, true));
                    }
                } else {
                    throw new RuntimeException("type mismatch.");
                }
            } else if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return map;
        }
        return new HashMap();
    }

    public <T> T[] a(T[] tArr, int i, boolean z) {
        if (tArr != null && tArr.length != 0) {
            return (T[]) b(tArr[0], i, z);
        }
        throw new RuntimeException("unable to get type of key and value.");
    }

    public <T> List<T> a(List<T> list, int i, boolean z) {
        if (list != null && !list.isEmpty()) {
            Object[] b = b(list.get(0), i, z);
            if (b == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj : b) {
                arrayList.add(obj);
            }
            return arrayList;
        }
        return new ArrayList();
    }

    public int a(String str) {
        this.a = str;
        return 0;
    }
}
