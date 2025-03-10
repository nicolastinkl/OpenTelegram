package com.tencent.beacon.a.d;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: PropertiesFile.java */
/* loaded from: classes.dex */
public class g {
    private FileChannel b;
    private MappedByteBuffer d;
    private long e;
    private Runnable f;
    private boolean g;
    private boolean h;
    private final Object a = new Object();
    private JSONObject c = new JSONObject();

    private g(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        this.b = randomAccessFile.getChannel();
        this.e = randomAccessFile.length();
        com.tencent.beacon.base.util.c.a("[properties]", "file size: " + this.e, new Object[0]);
        e();
    }

    private Object c(String str) {
        Object obj = null;
        try {
            synchronized (this.a) {
                obj = this.c.get(str);
            }
        } catch (Exception unused) {
            com.tencent.beacon.base.util.c.a("[properties]", "current jsonObject not exist key: " + str, new Object[0]);
        }
        return obj;
    }

    private Runnable d() {
        if (this.f == null) {
            this.f = new e(this);
        }
        return this.f;
    }

    private void e() throws IOException {
        if (this.e <= 10) {
            this.h = true;
            this.e = 4L;
        }
        MappedByteBuffer map = this.b.map(FileChannel.MapMode.READ_WRITE, 0L, this.e);
        this.d = map;
        map.rewind();
        if (this.h) {
            this.d.putInt(0, 1);
            a(d());
            return;
        }
        byte[] a = a(this.d);
        if (a == null) {
            return;
        }
        try {
            this.c = new JSONObject(new String(b(a), "ISO8859-1"));
        } catch (Exception e) {
            com.tencent.beacon.base.util.c.a("[properties]", "init error" + e.getMessage(), new Object[0]);
            com.tencent.beacon.a.b.g.e().a("504", "[properties] init error! msg: " + e.getMessage() + ". file size: " + this.e, e);
        }
        com.tencent.beacon.base.util.c.a("[properties]", "init json: " + this.c.toString(), new Object[0]);
    }

    public static g a(Context context, String str) throws IOException {
        File file = new File(context.getFilesDir(), "beacon");
        if (!(!file.exists() ? file.mkdirs() : true)) {
            com.tencent.beacon.base.util.e.a("mkdir " + file.getName() + " exception!");
        }
        return new g(new File(file, str + "V1"));
    }

    public static byte[] b(byte[] bArr) {
        byte[] a = a(bArr, "BEACONDEFAULTAES");
        if (a != null) {
            return a;
        }
        com.tencent.beacon.a.b.g.e().a("517", "default aesKey unEncryption failed");
        byte[] a2 = a(bArr, com.tencent.beacon.a.c.f.e().a());
        return a2 != null ? a2 : a(bArr, "");
    }

    private boolean c() {
        if (!this.g) {
            return false;
        }
        com.tencent.beacon.base.util.c.a("[properties]", "File is close!", new Object[0]);
        return true;
    }

    public synchronized void b(String str, Object obj) {
        Object c;
        if (c()) {
            return;
        }
        try {
            c = c(str);
        } catch (Exception e) {
            com.tencent.beacon.a.b.g.e().a("504", "[properties] JSON put error!", e);
            com.tencent.beacon.base.util.c.b("[properties] JSON put error!" + e.getMessage(), new Object[0]);
        }
        if (c == null || !c.equals(obj)) {
            if (obj instanceof String) {
                if (TextUtils.isEmpty((String) obj)) {
                    return;
                }
                if (!com.tencent.beacon.base.util.f.a((String) obj)) {
                    com.tencent.beacon.base.util.c.b("[properties] JSON put value not english ! !", new Object[0]);
                    return;
                }
            }
            synchronized (this.a) {
                this.c.put(str, obj);
            }
            a(d());
        }
    }

    private static byte[] a(byte[] bArr, String str) {
        try {
            return com.tencent.beacon.base.net.b.c.a(3, str, bArr);
        } catch (Throwable th) {
            com.tencent.beacon.base.util.c.a(th);
            com.tencent.beacon.a.b.g.e().a("513", "unEncrypt error: key=" + str, th);
            return null;
        }
    }

    public static byte[] a(byte[] bArr) throws Exception {
        return com.tencent.beacon.base.net.b.c.b(3, "BEACONDEFAULTAES", bArr);
    }

    private byte[] a(ByteBuffer byteBuffer) {
        int i = byteBuffer.getInt(0);
        if (i <= 1 || i > 2097152 || byteBuffer.capacity() <= 10) {
            return null;
        }
        byteBuffer.position(10);
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr, 0, i);
        return bArr;
    }

    public synchronized <T> T a(String str, T t) {
        if (c()) {
            return t;
        }
        Object c = c(str);
        if (c != null) {
            t = (T) c;
        }
        return t;
    }

    public synchronized <T> void b(String str, Set<T> set) {
        if (c()) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            Iterator<T> it = set.iterator();
            for (int i = 0; i < set.size(); i++) {
                if (it.hasNext()) {
                    jSONObject.put(String.valueOf(i), it.next());
                }
            }
            b(str, jSONObject);
        } catch (JSONException e) {
            com.tencent.beacon.base.util.c.a(e);
            com.tencent.beacon.a.b.g.e().a("504", "[properties] JSON put set error!", e);
        }
    }

    public synchronized <T> Set<T> a(String str, Set<T> set) {
        JSONObject jSONObject;
        if (c()) {
            return set;
        }
        try {
            HashSet hashSet = new HashSet();
            synchronized (this.a) {
                jSONObject = this.c.getJSONObject(str);
            }
            if (jSONObject != null) {
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    hashSet.add(jSONObject.get(keys.next()));
                }
            }
            if (!hashSet.isEmpty()) {
                set = hashSet;
            }
        } catch (JSONException e) {
            com.tencent.beacon.a.b.g.e().a("504", "[properties] JSON getSet error!", e);
            com.tencent.beacon.base.util.c.b("[properties] JSON get error!" + e.getMessage(), new Object[0]);
        }
        return set;
    }

    public synchronized void b(String str) {
        synchronized (this.a) {
            this.c.remove(str);
        }
        a(d());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j) throws IOException {
        if (j <= 2097152) {
            this.d.rewind();
            this.d = this.b.map(FileChannel.MapMode.READ_WRITE, 0L, j);
            return;
        }
        throw new IllegalArgumentException("file size to reach maximum!");
    }

    public Map<String, ?> b() {
        synchronized (this.a) {
            JSONObject jSONObject = this.c;
            if (jSONObject == null) {
                return null;
            }
            Iterator<String> keys = jSONObject.keys();
            HashMap hashMap = new HashMap();
            while (keys.hasNext()) {
                String next = keys.next();
                try {
                    hashMap.put(next, this.c.get(next));
                } catch (JSONException e) {
                    com.tencent.beacon.base.util.c.a(e);
                }
            }
            return hashMap;
        }
    }

    private void a(Runnable runnable) {
        com.tencent.beacon.a.b.a.a().a(new f(this, runnable));
    }

    public synchronized void a() {
        this.c = new JSONObject();
        a(d());
    }

    public boolean a(String str) {
        synchronized (this.a) {
            Iterator<String> keys = this.c.keys();
            if (keys != null) {
                while (keys.hasNext()) {
                    if (str.equals(keys.next())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
