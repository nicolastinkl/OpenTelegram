package com.tencent.qimei.c;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.tencent.qimei.beaconid.U;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/* compiled from: DeviceInfo.java */
@SuppressLint({"MissingPermission"})
/* loaded from: classes.dex */
public class c {
    public static final c a = new c();
    public long c;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;
    public String n;
    public long d = 0;
    public Enumeration<NetworkInterface> o = null;
    public final Context b = com.tencent.qimei.u.d.b().J();

    public c() {
        v();
    }

    public static synchronized c j() {
        c cVar;
        synchronized (c.class) {
            cVar = a;
        }
        return cVar;
    }

    @SuppressLint({"HardwareIds"})
    public synchronized String b() {
        String str = this.e;
        if (str != null) {
            return str;
        }
        String str2 = "";
        Context context = this.b;
        if (context == null) {
            return "";
        }
        try {
            str2 = Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            com.tencent.qimei.k.a.a(e);
        }
        String lowerCase = str2 != null ? str2.toLowerCase() : "";
        this.e = lowerCase;
        return lowerCase;
    }

    public synchronized String c() {
        if (this.j == null) {
            this.j = com.tencent.qimei.a.a.a(Build.VERSION.SDK_INT);
        }
        return this.j;
    }

    public synchronized String d() {
        String str = this.k;
        if (str != null) {
            return str;
        }
        String str2 = Build.MODEL;
        this.k = str2;
        return str2;
    }

    public synchronized String e() {
        return "";
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x007c A[Catch: all -> 0x00ab, TRY_LEAVE, TryCatch #0 {all -> 0x00ab, blocks: (B:3:0x0004, B:5:0x0008, B:9:0x0042, B:12:0x004f, B:15:0x0076, B:17:0x007c, B:19:0x0086, B:20:0x0090, B:22:0x0096, B:24:0x00a0, B:30:0x005a, B:32:0x0060, B:34:0x0066, B:36:0x006c, B:40:0x0013, B:42:0x001b, B:44:0x0023, B:46:0x0029, B:51:0x0030, B:56:0x0038), top: B:2:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0096 A[Catch: all -> 0x00ab, TRY_LEAVE, TryCatch #0 {all -> 0x00ab, blocks: (B:3:0x0004, B:5:0x0008, B:9:0x0042, B:12:0x004f, B:15:0x0076, B:17:0x007c, B:19:0x0086, B:20:0x0090, B:22:0x0096, B:24:0x00a0, B:30:0x005a, B:32:0x0060, B:34:0x0066, B:36:0x006c, B:40:0x0013, B:42:0x001b, B:44:0x0023, B:46:0x0029, B:51:0x0030, B:56:0x0038), top: B:2:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String f() {
        /*
            r8 = this;
            java.lang.String r0 = ".environment.rc"
            java.lang.String r1 = ""
            java.io.File r2 = new java.io.File     // Catch: java.lang.Throwable -> Lab
            java.lang.String r3 = "/"
            r2.<init>(r3)     // Catch: java.lang.Throwable -> Lab
            java.io.File[] r3 = r2.listFiles()     // Catch: java.lang.Throwable -> Lab
            if (r3 != 0) goto L13
        L11:
            r0 = r1
            goto L40
        L13:
            java.io.File[] r2 = r2.listFiles()     // Catch: java.lang.Throwable -> Lab
            int r3 = r2.length     // Catch: java.lang.Throwable -> Lab
            r4 = 0
        L19:
            if (r4 >= r3) goto L11
            r5 = r2[r4]     // Catch: java.lang.Throwable -> Lab
            java.lang.String r5 = r5.getName()     // Catch: java.lang.Throwable -> Lab
            java.lang.String r6 = "init."
            boolean r6 = r5.startsWith(r6)     // Catch: java.lang.Throwable -> Lab
            if (r6 == 0) goto L3d
            boolean r6 = r5.endsWith(r0)     // Catch: java.lang.Throwable -> Lab
            if (r6 != 0) goto L30
            goto L3d
        L30:
            r6 = 5
            int r7 = r5.indexOf(r0)     // Catch: java.lang.Throwable -> Lab
            if (r7 > r6) goto L38
            goto L3d
        L38:
            java.lang.String r0 = r5.substring(r6, r7)     // Catch: java.lang.Throwable -> Lab
            goto L40
        L3d:
            int r4 = r4 + 1
            goto L19
        L40:
            java.lang.String r2 = "/proc/self/mountinfo"
            java.lang.String r2 = com.tencent.qimei.j.a.c(r2)     // Catch: java.lang.Throwable -> Lab
            boolean r3 = r2.isEmpty()     // Catch: java.lang.Throwable -> Lab
            if (r3 == 0) goto L4d
            goto L75
        L4d:
            java.lang.String r3 = "/lxc_container/"
            boolean r3 = r2.contains(r3)     // Catch: java.lang.Throwable -> Lab
            if (r3 == 0) goto L58
            java.lang.String r2 = "lxc"
            goto L76
        L58:
            java.lang.String r3 = "/proc/self/cpuset"
            java.lang.String r3 = com.tencent.qimei.j.a.c(r3)     // Catch: java.lang.Throwable -> Lab
            java.lang.String r4 = "[\\t\\n\\r]"
            java.lang.String r3 = r3.replaceAll(r4, r1)     // Catch: java.lang.Throwable -> Lab
            java.lang.String r4 = "/docker"
            boolean r4 = r3.contains(r4)     // Catch: java.lang.Throwable -> Lab
            if (r4 == 0) goto L75
            boolean r2 = r2.contains(r3)     // Catch: java.lang.Throwable -> Lab
            if (r2 == 0) goto L75
            java.lang.String r2 = "docker"
            goto L76
        L75:
            r2 = r1
        L76:
            boolean r3 = r0.isEmpty()     // Catch: java.lang.Throwable -> Lab
            if (r3 != 0) goto L90
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lab
            r3.<init>()     // Catch: java.lang.Throwable -> Lab
            r3.append(r1)     // Catch: java.lang.Throwable -> Lab
            java.lang.String r4 = "1#"
            r3.append(r4)     // Catch: java.lang.Throwable -> Lab
            r3.append(r0)     // Catch: java.lang.Throwable -> Lab
            java.lang.String r1 = r3.toString()     // Catch: java.lang.Throwable -> Lab
        L90:
            boolean r0 = r2.isEmpty()     // Catch: java.lang.Throwable -> Lab
            if (r0 != 0) goto Laf
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lab
            r0.<init>()     // Catch: java.lang.Throwable -> Lab
            r0.append(r1)     // Catch: java.lang.Throwable -> Lab
            java.lang.String r3 = "2#"
            r0.append(r3)     // Catch: java.lang.Throwable -> Lab
            r0.append(r2)     // Catch: java.lang.Throwable -> Lab
            java.lang.String r1 = r0.toString()     // Catch: java.lang.Throwable -> Lab
            goto Laf
        Lab:
            r0 = move-exception
            com.tencent.qimei.k.a.a(r0)
        Laf:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.c.c.f():java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0050, code lost:
    
        if (r6.b.getPackageManager().hasSystemFeature("android.software.leanback") != false) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String g() {
        /*
            r6 = this;
            java.lang.String r0 = "uimode"
            java.lang.String r1 = "TV"
            android.content.Context r2 = r6.b     // Catch: java.lang.Throwable -> L7a
            java.lang.Object r2 = r2.getSystemService(r0)     // Catch: java.lang.Throwable -> L7a
            android.app.UiModeManager r2 = (android.app.UiModeManager) r2     // Catch: java.lang.Throwable -> L7a
            if (r2 != 0) goto L15
            boolean r3 = r6.a()     // Catch: java.lang.Throwable -> L7a
            if (r3 == 0) goto L15
            return r1
        L15:
            int r2 = r2.getCurrentModeType()     // Catch: java.lang.Throwable -> L7a
            r3 = 1
            r4 = 0
            android.content.Context r5 = r6.b     // Catch: java.lang.Throwable -> L53
            java.lang.Object r0 = r5.getSystemService(r0)     // Catch: java.lang.Throwable -> L53
            android.app.UiModeManager r0 = (android.app.UiModeManager) r0     // Catch: java.lang.Throwable -> L53
            int r0 = r0.getCurrentModeType()     // Catch: java.lang.Throwable -> L53
            r5 = 4
            if (r0 != r5) goto L2c
            r0 = 1
            goto L2d
        L2c:
            r0 = 0
        L2d:
            if (r0 == 0) goto L30
            goto L58
        L30:
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch: java.lang.Throwable -> L53
            r5 = 21
            if (r0 < r5) goto L57
            android.content.Context r0 = r6.b     // Catch: java.lang.Throwable -> L53
            android.content.pm.PackageManager r0 = r0.getPackageManager()     // Catch: java.lang.Throwable -> L53
            java.lang.String r5 = "android.hardware.type.television"
            boolean r0 = r0.hasSystemFeature(r5)     // Catch: java.lang.Throwable -> L53
            if (r0 != 0) goto L58
            android.content.Context r0 = r6.b     // Catch: java.lang.Throwable -> L53
            android.content.pm.PackageManager r0 = r0.getPackageManager()     // Catch: java.lang.Throwable -> L53
            java.lang.String r5 = "android.software.leanback"
            boolean r0 = r0.hasSystemFeature(r5)     // Catch: java.lang.Throwable -> L53
            if (r0 == 0) goto L57
            goto L58
        L53:
            r0 = move-exception
            com.tencent.qimei.k.a.a(r0)     // Catch: java.lang.Throwable -> L7a
        L57:
            r3 = 0
        L58:
            if (r3 == 0) goto L5b
            return r1
        L5b:
            r0 = 6
            if (r2 != r0) goto L61
            java.lang.String r0 = "WATCH"
            return r0
        L61:
            r0 = 3
            if (r2 != r0) goto L67
            java.lang.String r0 = "CAR"
            return r0
        L67:
            android.content.Context r2 = r6.b     // Catch: java.lang.Throwable -> L7a
            android.content.res.Resources r2 = r2.getResources()     // Catch: java.lang.Throwable -> L7a
            android.content.res.Configuration r2 = r2.getConfiguration()     // Catch: java.lang.Throwable -> L7a
            int r1 = r2.screenLayout     // Catch: java.lang.Throwable -> L7a
            r1 = r1 & 15
            if (r1 < r0) goto L85
            java.lang.String r0 = "Pad"
            return r0
        L7a:
            r0 = move-exception
            boolean r2 = r6.a()
            if (r2 == 0) goto L82
            return r1
        L82:
            com.tencent.qimei.k.a.a(r0)
        L85:
            java.lang.String r0 = "Phone"
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.c.c.g():java.lang.String");
    }

    public String h() {
        return "";
    }

    public String i() {
        return "";
    }

    public synchronized String k() {
        String hostAddress;
        if (this.i == null) {
            Enumeration<NetworkInterface> n = n();
            if (n != null) {
                loop0: while (n.hasMoreElements()) {
                    Enumeration<InetAddress> inetAddresses = n.nextElement().getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress() && (nextElement instanceof Inet4Address)) {
                            hostAddress = nextElement.getHostAddress();
                            break loop0;
                        }
                    }
                }
            }
            hostAddress = "0.0.0.0";
            this.i = hostAddress;
        }
        return this.i;
    }

    public synchronized String l() {
        return "";
    }

    public String m() {
        String str = this.g;
        return str == null ? "" : str;
    }

    public final synchronized Enumeration<NetworkInterface> n() {
        if (this.o == null) {
            try {
                this.o = NetworkInterface.getNetworkInterfaces();
            } catch (SocketException e) {
                com.tencent.qimei.k.a.a(e);
            }
        }
        return this.o;
    }

    public synchronized String o() {
        String o;
        if (this.m == null) {
            if (U.a) {
                try {
                    o = U.o();
                } catch (UnsatisfiedLinkError e) {
                    e.printStackTrace();
                }
                this.m = o;
            }
            o = "";
            this.m = o;
        }
        return this.m;
    }

    public synchronized String p() {
        String str = this.f;
        if (str != null) {
            return str;
        }
        String str2 = "Android " + Build.VERSION.RELEASE + ",level " + Build.VERSION.SDK;
        this.f = str2;
        return str2;
    }

    public synchronized String q() {
        String z;
        if (this.l == null) {
            Context context = this.b;
            if (U.a) {
                try {
                    z = U.z(context);
                } catch (UnsatisfiedLinkError e) {
                    e.printStackTrace();
                }
                this.l = z;
            }
            z = "";
            this.l = z;
        }
        return this.l;
    }

    public byte r() {
        return (byte) 1;
    }

    public String s() {
        if (U.a) {
            try {
                return U.s();
            } catch (UnsatisfiedLinkError e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public synchronized String t() {
        String u;
        if (this.n == null) {
            if (U.a) {
                try {
                    u = U.u();
                } catch (UnsatisfiedLinkError e) {
                    e.printStackTrace();
                }
                this.n = u;
            }
            u = "";
            this.n = u;
        }
        return this.n;
    }

    public boolean u() {
        String str = (String) com.tencent.qimei.a.a.a("com.huawei.system.BuildEx", "getOsBrand", new Class[0], new Object[0]);
        if (str == null) {
            return false;
        }
        return "harmony".equalsIgnoreCase(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0043, code lost:
    
        if (r3.hasTransport(0) != false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x005c, code lost:
    
        r2 = "mobile";
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x005a, code lost:
    
        if (r2.getType() == 0) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void v() {
        /*
            r8 = this;
            com.tencent.qimei.u.d r0 = com.tencent.qimei.u.d.b()
            android.content.Context r0 = r0.J()
            java.lang.String r1 = "unknown"
            if (r0 != 0) goto Le
            goto La7
        Le:
            java.lang.String r2 = "connectivity"
            java.lang.Object r2 = r0.getSystemService(r2)
            android.net.ConnectivityManager r2 = (android.net.ConnectivityManager) r2
            if (r2 != 0) goto L1a
            goto La7
        L1a:
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 29
            r5 = 0
            r6 = 1
            java.lang.String r7 = "mobile"
            if (r3 < r4) goto L46
            r3 = 0
            android.net.Network r4 = r2.getActiveNetwork()     // Catch: java.lang.Exception -> L31
            if (r4 != 0) goto L2c
            goto L62
        L2c:
            android.net.NetworkCapabilities r3 = r2.getNetworkCapabilities(r4)     // Catch: java.lang.Exception -> L31
            goto L35
        L31:
            r2 = move-exception
            com.tencent.qimei.k.a.a(r2)
        L35:
            if (r3 != 0) goto L38
            goto L62
        L38:
            boolean r2 = r3.hasTransport(r6)
            if (r2 == 0) goto L3f
            goto L53
        L3f:
            boolean r2 = r3.hasTransport(r5)
            if (r2 == 0) goto L62
            goto L5c
        L46:
            android.net.NetworkInfo r2 = r2.getActiveNetworkInfo()     // Catch: java.lang.Exception -> L5e
            if (r2 != 0) goto L4d
            goto L62
        L4d:
            int r3 = r2.getType()     // Catch: java.lang.Exception -> L5e
            if (r3 != r6) goto L56
        L53:
            java.lang.String r2 = "wifi"
            goto L63
        L56:
            int r2 = r2.getType()     // Catch: java.lang.Exception -> L5e
            if (r2 != 0) goto L62
        L5c:
            r2 = r7
            goto L63
        L5e:
            r2 = move-exception
            com.tencent.qimei.k.a.a(r2)
        L62:
            r2 = r1
        L63:
            boolean r3 = r2.equals(r7)
            if (r3 == 0) goto La6
            java.lang.String r2 = "phone"
            java.lang.Object r0 = r0.getSystemService(r2)
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0
            if (r0 != 0) goto L74
            goto La7
        L74:
            int r1 = android.os.Build.VERSION.SDK_INT     // Catch: java.lang.Exception -> L84
            r2 = 30
            if (r1 >= r2) goto L7f
            int r5 = r0.getNetworkType()     // Catch: java.lang.Exception -> L84
            goto L88
        L7f:
            int r5 = r0.getDataNetworkType()     // Catch: java.lang.Exception -> L84
            goto L88
        L84:
            r0 = move-exception
            com.tencent.qimei.k.a.a(r0)
        L88:
            switch(r5) {
                case 1: goto La3;
                case 2: goto La3;
                case 3: goto La0;
                case 4: goto La3;
                case 5: goto La0;
                case 6: goto La0;
                case 7: goto La3;
                case 8: goto La0;
                case 9: goto La0;
                case 10: goto La0;
                case 11: goto La3;
                case 12: goto La0;
                case 13: goto L9d;
                case 14: goto La0;
                case 15: goto La0;
                default: goto L8b;
            }
        L8b:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "unknown_"
            r0.append(r1)
            r0.append(r5)
            java.lang.String r1 = r0.toString()
            goto La7
        L9d:
            java.lang.String r1 = "4G"
            goto La7
        La0:
            java.lang.String r1 = "3G"
            goto La7
        La3:
            java.lang.String r1 = "2G"
            goto La7
        La6:
            r1 = r2
        La7:
            r8.g = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.c.c.v():void");
    }

    public final boolean a() {
        boolean z;
        boolean z2;
        boolean z3;
        try {
            Intent registerReceiver = this.b.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            z2 = registerReceiver.getIntExtra("status", -1) == 5;
            z3 = registerReceiver.getIntExtra("plugged", -1) == 1;
            StringBuilder sb = new StringBuilder();
            sb.append("AC电流：： ");
            sb.append(z3);
            com.tencent.qimei.k.a.b("SDK_INIT ｜ DeviceInfo", sb.toString(), new Object[0]);
        } catch (Throwable th) {
            com.tencent.qimei.k.a.a(th);
        }
        if (z2 && z3) {
            z = true;
            TelephonyManager telephonyManager = (TelephonyManager) this.b.getSystemService("phone");
            boolean z4 = telephonyManager != null || telephonyManager.getPhoneType() == 0;
            com.tencent.qimei.k.a.b("SDK_INIT ｜ DeviceInfo", "batteryIsTV： " + z + "simIsTV: " + z4, new Object[0]);
            return z || z4;
        }
        z = false;
        TelephonyManager telephonyManager2 = (TelephonyManager) this.b.getSystemService("phone");
        if (telephonyManager2 != null) {
        }
        com.tencent.qimei.k.a.b("SDK_INIT ｜ DeviceInfo", "batteryIsTV： " + z + "simIsTV: " + z4, new Object[0]);
        if (z) {
            return true;
        }
    }
}
