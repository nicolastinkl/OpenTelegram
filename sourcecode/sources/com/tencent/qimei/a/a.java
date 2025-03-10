package com.tencent.qimei.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.tencent.qimei.d.c;
import com.tencent.qimei.i.f;
import com.tencent.qimei.n.e;
import com.tencent.qimei.n.i;
import com.tencent.qimei.o.l;
import com.tencent.qimei.o.m;
import com.tencent.qimei.r.b;
import com.tencent.qimei.sdk.Qimei;
import com.tencent.qimei.u.d;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ClientEncrypt.java */
/* loaded from: classes.dex */
public class a {
    public static boolean a(long j) {
        return j == 0 || System.currentTimeMillis() - j >= 86400000;
    }

    public static String b(String str, String str2) throws Exception {
        return Base64.encodeToString(a(str.getBytes("UTF-8"), str2.getBytes("UTF-8"), 1), 2);
    }

    public static String c(String str, String str2) {
        try {
            RSAPublicKey rSAPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str2, 2)));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, rSAPublicKey);
            return Base64.encodeToString(cipher.doFinal(str.getBytes("UTF-8")), 2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String d(String str, String str2) {
        try {
            return a(str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static synchronized void e(String str, String str2) {
        synchronized (a.class) {
            if (com.tencent.qimei.c.a.i()) {
                JSONObject f = f(str);
                try {
                    f.put(str, str2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String jSONObject = f.toString();
                if (b.a.a.a(str, jSONObject) == 0) {
                    f.a(str).a("q_s_t", System.currentTimeMillis());
                    com.tencent.qimei.k.a.b("QM", "Qm成功更新到本地: %s (appKey: %s)", jSONObject, str);
                    if (TextUtils.isEmpty(f.a(str).c("is_first"))) {
                        f.a(str).a("is_first", "false");
                    }
                }
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:0|1|(4:3|(3:36|37|(8:9|(1:(1:16)(1:17))|18|(0)|21|22|(2:24|25)|(2:28|29)(1:31)))|5|(9:7|9|(3:11|13|(0)(0))|18|(0)|21|22|(0)|(0)(0)))|41|21|22|(0)|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0049, code lost:
    
        if (r2 != 100) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x005b, code lost:
    
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x005c, code lost:
    
        r6.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0060, code lost:
    
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0061, code lost:
    
        r6.printStackTrace();
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0054 A[Catch: NullPointerException -> 0x005b, JSONException -> 0x0060, TRY_LEAVE, TryCatch #3 {NullPointerException -> 0x005b, JSONException -> 0x0060, blocks: (B:22:0x004e, B:24:0x0054), top: B:21:0x004e }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.json.JSONObject f(java.lang.String r6) {
        /*
            com.tencent.qimei.r.b r0 = com.tencent.qimei.r.b.a.a
            boolean r0 = r0.a
            r1 = 0
            if (r0 != 0) goto L8
            goto L4c
        L8:
            long r2 = java.lang.System.currentTimeMillis()
            boolean r0 = com.tencent.qimei.beaconid.U.a
            if (r0 == 0) goto L19
            byte[] r0 = com.tencent.qimei.beaconid.U.d(r2)     // Catch: java.lang.UnsatisfiedLinkError -> L15
            goto L1a
        L15:
            r0 = move-exception
            r0.printStackTrace()
        L19:
            r0 = r1
        L1a:
            if (r0 == 0) goto L4c
            int r2 = r0.length
            r3 = 1
            if (r2 > r3) goto L21
            goto L4c
        L21:
            r2 = 0
            r2 = r0[r2]
            r4 = 100
            if (r2 == 0) goto L3d
            r5 = 104(0x68, float:1.46E-43)
            if (r2 == r5) goto L3d
            r5 = 101(0x65, float:1.42E-43)
            if (r2 != r5) goto L31
            goto L3d
        L31:
            if (r2 != r4) goto L3a
            com.tencent.qimei.q.a r6 = com.tencent.qimei.q.a.a(r6)
            r6.e = r3
            goto L3d
        L3a:
            a(r6, r2)
        L3d:
            java.lang.String r6 = new java.lang.String
            int r5 = r0.length
            byte[] r0 = java.util.Arrays.copyOfRange(r0, r3, r5)
            r6.<init>(r0)
            if (r2 == 0) goto L4e
            if (r2 != r4) goto L4c
            goto L4e
        L4c:
            java.lang.String r6 = ""
        L4e:
            boolean r0 = r6.isEmpty()     // Catch: java.lang.NullPointerException -> L5b org.json.JSONException -> L60
            if (r0 != 0) goto L64
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: java.lang.NullPointerException -> L5b org.json.JSONException -> L60
            r0.<init>(r6)     // Catch: java.lang.NullPointerException -> L5b org.json.JSONException -> L60
            r1 = r0
            goto L64
        L5b:
            r6 = move-exception
            r6.printStackTrace()
            goto L64
        L60:
            r6 = move-exception
            r6.printStackTrace()
        L64:
            if (r1 != 0) goto L6b
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>()
        L6b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.a.a.f(java.lang.String):org.json.JSONObject");
    }

    public static boolean g(String str) {
        return str == null || str.isEmpty() || d.b().J() == null;
    }

    public static boolean h(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static Qimei i(String str) {
        String e = e(str);
        if (e == null || e.isEmpty()) {
            return null;
        }
        Qimei a = m.b.a(e);
        a.setAppKey(str);
        return a;
    }

    public static String a(Context context) {
        return String.format("file://%s/jsfile/tun-cos-1258344701.html", context.getFilesDir().getAbsolutePath());
    }

    public static Qimei d(String str) {
        if (g(str)) {
            return null;
        }
        return l.a(str).c;
    }

    public static String a(String str) {
        StringBuilder sb = new StringBuilder(str);
        for (int length = sb.length(); length < 16; length++) {
            sb.append("0");
        }
        return sb.toString().substring(0, 16);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0039, code lost:
    
        if (r2.equals("0") == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0057, code lost:
    
        if (r2.equals(r1) == false) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String b(android.content.Context r10) {
        /*
            java.lang.String r0 = "0"
            if (r10 != 0) goto L5
            return r0
        L5:
            java.lang.String r1 = com.tencent.qimei.c.a.d()
            java.io.File r10 = r10.getFilesDir()
            java.lang.String r10 = r10.getAbsolutePath()
            java.lang.String r2 = "/"
            java.lang.String[] r2 = r10.split(r2)
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 23
            r5 = 4
            r6 = 2
            r7 = 1
            r8 = 3
            r9 = 0
            if (r3 >= r4) goto L3c
            int r3 = r2.length
            if (r3 >= r5) goto L26
            goto L2e
        L26:
            r2 = r2[r8]
            boolean r3 = r2.equals(r1)
            if (r3 == 0) goto L2f
        L2e:
            goto L5b
        L2f:
            boolean r3 = h(r2)
            if (r3 == 0) goto L59
            boolean r0 = r2.equals(r0)
            if (r0 != 0) goto L59
            goto L51
        L3c:
            int r3 = r2.length
            r4 = 5
            if (r3 >= r4) goto L41
            goto L5b
        L41:
            r3 = r2[r8]
            r2 = r2[r5]
            boolean r4 = h(r3)
            if (r4 == 0) goto L53
            boolean r0 = r3.equals(r0)
            if (r0 != 0) goto L53
        L51:
            r0 = 1
            goto L5c
        L53:
            boolean r0 = r2.equals(r1)
            if (r0 != 0) goto L5b
        L59:
            r0 = 2
            goto L5c
        L5b:
            r0 = 0
        L5c:
            java.lang.Object[] r2 = new java.lang.Object[r8]
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)
            r2[r9] = r3
            r2[r7] = r1
            r2[r6] = r10
            java.lang.String r10 = "MultiUser"
            java.lang.String r1 = "userType: %s package: %s file: %s"
            com.tencent.qimei.k.a.b(r10, r1, r2)
            java.lang.String r10 = java.lang.String.valueOf(r0)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.a.a.b(android.content.Context):java.lang.String");
    }

    public static boolean a() {
        ConnectivityManager connectivityManager;
        Context J = d.b().J();
        if (J == null || (connectivityManager = (ConnectivityManager) J.getSystemService("connectivity")) == null) {
            return false;
        }
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (networkCapabilities != null) {
                    return networkCapabilities.hasTransport(1) || networkCapabilities.hasTransport(0) || networkCapabilities.hasTransport(3);
                }
                return false;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (SecurityException e) {
            e.printStackTrace();
            com.tencent.qimei.k.a.a("网络", "没有网络状态查询权限，请在AndroidManifest文件中添加 <uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />", new Object[0]);
            return false;
        } catch (Exception e2) {
            com.tencent.qimei.k.a.a(e2);
            return false;
        }
    }

    public static int c(String str) {
        return l.a(str).f;
    }

    public static String e(String str) {
        JSONObject f = f(str);
        com.tencent.qimei.k.a.b("SDK_INIT ｜ 本地加密", " 获取存储Qm:%s ", f.toString());
        return f.optString(str);
    }

    public static void a(String str, String str2, c cVar) {
        com.tencent.qimei.b.a.a().a(new com.tencent.qimei.d.b(str, str2, cVar));
    }

    public static synchronized void b(String str) {
        synchronized (a.class) {
            if (com.tencent.qimei.c.a.i()) {
                b.a.a.a(str, "");
            }
        }
    }

    public static Object a(String str, String str2, Class[] clsArr, Object[] objArr) {
        try {
            return Class.forName(str).getMethod(str2, clsArr).invoke(null, objArr);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | NullPointerException | SecurityException | InvocationTargetException unused) {
            return null;
        }
    }

    public static String a(String str, String str2) throws Exception {
        return new String(a(Base64.decode(str, 2), str2.getBytes("UTF-8"), 2), "UTF-8").trim();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0016  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0014 A[DONT_GENERATE] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized java.lang.String a(int r6) {
        /*
            java.lang.Class<com.tencent.qimei.a.a> r0 = com.tencent.qimei.a.a.class
            monitor-enter(r0)
            boolean r1 = com.tencent.qimei.beaconid.U.a     // Catch: java.lang.Throwable -> Lb6
            r2 = 0
            if (r1 == 0) goto L11
            java.lang.String[] r6 = com.tencent.qimei.beaconid.U.c(r6)     // Catch: java.lang.UnsatisfiedLinkError -> Ld java.lang.Throwable -> Lb6
            goto L12
        Ld:
            r6 = move-exception
            r6.printStackTrace()     // Catch: java.lang.Throwable -> Lb6
        L11:
            r6 = r2
        L12:
            if (r6 != 0) goto L16
            monitor-exit(r0)
            return r2
        L16:
            r1 = 0
            r2 = 0
        L18:
            int r3 = r6.length     // Catch: java.lang.Throwable -> Lb6
            if (r2 >= r3) goto L26
            r3 = r6[r2]     // Catch: java.lang.Throwable -> Lb6
            if (r3 != 0) goto L23
            java.lang.String r3 = ""
            r6[r2] = r3     // Catch: java.lang.Throwable -> Lb6
        L23:
            int r2 = r2 + 1
            goto L18
        L26:
            r2 = 0
        L27:
            int r3 = r6.length     // Catch: java.lang.Throwable -> Lb6
            if (r2 >= r3) goto L65
            r3 = r6[r2]     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r3 = r3.trim()     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r4 = " "
            java.lang.String r5 = ""
            java.lang.String r3 = r3.replace(r4, r5)     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r4 = "\t"
            java.lang.String r5 = ""
            java.lang.String r3 = r3.replace(r4, r5)     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r4 = "&"
            java.lang.String r5 = ""
            java.lang.String r3 = r3.replace(r4, r5)     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r4 = ":"
            java.lang.String r5 = ""
            java.lang.String r3 = r3.replace(r4, r5)     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r4 = "="
            java.lang.String r5 = ""
            java.lang.String r3 = r3.replace(r4, r5)     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r4 = ";"
            java.lang.String r5 = ""
            java.lang.String r3 = r3.replace(r4, r5)     // Catch: java.lang.Throwable -> Lb6
            r6[r2] = r3     // Catch: java.lang.Throwable -> Lb6
            int r2 = r2 + 1
            goto L27
        L65:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb6
            r2.<init>()     // Catch: java.lang.Throwable -> Lb6
        L6a:
            int r3 = r6.length     // Catch: java.lang.Throwable -> Lb6
            if (r1 >= r3) goto La7
            r3 = 9
            if (r1 < r3) goto L8b
            java.lang.String r3 = "k"
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb6
            int r3 = r1 + 2
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r3 = ":"
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb6
            r3 = r6[r1]     // Catch: java.lang.Throwable -> Lb6
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r3 = ";"
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb6
            goto La4
        L8b:
            java.lang.String r3 = "k"
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb6
            int r3 = r1 + 1
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r3 = ":"
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb6
            r3 = r6[r1]     // Catch: java.lang.Throwable -> Lb6
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r3 = ";"
            r2.append(r3)     // Catch: java.lang.Throwable -> Lb6
        La4:
            int r1 = r1 + 1
            goto L6a
        La7:
            java.lang.String r6 = "k10:"
            r2.append(r6)     // Catch: java.lang.Throwable -> Lb6
            r6 = 1
            r2.append(r6)     // Catch: java.lang.Throwable -> Lb6
            java.lang.String r6 = r2.toString()     // Catch: java.lang.Throwable -> Lb6
            monitor-exit(r0)
            return r6
        Lb6:
            r6 = move-exception
            monitor-exit(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.a.a.a(int):java.lang.String");
    }

    public static void a(String str, byte b) {
        com.tencent.qimei.n.c a = i.a().a(e.REPORT_CACHE.K, Byte.valueOf(b));
        a.a = str;
        a.c = "/report";
        a.a("v6");
    }

    public static String a(HttpURLConnection httpURLConnection) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        InputStream inputStream = httpURLConnection.getInputStream();
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                String byteArrayOutputStream2 = byteArrayOutputStream.toString("UTF-8");
                com.tencent.qimei.j.a.a(byteArrayOutputStream);
                com.tencent.qimei.j.a.a(inputStream);
                return byteArrayOutputStream2;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0010 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0011  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] a(byte[] r2, byte[] r3, int r4) throws java.lang.Exception {
        /*
            boolean r0 = com.tencent.qimei.beaconid.U.a
            if (r0 == 0) goto Ld
            byte[] r0 = com.tencent.qimei.beaconid.U.a(r4, r3, r3, r2)     // Catch: java.lang.UnsatisfiedLinkError -> L9
            goto Le
        L9:
            r0 = move-exception
            r0.printStackTrace()
        Ld:
            r0 = 0
        Le:
            if (r0 == 0) goto L11
            return r0
        L11:
            javax.crypto.spec.SecretKeySpec r0 = new javax.crypto.spec.SecretKeySpec
            java.lang.String r1 = "AES"
            r0.<init>(r3, r1)
            javax.crypto.spec.IvParameterSpec r1 = new javax.crypto.spec.IvParameterSpec
            r1.<init>(r3)
            java.lang.String r3 = "AES/CBC/PKCS5Padding"
            javax.crypto.Cipher r3 = javax.crypto.Cipher.getInstance(r3)
            r3.init(r4, r0, r1)
            byte[] r2 = r3.doFinal(r2)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.a.a.a(byte[], byte[], int):byte[]");
    }

    public static Object a(String str, Object obj, String str2) {
        try {
            Field declaredField = Class.forName(str).getDeclaredField(str2);
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException unused) {
            return null;
        }
    }
}
