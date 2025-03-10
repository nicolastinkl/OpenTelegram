package com.tencent.qmsp.sdk.d;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.qmsp.sdk.a.f;
import com.tencent.qmsp.sdk.d.d;
import com.tencent.qmsp.sdk.f.g;
import com.tencent.qmsp.sdk.f.h;
import com.tencent.qmsp.sdk.f.k;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class c implements Handler.Callback {
    private SharedPreferences h;
    private com.tencent.qmsp.sdk.d.b i;
    private ConcurrentHashMap<String, com.tencent.qmsp.sdk.b.b> a = new ConcurrentHashMap<>();
    final Object c = new Object();
    private int d = 0;
    private d.b e = null;
    private String f = "";
    private String g = "";
    private int j = 0;
    private Handler b = new Handler(com.tencent.qmsp.sdk.app.b.e().b(), this);

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            c.this.a();
        }
    }

    class b implements com.tencent.qmsp.sdk.b.e {
        b() {
        }

        @Override // com.tencent.qmsp.sdk.b.e
        public void a(int i, JSONObject jSONObject) {
            if (i != 161 || jSONObject == null) {
                return;
            }
            c.this.a(i, jSONObject);
        }
    }

    /* renamed from: com.tencent.qmsp.sdk.d.c$c, reason: collision with other inner class name */
    static class C0034c {
        public int a;
        public int b;
        public String c;
        public String d;
        public String e;
        public String f;
        public int g;
        public boolean h;
        public int i;
        public long j;
        public int k;

        public C0034c(int i, int i2, String str, String str2, String str3, String str4, int i3, boolean z, int i4, long j, int i5) {
            this.a = i;
            this.b = i2;
            this.c = str;
            this.d = str2;
            this.e = str3;
            this.f = str4;
            this.g = i3;
            this.h = z;
            this.i = i4;
            this.j = j;
            this.k = i5;
        }

        public String toString() {
            return "filePath=" + this.c + ",fileName=" + this.d + ",fileId=" + this.b + ",fileUrl=" + this.e + ",fileHash=" + this.f + ",fileVersion=" + this.g + ",zipFlag=" + this.h + ",startTime=" + this.j + ",tryTimes=" + this.i + ",downloadFlag=" + this.k;
        }
    }

    public c(Context context) {
        this.h = null;
        this.i = null;
        this.h = context.getSharedPreferences(com.tencent.qmsp.sdk.c.b.a + a(d.b), 0);
        this.i = new com.tencent.qmsp.sdk.d.b();
    }

    private int a(int i, String str) {
        String a2;
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (i == 0) {
            g.a("Qp.QUM", 1, "No matched update from server.");
            return -1;
        }
        if (this.h.getInt(a(d.d), 0) < i) {
            return 0;
        }
        String c = c();
        if (new File(c).exists() && (a2 = com.tencent.qmsp.sdk.d.a.a(c)) != null && a2.equalsIgnoreCase(str)) {
            return a2.equalsIgnoreCase(str) ? 1 : -1;
        }
        return 0;
    }

    private String a(byte[] bArr) {
        return h.a(bArr);
    }

    private void a(int i, int i2) {
        String appID;
        try {
            JSONObject a2 = com.tencent.qmsp.sdk.a.d.a(2);
            if (a2 == null) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(com.tencent.qmsp.sdk.a.e.a(20), i);
            jSONObject.put(com.tencent.qmsp.sdk.a.e.a(21), i2);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(com.tencent.qmsp.sdk.a.e.a(15), a2);
            jSONObject2.put(com.tencent.qmsp.sdk.a.e.a(16), jSONObject);
            StringBuilder sb = new StringBuilder();
            sb.append("[SFU] request : ");
            sb.append(jSONObject2.toString());
            g.d("Qp.QUM", 0, sb.toString());
            com.tencent.qmsp.sdk.b.g b2 = com.tencent.qmsp.sdk.b.g.b();
            appID = com.tencent.qmsp.sdk.app.a.getAppID();
            b2.a(2, appID, 2, jSONObject2, new b());
            a("0X80078AA", i, this.j, "", "");
            g.a("Qp.QUM", 1, String.format("[SFU] send update query: fileid=%d, localversion=%d", Integer.valueOf(i), Integer.valueOf(i2)));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void a(String str, int i, int i2, String str2, String str3) {
        String format = String.format("%d", Integer.valueOf(i));
        d.b bVar = this.e;
        int i3 = bVar != null ? (int) bVar.b : 0;
        String format2 = bVar != null ? String.format("%d", Long.valueOf(bVar.a)) : "";
        g.a("Qp.QUM", 1, String.format("[SFU] report: actiontype=%s, actionname=%s, actionfrom=%d, actionresult=%d, sectionId=%s, reportId=%s, fileInfo: %s", str, str, Integer.valueOf(i3), Integer.valueOf(this.d), format2, format, "", ""));
        com.tencent.qmsp.sdk.a.g gVar = new com.tencent.qmsp.sdk.a.g();
        try {
            gVar.a(str).a(format).a(format2).a(this.d).a(i2).a(str2).a(str3);
            f.a(gVar.toString(), 2);
        } catch (Exception e) {
            e.printStackTrace();
            g.b(g.a, 0, "onReport error! <JsonObject userData>!");
        }
    }

    private void a(boolean z, com.tencent.qmsp.sdk.b.b bVar) {
        int i;
        if (!z || bVar == null) {
            a(3);
            return;
        }
        File file = new File(bVar.b);
        C0034c c0034c = (C0034c) bVar.a();
        if (!file.exists() || c0034c == null) {
            a(3);
            return;
        }
        g.a("Qp.QUM", 1, String.format("[SFU] http download complete: %s, %s", bVar.b, c0034c.e));
        int i2 = c0034c.a;
        if (i2 == 1) {
            File file2 = new File(c());
            new File(bVar.b).renameTo(file2);
            a("0X80078AC", c0034c.g, this.j, "", "");
            if (!this.i.a(file2.toString())) {
                a("0X80078AD", c0034c.g, this.j, c0034c.d, c0034c.e);
                g.a("Qp.QUM", 1, "[SFU] invalid config (sig not accepted)");
                a(1);
                return;
            } else {
                this.h.edit().putInt(a(d.d), c0034c.g).commit();
                if (this.i.a().isEmpty()) {
                    g.a("Qp.QUM", 1, "[SFU] config ok but without any sections");
                    i = 16;
                } else {
                    i = 5;
                }
            }
        } else {
            if (i2 != 2) {
                return;
            }
            a("0X80078AE", c0034c.g, this.j, "", "");
            i = 7;
        }
        b(i);
    }

    private boolean a(C0034c c0034c) {
        Context context;
        if (c0034c == null) {
            return false;
        }
        if (c0034c.a == 2 && c0034c.k != 1) {
            context = com.tencent.qmsp.sdk.app.a.getContext();
            if (!com.tencent.qmsp.sdk.f.f.b(context)) {
                g.a("Qp.QUM", 1, "[SFU] donot download file because not using wifi");
                com.tencent.qmsp.sdk.a.a.a(3, 3);
                return false;
            }
        }
        if (c0034c.i < 3 && this.a.contains(c0034c.f.toLowerCase())) {
            return false;
        }
        com.tencent.qmsp.sdk.b.b bVar = new com.tencent.qmsp.sdk.b.b();
        bVar.a = c0034c.e;
        bVar.b = c0034c.c + c0034c.d;
        bVar.d = c0034c.d;
        bVar.c = c0034c.c;
        c0034c.i = c0034c.i + 1;
        bVar.a(c0034c);
        this.a.put(c0034c.f.toLowerCase(), bVar);
        try {
            com.tencent.qmsp.sdk.b.d.a(bVar);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.a("Qp.QUM", 1, String.format("[SFU] begin http download %s", c0034c.e));
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x008b, code lost:
    
        if (r11.r == r5) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(com.tencent.qmsp.sdk.d.d.b r11) {
        /*
            r10 = this;
            java.lang.String r0 = "Qp.QUM"
            r1 = 0
            r2 = 1
            if (r11 == 0) goto L8e
            java.lang.Object[] r3 = new java.lang.Object[r2]
            long r4 = r11.a
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r3[r1] = r4
            java.lang.String r4 = "[SFU] backup: sid=%d"
            java.lang.String r3 = java.lang.String.format(r4, r3)
            com.tencent.qmsp.sdk.f.g.a(r0, r2, r3)
            long r3 = r11.r
            r5 = 0
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 != 0) goto L28
            java.lang.String r11 = "[SFU] no files need to backup"
            com.tencent.qmsp.sdk.f.g.a(r0, r2, r11)
        L26:
            r11 = 1
            goto L8f
        L28:
            if (r7 <= 0) goto L8e
            java.lang.String r3 = r10.f(r11)
            r4 = 0
        L2f:
            java.util.List<com.tencent.qmsp.sdk.d.d$a> r7 = r11.f25q
            int r7 = r7.size()
            if (r4 >= r7) goto L69
            java.util.List<com.tencent.qmsp.sdk.d.d$a> r7 = r11.f25q
            java.lang.Object r7 = r7.get(r4)
            com.tencent.qmsp.sdk.d.d$a r7 = (com.tencent.qmsp.sdk.d.d.a) r7
            int r8 = r7.j
            if (r2 != r8) goto L66
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r3)
            java.lang.String r9 = r7.c
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            java.io.File r9 = new java.io.File
            java.lang.String r7 = r7.f
            r9.<init>(r7)
            java.io.File r7 = new java.io.File
            r7.<init>(r8)
            r10.a(r9, r7)
            r7 = 1
            long r5 = r5 + r7
        L66:
            int r4 = r4 + 1
            goto L2f
        L69:
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.Long r4 = java.lang.Long.valueOf(r5)
            r3[r1] = r4
            java.util.List<com.tencent.qmsp.sdk.d.d$a> r4 = r11.f25q
            int r4 = r4.size()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r2] = r4
            java.lang.String r4 = "[SFU] backup %d files of %d"
            java.lang.String r3 = java.lang.String.format(r4, r3)
            com.tencent.qmsp.sdk.f.g.a(r0, r2, r3)
            long r3 = r11.r
            int r11 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r11 != 0) goto L8e
            goto L26
        L8e:
            r11 = 0
        L8f:
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r11)
            r3[r1] = r4
            java.lang.String r1 = "[SFU] backup result: %b"
            java.lang.String r1 = java.lang.String.format(r1, r3)
            com.tencent.qmsp.sdk.f.g.a(r0, r2, r1)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.d.c.a(com.tencent.qmsp.sdk.d.d$b):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0088 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0081 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.io.BufferedInputStream] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.io.BufferedInputStream] */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9, types: [java.io.BufferedInputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(java.io.File r5, java.io.File r6) {
        /*
            r4 = this;
            r0 = 0
            r1 = 0
            boolean r2 = r6.exists()     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L68
            if (r2 == 0) goto Lf
            boolean r2 = r6.delete()     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L68
            if (r2 != 0) goto L25
            return r1
        Lf:
            java.io.File r2 = new java.io.File     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L68
            java.lang.String r3 = r6.getParent()     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L68
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L68
            boolean r3 = r2.exists()     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L68
            if (r3 != 0) goto L25
            boolean r2 = r2.mkdirs()     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L68
            if (r2 != 0) goto L25
            return r1
        L25:
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L68
            r2.<init>(r6)     // Catch: java.lang.Throwable -> L65 java.io.IOException -> L68
            java.io.BufferedInputStream r6 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L5f java.io.IOException -> L61
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L5f java.io.IOException -> L61
            r3.<init>(r5)     // Catch: java.lang.Throwable -> L5f java.io.IOException -> L61
            r6.<init>(r3)     // Catch: java.lang.Throwable -> L5f java.io.IOException -> L61
            com.tencent.qmsp.sdk.f.a r5 = com.tencent.qmsp.sdk.f.a.a()     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
            r0 = 4096(0x1000, float:5.74E-42)
            byte[] r5 = r5.a(r0)     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
        L3e:
            int r0 = r6.read(r5)     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
            r3 = -1
            if (r0 == r3) goto L4c
            r2.write(r5, r1, r0)     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
            r2.flush()     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
            goto L3e
        L4c:
            com.tencent.qmsp.sdk.f.a r0 = com.tencent.qmsp.sdk.f.a.a()     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
            r0.a(r5)     // Catch: java.lang.Throwable -> L5b java.io.IOException -> L5d
            r5 = 1
            r2.close()     // Catch: java.io.IOException -> L57
        L57:
            r6.close()     // Catch: java.io.IOException -> L5a
        L5a:
            return r5
        L5b:
            r5 = move-exception
            goto L7c
        L5d:
            r5 = move-exception
            goto L63
        L5f:
            r5 = move-exception
            goto L7d
        L61:
            r5 = move-exception
            r6 = r0
        L63:
            r0 = r2
            goto L6a
        L65:
            r5 = move-exception
            r6 = r0
            goto L7f
        L68:
            r5 = move-exception
            r6 = r0
        L6a:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L7a
            if (r0 == 0) goto L74
            r0.close()     // Catch: java.io.IOException -> L73
            goto L74
        L73:
        L74:
            if (r6 == 0) goto L79
            r6.close()     // Catch: java.io.IOException -> L79
        L79:
            return r1
        L7a:
            r5 = move-exception
            r2 = r0
        L7c:
            r0 = r6
        L7d:
            r6 = r0
            r0 = r2
        L7f:
            if (r0 == 0) goto L86
            r0.close()     // Catch: java.io.IOException -> L85
            goto L86
        L85:
        L86:
            if (r6 == 0) goto L8b
            r6.close()     // Catch: java.io.IOException -> L8b
        L8b:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.d.c.a(java.io.File, java.io.File):boolean");
    }

    private void b() {
        try {
            this.h.edit().putLong(a(d.c), System.currentTimeMillis()).commit();
            b(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:89:0x0115, code lost:
    
        if (e(r13) == false) goto L84;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(int r13) {
        /*
            Method dump skipped, instructions count: 336
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.d.c.b(int):void");
    }

    private void b(com.tencent.qmsp.sdk.b.c cVar) {
        if (cVar == null) {
            return;
        }
        int i = cVar.a;
        if (i == 2) {
            g.a("Qp.QUM", 1, String.format("[SFU] http download error=%d", Integer.valueOf(i)));
            return;
        }
        C0034c c0034c = (C0034c) cVar.b.a();
        if (c0034c == null) {
            return;
        }
        boolean z = cVar.a == 0;
        if (!z) {
            try {
                int i2 = c0034c.i;
                if (i2 < 3) {
                    g.a("Qp.QUM", 1, String.format("[SFU] retried to download, retry=%d, result=%b, url=%s", Integer.valueOf(i2), Boolean.valueOf(z), c0034c.e));
                    a(c0034c);
                    return;
                } else {
                    File file = new File(cVar.b.b);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        this.a.remove(c0034c.f.toLowerCase());
        a(z, cVar.b);
    }

    private void b(d.b bVar) {
        if (bVar != null) {
            g.a("Qp.QUM", 1, String.format("[SFU] cleanup: sid=%d", Long.valueOf(bVar.a)));
            com.tencent.qmsp.sdk.f.d.a(g(bVar), false);
        }
    }

    private String c() {
        return e() + a(d.a);
    }

    private void c(int i) {
        this.d = i;
    }

    private boolean c(d.b bVar) {
        return true;
    }

    private boolean d() {
        boolean z;
        this.e = null;
        List<d.b> a2 = this.i.a();
        int i = 0;
        while (true) {
            if (i >= a2.size()) {
                z = false;
                break;
            }
            d.b bVar = a2.get(i);
            if (!bVar.a()) {
                if (bVar.m && bVar.l && bVar.n) {
                    this.e = bVar;
                    c(0);
                    g.a("Qp.QUM", 1, String.format("[SFU] next update: sid=%d", Long.valueOf(this.e.a)));
                    z = true;
                    break;
                }
                g.d("Qp.QUM", 1, String.format("[SFU] not matched section: sid=%d, os: %b, qq:%b, cpu:%b", Long.valueOf(bVar.a), Boolean.valueOf(bVar.m), Boolean.valueOf(bVar.l), Boolean.valueOf(bVar.n)));
                bVar.b();
            }
            i++;
        }
        g.a("Qp.QUM", 1, String.format("[SFU] get next section result? %b", Boolean.valueOf(z)));
        return z;
    }

    private boolean d(d.b bVar) {
        if (bVar == null) {
            return false;
        }
        g.a("Qp.QUM", 1, String.format("[SFU] download package: sid=%d", Long.valueOf(bVar.a)));
        return a(new C0034c(2, 0, g(bVar), bVar.c, bVar.e, bVar.d, 0, true, 0, System.currentTimeMillis(), bVar.u));
    }

    private String e() {
        String c = com.tencent.qmsp.sdk.a.b.c();
        File file = new File(c);
        if (!file.exists()) {
            file.mkdirs();
        }
        return c;
    }

    private boolean e(d.b bVar) {
        if (bVar == null) {
            return false;
        }
        g.a("Qp.QUM", 1, String.format("[SFU] get different: sid=%d", Long.valueOf(bVar.a)));
        List<d.a> list = bVar.o;
        for (int i = 0; i < list.size(); i++) {
            d.a aVar = list.get(i);
            if (new File(aVar.f).exists()) {
                String a2 = com.tencent.qmsp.sdk.d.a.a(aVar.f);
                if (a2 == null || !a2.equalsIgnoreCase(aVar.b)) {
                    aVar.j = 1;
                    if (a2 == null) {
                        a2 = "";
                    }
                    aVar.c = a2;
                    bVar.f25q.add(aVar);
                    bVar.r++;
                }
            } else {
                aVar.j = 2;
                bVar.f25q.add(aVar);
            }
        }
        g.a("Qp.QUM", 1, String.format("[SFU] need to update %d files of %d", Integer.valueOf(bVar.f25q.size()), Integer.valueOf(bVar.o.size())));
        return !bVar.f25q.isEmpty();
    }

    private String f(d.b bVar) {
        String str = g(bVar) + "bak" + File.separator;
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return str;
    }

    private boolean f() {
        boolean z;
        List<d.b> a2 = this.i.a();
        int i = 0;
        while (true) {
            if (i >= a2.size()) {
                z = true;
                break;
            }
            if (!a2.get(i).a()) {
                z = false;
                break;
            }
            i++;
        }
        Object[] objArr = new Object[1];
        objArr[0] = z ? "yes" : "no";
        g.a("Qp.QUM", 1, String.format("[SFU] all complete: %s", objArr));
        return z;
    }

    private String g(d.b bVar) {
        String str = e() + bVar.a + File.separator;
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return str;
    }

    private void g() {
        g.a("Qp.QUM", 1, "[SFU] all sections update complete");
        try {
            if (!TextUtils.isEmpty(this.f)) {
                com.tencent.qmsp.sdk.c.f.i().e();
                this.h.edit().putString(a(d.e), this.f).putString(a(d.f), this.g).commit();
                a("0X80078B6", 0, this.j, this.f, this.g);
            }
            g.a("Qp.QUM", 1, String.format("[SFU] notify update complete: %s", this.f));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private String h(d.b bVar) {
        return g(bVar) + bVar.c;
    }

    private void h() {
        synchronized (this.c) {
            a("0X80078B5", 0, this.j, "", "");
            this.e = null;
            g.a("Qp.QUM", 1, "[SFU] update ended");
        }
    }

    private void i() {
        a(1, this.h.getInt(a(d.d), 0));
        this.h.edit().remove(a(d.e)).commit();
    }

    private void i(d.b bVar) {
        if (bVar != null) {
            g.a("Qp.QUM", 1, String.format("[SFU] update complete: sid=%d", Long.valueOf(bVar.a)));
            bVar.b();
            if (j()) {
                this.f += String.format("#%d#", Long.valueOf(bVar.b));
                this.g += String.format("#%d#", Long.valueOf(bVar.a));
            }
            a("0X80078B4", 0, this.j, "", "");
        }
    }

    private boolean j() {
        return this.d == 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00a4, code lost:
    
        if (r4 == r14.p.size()) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean j(com.tencent.qmsp.sdk.d.d.b r14) {
        /*
            r13 = this;
            java.lang.String r0 = "Qp.QUM"
            r1 = 0
            r2 = 1
            if (r14 == 0) goto La8
            java.lang.Object[] r3 = new java.lang.Object[r2]
            long r4 = r14.a
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r3[r1] = r4
            java.lang.String r4 = "[SFU] rollback: sid=%d"
            java.lang.String r3 = java.lang.String.format(r4, r3)
            com.tencent.qmsp.sdk.f.g.a(r0, r2, r3)
            java.util.List<com.tencent.qmsp.sdk.d.d$a> r3 = r14.p
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L29
            java.lang.String r14 = "[SFU] no files need to rollback"
            com.tencent.qmsp.sdk.f.g.a(r0, r2, r14)
        L26:
            r14 = 1
            goto La9
        L29:
            java.lang.String r3 = r13.f(r14)
            r4 = 0
            r6 = 0
        L30:
            java.util.List<com.tencent.qmsp.sdk.d.d$a> r7 = r14.p
            int r7 = r7.size()
            if (r6 >= r7) goto L9b
            java.util.List<com.tencent.qmsp.sdk.d.d$a> r7 = r14.p
            java.lang.Object r7 = r7.get(r6)
            com.tencent.qmsp.sdk.d.d$a r7 = (com.tencent.qmsp.sdk.d.d.a) r7
            java.io.File r8 = new java.io.File
            java.lang.String r9 = r7.f
            r8.<init>(r9)
            java.io.File r9 = new java.io.File
            java.lang.String r10 = r7.g
            r9.<init>(r10)
            boolean r10 = r9.exists()
            r11 = 1
            if (r10 == 0) goto L60
            boolean r10 = r9.delete()
            if (r10 != 0) goto L5f
            r9.deleteOnExit()
        L5f:
            long r4 = r4 + r11
        L60:
            int r9 = r7.j
            r10 = 2
            if (r9 != r10) goto L75
            boolean r9 = r8.exists()
            if (r9 == 0) goto L75
            boolean r9 = r8.delete()
            if (r9 != 0) goto L74
            r8.deleteOnExit()
        L74:
            long r4 = r4 + r11
        L75:
            java.io.File r9 = new java.io.File
            java.lang.String r7 = r7.c
            r9.<init>(r3, r7)
            boolean r7 = r9.exists()
            if (r7 == 0) goto L98
            boolean r7 = r9.canRead()
            if (r7 == 0) goto L98
            boolean r7 = r8.exists()
            if (r7 == 0) goto L98
            boolean r7 = r8.canWrite()
            if (r7 == 0) goto L98
            r13.a(r9, r8)
            long r4 = r4 + r11
        L98:
            int r6 = r6 + 1
            goto L30
        L9b:
            java.util.List<com.tencent.qmsp.sdk.d.d$a> r14 = r14.p
            int r14 = r14.size()
            long r6 = (long) r14
            int r14 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r14 != 0) goto La8
            goto L26
        La8:
            r14 = 0
        La9:
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r14)
            r3[r1] = r4
            java.lang.String r1 = "[SFU] rollback result: %b"
            java.lang.String r1 = java.lang.String.format(r1, r3)
            com.tencent.qmsp.sdk.f.g.a(r0, r2, r1)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.d.c.j(com.tencent.qmsp.sdk.d.d$b):boolean");
    }

    private boolean k(d.b bVar) {
        boolean z;
        if (bVar != null) {
            g.a("Qp.QUM", 1, String.format("[SFU] unzip package: sid=%d", Long.valueOf(bVar.a)));
            String g = g(bVar);
            File file = new File(g);
            if (!file.exists()) {
                file.mkdir();
            }
            if (k.a(h(bVar), g) == 0) {
                z = true;
                g.a("Qp.QUM", 1, String.format("[SFU] unzip result: %b", Boolean.valueOf(z)));
                return z;
            }
        }
        z = false;
        g.a("Qp.QUM", 1, String.format("[SFU] unzip result: %b", Boolean.valueOf(z)));
        return z;
    }

    private boolean l(d.b bVar) {
        boolean z;
        if (bVar != null && !bVar.f25q.isEmpty()) {
            g.a("Qp.QUM", 1, String.format("[SFU] do update files: sid=%d", Long.valueOf(bVar.a)));
            String g = g(bVar);
            int i = 0;
            while (true) {
                if (i >= bVar.f25q.size()) {
                    break;
                }
                d.a aVar = bVar.f25q.get(i);
                File file = new File(g, aVar.b);
                File file2 = new File(aVar.f);
                if (file.exists() && file.canRead()) {
                    boolean a2 = a(file, file2);
                    if (!a2) {
                        String str = aVar.f + ".1";
                        File file3 = new File(str);
                        aVar.j = 3;
                        aVar.g = str;
                        a2 = a(file, file3);
                        g.a("Qp.QUM", 1, String.format("[SFU] copied failed, renamed to: %s", aVar.g));
                        if (a2) {
                            this.h.edit().putString(aVar.f, aVar.g).commit();
                        }
                    }
                    if (!a2) {
                        a("0X80078B2", (int) aVar.h, this.j, aVar.a, aVar.b);
                        g.a("Qp.QUM", 1, String.format("[SFU] failed copied: %s", aVar.f));
                        break;
                    }
                    g.a("Qp.QUM", 1, String.format("[SFU] success copied: %s", aVar.f));
                    bVar.p.add(aVar);
                    bVar.s++;
                } else {
                    g.a("Qp.QUM", 1, String.format("[SFU] copied failed, src not existed or cannot read: %s", file.toString()));
                }
                i++;
            }
            g.a("Qp.QUM", 1, String.format("[SFU] update %d files of %d", Long.valueOf(bVar.s), Integer.valueOf(bVar.f25q.size())));
            if (bVar.s == bVar.f25q.size()) {
                z = true;
                g.a("Qp.QUM", 1, String.format("[SFU] update result: %b", Boolean.valueOf(z)));
                return z;
            }
        }
        z = false;
        g.a("Qp.QUM", 1, String.format("[SFU] update result: %b", Boolean.valueOf(z)));
        return z;
    }

    private boolean m(d.b bVar) {
        boolean z;
        if (bVar != null) {
            g.a("Qp.QUM", 1, String.format("[SFU] verify: sid=%d", Long.valueOf(bVar.a)));
            if (bVar.f25q.isEmpty()) {
                g.a("Qp.QUM", 1, String.format("[SFU] no diff: sid=%d", Long.valueOf(bVar.a)));
            } else {
                for (int i = 0; i < bVar.f25q.size(); i++) {
                    d.a aVar = bVar.f25q.get(i);
                    String a2 = com.tencent.qmsp.sdk.d.a.a(TextUtils.isEmpty(aVar.g) ? aVar.f : aVar.g);
                    if (a2 == null || !a2.equalsIgnoreCase(aVar.b)) {
                        g.a("Qp.QUM", 1, String.format("[SFU] not matched: %s!=%s, sid=%d", a2, aVar.b, Long.valueOf(bVar.a)));
                    }
                }
            }
            z = true;
            g.a("Qp.QUM", 1, String.format("[SFU] verify result: %b", Boolean.valueOf(z)));
            return z;
        }
        z = false;
        g.a("Qp.QUM", 1, String.format("[SFU] verify result: %b", Boolean.valueOf(z)));
        return z;
    }

    private boolean n(d.b bVar) {
        boolean z;
        if (bVar != null) {
            g.a("Qp.QUM", 1, String.format("[SFU] verify package: sid=%d", Long.valueOf(bVar.a)));
            String a2 = com.tencent.qmsp.sdk.d.a.a(h(bVar));
            if (a2 == null) {
                return false;
            }
            z = a2.equalsIgnoreCase(bVar.d);
        } else {
            z = false;
        }
        g.a("Qp.QUM", 1, String.format("[SFU] verify result: %b", Boolean.valueOf(z)));
        return z;
    }

    public void a() {
        boolean taskStatus;
        AtomicInteger atomUpdateInterval;
        AtomicInteger atomUpdateInterval2;
        AtomicInteger atomUpdateInterval3;
        AtomicInteger atomUpdateInterval4;
        taskStatus = com.tencent.qmsp.sdk.app.a.getTaskStatus();
        if (!taskStatus) {
            g.a("Qp.QUM", 1, "[SFU] Plugin Update Task Finish！");
            return;
        }
        if (!com.tencent.qmsp.sdk.c.f.i().a(1001).booleanValue()) {
            atomUpdateInterval4 = com.tencent.qmsp.sdk.app.a.getAtomUpdateInterval();
            a(atomUpdateInterval4.get());
            return;
        }
        long j = this.h.getLong(a(d.c), 0L);
        long currentTimeMillis = System.currentTimeMillis() - j;
        long j2 = currentTimeMillis >= 0 ? currentTimeMillis : 0L;
        atomUpdateInterval = com.tencent.qmsp.sdk.app.a.getAtomUpdateInterval();
        long j3 = atomUpdateInterval.get();
        g.a("Qp.QUM", 2, String.format("[SFU] startSFU last time: %d, interval: %d", Long.valueOf(j), Long.valueOf(j2)));
        atomUpdateInterval2 = com.tencent.qmsp.sdk.app.a.getAtomUpdateInterval();
        if (j2 >= atomUpdateInterval2.get()) {
            b();
        } else {
            atomUpdateInterval3 = com.tencent.qmsp.sdk.app.a.getAtomUpdateInterval();
            j3 = atomUpdateInterval3.get() - j2;
            g.a("Qp.QUM", 0, "[SFU] next time: " + j3);
        }
        a(j3);
    }

    protected void a(int i) {
        g.b("Qp.QUM", 2, String.format("[SFU] update error: %d", Integer.valueOf(i)));
        switch (i) {
            case 1:
                File file = new File(c());
                if (file.exists()) {
                    file.delete();
                    break;
                }
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
                c(i);
                break;
        }
        b(1);
    }

    public void a(int i, Object obj) {
        String format;
        try {
            JSONObject jSONObject = (JSONObject) obj;
            StringBuilder sb = new StringBuilder();
            sb.append("[SFU] rcv : ");
            sb.append(obj);
            boolean z = false;
            g.a("Qp.QUM", 0, sb.toString());
            if (jSONObject != null) {
                int optInt = jSONObject.optInt("st");
                int intValue = Integer.valueOf(jSONObject.optString("tsi")).intValue();
                this.j = intValue;
                a("0X80078B8", optInt, intValue, "", "");
                if (optInt == 0) {
                    int optInt2 = jSONObject.optInt("sc");
                    int optInt3 = jSONObject.optInt("fi");
                    int optInt4 = jSONObject.optInt("fv");
                    String str = (String) jSONObject.opt("fh");
                    String str2 = (String) jSONObject.opt("fu");
                    int optInt5 = jSONObject.optInt("zf");
                    boolean z2 = optInt5 == 1;
                    int a2 = a(optInt4, str);
                    g.a("Qp.QUM", 1, String.format("[SFU] resp: cmd=%d, status=%d, fileid=%d, fileversion=%d, md5=%s, url=%s, zipped: %d", Integer.valueOf(optInt2), Integer.valueOf(optInt), Integer.valueOf(optInt3), Integer.valueOf(optInt4), str, str2, Integer.valueOf(optInt5)));
                    if (a2 == 0) {
                        a("0X80078AB", optInt3, this.j, "", "");
                        g.a("Qp.QUM", 1, String.format("Need to update config file, fileid=%d", Integer.valueOf(optInt3)));
                        z = a(new C0034c(1, optInt3, e(), a(d.a), str2, str, optInt4, z2, 0, System.currentTimeMillis(), 1));
                    } else if (a2 != 1) {
                        format = "[SFU] NO Need UPDATE";
                    } else if (this.i.a(new File(c()).toString())) {
                        b(this.i.a().isEmpty() ? 16 : 5);
                        z = true;
                    }
                } else {
                    format = String.format("[SFU] Server replied with error, status=%d", Integer.valueOf(optInt));
                }
                g.a("Qp.QUM", 1, format);
            }
            if (z) {
                return;
            }
            b(17);
        } catch (Exception e) {
            e.printStackTrace();
            b(17);
        }
    }

    public void a(long j) {
        com.tencent.qmsp.sdk.c.f.i().c().postDelayed(new a(), j);
    }

    public void a(com.tencent.qmsp.sdk.b.c cVar) {
        Handler handler = this.b;
        if (handler != null) {
            handler.obtainMessage(1052688, cVar).sendToTarget();
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what != 1052688) {
            return false;
        }
        b((com.tencent.qmsp.sdk.b.c) message.obj);
        return false;
    }
}
