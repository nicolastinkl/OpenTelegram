package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import android.util.Pair;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class aj implements Runnable {
    protected int a;
    protected long b;
    protected long c;
    private int d;
    private int e;
    private final Context f;
    private final int g;
    private final byte[] h;
    private final aa i;
    private final ac j;
    private final af k;
    private final ai l;
    private final int m;
    private final ah n;
    private final ah o;
    private String p;

    /* renamed from: q, reason: collision with root package name */
    private final String f12q;
    private final Map<String, String> r;
    private boolean s;

    public aj(Context context, int i, int i2, byte[] bArr, String str, String str2, ah ahVar, boolean z) {
        this(context, i, i2, bArr, str, str2, ahVar, 2, 30000, z);
    }

    public aj(Context context, int i, int i2, byte[] bArr, String str, String str2, ah ahVar, int i3, int i4, boolean z) {
        this.d = 2;
        this.e = 30000;
        this.p = null;
        this.a = 0;
        this.b = 0L;
        this.c = 0L;
        this.s = false;
        this.f = context;
        this.i = aa.a(context);
        this.h = bArr;
        this.j = ac.a();
        if (af.a == null) {
            af.a = new af(context);
        }
        this.k = af.a;
        ai a = ai.a();
        this.l = a;
        this.m = i;
        this.p = str;
        this.f12q = str2;
        this.n = ahVar;
        this.o = a.a;
        this.g = i2;
        if (i3 > 0) {
            this.d = i3;
        }
        if (i4 > 0) {
            this.e = i4;
        }
        this.s = z;
        this.r = null;
    }

    private static void a(String str) {
        al.e("[Upload] Failed to upload(%d): %s", 1, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(boolean r5, int r6, java.lang.String r7) {
        /*
            r4 = this;
            int r0 = r4.g
            r1 = 630(0x276, float:8.83E-43)
            if (r0 == r1) goto L1a
            r1 = 640(0x280, float:8.97E-43)
            if (r0 == r1) goto L17
            r1 = 830(0x33e, float:1.163E-42)
            if (r0 == r1) goto L1a
            r1 = 840(0x348, float:1.177E-42)
            if (r0 == r1) goto L17
            java.lang.String r0 = java.lang.String.valueOf(r0)
            goto L1c
        L17:
            java.lang.String r0 = "userinfo"
            goto L1c
        L1a:
            java.lang.String r0 = "crash"
        L1c:
            r1 = 1
            r2 = 0
            if (r5 == 0) goto L2a
            java.lang.Object[] r6 = new java.lang.Object[r1]
            r6[r2] = r0
            java.lang.String r0 = "[Upload] Success: %s"
            com.tencent.bugly.proguard.al.a(r0, r6)
            goto L3d
        L2a:
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r3[r2] = r6
            r3[r1] = r0
            r6 = 2
            r3[r6] = r7
            java.lang.String r6 = "[Upload] Failed to upload(%d) %s: %s"
            com.tencent.bugly.proguard.al.e(r6, r3)
        L3d:
            long r0 = r4.b
            long r2 = r4.c
            long r0 = r0 + r2
            r2 = 0
            int r6 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r6 <= 0) goto L5d
            com.tencent.bugly.proguard.ai r6 = r4.l
            boolean r0 = r4.s
            long r0 = r6.a(r0)
            long r2 = r4.b
            long r0 = r0 + r2
            long r2 = r4.c
            long r0 = r0 + r2
            com.tencent.bugly.proguard.ai r6 = r4.l
            boolean r2 = r4.s
            r6.a(r0, r2)
        L5d:
            com.tencent.bugly.proguard.ah r6 = r4.n
            if (r6 == 0) goto L64
            r6.a(r5, r7)
        L64:
            com.tencent.bugly.proguard.ah r6 = r4.o
            if (r6 == 0) goto L6b
            r6.a(r5, r7)
        L6b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.aj.a(boolean, int, java.lang.String):void");
    }

    private static boolean a(br brVar, aa aaVar, ac acVar) {
        if (brVar == null) {
            al.d("resp == null!", new Object[0]);
            return false;
        }
        byte b = brVar.a;
        if (b != 0) {
            al.e("resp result error %d", Byte.valueOf(b));
            return false;
        }
        try {
            if (!ap.b(brVar.g) && !aa.b().i().equals(brVar.g)) {
                w.a().a(ac.a, "device", brVar.g.getBytes("UTF-8"), true);
                aaVar.d(brVar.g);
            }
        } catch (Throwable th) {
            al.a(th);
        }
        aaVar.m = brVar.e;
        int i = brVar.b;
        if (i == 510) {
            byte[] bArr = brVar.c;
            if (bArr == null) {
                al.e("[Upload] Strategy data is null. Response cmd: %d", Integer.valueOf(i));
                return false;
            }
            bt btVar = (bt) ae.a(bArr, bt.class);
            if (btVar == null) {
                al.e("[Upload] Failed to decode strategy from server. Response cmd: %d", Integer.valueOf(brVar.b));
                return false;
            }
            acVar.a(btVar);
        }
        return true;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        ac acVar;
        Pair pair;
        boolean booleanValue;
        try {
            this.a = 0;
            this.b = 0L;
            this.c = 0L;
            if (ab.c(this.f) == null) {
                str = "network is not available";
            } else {
                byte[] bArr = this.h;
                if (bArr != null && bArr.length != 0) {
                    if (this.f != null && this.i != null && (acVar = this.j) != null && this.k != null) {
                        str = acVar.c() == null ? "illegal local strategy" : null;
                    }
                    str = "illegal access error";
                }
                str = "request package is empty!";
            }
            if (str != null) {
                a(false, 0, str);
                return;
            }
            byte[] a = ap.a(this.h);
            if (a == null) {
                a(false, 0, "failed to zip request body");
                return;
            }
            HashMap hashMap = new HashMap(10);
            hashMap.put("tls", "1");
            hashMap.put("prodId", this.i.e());
            hashMap.put("bundleId", this.i.c);
            hashMap.put("appVer", this.i.o);
            Map<String, String> map = this.r;
            if (map != null) {
                hashMap.putAll(map);
            }
            hashMap.put("cmd", Integer.toString(this.g));
            hashMap.put("platformId", Byte.toString((byte) 1));
            hashMap.put("sdkVer", this.i.h);
            hashMap.put("strategylastUpdateTime", Long.toString(this.j.c().o));
            this.l.a(this.m, System.currentTimeMillis());
            String str2 = this.p;
            this.j.c();
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = i + 1;
                if (i < this.d) {
                    if (i3 > 1) {
                        al.d("[Upload] Failed to upload last time, wait and try(%d) again.", Integer.valueOf(i3));
                        ap.b(this.e);
                        if (i3 == this.d) {
                            al.d("[Upload] Use the back-up url at the last time: %s", this.f12q);
                            str2 = this.f12q;
                        }
                    }
                    al.c("[Upload] Send %d bytes", Integer.valueOf(a.length));
                    str2 = b(str2);
                    al.c("[Upload] Upload to %s with cmd %d (pid=%d | tid=%d).", str2, Integer.valueOf(this.g), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                    byte[] a2 = this.k.a(str2, a, this, hashMap);
                    Map<String, String> map2 = this.k.c;
                    Pair<Boolean, Boolean> a3 = a(a2, map2);
                    if (!((Boolean) a3.first).booleanValue()) {
                        booleanValue = ((Boolean) a3.second).booleanValue();
                    } else {
                        Pair<Boolean, Boolean> a4 = a(map2);
                        if (!((Boolean) a4.first).booleanValue()) {
                            booleanValue = ((Boolean) a4.second).booleanValue();
                        } else {
                            byte[] b = ap.b(a2);
                            if (b != null) {
                                a2 = b;
                            }
                            br a5 = ae.a(a2);
                            if (a5 == null) {
                                a(false, 1, "failed to decode response package");
                                Boolean bool = Boolean.FALSE;
                                pair = new Pair(bool, bool);
                            } else {
                                Object[] objArr = new Object[2];
                                objArr[0] = Integer.valueOf(a5.b);
                                byte[] bArr2 = a5.c;
                                objArr[1] = Integer.valueOf(bArr2 == null ? 0 : bArr2.length);
                                al.c("[Upload] Response cmd is: %d, length of sBuffer is: %d", objArr);
                                if (!a(a5, this.i, this.j)) {
                                    a(false, 2, "failed to process response package");
                                    Boolean bool2 = Boolean.FALSE;
                                    pair = new Pair(bool2, bool2);
                                } else {
                                    a(true, 2, "successfully uploaded");
                                    Boolean bool3 = Boolean.TRUE;
                                    pair = new Pair(bool3, bool3);
                                }
                            }
                            booleanValue = !((Boolean) pair.first).booleanValue() ? ((Boolean) pair.second).booleanValue() : false;
                        }
                    }
                    if (!booleanValue) {
                        return;
                    }
                    i = i3;
                    i2 = 1;
                } else {
                    a(false, i2, "failed after many attempts");
                    return;
                }
            }
        } catch (Throwable th) {
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private Pair<Boolean, Boolean> a(byte[] bArr, Map<String, String> map) {
        if (bArr == null) {
            a("Failed to upload for no response!");
            return new Pair<>(Boolean.FALSE, Boolean.TRUE);
        }
        al.c("[Upload] Received %d bytes", Integer.valueOf(bArr.length));
        if (bArr.length == 0) {
            a(false, 1, "response data from server is empty");
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    al.c("[Upload] HTTP headers from server: key = %s, value = %s", entry.getKey(), entry.getValue());
                }
            }
            Boolean bool = Boolean.FALSE;
            return new Pair<>(bool, bool);
        }
        Boolean bool2 = Boolean.TRUE;
        return new Pair<>(bool2, bool2);
    }

    public final void a(long j) {
        this.a++;
        this.b += j;
    }

    public final void b(long j) {
        this.c += j;
    }

    private static String b(String str) {
        if (ap.b(str)) {
            return str;
        }
        try {
            return String.format("%s?aid=%s", str, UUID.randomUUID().toString());
        } catch (Throwable th) {
            al.a(th);
            return str;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.util.Pair<java.lang.Boolean, java.lang.Boolean> a(java.util.Map<java.lang.String, java.lang.String> r8) {
        /*
            Method dump skipped, instructions count: 293
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.aj.a(java.util.Map):android.util.Pair");
    }
}
