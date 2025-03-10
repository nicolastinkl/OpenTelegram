package com.shubao.xinstall.a.e;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.shubao.xinstall.a.b.d;
import com.shubao.xinstall.a.f.m;
import com.xinstall.model.XAppError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class a {
    private boolean a;

    private a() {
    }

    private a(boolean z) {
        this.a = z;
    }

    public static d a(String str, Map map, String str2, String str3) {
        d dVar = new d();
        try {
            String replace = str2.replace("\\/", "/");
            String a = com.shubao.xinstall.a.f.c.a("qMNyC9JYhVeRpw8c_ehmgNTw7QRw8Kug8YF");
            if (a.length() > 16) {
                a = a.substring(0, 16);
            }
            String a2 = com.shubao.xinstall.a.f.c.a(str3);
            if (a2.length() > 16) {
                a2 = a2.substring(0, 16);
            }
            String replace2 = com.shubao.xinstall.a.f.b.a(replace, a, a2).replace("/n", "");
            HashMap hashMap = new HashMap();
            hashMap.put("__d", replace2);
            hashMap.put("__tt", str3);
            hashMap.put("__t", "android");
            ArrayList arrayList = new ArrayList();
            arrayList.add("__d");
            arrayList.add("__tt");
            arrayList.add("__t");
            hashMap.put("__s", com.shubao.xinstall.a.f.c.a(com.shubao.xinstall.a.f.c.b(m.a(hashMap, arrayList)) + "mV6ehmgNTw7QRw8Kug8YF"));
            arrayList.add("__s");
            return a(str, map, m.a(hashMap, arrayList), str3, true);
        } catch (Exception e) {
            dVar.a = b.FAIL;
            dVar.b = XAppError.REQUEST_FAIL;
            dVar.d = e.toString();
            return dVar;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0293 A[Catch: IOException -> 0x028a, TRY_LEAVE, TryCatch #1 {IOException -> 0x028a, blocks: (B:109:0x0286, B:99:0x028e, B:101:0x0293), top: B:108:0x0286 }] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:107:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0286 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x021c A[Catch: all -> 0x0280, TryCatch #0 {all -> 0x0280, blocks: (B:71:0x0218, B:73:0x021c, B:74:0x0223, B:76:0x0235, B:78:0x0255, B:79:0x025b, B:80:0x0265, B:82:0x025e), top: B:70:0x0218 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0235 A[Catch: all -> 0x0280, TryCatch #0 {all -> 0x0280, blocks: (B:71:0x0218, B:73:0x021c, B:74:0x0223, B:76:0x0235, B:78:0x0255, B:79:0x025b, B:80:0x0265, B:82:0x025e), top: B:70:0x0218 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0272 A[Catch: IOException -> 0x026e, TryCatch #10 {IOException -> 0x026e, blocks: (B:93:0x026a, B:85:0x0272, B:87:0x0277), top: B:92:0x026a }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0277 A[Catch: IOException -> 0x026e, TRY_LEAVE, TryCatch #10 {IOException -> 0x026e, blocks: (B:93:0x026a, B:85:0x0272, B:87:0x0277), top: B:92:0x026a }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x026a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x028e A[Catch: IOException -> 0x028a, TryCatch #1 {IOException -> 0x028a, blocks: (B:109:0x0286, B:99:0x028e, B:101:0x0293), top: B:108:0x0286 }] */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v10 */
    /* JADX WARN: Type inference failed for: r15v11, types: [java.io.BufferedOutputStream] */
    /* JADX WARN: Type inference failed for: r15v2, types: [java.io.BufferedOutputStream] */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r15v8 */
    /* JADX WARN: Type inference failed for: r15v9, types: [java.io.BufferedOutputStream] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.shubao.xinstall.a.b.d a(java.lang.String r19, java.util.Map r20, java.lang.String r21, java.lang.String r22, boolean r23) {
        /*
            Method dump skipped, instructions count: 668
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shubao.xinstall.a.e.a.a(java.lang.String, java.util.Map, java.lang.String, java.lang.String, boolean):com.shubao.xinstall.a.b.d");
    }

    public static d a(String str, Map map, Map map2) {
        d dVar = new d();
        try {
            String valueOf = String.valueOf(System.currentTimeMillis());
            return a(str, map, m.a((Map<Object, Object>) map2, valueOf), valueOf, false);
        } catch (Exception e) {
            dVar.a = b.FAIL;
            dVar.b = XAppError.REQUEST_FAIL;
            dVar.d = e.toString();
            return dVar;
        }
    }

    public static a a(boolean z) {
        return new a(z);
    }

    public static boolean a() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) com.shubao.xinstall.a.f.d.a.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
