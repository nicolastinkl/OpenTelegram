package com.tencent.beacon.base.util;

import android.content.SharedPreferences;
import android.text.format.Time;
import com.tencent.beacon.a.b.g;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: CoreUtils.java */
/* loaded from: classes.dex */
public class b {
    private static final Random a = new Random();
    private static final AtomicInteger b = new AtomicInteger(0);
    private static final SimpleDateFormat c = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public static boolean a(long j, long j2) {
        Time time = new Time();
        time.set(j);
        int i = time.year;
        int i2 = time.month;
        int i3 = time.monthDay;
        time.set(j2);
        return i == time.year && i2 == time.month && i3 == time.monthDay;
    }

    public static byte[] b(byte[] bArr, int i, String str) {
        if (bArr == null || bArr.length <= 0 || i == -1) {
            return bArr;
        }
        try {
            return com.tencent.beacon.base.net.b.c.a(i, str, bArr);
        } catch (Throwable th) {
            c.e("data length: " + bArr.length + ",type:" + i + ",key: " + str + ",error: " + th.getMessage(), new Object[0]);
            g e = g.e();
            StringBuilder sb = new StringBuilder();
            sb.append("data length: ");
            sb.append(bArr.length);
            sb.append(",type:");
            sb.append(i);
            sb.append(",key: ");
            sb.append(str);
            e.a("508", sb.toString(), th);
            return null;
        }
    }

    public static long c() {
        return new Date().getTime() + com.tencent.beacon.a.c.c.d().k();
    }

    public static Date d(String str) {
        if (str != null && str.trim().length() > 0) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(str);
            } catch (ParseException e) {
                c.a(e);
            }
        }
        return null;
    }

    public static String c(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes(Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            for (byte b2 : digest) {
                int i = b2 & 255;
                if (i < 16) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (Exception e) {
            c.a(e);
            return str;
        }
    }

    public static String d() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
        } catch (Throwable th) {
            c.a(th);
            return "";
        }
    }

    public static byte[] b(byte[] bArr, int i) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        c.a("zp: %s len: %s", Integer.valueOf(i), Integer.valueOf(bArr.length));
        try {
            return com.tencent.beacon.base.net.b.a.a(i, bArr);
        } catch (Throwable th) {
            g.e().a("509", "zipData length: " + bArr.length + ",type:" + i, th);
            c.a(th);
            c.b("err zp : %s", th.toString());
            return null;
        }
    }

    public static byte[] a(Object obj) {
        ObjectOutputStream objectOutputStream;
        if (!(obj instanceof Serializable)) {
            c.b("not serial obj ", new Object[0]);
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        } catch (Throwable th) {
            th = th;
            objectOutputStream = null;
        }
        try {
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            a(objectOutputStream);
            a(byteArrayOutputStream);
            return byteArray;
        } catch (Throwable th2) {
            th = th2;
            try {
                c.a(th);
                c.b(th.getMessage(), new Object[0]);
                a(objectOutputStream);
                a(byteArrayOutputStream);
                return null;
            } catch (Throwable th3) {
                a(objectOutputStream);
                a(byteArrayOutputStream);
                throw th3;
            }
        }
    }

    public static byte[] b(byte[] bArr, int i, int i2, String str) {
        if (bArr == null) {
            return null;
        }
        try {
            return a(b(bArr, i), i2, str);
        } catch (Throwable th) {
            c.a(th);
            return null;
        }
    }

    public static String b(String str) {
        String c2 = c(str);
        if (c2 == null) {
            return c2;
        }
        try {
            return c2.substring(8, 24);
        } catch (Exception e) {
            c.a(e);
            return c2;
        }
    }

    public static String b() {
        com.tencent.beacon.a.c.c d = com.tencent.beacon.a.c.c.d();
        return b((d != null ? d.f() : "") + "_" + com.tencent.beacon.a.c.e.l().d() + "_" + new Date().getTime() + "_" + (a.nextInt(2147473647) + 1000));
    }

    public static Object a(byte[] bArr) {
        Throwable th;
        ObjectInputStream objectInputStream;
        if (bArr == null) {
            return null;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
        } catch (Throwable th2) {
            th = th2;
            objectInputStream = null;
        }
        try {
            Object readObject = objectInputStream.readObject();
            a(objectInputStream);
            a(byteArrayInputStream);
            return readObject;
        } catch (Throwable th3) {
            th = th3;
            try {
                c.a(th);
                c.b(th.getMessage(), new Object[0]);
                a(objectInputStream);
                a(byteArrayInputStream);
                return null;
            } catch (Throwable th4) {
                a(objectInputStream);
                a(byteArrayInputStream);
                throw th4;
            }
        }
    }

    public static byte[] a(byte[] bArr, int i, String str) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        c.a("CoreUtils", "encry data length:%d type: %d", Integer.valueOf(bArr.length), Integer.valueOf(i));
        try {
            return com.tencent.beacon.base.net.b.c.b(i, str, bArr);
        } catch (Throwable th) {
            c.a(th);
            g.e().a("507", "data length: " + bArr.length + ",type:" + i + ",key: " + str, th);
            return null;
        }
    }

    public static byte[] a(byte[] bArr, int i) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        c.a("unzp: %s len: %s", Integer.valueOf(i), Integer.valueOf(bArr.length));
        try {
            return com.tencent.beacon.base.net.b.a.b(i, bArr);
        } catch (Throwable th) {
            g.e().a("509", "unzipData length: " + bArr.length + ",type:" + i, th);
            c.a(th);
            StringBuilder sb = new StringBuilder();
            sb.append("err unzp}");
            sb.append(th.toString());
            c.b(sb.toString(), new Object[0]);
            return null;
        }
    }

    public static byte[] a(byte[] bArr, int i, int i2, String str) {
        try {
            return a(b(bArr, i2, str), i);
        } catch (Exception e) {
            c.a(e);
            return null;
        }
    }

    public static ArrayList<String> a(String[] strArr) {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            Process exec = Runtime.getRuntime().exec(strArr);
            bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream(), Charset.forName("UTF-8")));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    arrayList.add(readLine);
                } catch (Throwable th) {
                    th = th;
                    bufferedReader2 = null;
                }
            }
            bufferedReader2 = new BufferedReader(new InputStreamReader(exec.getErrorStream(), Charset.forName("UTF-8")));
            while (true) {
                try {
                    String readLine2 = bufferedReader2.readLine();
                    if (readLine2 != null) {
                        arrayList.add(readLine2);
                    } else {
                        a(bufferedReader);
                        a(bufferedReader2);
                        return arrayList;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        c.a(th);
                        a(bufferedReader);
                        a(bufferedReader2);
                        return null;
                    } catch (Throwable th3) {
                        a(bufferedReader);
                        a(bufferedReader2);
                        throw th3;
                    }
                }
            }
        } catch (Throwable th4) {
            th = th4;
            bufferedReader = null;
            bufferedReader2 = null;
        }
    }

    public static HashSet<String> a(ArrayList<String> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        HashSet<String> hashSet = new HashSet<>(arrayList.size());
        hashSet.addAll(arrayList);
        return hashSet;
    }

    public static int a(String str, int i, int i2, int i3) {
        int i4;
        if (str == null) {
            return i;
        }
        try {
            i4 = Integer.parseInt(str);
        } catch (Exception e) {
            c.a(e);
            i4 = i;
        }
        return (i4 < i2 || i4 > i3) ? i : i4;
    }

    public static long a(String str, long j, long j2, long j3) {
        long j4;
        if (str == null) {
            return j;
        }
        try {
            j4 = Long.parseLong(str);
        } catch (Exception e) {
            c.a(e);
            j4 = j;
        }
        return (j4 < j2 || j4 > j3) ? j : j4;
    }

    public static boolean a(String str, boolean z) {
        if (str == null) {
            return z;
        }
        if (str.toLowerCase().equals("y")) {
            return true;
        }
        if (str.toLowerCase().equals("n")) {
            return false;
        }
        return z;
    }

    public static boolean a(String str) {
        return str.startsWith("rqd_");
    }

    public static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        String replace = (th.getMessage() + "\n" + stringWriter.getBuffer().toString()).replace("\t", " ").replace("\n", " ").replace("$", "-");
        return replace.length() > 10240 ? replace.substring(0, 10240) : replace;
    }

    public static void a(Closeable... closeableArr) {
        for (Closeable closeable : closeableArr) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    c.a(e);
                }
            }
        }
    }

    public static boolean a(SharedPreferences.Editor editor) {
        if (editor != null) {
            return true;
        }
        c.b("BeaconProperties editor is null!", new Object[0]);
        return false;
    }

    public static String a() {
        return a(16);
    }

    private static String a(int i) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append("abcdef0123456789".charAt(random.nextInt(16)));
        }
        return stringBuffer.toString();
    }
}
