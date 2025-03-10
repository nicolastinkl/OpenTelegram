package com.tencent.qimei.i;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import org.json.JSONObject;

/* compiled from: PropertiesFile.java */
/* loaded from: classes.dex */
public class e {
    public RandomAccessFile a;
    public FileChannel b;
    public JSONObject c;
    public MappedByteBuffer d;
    public long e;
    public Runnable f;
    public final Object g = new Object();
    public boolean h;
    public boolean i;

    public e(File file) throws IOException {
        byte[] bArr;
        this.c = new JSONObject();
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        this.a = randomAccessFile;
        this.b = randomAccessFile.getChannel();
        long length = this.a.length();
        this.e = length;
        if (length <= 10) {
            this.i = true;
            this.e = 4L;
        }
        MappedByteBuffer map = this.b.map(FileChannel.MapMode.READ_WRITE, 0L, this.e);
        this.d = map;
        map.rewind();
        if (this.i) {
            this.d.putInt(0, 1);
            if (this.f == null) {
                this.f = new c(this);
            }
            com.tencent.qimei.b.a.a().a(new d(this, this.f));
            return;
        }
        MappedByteBuffer mappedByteBuffer = this.d;
        int i = mappedByteBuffer.getInt(0);
        if (i <= 1 || i > 2097152 || mappedByteBuffer.capacity() <= 10 || mappedByteBuffer.capacity() < i + 10) {
            bArr = null;
        } else {
            mappedByteBuffer.position(10);
            bArr = new byte[i];
            mappedByteBuffer.get(bArr, 0, i);
        }
        if (bArr == null) {
            return;
        }
        try {
            byte[] a = a(bArr, "BEACONDEFAULTAES");
            if (a == null && (a = a(bArr, com.tencent.qimei.c.c.j().b())) == null) {
                a = a(bArr, "");
            }
            this.c = new JSONObject(new String(a, "ISO8859-1"));
        } catch (Exception e) {
            e.getMessage();
        }
        this.c.toString();
    }

    public static e a(Context context, String str) throws IOException {
        File file = new File(context.getFilesDir(), "beacon");
        if (!(!file.exists() ? file.mkdirs() : true)) {
            com.tencent.qimei.j.e.a("mkdir " + file.getName() + " exception!");
        }
        File file2 = new File(file, str + "V1");
        if (file2.exists()) {
            return new e(file2);
        }
        return null;
    }

    public final Object a(String str) {
        Object obj = null;
        try {
            synchronized (this.g) {
                obj = this.c.get(str);
            }
        } catch (Exception unused) {
        }
        return obj;
    }

    public synchronized <T> T a(String str, T t) {
        if (this.h) {
            return t;
        }
        Object a = a(str);
        if (a != null) {
            t = (T) a;
        }
        return t;
    }

    public final byte[] a(byte[] bArr, String str) {
        try {
            return com.tencent.qimei.a.a.a(bArr, com.tencent.qimei.a.a.a(str).getBytes(), 2);
        } catch (Throwable th) {
            com.tencent.qimei.k.a.a(th);
            return null;
        }
    }

    public final byte[] a(byte[] bArr) throws Exception {
        return com.tencent.qimei.a.a.a(bArr, com.tencent.qimei.a.a.a("BEACONDEFAULTAES").getBytes(), 1);
    }

    public final void a(long j) throws IOException {
        if (j <= 2097152) {
            this.d.rewind();
            this.d = this.b.map(FileChannel.MapMode.READ_WRITE, 0L, j);
            return;
        }
        throw new IllegalArgumentException("file size to reach maximum!");
    }

    public synchronized void a() {
        try {
            this.c = null;
            this.h = true;
            this.d.clear();
            this.a.close();
        } catch (Exception unused) {
        }
    }
}
