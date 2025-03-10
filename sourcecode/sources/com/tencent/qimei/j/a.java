package com.tencent.qimei.j;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

/* compiled from: CoreUtils.java */
/* loaded from: classes.dex */
public class a {
    public static final Random a = new Random();

    public static String a(String str) {
        String b = b(str);
        if (b == null) {
            return b;
        }
        try {
            return b.substring(8, 24);
        } catch (Exception e) {
            com.tencent.qimei.k.a.a(e);
            return b;
        }
    }

    public static String b(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes(Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (Exception e) {
            com.tencent.qimei.k.a.a(e);
            return str;
        }
    }

    public static String c(String str) {
        FileInputStream fileInputStream;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(str);
        } catch (Exception unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read == -1) {
                    String byteArrayOutputStream2 = byteArrayOutputStream.toString("UTF-8");
                    a(fileInputStream);
                    a(byteArrayOutputStream);
                    return byteArrayOutputStream2;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
        } catch (Exception unused2) {
            fileInputStream2 = fileInputStream;
            a(fileInputStream2);
            a(byteArrayOutputStream);
            return "";
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            a(fileInputStream2);
            a(byteArrayOutputStream);
            throw th;
        }
    }

    public static String a() {
        return a("_" + com.tencent.qimei.c.a.d() + "_" + new Date().getTime() + "_" + (a.nextInt(2147473647) + 1000));
    }

    public static void a(Closeable... closeableArr) {
        for (Closeable closeable : closeableArr) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
