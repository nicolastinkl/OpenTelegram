package com.tencent.bugly.proguard;

import android.app.ActivityManager;
import android.content.Context;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class bd implements NativeExceptionHandler {
    private final Context a;
    private final as b;
    private final aa c;
    private final ac d;

    public bd(Context context, aa aaVar, as asVar, ac acVar) {
        this.a = context;
        this.b = asVar;
        this.c = aaVar;
        this.d = acVar;
    }

    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    public final CrashDetailBean packageCrashDatas(String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, byte[] bArr, Map<String, String> map, boolean z, boolean z2) {
        int i;
        String str12;
        int indexOf;
        boolean i2 = at.a().i();
        if (i2) {
            al.e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
        }
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        crashDetailBean.b = 1;
        crashDetailBean.e = this.c.g();
        aa aaVar = this.c;
        crashDetailBean.f = aaVar.o;
        crashDetailBean.g = aaVar.q();
        crashDetailBean.m = this.c.f();
        crashDetailBean.n = str3;
        crashDetailBean.o = i2 ? " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]" : "";
        crashDetailBean.p = str4;
        String str13 = str5 != null ? str5 : "";
        crashDetailBean.f10q = str13;
        crashDetailBean.r = j;
        crashDetailBean.u = ap.c(str13.getBytes());
        crashDetailBean.A = str;
        crashDetailBean.B = str2;
        crashDetailBean.L = this.c.s();
        crashDetailBean.h = this.c.p();
        crashDetailBean.i = this.c.A();
        crashDetailBean.v = str8;
        String dumpFilePath = NativeCrashHandler.getInstance() != null ? NativeCrashHandler.getDumpFilePath() : null;
        String a = be.a(dumpFilePath, str8);
        if (!ap.b(a)) {
            crashDetailBean.Z = a;
        }
        crashDetailBean.aa = be.b(dumpFilePath);
        crashDetailBean.w = be.a(str9, at.f, at.k, at.p);
        crashDetailBean.x = be.a(str10, at.f, null, true);
        crashDetailBean.N = str7;
        crashDetailBean.O = str6;
        crashDetailBean.P = str11;
        crashDetailBean.F = this.c.k();
        crashDetailBean.G = this.c.j();
        crashDetailBean.H = this.c.l();
        crashDetailBean.I = ab.b(this.a);
        crashDetailBean.J = ab.g();
        crashDetailBean.K = ab.h();
        if (z) {
            crashDetailBean.C = ab.j();
            crashDetailBean.D = ab.f();
            crashDetailBean.E = ab.l();
            crashDetailBean.y = ao.a();
            aa aaVar2 = this.c;
            crashDetailBean.Q = aaVar2.a;
            crashDetailBean.R = aaVar2.a();
            crashDetailBean.z = ap.a(this.c.Q, at.h);
            int indexOf2 = crashDetailBean.f10q.indexOf("java:\n");
            if (indexOf2 > 0 && (i = indexOf2 + 6) < crashDetailBean.f10q.length()) {
                String str14 = crashDetailBean.f10q;
                String substring = str14.substring(i, str14.length() - 1);
                if (substring.length() > 0 && crashDetailBean.z.containsKey(crashDetailBean.B) && (indexOf = (str12 = crashDetailBean.z.get(crashDetailBean.B)).indexOf(substring)) > 0) {
                    String substring2 = str12.substring(indexOf);
                    crashDetailBean.z.put(crashDetailBean.B, substring2);
                    crashDetailBean.f10q = crashDetailBean.f10q.substring(0, i);
                    crashDetailBean.f10q += substring2;
                }
            }
            if (str == null) {
                crashDetailBean.A = this.c.d;
            }
            crashDetailBean.U = this.c.z();
            aa aaVar3 = this.c;
            crashDetailBean.V = aaVar3.x;
            crashDetailBean.W = aaVar3.t();
            crashDetailBean.X = this.c.y();
        } else {
            crashDetailBean.C = -1L;
            crashDetailBean.D = -1L;
            crashDetailBean.E = -1L;
            if (crashDetailBean.w == null) {
                crashDetailBean.w = "This crash occurred at last process! Log is miss, when get an terrible ABRT Native Exception etc.";
            }
            crashDetailBean.Q = -1L;
            crashDetailBean.U = -1;
            crashDetailBean.V = -1;
            crashDetailBean.W = map;
            crashDetailBean.X = this.c.y();
            crashDetailBean.z = null;
            if (str == null) {
                crashDetailBean.A = "unknown(record)";
            }
            if (bArr != null) {
                crashDetailBean.y = bArr;
            }
        }
        return crashDetailBean;
    }

    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    public final boolean getAndUpdateAnrState() {
        if (ay.a() == null) {
            return false;
        }
        ay a = ay.a();
        if (a.a.get()) {
            al.c("anr is processing, return", new Object[0]);
            return false;
        }
        ActivityManager activityManager = a.b;
        if (!((z.a(activityManager) || az.a(activityManager, 0L) == null) ? false : true)) {
            al.c("proc is not in anr, wait next check", new Object[0]);
            return false;
        }
        if (a.a(System.currentTimeMillis())) {
            return false;
        }
        return a.a(true);
    }

    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    public final void handleNativeException(int i, int i2, long j, long j2, String str, String str2, String str3, String str4, int i3, String str5, int i4, int i5, int i6, String str6, String str7) {
        al.a("Native Crash Happen v1", new Object[0]);
        handleNativeException2(i, i2, j, j2, str, str2, str3, str4, i3, str5, i4, i5, i6, str6, str7, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x009c A[Catch: all -> 0x0227, TryCatch #2 {all -> 0x0227, blocks: (B:78:0x0016, B:4:0x0036, B:6:0x0049, B:8:0x0051, B:9:0x005d, B:11:0x0069, B:14:0x0070, B:15:0x007c, B:17:0x009c, B:18:0x00cf, B:20:0x00f2, B:21:0x00f9, B:24:0x0105, B:26:0x010d, B:34:0x0155, B:35:0x0159, B:37:0x0163, B:74:0x00ba, B:75:0x007a), top: B:77:0x0016 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00f2 A[Catch: all -> 0x0227, TryCatch #2 {all -> 0x0227, blocks: (B:78:0x0016, B:4:0x0036, B:6:0x0049, B:8:0x0051, B:9:0x005d, B:11:0x0069, B:14:0x0070, B:15:0x007c, B:17:0x009c, B:18:0x00cf, B:20:0x00f2, B:21:0x00f9, B:24:0x0105, B:26:0x010d, B:34:0x0155, B:35:0x0159, B:37:0x0163, B:74:0x00ba, B:75:0x007a), top: B:77:0x0016 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01a6 A[Catch: all -> 0x0223, TryCatch #1 {all -> 0x0223, blocks: (B:41:0x01a0, B:43:0x01a6, B:45:0x01af), top: B:40:0x01a0 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01af A[Catch: all -> 0x0223, TRY_LEAVE, TryCatch #1 {all -> 0x0223, blocks: (B:41:0x01a0, B:43:0x01a6, B:45:0x01af), top: B:40:0x01a0 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00ba A[Catch: all -> 0x0227, TryCatch #2 {all -> 0x0227, blocks: (B:78:0x0016, B:4:0x0036, B:6:0x0049, B:8:0x0051, B:9:0x005d, B:11:0x0069, B:14:0x0070, B:15:0x007c, B:17:0x009c, B:18:0x00cf, B:20:0x00f2, B:21:0x00f9, B:24:0x0105, B:26:0x010d, B:34:0x0155, B:35:0x0159, B:37:0x0163, B:74:0x00ba, B:75:0x007a), top: B:77:0x0016 }] */
    @Override // com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleNativeException2(int r28, int r29, long r30, long r32, java.lang.String r34, java.lang.String r35, java.lang.String r36, java.lang.String r37, int r38, java.lang.String r39, int r40, int r41, int r42, java.lang.String r43, java.lang.String r44, java.lang.String[] r45) {
        /*
            Method dump skipped, instructions count: 563
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.bd.handleNativeException2(int, int, long, long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, int, int, int, java.lang.String, java.lang.String, java.lang.String[]):void");
    }

    private static Map<String, String> a(String[] strArr) {
        HashMap hashMap = new HashMap(strArr == null ? 1 : strArr.length);
        if (strArr != null) {
            for (int i = 0; i < strArr.length; i++) {
                String str = strArr[i];
                if (str != null) {
                    al.a("Extra message[%d]: %s", Integer.valueOf(i), str);
                    String[] split = str.split("=");
                    if (split.length == 2) {
                        hashMap.put(split[0], split[1]);
                    } else {
                        al.d("bad extraMsg %s", str);
                    }
                }
            }
        } else {
            al.c("not found extraMsg", new Object[0]);
        }
        return hashMap;
    }
}
