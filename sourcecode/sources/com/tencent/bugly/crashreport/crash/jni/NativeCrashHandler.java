package com.tencent.bugly.crashreport.crash.jni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.proguard.aa;
import com.tencent.bugly.proguard.ab;
import com.tencent.bugly.proguard.ac;
import com.tencent.bugly.proguard.ak;
import com.tencent.bugly.proguard.al;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.as;
import com.tencent.bugly.proguard.at;
import com.tencent.bugly.proguard.bd;
import com.tencent.bugly.proguard.be;
import com.tencent.bugly.proguard.q;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public class NativeCrashHandler implements q {
    static String a = null;
    private static NativeCrashHandler b = null;
    private static int c = 1;
    private static boolean n = true;
    private final Context d;
    private final aa e;
    private final ak f;
    private NativeExceptionHandler g;
    private final boolean h;
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;
    private boolean l = false;
    private as m;

    private native String getProperties(String str);

    private native String getSoCpuAbi();

    protected native boolean appendNativeLog(String str, String str2, String str3);

    protected native boolean appendWholeNativeLog(String str);

    protected native String getNativeKeyValueList();

    protected native String getNativeLog();

    protected native boolean putNativeKeyValue(String str, String str2);

    protected native String regist(String str, boolean z, int i);

    protected native String removeNativeKeyValue(String str);

    protected native void setNativeInfo(int i, String str);

    protected native void testCrash();

    protected native String unregist();

    static /* synthetic */ boolean a(NativeCrashHandler nativeCrashHandler, String str) {
        return nativeCrashHandler.a(999, str);
    }

    @SuppressLint({"SdCardPath"})
    private NativeCrashHandler(Context context, aa aaVar, as asVar, ak akVar, boolean z, String str) {
        this.d = ap.a(context);
        if (ap.b(a)) {
            try {
                if (ap.b(str)) {
                    str = context.getDir("bugly", 0).getAbsolutePath();
                }
            } catch (Throwable unused) {
                str = "/data/data/" + aa.a(context).c + "/app_bugly";
            }
            a = str;
        }
        this.m = asVar;
        this.e = aaVar;
        this.f = akVar;
        this.h = z;
        this.g = new bd(context, aaVar, asVar, ac.a());
    }

    public static synchronized NativeCrashHandler getInstance(Context context, aa aaVar, as asVar, ac acVar, ak akVar, boolean z, String str) {
        NativeCrashHandler nativeCrashHandler;
        synchronized (NativeCrashHandler.class) {
            if (b == null) {
                b = new NativeCrashHandler(context, aaVar, asVar, akVar, z, str);
            }
            nativeCrashHandler = b;
        }
        return nativeCrashHandler;
    }

    public static synchronized NativeCrashHandler getInstance() {
        NativeCrashHandler nativeCrashHandler;
        synchronized (NativeCrashHandler.class) {
            nativeCrashHandler = b;
        }
        return nativeCrashHandler;
    }

    public static synchronized String getDumpFilePath() {
        String str;
        synchronized (NativeCrashHandler.class) {
            str = a;
        }
        return str;
    }

    public static synchronized void setDumpFilePath(String str) {
        synchronized (NativeCrashHandler.class) {
            a = str;
        }
    }

    public static void setShouldHandleInJava(boolean z) {
        n = z;
        NativeCrashHandler nativeCrashHandler = b;
        if (nativeCrashHandler != null) {
            nativeCrashHandler.a(999, String.valueOf(z));
        }
    }

    public static boolean isShouldHandleInJava() {
        return n;
    }

    public String getRunningCpuAbi() {
        try {
            return getSoCpuAbi();
        } catch (Throwable unused) {
            al.d("get so cpu abi failed，please upgrade bugly so version", new Object[0]);
            return "";
        }
    }

    private synchronized void a(boolean z) {
        if (this.k) {
            al.d("[Native] Native crash report has already registered.", new Object[0]);
            return;
        }
        if (this.j) {
            try {
                String regist = regist(a, z, c);
                if (regist != null) {
                    al.a("[Native] Native Crash Report enable.", new Object[0]);
                    this.e.u = regist;
                    String concat = "-".concat(regist);
                    if (!at.b && !this.e.h.contains(concat)) {
                        aa aaVar = this.e;
                        aaVar.h = aaVar.h.concat("-").concat(this.e.u);
                    }
                    al.a("comInfo.sdkVersion %s", this.e.h);
                    this.k = true;
                    String runningCpuAbi = getRunningCpuAbi();
                    if (!TextUtils.isEmpty(runningCpuAbi)) {
                        this.e.e(runningCpuAbi);
                    }
                    return;
                }
            } catch (Throwable unused) {
                al.c("[Native] Failed to load Bugly SO file.", new Object[0]);
            }
        } else if (this.i) {
            try {
                Class cls = Integer.TYPE;
                Class[] clsArr = {String.class, String.class, cls, cls};
                Object[] objArr = new Object[4];
                objArr[0] = a;
                objArr[1] = ab.d();
                objArr[2] = Integer.valueOf(z ? 1 : 5);
                objArr[3] = 1;
                String str = (String) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "registNativeExceptionHandler2", clsArr, objArr);
                if (str == null) {
                    aa.b();
                    str = (String) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "registNativeExceptionHandler", new Class[]{String.class, String.class, cls}, new Object[]{a, ab.d(), Integer.valueOf(aa.B())});
                }
                if (str != null) {
                    this.k = true;
                    this.e.u = str;
                    ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", new Class[]{Boolean.TYPE}, new Object[]{Boolean.TRUE});
                    ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "setLogMode", new Class[]{cls}, new Object[]{Integer.valueOf(z ? 1 : 5)});
                    String runningCpuAbi2 = getRunningCpuAbi();
                    if (!TextUtils.isEmpty(runningCpuAbi2)) {
                        this.e.e(runningCpuAbi2);
                    }
                    return;
                }
            } catch (Throwable unused2) {
            }
        }
        this.j = false;
        this.i = false;
    }

    public synchronized void startNativeMonitor() {
        if (!this.j && !this.i) {
            boolean z = !ap.b(this.e.t);
            if (at.b) {
                boolean a2 = a(z ? this.e.t : "Bugly_Native", z);
                this.j = a2;
                if (!a2 && !z) {
                    this.i = a("NativeRQD", false);
                }
            } else {
                String str = "Bugly_Native";
                aa aaVar = this.e;
                String str2 = aaVar.t;
                if (z) {
                    str = str2;
                } else {
                    aaVar.getClass();
                }
                this.j = a(str, z);
            }
            if (this.j || this.i) {
                a(this.h);
                setNativeAppVersion(this.e.o);
                setNativeAppChannel(this.e.s);
                setNativeAppPackage(this.e.c);
                setNativeUserId(this.e.f());
                setNativeIsAppForeground(this.e.a());
                setNativeLaunchTime(this.e.a);
                return;
            }
            return;
        }
        a(this.h);
    }

    public void checkUploadRecordCrash() {
        this.f.a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.1
            @Override // java.lang.Runnable
            public final void run() {
                int i;
                if (ap.a(NativeCrashHandler.this.d, "native_record_lock")) {
                    if (!NativeCrashHandler.n) {
                        NativeCrashHandler.a(NativeCrashHandler.this, "false");
                    }
                    CrashDetailBean a2 = be.a(NativeCrashHandler.this.d, NativeCrashHandler.a, NativeCrashHandler.this.g);
                    if (a2 != null) {
                        al.a("[Native] Get crash from native record.", new Object[0]);
                        if (!NativeCrashHandler.this.m.a(a2, true)) {
                            NativeCrashHandler.this.m.b(a2, false);
                        }
                        be.a(false, NativeCrashHandler.a);
                    }
                    NativeCrashHandler nativeCrashHandler = NativeCrashHandler.this;
                    long b2 = ap.b() - at.j;
                    long b3 = ap.b() + 86400000;
                    File file = new File(NativeCrashHandler.a);
                    if (file.exists() && file.isDirectory()) {
                        try {
                            File[] listFiles = file.listFiles();
                            if (listFiles != null && listFiles.length != 0) {
                                Arrays.sort(listFiles, new Comparator<File>(nativeCrashHandler) { // from class: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.2
                                    @Override // java.util.Comparator
                                    public final /* synthetic */ int compare(File file2, File file3) {
                                        return Long.compare(file3.lastModified(), file2.lastModified());
                                    }
                                });
                                long j = 0;
                                int length = listFiles.length;
                                int i2 = 0;
                                int i3 = 0;
                                int i4 = 0;
                                while (i2 < length) {
                                    File file2 = listFiles[i2];
                                    long lastModified = file2.lastModified();
                                    j += file2.length();
                                    if (lastModified >= b2 && lastModified < b3 && j < at.i) {
                                        i = length;
                                        i2++;
                                        length = i;
                                    }
                                    i = length;
                                    al.a("[Native] Delete record file: %s", file2.getAbsolutePath());
                                    i3++;
                                    if (file2.delete()) {
                                        i4++;
                                    }
                                    i2++;
                                    length = i;
                                }
                                al.c("[Native] Number of record files overdue: %d, has deleted: %d", Integer.valueOf(i3), Integer.valueOf(i4));
                            }
                        } catch (Throwable th) {
                            al.a(th);
                        }
                    }
                    ap.b(NativeCrashHandler.this.d, "native_record_lock");
                    return;
                }
                al.a("[Native] Failed to lock file for handling native crash record.", new Object[0]);
            }
        });
    }

    private static boolean a(String str, boolean z) {
        boolean z2;
        try {
            al.a("[Native] Trying to load so: %s", str);
            if (z) {
                System.load(str);
            } else {
                System.loadLibrary(str);
            }
            try {
                al.a("[Native] Successfully loaded SO: %s", str);
                return true;
            } catch (Throwable th) {
                th = th;
                z2 = true;
                al.d(th.getMessage(), new Object[0]);
                al.d("[Native] Failed to load so: %s", str);
                return z2;
            }
        } catch (Throwable th2) {
            th = th2;
            z2 = false;
        }
    }

    private synchronized void c() {
        if (!this.k) {
            al.d("[Native] Native crash report has already unregistered.", new Object[0]);
            return;
        }
        try {
            if (unregist() != null) {
                al.a("[Native] Successfully closed native crash report.", new Object[0]);
                this.k = false;
                return;
            }
        } catch (Throwable unused) {
            al.c("[Native] Failed to close native crash report.", new Object[0]);
        }
        try {
            ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", new Class[]{Boolean.TYPE}, new Object[]{Boolean.FALSE});
            this.k = false;
            al.a("[Native] Successfully closed native crash report.", new Object[0]);
        } catch (Throwable unused2) {
            al.c("[Native] Failed to close native crash report.", new Object[0]);
            this.j = false;
            this.i = false;
        }
    }

    public void testNativeCrash() {
        if (!this.j) {
            al.d("[Native] Bugly SO file has not been load.", new Object[0]);
        } else {
            testCrash();
        }
    }

    public void testNativeCrash(boolean z, boolean z2, boolean z3) {
        a(16, String.valueOf(z));
        a(17, String.valueOf(z2));
        a(18, String.valueOf(z3));
        testNativeCrash();
    }

    public void dumpAnrNativeStack() {
        a(19, "1");
    }

    public void resendSigquit() {
        a(20, "");
    }

    public void unBlockSigquit(boolean z) {
        if (z) {
            a(21, "true");
        } else {
            a(21, "false");
        }
    }

    public NativeExceptionHandler getNativeExceptionHandler() {
        return this.g;
    }

    public void removeEmptyNativeRecordFiles() {
        be.c(a);
    }

    private synchronized void b(boolean z) {
        if (z) {
            startNativeMonitor();
        } else {
            c();
        }
    }

    public synchronized boolean isUserOpened() {
        return this.l;
    }

    private synchronized void c(boolean z) {
        if (this.l != z) {
            al.a("user change native %b", Boolean.valueOf(z));
            this.l = z;
        }
    }

    public synchronized void setUserOpened(boolean z) {
        c(z);
        boolean isUserOpened = isUserOpened();
        ac a2 = ac.a();
        if (a2 != null) {
            isUserOpened = isUserOpened && a2.c().f;
        }
        if (isUserOpened != this.k) {
            al.a("native changed to %b", Boolean.valueOf(isUserOpened));
            b(isUserOpened);
        }
    }

    public synchronized void onStrategyChanged(StrategyBean strategyBean) {
        if (strategyBean != null) {
            boolean z = strategyBean.f;
            if (z != this.k) {
                al.d("server native changed to %b", Boolean.valueOf(z));
            }
        }
        boolean z2 = ac.a().c().f && this.l;
        if (z2 != this.k) {
            al.a("native changed to %b", Boolean.valueOf(z2));
            b(z2);
        }
    }

    @Override // com.tencent.bugly.proguard.q
    public boolean appendLogToNative(String str, String str2, String str3) {
        if ((this.i || this.j) && str != null && str2 != null && str3 != null) {
            try {
                if (this.j) {
                    return appendNativeLog(str, str2, str3);
                }
                Boolean bool = (Boolean) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "appendNativeLog", new Class[]{String.class, String.class, String.class}, new Object[]{str, str2, str3});
                if (bool != null) {
                    return bool.booleanValue();
                }
                return false;
            } catch (UnsatisfiedLinkError unused) {
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override // com.tencent.bugly.proguard.q
    public String getLogFromNative() {
        if (!this.i && !this.j) {
            return null;
        }
        try {
            if (this.j) {
                return getNativeLog();
            }
            return (String) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "getNativeLog", null, null);
        } catch (UnsatisfiedLinkError unused) {
            return null;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public boolean putKeyValueToNative(String str, String str2) {
        if ((this.i || this.j) && str != null && str2 != null) {
            try {
                if (this.j) {
                    return putNativeKeyValue(str, str2);
                }
                Boolean bool = (Boolean) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "putNativeKeyValue", new Class[]{String.class, String.class}, new Object[]{str, str2});
                if (bool != null) {
                    return bool.booleanValue();
                }
                return false;
            } catch (UnsatisfiedLinkError unused) {
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return false;
    }

    private boolean a(int i, String str) {
        if (!this.j) {
            return false;
        }
        try {
            setNativeInfo(i, str);
            return true;
        } catch (UnsatisfiedLinkError unused) {
            return false;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    public boolean filterSigabrtSysLog() {
        return a(998, "true");
    }

    public boolean setNativeAppVersion(String str) {
        return a(10, str);
    }

    public boolean setNativeAppChannel(String str) {
        return a(12, str);
    }

    public boolean setNativeAppPackage(String str) {
        return a(13, str);
    }

    public boolean setNativeUserId(String str) {
        return a(11, str);
    }

    @Override // com.tencent.bugly.proguard.q
    public boolean setNativeIsAppForeground(boolean z) {
        return a(14, z ? "true" : "false");
    }

    public boolean setNativeLaunchTime(long j) {
        try {
            return a(15, String.valueOf(j));
        } catch (NumberFormatException e) {
            if (al.a(e)) {
                return false;
            }
            e.printStackTrace();
            return false;
        }
    }

    public void enableCatchAnrTrace() {
        if (Build.VERSION.SDK_INT > 19) {
            c |= 2;
        }
    }

    public void disableCatchAnrTrace() {
        if (Build.VERSION.SDK_INT > 19) {
            c = 1;
        }
    }

    public boolean isEnableCatchAnrTrace() {
        return (c & 2) == 2;
    }

    public String getSystemProperty(String str) {
        return (this.j || this.i) ? getProperties(str) : "fail";
    }
}
