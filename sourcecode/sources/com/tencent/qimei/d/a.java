package com.tencent.qimei.d;

/* compiled from: HttpClientUtils.java */
/* loaded from: classes.dex */
public class a implements Runnable {
    public final /* synthetic */ String a;
    public final /* synthetic */ String b;
    public final /* synthetic */ c c;

    public a(String str, String str2, c cVar) {
        this.a = str;
        this.b = str2;
        this.c = cVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00d2  */
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
            java.lang.String r3 = ""
            r4 = 1
            r5 = 0
            r6 = -1
            java.net.URL r7 = new java.net.URL     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            r7.<init>(r0)     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            java.net.URLConnection r0 = r7.openConnection()     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            java.lang.String r7 = "GET"
            r0.setRequestMethod(r7)     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            r7 = 30000(0x7530, float:4.2039E-41)
            r0.setConnectTimeout(r7)     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            r7 = 10000(0x2710, float:1.4013E-41)
            r0.setReadTimeout(r7)     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            r0.setInstanceFollowRedirects(r5)     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            java.lang.String r7 = "Content-Type"
            java.lang.String r8 = "application/json"
            r0.setRequestProperty(r7, r8)     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            java.lang.String r7 = "If-Modified-Since"
            r0.setRequestProperty(r7, r1)     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            r0.setDoInput(r4)     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            r0.setUseCaches(r5)     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            r0.connect()     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            int r6 = r0.getResponseCode()     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            r1 = 200(0xc8, float:2.8E-43)
            if (r6 != r1) goto L5a
            java.lang.String r1 = com.tencent.qimei.a.a.a(r0)     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            java.lang.String r7 = "Last-Modified"
            java.lang.String r3 = r0.getHeaderField(r7)     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            java.lang.String r0 = "0"
            r7 = 1
            goto Lc8
        L54:
            r0 = move-exception
            goto L72
        L56:
            r0 = move-exception
            goto L8a
        L58:
            r0 = move-exception
            goto Lb0
        L5a:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            r1.<init>()     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            java.lang.String r7 = "response status code != 2XX. msg: "
            r1.append(r7)     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            java.lang.String r0 = r0.getResponseMessage()     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            r1.append(r0)     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L54 java.lang.SecurityException -> L56 java.net.ConnectException -> L58
            java.lang.String r0 = "452"
            goto Lc7
        L72:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r7 = "https connect error: "
            r1.append(r7)
            java.lang.String r0 = r0.getMessage()
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            java.lang.String r0 = "499"
            goto Lc7
        L8a:
            r0.printStackTrace()
            java.lang.Object[] r1 = new java.lang.Object[r5]
            java.lang.String r7 = "网络"
            java.lang.String r8 = "没有网络权限，请在AndroidManifest文件中添加 <uses-permission android:name=\"android.permission.INTERNET\" /> "
            com.tencent.qimei.k.a.a(r7, r8, r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r7 = "security error: "
            r1.append(r7)
            java.lang.String r0 = r0.getMessage()
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            java.lang.String r0 = "199"
            goto Lc7
        Lb0:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r7 = "https connect timeout: "
            r1.append(r7)
            java.lang.String r0 = r0.getMessage()
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            java.lang.String r0 = "451"
        Lc7:
            r7 = 0
        Lc8:
            if (r7 == 0) goto Ld2
            java.lang.String[] r0 = new java.lang.String[r4]
            r0[r5] = r3
            r2.a(r1, r0)
            goto Ld5
        Ld2:
            r2.a(r0, r6, r1)
        Ld5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.d.a.run():void");
    }
}
