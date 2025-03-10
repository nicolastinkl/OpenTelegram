package io.openinstall.sdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class bc {
    private static bc k;
    private static final Object l = new Object();
    private final Context a;
    private final ax b;
    private final String c;
    private final String d = Build.VERSION.RELEASE;
    private final Integer e;
    private final String f;
    private final String g;
    private final String h;
    private final String i;
    private final String j;
    private String m;
    private String n;
    private String o;

    @SuppressLint({"HardwareIds"})
    private bc(Context context, ax axVar) {
        Integer num;
        this.a = context;
        this.b = axVar;
        this.c = context.getPackageName();
        String str = null;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            num = Integer.valueOf(packageInfo.versionCode);
            try {
                str = packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException unused) {
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            num = null;
        }
        this.e = num;
        this.f = str;
        this.g = Build.MODEL;
        this.h = Build.ID;
        this.i = Build.DISPLAY;
        this.j = Build.BRAND;
    }

    public static bc a(Context context, ax axVar) {
        synchronized (l) {
            if (k == null) {
                k = new bc(context.getApplicationContext(), axVar);
            }
        }
        return k;
    }

    private boolean a(String str) {
        return TextUtils.isEmpty(str) || str.equalsIgnoreCase(fw.m);
    }

    private boolean b(String str) {
        return TextUtils.isEmpty(str) || str.equalsIgnoreCase(fw.i) || str.equalsIgnoreCase(fw.m);
    }

    @SuppressLint({"HardwareIds"})
    public String a() {
        String str = this.m;
        if (str != null) {
            return str;
        }
        String f = this.b.f();
        if (TextUtils.isEmpty(f)) {
            try {
                f = Settings.Secure.getString(this.a.getContentResolver(), "android_id");
            } catch (Throwable unused) {
            }
        }
        if (b(f)) {
            this.b.d(fw.m);
            f = "";
        } else {
            this.b.d(f);
        }
        this.m = f;
        return this.m;
    }

    @SuppressLint({"HardwareIds"})
    public String b() {
        String str = this.n;
        if (str != null) {
            return str;
        }
        String g = this.b.g();
        if (TextUtils.isEmpty(g)) {
            if (Build.VERSION.SDK_INT < 26) {
                g = Build.SERIAL;
            } else {
                try {
                    g = Build.getSerial();
                } catch (Throwable unused) {
                }
            }
        }
        if (a(g)) {
            this.b.e(fw.m);
            g = "";
        } else {
            this.b.e(g);
        }
        this.n = g;
        return this.n;
    }

    public String c() {
        String str = this.o;
        if (str != null) {
            return str;
        }
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(this.a.getPackageManager().getPackageInfo(this.a.getPackageName(), 64).signatures[0].toByteArray());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                String upperCase = Integer.toHexString(b & 255).toUpperCase(Locale.US);
                if (upperCase.length() == 1) {
                    sb.append("0");
                }
                sb.append(upperCase);
                sb.append(":");
            }
            String sb2 = sb.toString();
            this.o = sb2.substring(0, sb2.length() - 1);
        } catch (Throwable unused) {
        }
        return this.o;
    }

    public String d() {
        return this.c;
    }

    public String e() {
        return this.d;
    }

    public Integer f() {
        return this.e;
    }

    public String g() {
        return this.f;
    }

    public String h() {
        return this.g;
    }

    public String i() {
        return this.h;
    }

    public String j() {
        return this.i;
    }

    public String k() {
        return this.j;
    }

    public String l() {
        FileChannel fileChannel;
        try {
            fileChannel = new RandomAccessFile(this.a.getApplicationInfo().sourceDir, "r").getChannel();
            try {
                dz a = ea.a(fileChannel);
                if (a == null) {
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (IOException unused) {
                        }
                    }
                    return "";
                }
                byte[] c = a.c();
                if (c == null) {
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return "";
                }
                String str = new String(Base64.encode(c, 10), ds.c);
                if (fileChannel != null) {
                    try {
                        fileChannel.close();
                    } catch (IOException unused3) {
                    }
                }
                return str;
            } catch (IOException unused4) {
                if (fileChannel == null) {
                    return null;
                }
                try {
                    fileChannel.close();
                    return null;
                } catch (IOException unused5) {
                    return null;
                }
            } catch (Throwable unused6) {
                if (fileChannel == null) {
                    return null;
                }
                fileChannel.close();
                return null;
            }
        } catch (IOException unused7) {
            fileChannel = null;
        } catch (Throwable unused8) {
            fileChannel = null;
        }
    }

    public List<String> m() {
        ArrayList arrayList = new ArrayList();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces != null) {
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface nextElement = networkInterfaces.nextElement();
                    if (!nextElement.isLoopback() && nextElement.isUp() && !nextElement.getName().startsWith("dummy")) {
                        Enumeration<InetAddress> inetAddresses = nextElement.getInetAddresses();
                        while (inetAddresses.hasMoreElements()) {
                            InetAddress nextElement2 = inetAddresses.nextElement();
                            if (nextElement2 instanceof Inet4Address) {
                                arrayList.add(nextElement2.getHostAddress());
                            }
                        }
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return arrayList;
    }
}
