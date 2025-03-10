package com.tencent.qmsp.sdk.d;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import com.tencent.qmsp.sdk.d.d;
import com.tencent.qmsp.sdk.f.g;
import com.tencent.qmsp.sdk.f.h;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class b {
    private List<d.b> a = new ArrayList();
    private SharedPreferences b;

    public b() {
        Context context;
        this.b = null;
        context = com.tencent.qmsp.sdk.app.a.getContext();
        this.b = context.getSharedPreferences(com.tencent.qmsp.sdk.c.b.a + a(d.b), 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String a(java.lang.String r3, java.lang.String r4, java.lang.String r5) {
        /*
            r2 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 != 0) goto L24
            java.lang.String r0 = "1"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto Lf
            goto L24
        Lf:
            java.lang.String r0 = "2"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L21
            android.content.Context r3 = com.tencent.qmsp.sdk.app.QmspSDK.getContext()
            r0 = 0
            java.io.File r3 = r3.getExternalFilesDir(r0)
            goto L2c
        L21:
            java.lang.String r3 = ""
            goto L30
        L24:
            android.content.Context r3 = com.tencent.qmsp.sdk.app.QmspSDK.getContext()
            java.io.File r3 = r3.getFilesDir()
        L2c:
            java.lang.String r3 = r3.getParent()
        L30:
            java.lang.String r0 = java.io.File.separator
            boolean r1 = r3.endsWith(r0)
            if (r1 != 0) goto L4d
            boolean r1 = r4.startsWith(r0)
            if (r1 != 0) goto L4d
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            r1.append(r0)
            java.lang.String r3 = r1.toString()
        L4d:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            r1.append(r4)
            java.lang.String r3 = r1.toString()
            boolean r4 = r4.endsWith(r0)
            if (r4 == 0) goto L6b
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r3)
            goto L76
        L6b:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r3)
            r4.append(r0)
        L76:
            r4.append(r5)
            java.lang.String r3 = r4.toString()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.d.b.a(java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    private String a(byte[] bArr) {
        return h.a(bArr);
    }

    private boolean a(String str, long j, long j2) {
        if (str == null || !str.equals("android") || j < 0 || j2 < 0) {
            return false;
        }
        long j3 = Build.VERSION.SDK_INT;
        if (j == 0 && j2 == 0) {
            return true;
        }
        return (j != 0 || j2 <= 0) ? (j <= 0 || j2 != 0) ? j > 0 && j2 > 0 && j3 >= j && j3 <= j2 : j3 >= j : j3 <= j2;
    }

    private boolean a(String str, String str2) {
        if (str != null && str2 != null) {
            String replace = str.replace(" ", "");
            String replace2 = str2.replace(" ", "");
            boolean equals = replace.equals("*");
            boolean equals2 = replace2.equals("*");
            if (equals && equals2) {
                return true;
            }
            String c = com.tencent.qmsp.sdk.a.c.c();
            if (TextUtils.isEmpty(c)) {
                return false;
            }
            try {
                return (!equals || equals2) ? (equals || !equals2) ? !equals && !equals2 && b(c, replace2) <= 0 && b(c, replace) >= 0 : b(c, replace) >= 0 : b(c, replace2) <= 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private int b(String str, String str2) {
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int length = split.length < split2.length ? split.length : split2.length;
        for (int i = 0; i < length; i++) {
            int parseInt = Integer.parseInt(split[i]);
            int parseInt2 = Integer.parseInt(split2[i]);
            if (parseInt > parseInt2) {
                return 1;
            }
            if (parseInt < parseInt2) {
                return -1;
            }
        }
        return 0;
    }

    private String b() {
        return com.tencent.qmsp.sdk.a.b.c() + a(d.a);
    }

    private boolean b(String str) {
        if (str == null) {
            return false;
        }
        String replace = str.replace(" ", "");
        if (replace.equals("*")) {
            return true;
        }
        String str2 = Build.CPU_ABI;
        String[] split = replace.split(",");
        if (split == null) {
            return false;
        }
        for (String str3 : split) {
            if (str2.contains(str3)) {
                return true;
            }
        }
        return false;
    }

    private boolean c(String str) {
        try {
            ArrayList arrayList = new ArrayList();
            JSONObject jSONObject = new JSONObject(str);
            byte[][] bArr = d.g;
            jSONObject.getLong(a(bArr[0]));
            jSONObject.getBoolean(a(bArr[1]));
            JSONArray jSONArray = jSONObject.getJSONArray(a(bArr[2]));
            for (int i = 0; i < jSONArray.length(); i++) {
                d.b bVar = new d.b();
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                byte[][] bArr2 = d.g;
                bVar.a = jSONObject2.getLong(a(bArr2[3]));
                bVar.b = jSONObject2.getLong(a(bArr2[4]));
                jSONObject2.getLong(a(bArr2[7]));
                bVar.c = jSONObject2.getString(a(bArr2[5]));
                bVar.d = jSONObject2.getString(a(bArr2[6]));
                bVar.e = jSONObject2.getString(a(bArr2[8]));
                bVar.f = jSONObject2.getLong(a(bArr2[15]));
                bVar.g = jSONObject2.getLong(a(bArr2[16]));
                bVar.j = jSONObject2.getString(a(bArr2[18]));
                bVar.h = jSONObject2.getString(a(bArr2[10]));
                bVar.i = jSONObject2.getString(a(bArr2[11]));
                String string = jSONObject2.getString(a(bArr2[9]));
                bVar.k = string;
                bVar.m = a(string, bVar.f, bVar.g);
                bVar.n = b(bVar.j);
                bVar.l = a(bVar.h, bVar.i);
                if (jSONObject2.has(a(bArr2[20]))) {
                    bVar.u = jSONObject2.getInt(a(bArr2[20]));
                }
                JSONArray jSONArray2 = jSONObject2.getJSONArray(a(bArr2[12]));
                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                    JSONObject jSONObject3 = jSONArray2.getJSONObject(i2);
                    d.a aVar = new d.a();
                    byte[][] bArr3 = d.g;
                    jSONObject3.getLong(a(bArr3[7]));
                    aVar.d = jSONObject3.getString(a(bArr3[13]));
                    aVar.e = jSONObject3.getString(a(bArr3[14]));
                    String string2 = jSONObject3.getString(a(bArr3[5]));
                    aVar.a = string2;
                    aVar.f = a(aVar.d, aVar.e, string2);
                    aVar.b = jSONObject3.getString(a(bArr3[6]));
                    aVar.h = jSONObject3.getLong(a(bArr3[17]));
                    if (jSONObject3.has(a(bArr3[19]))) {
                        aVar.i = jSONObject3.getString(a(bArr3[19]));
                    }
                    bVar.o.add(aVar);
                }
                arrayList.add(bVar);
            }
            this.a = arrayList;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            g.a("Qp.QPUpdate", 1, "[SFU] parsing config error");
            return false;
        }
    }

    public List<d.b> a() {
        return this.a;
    }

    public List<d.b> a(long j) {
        ArrayList arrayList = new ArrayList();
        if (a(b()) && !this.a.isEmpty()) {
            for (int i = 0; i < this.a.size(); i++) {
                d.b bVar = this.a.get(i);
                if (bVar.b == j) {
                    for (int i2 = 0; i2 < bVar.o.size(); i2++) {
                        d.a aVar = bVar.o.get(i2);
                        aVar.g = this.b.getString(aVar.f, "");
                    }
                    for (int i3 = 0; i3 < bVar.f25q.size(); i3++) {
                        d.a aVar2 = bVar.f25q.get(i3);
                        aVar2.g = this.b.getString(aVar2.f, "");
                    }
                    for (int i4 = 0; i4 < bVar.p.size(); i4++) {
                        d.a aVar3 = bVar.p.get(i4);
                        aVar3.g = this.b.getString(aVar3.f, "");
                    }
                    arrayList.add(bVar);
                }
            }
        }
        return arrayList;
    }

    public boolean a(String str) {
        byte[] a = e.a(new File(str), null);
        if (a != null) {
            return c(new String(a));
        }
        g.a("Qp.QPUpdate", 1, String.format("[SFU] invalid sig of config: %s", str));
        return false;
    }
}
