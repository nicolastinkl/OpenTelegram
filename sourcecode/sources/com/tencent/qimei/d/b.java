package com.tencent.qimei.d;

/* compiled from: HttpClientUtils.java */
/* loaded from: classes.dex */
public class b implements Runnable {
    public final /* synthetic */ String a;
    public final /* synthetic */ String b;
    public final /* synthetic */ c c;

    public b(String str, String str2, c cVar) {
        this.a = str;
        this.b = str2;
        this.c = cVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00d6  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            r9 = this;
            java.lang.String r0 = r9.a
            java.lang.String r1 = r9.b
            com.tencent.qimei.d.c r2 = r9.c
            r3 = 1
            r4 = 0
            r5 = -1
            java.net.URL r6 = new java.net.URL     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            r6.<init>(r0)     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            java.net.URLConnection r0 = r6.openConnection()     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            java.lang.String r6 = "POST"
            r0.setRequestMethod(r6)     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            r6 = 30000(0x7530, float:4.2039E-41)
            r0.setConnectTimeout(r6)     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            r6 = 10000(0x2710, float:1.4013E-41)
            r0.setReadTimeout(r6)     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            r0.setInstanceFollowRedirects(r4)     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            java.lang.String r6 = "Content-Type"
            java.lang.String r7 = "application/json"
            r0.setRequestProperty(r6, r7)     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            r0.setDoInput(r3)     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            r0.setDoOutput(r3)     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            r0.setUseCaches(r4)     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            java.io.OutputStreamWriter r6 = new java.io.OutputStreamWriter     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            java.io.OutputStream r7 = r0.getOutputStream()     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            java.lang.String r8 = "UTF-8"
            r6.<init>(r7, r8)     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            r6.write(r1)     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            r6.flush()     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            r0.connect()     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            int r5 = r0.getResponseCode()     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            r1 = 200(0xc8, float:2.8E-43)
            if (r5 != r1) goto L60
            java.lang.String r0 = com.tencent.qimei.a.a.a(r0)     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            java.lang.String r1 = "0"
            goto Lce
        L5a:
            r0 = move-exception
            goto L78
        L5c:
            r0 = move-exception
            goto L90
        L5e:
            r0 = move-exception
            goto Lb6
        L60:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            r1.<init>()     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            java.lang.String r3 = "response status code != 2XX. msg: "
            r1.append(r3)     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            java.lang.String r0 = r0.getResponseMessage()     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            r1.append(r0)     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L5a java.lang.SecurityException -> L5c java.net.ConnectException -> L5e
            java.lang.String r1 = "452"
            goto Lcd
        L78:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "https connect error: "
            r1.append(r3)
            java.lang.String r0 = r0.getMessage()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            java.lang.String r1 = "499"
            goto Lcd
        L90:
            r0.printStackTrace()
            java.lang.Object[] r1 = new java.lang.Object[r4]
            java.lang.String r3 = "网络"
            java.lang.String r6 = "没有网络权限，请在AndroidManifest文件中添加 <uses-permission android:name=\"android.permission.INTERNET\" /> "
            com.tencent.qimei.k.a.a(r3, r6, r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "security error: "
            r1.append(r3)
            java.lang.String r0 = r0.getMessage()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            java.lang.String r1 = "199"
            goto Lcd
        Lb6:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "https connect timeout: "
            r1.append(r3)
            java.lang.String r0 = r0.getMessage()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            java.lang.String r1 = "451"
        Lcd:
            r3 = 0
        Lce:
            if (r3 == 0) goto Ld6
            java.lang.String[] r1 = new java.lang.String[r4]
            r2.a(r0, r1)
            goto Ld9
        Ld6:
            r2.a(r1, r5, r0)
        Ld9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.d.b.run():void");
    }
}
