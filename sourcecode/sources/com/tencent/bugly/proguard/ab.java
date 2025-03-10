package com.tencent.bugly.proguard;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.telegram.messenger.LiteMode;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class ab {
    private static final ArrayList<a> a = new ArrayList<a>() { // from class: com.tencent.bugly.proguard.ab.1
        {
            byte b2 = 0;
            add(new l(b2));
            add(new f(b2));
            add(new g(b2));
            add(new m(b2));
            add(new h(b2));
            add(new i(b2));
            add(new k(b2));
            add(new e(b2));
            add(new j(b2));
            add(new b(b2));
            add(new d(b2));
            add(new c(b2));
        }
    };
    private static final Map<Integer, String> b = new HashMap<Integer, String>() { // from class: com.tencent.bugly.proguard.ab.2
        {
            put(1, "GPRS");
            put(2, "EDGE");
            put(3, "UMTS");
            put(8, "HSDPA");
            put(9, "HSUPA");
            put(10, "HSPA");
            put(4, "CDMA");
            put(5, "EVDO_0");
            put(6, "EVDO_A");
            put(7, "1xRTT");
            put(11, "iDen");
            put(12, "EVDO_B");
            put(13, "LTE");
            put(14, "eHRPD");
            put(15, "HSPA+");
        }
    };
    private static final String[] c = {"/su", "/su/bin/su", "/sbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/data/local/su", "/system/xbin/su", "/system/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/system/bin/cufsdosck", "/system/xbin/cufsdosck", "/system/bin/cufsmgr", "/system/xbin/cufsmgr", "/system/bin/cufaevdd", "/system/xbin/cufaevdd", "/system/bin/conbb", "/system/xbin/conbb"};

    public static String m() {
        return "";
    }

    public static String a() {
        try {
            return Build.MODEL;
        } catch (Throwable th) {
            if (al.a(th)) {
                return "fail";
            }
            th.printStackTrace();
            return "fail";
        }
    }

    public static String b() {
        try {
            return Build.VERSION.RELEASE;
        } catch (Throwable th) {
            if (al.a(th)) {
                return "fail";
            }
            th.printStackTrace();
            return "fail";
        }
    }

    public static int c() {
        try {
            return Build.VERSION.SDK_INT;
        } catch (Throwable th) {
            if (al.a(th)) {
                return -1;
            }
            th.printStackTrace();
            return -1;
        }
    }

    private static boolean s() {
        try {
            return Environment.getExternalStorageState().equals("mounted");
        } catch (Throwable th) {
            if (al.a(th)) {
                return false;
            }
            th.printStackTrace();
            return false;
        }
    }

    public static String d() {
        try {
            return String.valueOf(System.getProperty("os.arch"));
        } catch (Throwable th) {
            if (al.a(th)) {
                return "fail";
            }
            th.printStackTrace();
            return "fail";
        }
    }

    public static String a(Context context) {
        if (context != null && context.getApplicationInfo() != null) {
            String str = context.getApplicationInfo().nativeLibraryDir;
            if (TextUtils.isEmpty(str)) {
                return "fail";
            }
            if (str.endsWith("arm")) {
                return "armeabi-v7a";
            }
            if (str.endsWith("arm64")) {
                return "arm64-v8a";
            }
            if (str.endsWith("x86")) {
                return "x86";
            }
            if (str.endsWith("x86_64")) {
                return "x86_64";
            }
        }
        return "fail";
    }

    public static long e() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return statFs.getBlockCount() * statFs.getBlockSize();
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return -1L;
        }
    }

    public static long f() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return statFs.getAvailableBlocks() * statFs.getBlockSize();
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return -1L;
        }
    }

    public static long b(Context context) {
        long pss;
        ActivityManager activityManager;
        try {
            activityManager = (ActivityManager) context.getSystemService("activity");
        } catch (Throwable unused) {
            pss = Debug.getPss();
        }
        if (activityManager == null) {
            return 0L;
        }
        pss = activityManager.getProcessMemoryInfo(new int[]{Process.myPid()})[0].getTotalPss();
        return pss * 1024;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001d, code lost:
    
        r0 = java.lang.Long.parseLong(r2.replaceAll("[^\\d]", ""));
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x0034 -> B:13:0x0046). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long g() {
        /*
            r0 = 0
            r2 = 0
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L3a
            java.io.FileReader r4 = new java.io.FileReader     // Catch: java.lang.Throwable -> L3a
            java.lang.String r5 = "/proc/self/status"
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L3a
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L3a
            java.lang.String r2 = r3.readLine()     // Catch: java.lang.Throwable -> L38
        L13:
            if (r2 == 0) goto L2f
            java.lang.String r4 = "VmSize"
            boolean r4 = r2.startsWith(r4)     // Catch: java.lang.Throwable -> L38
            if (r4 == 0) goto L2a
            java.lang.String r4 = "[^\\d]"
            java.lang.String r5 = ""
            java.lang.String r2 = r2.replaceAll(r4, r5)     // Catch: java.lang.Throwable -> L38
            long r0 = java.lang.Long.parseLong(r2)     // Catch: java.lang.Throwable -> L38
            goto L2f
        L2a:
            java.lang.String r2 = r3.readLine()     // Catch: java.lang.Throwable -> L38
            goto L13
        L2f:
            r3.close()     // Catch: java.lang.Throwable -> L33
            goto L46
        L33:
            r2 = move-exception
            r2.printStackTrace()
            goto L46
        L38:
            r2 = move-exception
            goto L3e
        L3a:
            r3 = move-exception
            r6 = r3
            r3 = r2
            r2 = r6
        L3e:
            com.tencent.bugly.proguard.al.a(r2)     // Catch: java.lang.Throwable -> L4b
            if (r3 == 0) goto L46
            r3.close()     // Catch: java.lang.Throwable -> L33
        L46:
            r2 = 1024(0x400, double:5.06E-321)
            long r0 = r0 * r2
            return r0
        L4b:
            r0 = move-exception
            if (r3 == 0) goto L56
            r3.close()     // Catch: java.lang.Throwable -> L52
            goto L56
        L52:
            r1 = move-exception
            r1.printStackTrace()
        L56:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ab.g():long");
    }

    public static long h() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    public static long i() {
        FileReader fileReader;
        Throwable th;
        BufferedReader bufferedReader;
        try {
            fileReader = new FileReader("/proc/meminfo");
            try {
                bufferedReader = new BufferedReader(fileReader, LiteMode.FLAG_AUTOPLAY_GIFS);
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
            }
        } catch (Throwable th3) {
            fileReader = null;
            th = th3;
            bufferedReader = null;
        }
        try {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                long parseLong = Long.parseLong(readLine.split(":\\s+", 2)[1].toLowerCase().replace("kb", "").trim()) * 1024;
                try {
                    bufferedReader.close();
                } catch (IOException e2) {
                    if (!al.a(e2)) {
                        e2.printStackTrace();
                    }
                }
                try {
                    fileReader.close();
                } catch (IOException e3) {
                    if (!al.a(e3)) {
                        e3.printStackTrace();
                    }
                }
                return parseLong;
            }
            try {
                bufferedReader.close();
            } catch (IOException e4) {
                if (!al.a(e4)) {
                    e4.printStackTrace();
                }
            }
            try {
                fileReader.close();
                return -1L;
            } catch (IOException e5) {
                if (al.a(e5)) {
                    return -1L;
                }
                e5.printStackTrace();
                return -1L;
            }
        } catch (Throwable th4) {
            th = th4;
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e6) {
                        if (!al.a(e6)) {
                            e6.printStackTrace();
                        }
                    }
                }
                if (fileReader == null) {
                    return -2L;
                }
                try {
                    fileReader.close();
                    return -2L;
                } catch (IOException e7) {
                    if (al.a(e7)) {
                        return -2L;
                    }
                    e7.printStackTrace();
                    return -2L;
                }
            } catch (Throwable th5) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e8) {
                        if (!al.a(e8)) {
                            e8.printStackTrace();
                        }
                    }
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e9) {
                        if (!al.a(e9)) {
                            e9.printStackTrace();
                        }
                    }
                }
                throw th5;
            }
        }
    }

    public static long j() {
        FileReader fileReader;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            fileReader = new FileReader("/proc/meminfo");
            try {
                bufferedReader = new BufferedReader(fileReader, LiteMode.FLAG_AUTOPLAY_GIFS);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileReader = null;
        }
        try {
            bufferedReader.readLine();
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                try {
                    bufferedReader.close();
                } catch (IOException e2) {
                    if (!al.a(e2)) {
                        e2.printStackTrace();
                    }
                }
                try {
                    fileReader.close();
                } catch (IOException e3) {
                    if (!al.a(e3)) {
                        e3.printStackTrace();
                    }
                }
                return -1L;
            }
            long parseLong = (Long.parseLong(readLine.split(":\\s+", 2)[1].toLowerCase().replace("kb", "").trim()) * 1024) + 0;
            String readLine2 = bufferedReader.readLine();
            if (readLine2 == null) {
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                    if (!al.a(e4)) {
                        e4.printStackTrace();
                    }
                }
                try {
                    fileReader.close();
                } catch (IOException e5) {
                    if (!al.a(e5)) {
                        e5.printStackTrace();
                    }
                }
                return -1L;
            }
            long parseLong2 = Long.parseLong(readLine2.split(":\\s+", 2)[1].toLowerCase().replace("kb", "").trim());
            Long.signum(parseLong2);
            long j2 = parseLong + (parseLong2 * 1024);
            String readLine3 = bufferedReader.readLine();
            if (readLine3 == null) {
                try {
                    bufferedReader.close();
                } catch (IOException e6) {
                    if (!al.a(e6)) {
                        e6.printStackTrace();
                    }
                }
                try {
                    fileReader.close();
                } catch (IOException e7) {
                    if (!al.a(e7)) {
                        e7.printStackTrace();
                    }
                }
                return -1L;
            }
            long parseLong3 = j2 + (Long.parseLong(readLine3.split(":\\s+", 2)[1].toLowerCase().replace("kb", "").trim()) * 1024);
            try {
                bufferedReader.close();
            } catch (IOException e8) {
                if (!al.a(e8)) {
                    e8.printStackTrace();
                }
            }
            try {
                fileReader.close();
            } catch (IOException e9) {
                if (!al.a(e9)) {
                    e9.printStackTrace();
                }
            }
            return parseLong3;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader2 = bufferedReader;
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException e10) {
                        if (!al.a(e10)) {
                            e10.printStackTrace();
                        }
                    }
                }
                if (fileReader == null) {
                    return -2L;
                }
                try {
                    fileReader.close();
                    return -2L;
                } catch (IOException e11) {
                    if (al.a(e11)) {
                        return -2L;
                    }
                    e11.printStackTrace();
                    return -2L;
                }
            } catch (Throwable th4) {
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException e12) {
                        if (!al.a(e12)) {
                            e12.printStackTrace();
                        }
                    }
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e13) {
                        if (!al.a(e13)) {
                            e13.printStackTrace();
                        }
                    }
                }
                throw th4;
            }
        }
    }

    public static long k() {
        if (!s()) {
            return 0L;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return statFs.getBlockCount() * statFs.getBlockSize();
        } catch (Throwable th) {
            if (al.a(th)) {
                return -2L;
            }
            th.printStackTrace();
            return -2L;
        }
    }

    public static long l() {
        if (!s()) {
            return 0L;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return statFs.getAvailableBlocks() * statFs.getBlockSize();
        } catch (Throwable th) {
            if (al.a(th)) {
                return -2L;
            }
            th.printStackTrace();
            return -2L;
        }
    }

    public static String c(Context context) {
        NetworkInfo activeNetworkInfo;
        TelephonyManager telephonyManager;
        String str = "unknown";
        try {
            activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception e2) {
            e = e2;
        }
        if (activeNetworkInfo == null) {
            return null;
        }
        if (activeNetworkInfo.getType() == 1) {
            return "WIFI";
        }
        if (activeNetworkInfo.getType() == 0 && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null) {
            int networkType = telephonyManager.getNetworkType();
            String str2 = b.get(Integer.valueOf(networkType));
            if (str2 == null) {
                try {
                    str = "MOBILE(" + networkType + ")";
                } catch (Exception e3) {
                    e = e3;
                    str = str2;
                    if (!al.a(e)) {
                        e.printStackTrace();
                    }
                    return str;
                }
            } else {
                str = str2;
            }
        }
        return str;
    }

    public static String n() {
        Iterator<a> it = a.iterator();
        while (it.hasNext()) {
            String a2 = it.next().a();
            if (!TextUtils.isEmpty(a2)) {
                return a2;
            }
        }
        return null;
    }

    public static boolean o() {
        return !TextUtils.isEmpty(new i((byte) 0).a());
    }

    public static boolean p() {
        return !TextUtils.isEmpty(new k((byte) 0).a());
    }

    public static boolean q() {
        boolean z;
        String[] strArr = c;
        int length = strArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                z = false;
                break;
            }
            if (new File(strArr[i2]).exists()) {
                z = true;
                break;
            }
            i2++;
        }
        String str = Build.TAGS;
        return (str != null && str.contains("test-keys")) || z;
    }

    public static boolean r() {
        float maxMemory = (float) ((Runtime.getRuntime().maxMemory() * 1.0d) / 1048576.0d);
        float f2 = (float) ((Runtime.getRuntime().totalMemory() * 1.0d) / 1048576.0d);
        float f3 = maxMemory - f2;
        al.c("maxMemory : %f", Float.valueOf(maxMemory));
        al.c("totalMemory : %f", Float.valueOf(f2));
        al.c("freeMemory : %f", Float.valueOf(f3));
        return f3 < 10.0f;
    }

    /* compiled from: BUGLY */
    static abstract class a {
        public abstract String a();

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }
    }

    /* compiled from: BUGLY */
    static class l extends a {
        private l() {
            super((byte) 0);
        }

        /* synthetic */ l(byte b) {
            this();
        }

        @Override // com.tencent.bugly.proguard.ab.a
        public final String a() {
            String a = ap.a("ro.miui.ui.version.name");
            if (ap.b(a) || a.equals("fail")) {
                return null;
            }
            return "XiaoMi/MIUI/".concat(a);
        }
    }

    /* compiled from: BUGLY */
    static class f extends a {
        private f() {
            super((byte) 0);
        }

        /* synthetic */ f(byte b) {
            this();
        }

        @Override // com.tencent.bugly.proguard.ab.a
        public final String a() {
            String a = ap.a("ro.build.version.emui");
            if (ap.b(a) || a.equals("fail")) {
                return null;
            }
            return "HuaWei/EMOTION/".concat(a);
        }
    }

    /* compiled from: BUGLY */
    static class g extends a {
        private g() {
            super((byte) 0);
        }

        /* synthetic */ g(byte b) {
            this();
        }

        @Override // com.tencent.bugly.proguard.ab.a
        public final String a() {
            String a = ap.a("ro.lenovo.series");
            if (ap.b(a) || a.equals("fail")) {
                return null;
            }
            return "Lenovo/VIBE/".concat(String.valueOf(ap.a("ro.build.version.incremental")));
        }
    }

    /* compiled from: BUGLY */
    static class m extends a {
        private m() {
            super((byte) 0);
        }

        /* synthetic */ m(byte b) {
            this();
        }

        @Override // com.tencent.bugly.proguard.ab.a
        public final String a() {
            String a = ap.a("ro.build.nubia.rom.name");
            if (ap.b(a) || a.equals("fail")) {
                return null;
            }
            return "Zte/NUBIA/" + a + "_" + ap.a("ro.build.nubia.rom.code");
        }
    }

    /* compiled from: BUGLY */
    static class h extends a {
        private h() {
            super((byte) 0);
        }

        /* synthetic */ h(byte b) {
            this();
        }

        @Override // com.tencent.bugly.proguard.ab.a
        public final String a() {
            String a = ap.a("ro.meizu.product.model");
            if (ap.b(a) || a.equals("fail")) {
                return null;
            }
            return "Meizu/FLYME/" + ap.a("ro.build.display.id");
        }
    }

    /* compiled from: BUGLY */
    static class i extends a {
        private i() {
            super((byte) 0);
        }

        /* synthetic */ i(byte b) {
            this();
        }

        @Override // com.tencent.bugly.proguard.ab.a
        public final String a() {
            String a = ap.a("ro.build.version.opporom");
            if (ap.b(a) || a.equals("fail")) {
                return null;
            }
            return "Oppo/COLOROS/".concat(a);
        }
    }

    /* compiled from: BUGLY */
    static class k extends a {
        private k() {
            super((byte) 0);
        }

        /* synthetic */ k(byte b) {
            this();
        }

        @Override // com.tencent.bugly.proguard.ab.a
        public final String a() {
            String a = ap.a("ro.vivo.os.build.display.id");
            if (ap.b(a) || a.equals("fail")) {
                return null;
            }
            return "vivo/FUNTOUCH/".concat(a);
        }
    }

    /* compiled from: BUGLY */
    static class e extends a {
        private e() {
            super((byte) 0);
        }

        /* synthetic */ e(byte b) {
            this();
        }

        @Override // com.tencent.bugly.proguard.ab.a
        public final String a() {
            String a = ap.a("ro.aa.romver");
            if (ap.b(a) || a.equals("fail")) {
                return null;
            }
            return "htc/" + a + "/" + ap.a("ro.build.description");
        }
    }

    /* compiled from: BUGLY */
    static class j extends a {
        private j() {
            super((byte) 0);
        }

        /* synthetic */ j(byte b) {
            this();
        }

        @Override // com.tencent.bugly.proguard.ab.a
        public final String a() {
            String a = ap.a("ro.lewa.version");
            if (ap.b(a) || a.equals("fail")) {
                return null;
            }
            return "tcl/" + a + "/" + ap.a("ro.build.display.id");
        }
    }

    /* compiled from: BUGLY */
    static class b extends a {
        private b() {
            super((byte) 0);
        }

        /* synthetic */ b(byte b) {
            this();
        }

        @Override // com.tencent.bugly.proguard.ab.a
        public final String a() {
            String a = ap.a("ro.gn.gnromvernumber");
            if (ap.b(a) || a.equals("fail")) {
                return null;
            }
            return "amigo/" + a + "/" + ap.a("ro.build.display.id");
        }
    }

    /* compiled from: BUGLY */
    static class d extends a {
        private d() {
            super((byte) 0);
        }

        /* synthetic */ d(byte b) {
            this();
        }

        @Override // com.tencent.bugly.proguard.ab.a
        public final String a() {
            String a = ap.a("ro.build.tyd.kbstyle_version");
            if (ap.b(a) || a.equals("fail")) {
                return null;
            }
            return "dido/".concat(a);
        }
    }

    /* compiled from: BUGLY */
    static class c extends a {
        private c() {
            super((byte) 0);
        }

        /* synthetic */ c(byte b) {
            this();
        }

        @Override // com.tencent.bugly.proguard.ab.a
        public final String a() {
            return ap.a("ro.build.fingerprint") + "/" + ap.a("ro.build.rom.id");
        }
    }
}
