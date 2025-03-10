package com.shubao.xinstall.a.f;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import com.xinstall.XINConfiguration;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.IdentityHashMap;
import java.util.Locale;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
public class i {
    private static i D;
    private String A;
    private String B;
    private IdentityHashMap<String, String> G;
    public final Context a;
    public final String b;
    public final String c;
    public final String d;
    public final String e;
    public String f;
    public final IdentityHashMap<String, String> g = new IdentityHashMap<>();
    private final String h;
    private final String i;
    private final Integer j;
    private final String k;
    private String l;
    private String m;
    private String n;
    private final String o;
    private final String p;

    /* renamed from: q, reason: collision with root package name */
    private final String f2q;
    private final String r;
    private final String s;
    private String t;
    private String u;
    private final Integer v;
    private final Integer w;
    private final Integer x;
    private final Integer y;
    private String z;
    private static Boolean C = Boolean.FALSE;
    private static String E = null;
    private static String F = null;

    class a extends BroadcastReceiver {
        a() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("level", 0);
                int intExtra2 = intent.getIntExtra("scale", 100);
                i iVar = i.this;
                StringBuilder sb = new StringBuilder();
                sb.append((intExtra * 100) / intExtra2);
                iVar.t = sb.toString();
                if (intent.getIntExtra("status", 1) == 2) {
                    i.this.u = "1";
                } else {
                    i.this.u = "0";
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0099 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @android.annotation.SuppressLint({"MissingPermission"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private i(android.content.Context r8) {
        /*
            Method dump skipped, instructions count: 416
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shubao.xinstall.a.f.i.<init>(android.content.Context):void");
    }

    public static i a(Context context) {
        synchronized (i.class) {
            if (D == null) {
                D = new i(context.getApplicationContext());
            }
        }
        return D;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x005f  */
    @android.annotation.SuppressLint({"MissingPermission"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String b() {
        /*
            r5 = this;
            java.lang.String r0 = com.shubao.xinstall.a.f.i.F
            if (r0 == 0) goto L7
            java.lang.String r0 = r5.z
            return r0
        L7:
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 23
            r2 = 1
            if (r0 < r1) goto L25
            com.xinstall.XINConfiguration r1 = com.shubao.xinstall.a.b.a
            if (r1 == 0) goto L24
            java.lang.Boolean r1 = r1.isImeiEnable()
            if (r1 == 0) goto L24
            com.xinstall.XINConfiguration r1 = com.shubao.xinstall.a.b.a
            java.lang.Boolean r1 = r1.isImeiEnable()
            boolean r1 = r1.booleanValue()
            if (r1 == r2) goto L25
        L24:
            r2 = 0
        L25:
            if (r2 == 0) goto L5b
            r1 = 0
            android.content.Context r2 = r5.a     // Catch: java.lang.Exception -> L45 java.lang.SecurityException -> L4b
            java.lang.String r3 = "phone"
            java.lang.Object r2 = r2.getSystemService(r3)     // Catch: java.lang.Exception -> L45 java.lang.SecurityException -> L4b
            android.telephony.TelephonyManager r2 = (android.telephony.TelephonyManager) r2     // Catch: java.lang.Exception -> L45 java.lang.SecurityException -> L4b
            java.lang.String r3 = r2.getDeviceId()     // Catch: java.lang.Exception -> L45 java.lang.SecurityException -> L4b
            if (r3 != 0) goto L50
            r4 = 26
            if (r0 < r4) goto L50
            java.lang.String r3 = r2.getImei()     // Catch: java.lang.Exception -> L41 java.lang.SecurityException -> L43
            goto L50
        L41:
            r0 = move-exception
            goto L47
        L43:
            r0 = move-exception
            goto L4d
        L45:
            r0 = move-exception
            r3 = r1
        L47:
            r0.printStackTrace()
            goto L50
        L4b:
            r0 = move-exception
            r3 = r1
        L4d:
            r0.printStackTrace()
        L50:
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 == 0) goto L59
            r5.z = r1
            goto L5b
        L59:
            r5.z = r3
        L5b:
            java.lang.String r0 = r5.z
            if (r0 != 0) goto L61
            java.lang.String r0 = ""
        L61:
            com.shubao.xinstall.a.f.i.F = r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shubao.xinstall.a.f.i.b():java.lang.String");
    }

    private String c() {
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(this.a.getPackageManager().getPackageInfo(this.a.getPackageName(), 64).signatures[0].toByteArray());
            StringBuilder sb = new StringBuilder();
            for (byte b = 0; b < digest.length; b = (byte) (b + 1)) {
                String upperCase = Integer.toHexString(digest[b] & 255).toUpperCase(Locale.US);
                if (upperCase.length() == 1) {
                    sb.append("0");
                }
                sb.append(upperCase);
                sb.append(":");
            }
            String sb2 = sb.toString();
            return sb2.substring(0, sb2.length() - 1);
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException unused) {
            return null;
        }
    }

    private static String d() {
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder();
        FileReader fileReader = null;
        try {
            try {
                try {
                    FileReader fileReader2 = new FileReader("/proc/cpuinfo");
                    try {
                        bufferedReader = new BufferedReader(fileReader2);
                        while (true) {
                            try {
                                String readLine = bufferedReader.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                if (readLine.contains("Hardware")) {
                                    sb.append(readLine.substring(readLine.indexOf(":") + 1).trim());
                                    break;
                                }
                            } catch (FileNotFoundException e) {
                                e = e;
                                fileReader = fileReader2;
                                Log.d("you", "getCpuInfo FileNotFoundException e:" + e.getMessage());
                                fileReader.close();
                                bufferedReader.close();
                                Log.d("you", "getCpuInfo  return space");
                                return "";
                            } catch (IOException e2) {
                                e = e2;
                                fileReader = fileReader2;
                                Log.d("you", "getCpuInfo IOException e:" + e.getMessage());
                                fileReader.close();
                                bufferedReader.close();
                                Log.d("you", "getCpuInfo  return space");
                                return "";
                            } catch (Exception e3) {
                                e = e3;
                                fileReader = fileReader2;
                                Log.d("you", "getCpuInfo Exception e:" + e.getMessage());
                                fileReader.close();
                                bufferedReader.close();
                                Log.d("you", "getCpuInfo  return space");
                                return "";
                            } catch (Throwable th) {
                                th = th;
                                fileReader = fileReader2;
                                try {
                                    fileReader.close();
                                    bufferedReader.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                } catch (Exception e5) {
                                    e5.printStackTrace();
                                }
                                throw th;
                            }
                        }
                        String sb2 = sb.toString();
                        try {
                            fileReader2.close();
                            bufferedReader.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        } catch (Exception e7) {
                            e7.printStackTrace();
                        }
                        return sb2;
                    } catch (FileNotFoundException e8) {
                        e = e8;
                        bufferedReader = null;
                    } catch (IOException e9) {
                        e = e9;
                        bufferedReader = null;
                    } catch (Exception e10) {
                        e = e10;
                        bufferedReader = null;
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedReader = null;
                    }
                } catch (FileNotFoundException e11) {
                    e = e11;
                    bufferedReader = null;
                } catch (IOException e12) {
                    e = e12;
                    bufferedReader = null;
                } catch (Exception e13) {
                    e = e13;
                    bufferedReader = null;
                } catch (Throwable th3) {
                    th = th3;
                    bufferedReader = null;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (IOException e14) {
            e14.printStackTrace();
        } catch (Exception e15) {
            e15.printStackTrace();
        }
    }

    private static String e() {
        String str = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM);
            str = bufferedReader.readLine().split("\\s+")[1];
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (str == null) {
            return "0.0";
        }
        return new DecimalFormat("0.0").format((Double.valueOf(str).doubleValue() / 1024.0d) / 1024.0d);
    }

    public final IdentityHashMap<String, String> a() {
        XINConfiguration xINConfiguration;
        if (this.G == null) {
            IdentityHashMap<String, String> identityHashMap = new IdentityHashMap<>();
            this.G = identityHashMap;
            identityHashMap.put("os", "Android");
            this.G.put("brand", this.e);
            this.G.put("model", this.b);
            this.G.put("pkg", this.h);
            this.G.put("version", this.i);
            this.G.put("deviceId", this.l);
            this.G.put("serialNumber", this.m);
            this.G.put("androidId", this.n);
            this.G.put("certFinger", c());
            this.G.put("width", String.valueOf(this.v));
            this.G.put("height", String.valueOf(this.w));
            this.G.put("realHeight", String.valueOf(this.y));
            this.G.put("realWidth", String.valueOf(this.x));
            this.G.put("versionCode", String.valueOf(this.j));
            this.G.put("appVersion", this.o);
            this.G.put("apiVersion", "1.7.4");
            this.G.put("buildId", this.c);
            this.G.put("buildDisplay", this.d);
            this.G.put("cpuInfo", this.k);
            this.G.put("gpuInfo", this.f);
            this.G.put("q", this.t);
            this.G.put("iq", this.u);
            this.G.put("utc", this.s);
            this.G.put("sdkType", "2");
            this.G.put("sdkThirdVersion", "1.7.4");
            this.G.put("isNight", this.p);
            this.G.put("ccore", this.f2q);
            this.G.put("ram", this.r);
            this.G.putAll(this.g);
        }
        if (this.G != null && (xINConfiguration = com.shubao.xinstall.a.b.a) != null && xINConfiguration.isAdEnable()) {
            this.G.put("advertisingId", com.shubao.xinstall.a.b.a.getGaid());
            this.G.put("oaid", com.shubao.xinstall.a.b.a.getOaid());
            this.G.put("imei", b());
        }
        return this.G;
    }
}
