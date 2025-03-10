package com.tencent.beacon.base.net.b;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.tencent.beacon.a.b.g;
import com.tencent.beacon.a.c.f;
import com.tencent.beacon.e.h;
import com.tencent.beacon.pack.RequestPackage;
import com.tencent.cos.xml.crypto.Headers;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: NetUtils.java */
/* loaded from: classes.dex */
public final class d {
    private static Map<String, String> a;

    public static boolean a(Map<String, List<String>> map) {
        if (map == null) {
            return false;
        }
        if (map.containsKey("encrypt-status")) {
            com.tencent.beacon.base.util.c.a("[BeaconNet]", "parse response header fail! cause by svr encrypt error!", new Object[0]);
            return false;
        }
        if (!map.containsKey("session_id") || !map.containsKey("max_time")) {
            return true;
        }
        List<String> list = map.get("session_id");
        List<String> list2 = map.get("max_time");
        String str = list != null ? list.get(0) : null;
        String str2 = list2 != null ? list2.get(0) : null;
        if (str == null || str2 == null) {
            return true;
        }
        a(str, str2);
        return true;
    }

    public static NetworkInfo b() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) com.tencent.beacon.a.c.c.d().c().getSystemService("connectivity");
            if (connectivityManager == null) {
                return null;
            }
            return connectivityManager.getActiveNetworkInfo();
        } catch (Throwable th) {
            com.tencent.beacon.base.util.c.a(th);
            return null;
        }
    }

    public static void c(Map<String, String> map) {
        if (map != null) {
            if (map.containsKey("encrypt-status")) {
                com.tencent.beacon.base.util.c.a("[BeaconNet]", "parse response header fail! cause by svr encrypt error!", new Object[0]);
            }
            if (map.containsKey("session_id") && map.containsKey("max_time")) {
                a(map.get("session_id"), map.get("max_time"));
            }
        }
    }

    public static boolean d() {
        NetworkInfo b = b();
        return b != null && b.isConnected();
    }

    public static String b(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.trim().length() > 0 && a(key)) {
                String trim = key.trim();
                sb.append("&");
                sb.append(trim.replace("|", "%7C").replace("&", "%26").replace("=", "%3D"));
                sb.append("=");
                String value = entry.getValue();
                if (value != null) {
                    sb.append(value.trim().replace('\n', ' ').replace('\r', ' ').replace("|", "%7C").replace("&", "%26").replace("=", "%3D"));
                }
            } else {
                com.tencent.beacon.base.util.c.e("[core] '%s' should be ASCII code in 32-126!", key);
            }
        }
        return sb.length() > 0 ? sb.substring(1) : "";
    }

    public static String c() {
        NetworkInfo b = b();
        if (b == null) {
            return "unknown";
        }
        if (b.getType() == 1) {
            return "wifi";
        }
        String extraInfo = b.getExtraInfo();
        if (extraInfo != null && extraInfo.length() > 64) {
            extraInfo = extraInfo.substring(0, 64);
        }
        return "" + extraInfo;
    }

    private static void a(String str, String str2) {
        com.tencent.beacon.base.util.c.a("[BeaconNet]", "update strategy sid: %s, max_time: %s", str, str2);
        h.b().a(str, str2);
    }

    public static RequestPackage a(int i, byte[] bArr, Map<String, String> map, String str) {
        com.tencent.beacon.a.c.c d = com.tencent.beacon.a.c.c.d();
        com.tencent.beacon.a.c.e l = com.tencent.beacon.a.c.e.l();
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.model = f.e().h();
        requestPackage.osVersion = l.s();
        requestPackage.platformId = d.h();
        requestPackage.appkey = str;
        requestPackage.appVersion = com.tencent.beacon.a.c.b.a();
        requestPackage.sdkId = d.i();
        requestPackage.sdkVersion = d.j();
        requestPackage.cmd = i;
        requestPackage.encryType = (byte) 3;
        requestPackage.zipType = (byte) 2;
        requestPackage.sBuffer = bArr;
        requestPackage.reserved = b(map);
        return requestPackage;
    }

    public static Map<String, String> a(String str, Map<String, String> map) {
        if (map == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        int i = 0;
        int i2 = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String valueOf = String.valueOf(entry.getKey());
            int length = valueOf.trim().length();
            if (length > 0 && a(valueOf)) {
                String trim = valueOf.trim();
                if (length > 64) {
                    trim = trim.substring(i, 64);
                    String str2 = "[event] eventName: " + str + ", key: " + trim + " should be less than 64!";
                    g.e().a("102", str2);
                    com.tencent.beacon.base.util.e.a(str2);
                }
                String replace = trim.replace("|", "%7C").replace("&", "%26").replace("=", "%3D");
                String trim2 = String.valueOf(entry.getValue()).trim();
                if (trim2.length() > 10240) {
                    String str3 = "[event] eventName: " + str + ", key: " + replace + "'s value > 10K.";
                    g.e().a("103", str3);
                    com.tencent.beacon.base.util.e.a(str3);
                    trim2 = trim2.substring(0, 10240);
                }
                String replace2 = trim2.replace('\n', ' ').replace('\r', ' ').replace("|", "%7C").replace("&", "%26").replace("=", "%3D");
                hashMap.put(replace, replace2);
                i2 += replace.length() + replace2.length();
                i = 0;
            } else {
                i = 0;
                com.tencent.beacon.base.util.c.e("[core] '%s' should be ASCII code in 32-126!", valueOf);
                g.e().a("102", "[event] eventName: " + str + ", key: " + valueOf + " should be ASCII code in 32-126!");
            }
        }
        if (i2 <= 46080) {
            return hashMap;
        }
        String str4 = "[event] eventName: " + str + " params > 45K";
        g.e().a("104", str4);
        com.tencent.beacon.base.util.e.a(str4);
        return null;
    }

    public static boolean a(String str) {
        int length = str.length();
        boolean z = true;
        while (true) {
            length--;
            if (length < 0) {
                return z;
            }
            char charAt = str.charAt(length);
            if (charAt < ' ' || charAt >= 127) {
                z = false;
            }
        }
    }

    public static synchronized Map<String, String> a() {
        Map<String, String> map;
        synchronized (d.class) {
            if (a == null) {
                HashMap hashMap = new HashMap(4);
                a = hashMap;
                hashMap.put("wup_version", "3.0");
                a.put("TYPE_COMPRESS", String.valueOf(2));
                a.put("encr_type", "rsapost");
                a.put(Headers.CONTENT_TYPE, "jce");
                h b = h.b();
                if (b != null) {
                    a.put("bea_key", b.d());
                }
            }
            map = a;
        }
        return map;
    }

    public static void a(long j, long j2, String str) {
        com.tencent.beacon.base.util.c.a("[BeaconNet]", "fixBeaconInfo, serverTime: " + j2 + ",ip: " + str, new Object[0]);
        com.tencent.beacon.a.c.c d = com.tencent.beacon.a.c.c.d();
        d.b(str);
        d.a(j2 - ((j + new Date().getTime()) / 2));
    }
}
