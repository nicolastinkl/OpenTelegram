package com.shubao.xinstall.a.a.a;

import android.net.Uri;

/* loaded from: classes.dex */
public final class h implements Runnable {
    private Uri a;
    private com.shubao.xinstall.a.a.b b;

    public h(Uri uri, com.shubao.xinstall.a.a.b bVar) {
        this.a = uri;
        this.b = bVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x007b  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r4 = this;
            com.shubao.xinstall.a.a.b r0 = r4.b
            com.shubao.xinstall.a.a.h r0 = r0.c()
            r1 = 10
            boolean r0 = r0.a(r1)
            if (r0 != 0) goto L2b
            com.shubao.xinstall.a.a.b r0 = r4.b
            com.shubao.xinstall.a.a.g r0 = r0.f()
            java.lang.String r0 = r0.d()
            boolean r1 = com.shubao.xinstall.a.f.o.a
            if (r1 == 0) goto L2a
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r1 = "初始化失败:"
            java.lang.String r0 = r1.concat(r0)
            com.shubao.xinstall.a.f.o.a(r0)
        L2a:
            return
        L2b:
            com.shubao.xinstall.a.a.b r0 = r4.b
            com.shubao.xinstall.a.b.a r0 = r0.d()
            java.lang.Boolean r0 = r0.a()
            boolean r0 = r0.booleanValue()
            if (r0 != 0) goto L46
            boolean r0 = com.shubao.xinstall.a.f.o.a
            if (r0 == 0) goto Lef
            java.lang.String r0 = "没有唤醒上报能力"
            com.shubao.xinstall.a.f.o.a(r0)
            return
        L46:
            r0 = 0
            android.net.Uri r1 = r4.a
            if (r1 == 0) goto L76
            java.util.List r1 = r1.getPathSegments()
            if (r1 == 0) goto L76
            int r2 = r1.size()
            r3 = 2
            if (r2 <= r3) goto L76
            java.lang.Object r1 = r1.get(r3)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r1 = com.shubao.xinstall.a.f.g.b(r1)
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch: org.json.JSONException -> L6f
            r2.<init>(r1)     // Catch: org.json.JSONException -> L6f
            java.lang.String r0 = "uo"
            r2.remove(r0)     // Catch: org.json.JSONException -> L6d
            goto L75
        L6d:
            r0 = move-exception
            goto L72
        L6f:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L72:
            r0.printStackTrace()
        L75:
            r0 = r2
        L76:
            if (r0 != 0) goto L7b
            java.lang.String r0 = ""
            goto L7f
        L7b:
            java.lang.String r0 = r0.toString()
        L7f:
            long r1 = java.lang.System.currentTimeMillis()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r2 = 1
            com.shubao.xinstall.a.e.a.a(r2)
            com.shubao.xinstall.a.a.b r2 = r4.b
            java.lang.String r3 = "wakeup22"
            java.lang.String r2 = r2.a(r3)
            com.shubao.xinstall.a.a.b r3 = r4.b
            java.util.IdentityHashMap r3 = r3.a()
            com.shubao.xinstall.a.b.d r0 = com.shubao.xinstall.a.e.a.a(r2, r3, r0, r1)
            com.shubao.xinstall.a.a.b r1 = r4.b
            java.lang.String r2 = r0.e
            r1.d(r2)
            com.shubao.xinstall.a.e.b r1 = r0.a
            com.shubao.xinstall.a.e.b r2 = com.shubao.xinstall.a.e.b.SUCCESS
            if (r1 != r2) goto Lb5
            boolean r0 = com.shubao.xinstall.a.f.o.a
            if (r0 == 0) goto Lef
            java.lang.String r0 = "唤醒上报成功"
            com.shubao.xinstall.a.f.o.a(r0)
            return
        Lb5:
            com.shubao.xinstall.a.e.b r2 = com.shubao.xinstall.a.e.b.RESP588
            if (r1 != r2) goto Ld7
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "唤醒上报失败: "
            r1.<init>(r2)
            java.lang.String r0 = r0.d
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            com.shubao.xinstall.a.f.o.c(r0)
            com.shubao.xinstall.a.a.b r0 = r4.b
            com.shubao.xinstall.a.a.e r0 = r0.j()
            r0.a()
            return
        Ld7:
            boolean r1 = com.shubao.xinstall.a.f.o.a
            if (r1 == 0) goto Lef
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "唤醒上报失败:"
            r1.<init>(r2)
            java.lang.String r0 = r0.d
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            com.shubao.xinstall.a.f.o.c(r0)
        Lef:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shubao.xinstall.a.a.a.h.run():void");
    }
}
