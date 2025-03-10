package com.tencent.qimei.i;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import java.nio.charset.Charset;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: BeaconSharedPrefs.java */
@Deprecated
/* loaded from: classes.dex */
public class b {
    public static b a;
    public static byte[] b = {33, 94, 120, 74, 111, 43, 35};
    public SharedPreferences c;
    public SharedPreferences.Editor d;
    public Lock e = new ReentrantLock();

    public b() {
        new a(this);
        Context J = com.tencent.qimei.u.d.b().J();
        if (J != null) {
            this.c = J.getSharedPreferences("DENGTA_META", 0);
        }
    }

    public static synchronized b b() {
        b bVar;
        synchronized (b.class) {
            if (a == null) {
                a = new b();
            }
            bVar = a;
        }
        return bVar;
    }

    public synchronized String a(String str, String str2) {
        return this.c.getString(str, str2);
    }

    public final synchronized void a() {
        if (this.e.tryLock()) {
            this.d.commit();
            this.e.unlock();
        }
    }

    public synchronized String a(String str, String str2, String str3) {
        SharedPreferences sharedPreferences = this.c;
        String string = sharedPreferences.getString(str, "");
        int i = 0;
        if (string != null && !string.trim().equals("")) {
            byte[] bytes = string.getBytes(Charset.defaultCharset());
            int i2 = 0;
            while (i < bytes.length) {
                byte b2 = bytes[i];
                byte[] bArr = b;
                bytes[i] = (byte) (b2 ^ bArr[i2]);
                i2 = (i2 + 1) % bArr.length;
                i++;
            }
            sharedPreferences.edit().remove(str).putString(str2, Base64.encodeToString(bytes, 2)).commit();
            return string;
        }
        String string2 = sharedPreferences.getString(str2, "");
        if (string2 == null || string2.trim().equals("")) {
            return str3;
        }
        byte[] decode = Base64.decode(string2, 2);
        int i3 = 0;
        while (i < decode.length) {
            byte b3 = decode[i];
            byte[] bArr2 = b;
            decode[i] = (byte) (b3 ^ bArr2[i3]);
            i3 = (i3 + 1) % bArr2.length;
            i++;
        }
        return new String(decode, Charset.defaultCharset());
    }
}
