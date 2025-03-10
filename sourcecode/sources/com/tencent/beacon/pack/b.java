package com.tencent.beacon.pack;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: JceOutputStream.java */
/* loaded from: classes.dex */
public class b {
    public static final byte[] a = new byte[0];
    private static final char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    protected String c;
    private ByteBuffer d;

    public b(int i) {
        this.c = "GBK";
        this.d = ByteBuffer.allocate(i);
    }

    public ByteBuffer a() {
        return this.d;
    }

    public byte[] b() {
        byte[] bArr = new byte[this.d.position()];
        System.arraycopy(this.d.array(), 0, bArr, 0, this.d.position());
        return bArr;
    }

    public void a(int i) {
        if (this.d.remaining() < i) {
            try {
                ByteBuffer allocate = ByteBuffer.allocate((this.d.capacity() + i) * 2);
                allocate.put(this.d.array(), 0, this.d.position());
                this.d = allocate;
            } catch (IllegalArgumentException e) {
                throw e;
            }
        }
    }

    public void b(byte b2, int i) {
        if (i < 15) {
            this.d.put((byte) (b2 | (i << 4)));
        } else if (i < 256) {
            this.d.put((byte) (b2 | 240));
            this.d.put((byte) i);
        } else {
            throw new RuntimeException("tag is too large: " + i);
        }
    }

    public b() {
        this(128);
    }

    public void a(boolean z, int i) {
        a(z ? (byte) 1 : (byte) 0, i);
    }

    public void a(byte b2, int i) {
        a(3);
        if (b2 == 0) {
            b(AbstractJceStruct.ZERO_TAG, i);
        } else {
            b((byte) 0, i);
            this.d.put(b2);
        }
    }

    public void a(short s, int i) {
        a(4);
        if (s >= -128 && s <= 127) {
            a((byte) s, i);
        } else {
            b((byte) 1, i);
            this.d.putShort(s);
        }
    }

    public void a(int i, int i2) {
        a(6);
        if (i >= -32768 && i <= 32767) {
            a((short) i, i2);
        } else {
            b((byte) 2, i2);
            this.d.putInt(i);
        }
    }

    public void a(long j, int i) {
        a(10);
        if (j >= -2147483648L && j <= 2147483647L) {
            a((int) j, i);
        } else {
            b((byte) 3, i);
            this.d.putLong(j);
        }
    }

    public void a(float f, int i) {
        a(6);
        b((byte) 4, i);
        this.d.putFloat(f);
    }

    public void a(double d, int i) {
        a(10);
        b((byte) 5, i);
        this.d.putDouble(d);
    }

    public void a(String str, int i) {
        byte[] bytes;
        try {
            bytes = str.getBytes(this.c);
        } catch (UnsupportedEncodingException unused) {
            bytes = str.getBytes(Charset.forName("UTF-8"));
        }
        a(bytes.length + 10);
        if (bytes.length > 255) {
            b((byte) 7, i);
            this.d.putInt(bytes.length);
            this.d.put(bytes);
        } else {
            b((byte) 6, i);
            this.d.put((byte) bytes.length);
            this.d.put(bytes);
        }
    }

    public <K, V> void a(Map<K, V> map, int i) {
        a(8);
        b((byte) 8, i);
        a(map == null ? 0 : map.size(), 0);
        if (map != null) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                a(entry.getKey(), 0);
                a(entry.getValue(), 1);
            }
        }
    }

    public void a(boolean[] zArr, int i) {
        a(8);
        b((byte) 9, i);
        a(zArr.length, 0);
        for (boolean z : zArr) {
            a(z, 0);
        }
    }

    public void a(byte[] bArr, int i) {
        a(bArr.length + 8);
        b(AbstractJceStruct.SIMPLE_LIST, i);
        b((byte) 0, 0);
        a(bArr.length, 0);
        this.d.put(bArr);
    }

    public void a(short[] sArr, int i) {
        a(8);
        b((byte) 9, i);
        a(sArr.length, 0);
        for (short s : sArr) {
            a(s, 0);
        }
    }

    public void a(int[] iArr, int i) {
        a(8);
        b((byte) 9, i);
        a(iArr.length, 0);
        for (int i2 : iArr) {
            a(i2, 0);
        }
    }

    public void a(long[] jArr, int i) {
        a(8);
        b((byte) 9, i);
        a(jArr.length, 0);
        for (long j : jArr) {
            a(j, 0);
        }
    }

    public void a(float[] fArr, int i) {
        a(8);
        b((byte) 9, i);
        a(fArr.length, 0);
        for (float f : fArr) {
            a(f, 0);
        }
    }

    public void a(double[] dArr, int i) {
        a(8);
        b((byte) 9, i);
        a(dArr.length, 0);
        for (double d : dArr) {
            a(d, 0);
        }
    }

    public <T> void a(Collection<T> collection, int i) {
        a(8);
        b((byte) 9, i);
        a(collection == null ? 0 : collection.size(), 0);
        if (collection != null) {
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                a(it.next(), 0);
            }
        }
    }

    public void a(AbstractJceStruct abstractJceStruct, int i) {
        a(2);
        b((byte) 10, i);
        abstractJceStruct.writeTo(this);
        a(2);
        b(AbstractJceStruct.STRUCT_END, 0);
    }

    public void a(Object obj, int i) {
        if (obj == null) {
            obj = "";
        }
        if (obj instanceof Byte) {
            a(((Byte) obj).byteValue(), i);
            return;
        }
        if (obj instanceof Boolean) {
            a(((Boolean) obj).booleanValue(), i);
            return;
        }
        if (obj instanceof Short) {
            a(((Short) obj).shortValue(), i);
            return;
        }
        if (obj instanceof Integer) {
            a(((Integer) obj).intValue(), i);
            return;
        }
        if (obj instanceof Long) {
            a(((Long) obj).longValue(), i);
            return;
        }
        if (obj instanceof Float) {
            a(((Float) obj).floatValue(), i);
            return;
        }
        if (obj instanceof Double) {
            a(((Double) obj).doubleValue(), i);
            return;
        }
        if (obj instanceof String) {
            a((String) obj, i);
            return;
        }
        if (obj instanceof Map) {
            a((Map) obj, i);
            return;
        }
        if (obj instanceof List) {
            a((Collection) obj, i);
            return;
        }
        if (obj instanceof AbstractJceStruct) {
            a((AbstractJceStruct) obj, i);
            return;
        }
        if (obj instanceof byte[]) {
            a((byte[]) obj, i);
            return;
        }
        if (obj instanceof boolean[]) {
            a((boolean[]) obj, i);
            return;
        }
        if (obj instanceof short[]) {
            a((short[]) obj, i);
            return;
        }
        if (obj instanceof int[]) {
            a((int[]) obj, i);
            return;
        }
        if (obj instanceof long[]) {
            a((long[]) obj, i);
            return;
        }
        if (obj instanceof float[]) {
            a((float[]) obj, i);
            return;
        }
        if (obj instanceof double[]) {
            a((double[]) obj, i);
            return;
        }
        if (obj.getClass().isArray()) {
            a((Object[]) obj, i);
        } else {
            if (obj instanceof Collection) {
                a((Collection) obj, i);
                return;
            }
            throw new RuntimeException("write object error: unsupport type. " + obj.getClass());
        }
    }

    private void a(Object[] objArr, int i) {
        a(8);
        b((byte) 9, i);
        a(objArr.length, 0);
        for (Object obj : objArr) {
            a(obj, 0);
        }
    }

    public int a(String str) {
        this.c = str;
        return 0;
    }
}
