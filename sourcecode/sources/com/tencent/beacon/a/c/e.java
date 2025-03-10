package com.tencent.beacon.a.c;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.tencent.beacon.a.d.a;
import com.tencent.beacon.base.net.b.e;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;
import org.telegram.messenger.LiteMode;

/* compiled from: DeviceInfo.java */
/* loaded from: classes.dex */
public class e implements e.a {
    private static volatile e a;
    private final Context b;
    private String c = "";
    private String d = "";
    private String e = "";
    private String f = "";
    private String g = "";

    private e() {
        Context c = c.d().c();
        this.b = c;
        com.tencent.beacon.base.net.b.e.a(c, this);
        E();
    }

    private void E() {
        this.f = F();
    }

    private String F() {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        TelephonyManager telephonyManager;
        String str = "unknown";
        try {
            connectivityManager = (ConnectivityManager) this.b.getSystemService("connectivity");
        } catch (Exception e) {
            com.tencent.beacon.base.util.c.a(e);
        }
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
            return "unknown";
        }
        if (activeNetworkInfo.getType() == 1) {
            str = "wifi";
        } else if (activeNetworkInfo.getType() == 0 && (telephonyManager = (TelephonyManager) this.b.getSystemService("phone")) != null) {
            int networkType = telephonyManager.getNetworkType();
            if (networkType != 20) {
                switch (networkType) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        str = "2G";
                        break;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        str = "3G";
                        break;
                    case 13:
                        str = "4G";
                        break;
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("unknown_");
                        sb.append(telephonyManager.getNetworkType());
                        str = sb.toString();
                        break;
                }
            } else {
                str = "5G";
            }
        }
        com.tencent.beacon.base.util.c.a("[DeviceInfo] NetWork Type:" + str, new Object[0]);
        return str;
    }

    public static e l() {
        if (a == null) {
            synchronized (e.class) {
                if (a == null) {
                    a = new e();
                }
            }
        }
        return a;
    }

    public String A() {
        return "";
    }

    public void B() {
        String string = com.tencent.beacon.a.d.a.a().getString("BEACON_ANDROID_ID_DENGTA", "");
        this.c = string;
        if (TextUtils.isEmpty(string)) {
            this.c = com.tencent.beacon.base.util.b.a();
            a.SharedPreferencesEditorC0016a edit = com.tencent.beacon.a.d.a.a().edit();
            if (com.tencent.beacon.base.util.b.a((SharedPreferences.Editor) edit)) {
                edit.putString("BEACON_ANDROID_ID_DENGTA", this.c);
            }
        }
    }

    public boolean C() {
        BufferedReader bufferedReader;
        boolean z;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]{"/system/bin/cat", "/proc/cpuinfo"}).getInputStream(), Charset.defaultCharset()));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        z = false;
                        break;
                    }
                    if (-1 != readLine.toLowerCase().indexOf("armv7")) {
                        z = true;
                        break;
                    }
                } catch (Throwable th) {
                    th = th;
                    try {
                        com.tencent.beacon.base.util.c.a(th);
                        com.tencent.beacon.base.util.b.a(bufferedReader);
                        return false;
                    } catch (Throwable th2) {
                        com.tencent.beacon.base.util.b.a(bufferedReader);
                        throw th2;
                    }
                }
            }
            com.tencent.beacon.base.util.b.a(bufferedReader);
            return z;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
        }
    }

    public String D() {
        if (TextUtils.isEmpty(this.g)) {
            try {
                Class<?> cls = Class.forName("com.huawei.system.BuildEx");
                String str = "harmony".equals(cls.getMethod("getOsBrand", new Class[0]).invoke(cls, new Object[0])) ? "Y" : "N";
                this.g = str;
                return str;
            } catch (Throwable unused) {
                this.g = "N";
            }
        }
        return this.g;
    }

    public String a(Context context) {
        try {
            if (Integer.parseInt(Build.VERSION.SDK) < 9) {
                com.tencent.beacon.base.util.c.b("[audit] Api level < 9;return null!", new Object[0]);
                return "";
            }
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            StringBuilder sb = new StringBuilder();
            sb.append("[audit] get app_last_updated_time:");
            sb.append(String.valueOf(packageInfo.lastUpdateTime));
            com.tencent.beacon.base.util.c.a(sb.toString(), new Object[0]);
            return String.valueOf(packageInfo.lastUpdateTime);
        } catch (Throwable th) {
            com.tencent.beacon.base.util.c.a(th);
            com.tencent.beacon.base.util.c.b("[audit] get app_last_updated_time failed!", new Object[0]);
            return "";
        }
    }

    @Override // com.tencent.beacon.base.net.b.e.a
    public void b() {
        E();
    }

    public boolean c() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public String d() {
        return this.c;
    }

    public int e() {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]{"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"}).getInputStream(), Charset.forName("UTF-8")));
        } catch (Throwable th) {
            th = th;
            bufferedReader = null;
        }
        try {
            String readLine = bufferedReader.readLine();
            int parseInt = readLine != null ? Integer.parseInt(readLine.trim()) / 1000 : 0;
            com.tencent.beacon.base.util.b.a(bufferedReader);
            return parseInt;
        } catch (Throwable th2) {
            th = th2;
            try {
                com.tencent.beacon.base.util.c.a(th);
                com.tencent.beacon.base.util.b.a(bufferedReader);
                return 0;
            } catch (Throwable th3) {
                com.tencent.beacon.base.util.b.a(bufferedReader);
                throw th3;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0038, code lost:
    
        r0 = r1.substring(r1.indexOf(":") + 1).trim();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String f() {
        /*
            r7 = this;
            java.lang.String r0 = ""
            java.lang.String r1 = "/system/bin/cat"
            java.lang.String r2 = "/proc/cpuinfo"
            java.lang.String[] r1 = new java.lang.String[]{r1, r2}
            r2 = 0
            r3 = 1
            java.lang.Runtime r4 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L4f
            java.lang.Process r1 = r4.exec(r1)     // Catch: java.lang.Throwable -> L4f
            java.io.InputStream r1 = r1.getInputStream()     // Catch: java.lang.Throwable -> L4f
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L4f
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L4f
            java.lang.String r6 = "UTF-8"
            java.nio.charset.Charset r6 = java.nio.charset.Charset.forName(r6)     // Catch: java.lang.Throwable -> L4f
            r5.<init>(r1, r6)     // Catch: java.lang.Throwable -> L4f
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L4f
        L28:
            java.lang.String r1 = r4.readLine()     // Catch: java.lang.Throwable -> L4d
            if (r1 == 0) goto L45
            java.lang.String r5 = "Processor"
            boolean r5 = r1.contains(r5)     // Catch: java.lang.Throwable -> L4d
            if (r5 == 0) goto L28
            java.lang.String r5 = ":"
            int r5 = r1.indexOf(r5)     // Catch: java.lang.Throwable -> L4d
            int r5 = r5 + r3
            java.lang.String r1 = r1.substring(r5)     // Catch: java.lang.Throwable -> L4d
            java.lang.String r0 = r1.trim()     // Catch: java.lang.Throwable -> L4d
        L45:
            java.io.Closeable[] r1 = new java.io.Closeable[r3]
            r1[r2] = r4
            com.tencent.beacon.base.util.b.a(r1)
            goto L5b
        L4d:
            r1 = move-exception
            goto L51
        L4f:
            r1 = move-exception
            r4 = 0
        L51:
            com.tencent.beacon.base.util.c.a(r1)     // Catch: java.lang.Throwable -> L5c
            java.io.Closeable[] r1 = new java.io.Closeable[r3]
            r1[r2] = r4
            com.tencent.beacon.base.util.b.a(r1)
        L5b:
            return r0
        L5c:
            r0 = move-exception
            java.io.Closeable[] r1 = new java.io.Closeable[r3]
            r1[r2] = r4
            com.tencent.beacon.base.util.b.a(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.a.c.e.f():java.lang.String");
    }

    public String g() {
        return Locale.getDefault().getCountry();
    }

    public String h() {
        return Build.HARDWARE;
    }

    public DisplayMetrics i() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this.b.getSystemService("window");
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics;
    }

    public long j() {
        ActivityManager activityManager = (ActivityManager) this.b.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        if (activityManager == null) {
            return -1L;
        }
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public String k() {
        long j = j();
        if (j <= 0) {
            return "0";
        }
        return ((j / 1024) / 1024) + "";
    }

    public boolean m() {
        return com.tencent.beacon.base.util.d.d();
    }

    public String n() {
        return Locale.getDefault().getLanguage();
    }

    public String o() {
        return Build.MANUFACTURER;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: PHI empty after try-catch fix!
        	at jadx.core.dex.visitors.ssa.SSATransform.fixPhiInTryCatch(SSATransform.java:222)
        	at jadx.core.dex.visitors.ssa.SSATransform.fixLastAssignInTry(SSATransform.java:202)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:58)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:44)
        */
    public java.lang.String p() {
        /*
            r10 = this;
            java.lang.String r0 = "getNandInfo error"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r2 = 0
            r3 = 1
            r4 = 0
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2d
            java.io.FileReader r6 = new java.io.FileReader     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2d
            java.io.File r7 = new java.io.File     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2d
            java.lang.String r8 = "/sys/block/mmcblk0/device/type"
            r7.<init>(r8)     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2d
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2d
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L2a java.lang.Exception -> L2d
            java.lang.String r6 = r5.readLine()     // Catch: java.lang.Exception -> L2e java.lang.Throwable -> Lcf
            r1.append(r6)     // Catch: java.lang.Exception -> L2e java.lang.Throwable -> Lcf
            java.io.Closeable[] r6 = new java.io.Closeable[r3]
            r6[r4] = r5
            com.tencent.beacon.base.util.b.a(r6)
            goto L3a
        L2a:
            r0 = move-exception
            goto Ld1
        L2d:
            r5 = r2
        L2e:
            java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> Lcf
            com.tencent.beacon.base.util.c.a(r0, r6)     // Catch: java.lang.Throwable -> Lcf
            java.io.Closeable[] r6 = new java.io.Closeable[r3]
            r6[r4] = r5
            com.tencent.beacon.base.util.b.a(r6)
        L3a:
            java.lang.String r5 = ","
            r1.append(r5)
            java.lang.String r6 = "/sys/block/mmcblk0/device/name"
            java.io.BufferedReader r7 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L5f java.lang.Exception -> L61
            java.io.FileReader r8 = new java.io.FileReader     // Catch: java.lang.Throwable -> L5f java.lang.Exception -> L61
            java.io.File r9 = new java.io.File     // Catch: java.lang.Throwable -> L5f java.lang.Exception -> L61
            r9.<init>(r6)     // Catch: java.lang.Throwable -> L5f java.lang.Exception -> L61
            r8.<init>(r9)     // Catch: java.lang.Throwable -> L5f java.lang.Exception -> L61
            r7.<init>(r8)     // Catch: java.lang.Throwable -> L5f java.lang.Exception -> L61
            java.lang.String r6 = r7.readLine()     // Catch: java.lang.Exception -> L62 java.lang.Throwable -> Lc5
            r1.append(r6)     // Catch: java.lang.Exception -> L62 java.lang.Throwable -> Lc5
            java.io.Closeable[] r6 = new java.io.Closeable[r3]
            r6[r4] = r7
            com.tencent.beacon.base.util.b.a(r6)
            goto L6e
        L5f:
            r0 = move-exception
            goto Lc7
        L61:
            r7 = r2
        L62:
            java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> Lc5
            com.tencent.beacon.base.util.c.a(r0, r6)     // Catch: java.lang.Throwable -> Lc5
            java.io.Closeable[] r6 = new java.io.Closeable[r3]
            r6[r4] = r7
            com.tencent.beacon.base.util.b.a(r6)
        L6e:
            r1.append(r5)
            java.lang.String r5 = "/sys/block/mmcblk0/device/cid"
            java.io.BufferedReader r6 = new java.io.BufferedReader     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lbc
            java.io.FileReader r7 = new java.io.FileReader     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lbc
            java.io.File r8 = new java.io.File     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lbc
            r8.<init>(r5)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lbc
            r7.<init>(r8)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lbc
            r6.<init>(r7)     // Catch: java.lang.Exception -> L95 java.lang.Throwable -> Lbc
            java.lang.String r2 = r6.readLine()     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L94
            r1.append(r2)     // Catch: java.lang.Throwable -> L91 java.lang.Exception -> L94
            java.io.Closeable[] r0 = new java.io.Closeable[r3]
            r0[r4] = r6
            com.tencent.beacon.base.util.b.a(r0)
            goto La1
        L91:
            r0 = move-exception
            r2 = r6
            goto Lbd
        L94:
            r2 = r6
        L95:
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> Lbc
            com.tencent.beacon.base.util.c.a(r0, r5)     // Catch: java.lang.Throwable -> Lbc
            java.io.Closeable[] r0 = new java.io.Closeable[r3]
            r0[r4] = r2
            com.tencent.beacon.base.util.b.a(r0)
        La1:
            java.lang.String r0 = r1.toString()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "[DeviceInfo] Nand Info:"
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            java.lang.Object[] r2 = new java.lang.Object[r4]
            com.tencent.beacon.base.util.c.a(r1, r2)
            return r0
        Lbc:
            r0 = move-exception
        Lbd:
            java.io.Closeable[] r1 = new java.io.Closeable[r3]
            r1[r4] = r2
            com.tencent.beacon.base.util.b.a(r1)
            throw r0
        Lc5:
            r0 = move-exception
            r2 = r7
        Lc7:
            java.io.Closeable[] r1 = new java.io.Closeable[r3]
            r1[r4] = r2
            com.tencent.beacon.base.util.b.a(r1)
            throw r0
        Lcf:
            r0 = move-exception
            r2 = r5
        Ld1:
            java.io.Closeable[] r1 = new java.io.Closeable[r3]
            r1[r4] = r2
            com.tencent.beacon.base.util.b.a(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.a.c.e.p():java.lang.String");
    }

    public String q() {
        return this.f;
    }

    public int r() {
        try {
            File[] listFiles = new File("/sys/devices/system/cpu/").listFiles(new d(this));
            if (listFiles == null) {
                return 1;
            }
            return listFiles.length;
        } catch (Exception e) {
            com.tencent.beacon.base.util.c.b("[model] CPU Count: Failed.", new Object[0]);
            com.tencent.beacon.base.util.c.a(e);
            return 1;
        }
    }

    public String s() {
        if (!TextUtils.isEmpty(this.d)) {
            return this.d;
        }
        String str = "Android " + Build.VERSION.RELEASE + ",level " + Build.VERSION.SDK;
        this.d = str;
        com.tencent.beacon.base.util.c.a("[DeviceInfo] os version: %s", str);
        return this.d;
    }

    public String t() {
        BufferedReader bufferedReader;
        FileReader fileReader;
        String readLine;
        try {
            fileReader = new FileReader("/proc/meminfo");
            try {
                bufferedReader = new BufferedReader(fileReader, LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM);
            } catch (Throwable th) {
                th = th;
                bufferedReader = null;
            }
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
            fileReader = null;
        }
        try {
            readLine = bufferedReader.readLine();
        } catch (Throwable th3) {
            th = th3;
            try {
                com.tencent.beacon.base.util.c.b("[model] get free RAM error!", new Object[0]);
                com.tencent.beacon.base.util.c.a(th);
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th4) {
                        com.tencent.beacon.base.util.c.b("[model] IO close error!", new Object[0]);
                        com.tencent.beacon.base.util.c.a(th4);
                    }
                }
                if (fileReader != null) {
                    fileReader.close();
                }
                return null;
            } catch (Throwable th5) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th6) {
                        com.tencent.beacon.base.util.c.b("[model] IO close error!", new Object[0]);
                        com.tencent.beacon.base.util.c.a(th6);
                        throw th5;
                    }
                }
                if (fileReader != null) {
                    fileReader.close();
                }
                throw th5;
            }
        }
        if (readLine == null) {
            try {
                bufferedReader.close();
                fileReader.close();
            } catch (Throwable th7) {
                com.tencent.beacon.base.util.c.b("[model] IO close error!", new Object[0]);
                com.tencent.beacon.base.util.c.a(th7);
            }
            return null;
        }
        String str = (Long.parseLong(readLine.split(":\\s+", 2)[1].toLowerCase().replace("kb", "").trim()) / 1024) + "";
        try {
            bufferedReader.close();
            fileReader.close();
        } catch (Throwable th8) {
            com.tencent.beacon.base.util.c.b("[model] IO close error!", new Object[0]);
            com.tencent.beacon.base.util.c.a(th8);
        }
        return str;
    }

    public String u() {
        DisplayMetrics i = i();
        if (i == null) {
            return "";
        }
        return i.widthPixels + "*" + i.heightPixels;
    }

    public String v() {
        ArrayList<String> a2 = com.tencent.beacon.base.util.b.a(new String[]{"/system/bin/sh", "-c", "getprop ro.build.fingerprint"});
        return (a2 == null || a2.size() <= 0) ? "" : a2.get(0);
    }

    public String w() {
        if (!TextUtils.isEmpty(this.e)) {
            return this.e;
        }
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long blockSize = statFs.getBlockSize();
        this.e = (((statFs.getBlockCount() * blockSize) / 1024) / 1024) + "";
        com.tencent.beacon.base.util.c.a("[DeviceInfo] Rom Size:" + this.e, new Object[0]);
        return this.e;
    }

    public long x() {
        if (!c()) {
            return 0L;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((statFs.getBlockCount() * statFs.getBlockSize()) / 1024) / 1024;
        } catch (Throwable th) {
            com.tencent.beacon.base.util.c.a(th);
            return 0L;
        }
    }

    public int y() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this.b.getSystemService("window");
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            double d = displayMetrics.density;
            if (d - 0.75d <= 1.0E-6d) {
                return 120;
            }
            if (d - 1.5d <= 1.0E-6d) {
                return 240;
            }
            if (d - 2.0d <= 1.0E-6d) {
                return 320;
            }
            if (d - 3.0d <= 1.0E-6d) {
                return 480;
            }
        }
        return 160;
    }

    public String z() {
        return "";
    }

    @Override // com.tencent.beacon.base.net.b.e.a
    public void a() {
        E();
    }
}
