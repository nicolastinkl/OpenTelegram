package com.tencent.qimei.j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/* compiled from: FileUtil.java */
/* loaded from: classes.dex */
public class c {
    public static final String a = "c";

    /* JADX WARN: Removed duplicated region for block: B:45:0x0096 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(java.lang.String r5, java.lang.String r6, java.lang.String r7) {
        /*
            java.lang.String r0 = "%s write success."
            java.io.File r1 = new java.io.File
            r1.<init>(r5)
            boolean r2 = r1.exists()
            if (r2 != 0) goto L10
            r1.mkdir()
        L10:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r5)
            r1.append(r7)
            java.lang.String r5 = r1.toString()
            java.io.File r1 = new java.io.File
            r1.<init>(r5)
            boolean r5 = r1.exists()
            if (r5 != 0) goto L32
            r1.createNewFile()     // Catch: java.io.IOException -> L2e
            goto L32
        L2e:
            r5 = move-exception
            com.tencent.qimei.k.a.a(r5)
        L32:
            r5 = 0
            r2 = 0
            r3 = 1
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L5e java.io.IOException -> L60 java.io.FileNotFoundException -> L7a
            r4.<init>(r1)     // Catch: java.lang.Throwable -> L5e java.io.IOException -> L60 java.io.FileNotFoundException -> L7a
            byte[] r5 = r6.getBytes()     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5a java.io.FileNotFoundException -> L5c
            r4.write(r5)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5a java.io.FileNotFoundException -> L5c
            r4.flush()     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5a java.io.FileNotFoundException -> L5c
            r4.close()     // Catch: java.io.IOException -> L48
            goto L4c
        L48:
            r5 = move-exception
            com.tencent.qimei.k.a.a(r5)
        L4c:
            java.lang.String r5 = com.tencent.qimei.j.c.a
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r6[r2] = r7
            com.tencent.qimei.k.a.b(r5, r0, r6)
            goto L93
        L56:
            r5 = move-exception
            r6 = r5
            r5 = r4
            goto L94
        L5a:
            r5 = move-exception
            goto L63
        L5c:
            r5 = move-exception
            goto L7d
        L5e:
            r6 = move-exception
            goto L94
        L60:
            r6 = move-exception
            r4 = r5
            r5 = r6
        L63:
            com.tencent.qimei.k.a.a(r5)     // Catch: java.lang.Throwable -> L56
            if (r4 == 0) goto L93
            r4.close()     // Catch: java.io.IOException -> L6c
            goto L70
        L6c:
            r5 = move-exception
            com.tencent.qimei.k.a.a(r5)
        L70:
            java.lang.String r5 = com.tencent.qimei.j.c.a
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r6[r2] = r7
            com.tencent.qimei.k.a.b(r5, r0, r6)
            goto L93
        L7a:
            r6 = move-exception
            r4 = r5
            r5 = r6
        L7d:
            com.tencent.qimei.k.a.a(r5)     // Catch: java.lang.Throwable -> L56
            if (r4 == 0) goto L93
            r4.close()     // Catch: java.io.IOException -> L86
            goto L8a
        L86:
            r5 = move-exception
            com.tencent.qimei.k.a.a(r5)
        L8a:
            java.lang.String r5 = com.tencent.qimei.j.c.a
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r6[r2] = r7
            com.tencent.qimei.k.a.b(r5, r0, r6)
        L93:
            return
        L94:
            if (r5 == 0) goto La7
            r5.close()     // Catch: java.io.IOException -> L9a
            goto L9e
        L9a:
            r5 = move-exception
            com.tencent.qimei.k.a.a(r5)
        L9e:
            java.lang.String r5 = com.tencent.qimei.j.c.a
            java.lang.Object[] r1 = new java.lang.Object[r3]
            r1[r2] = r7
            com.tencent.qimei.k.a.b(r5, r0, r1)
        La7:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qimei.j.c.a(java.lang.String, java.lang.String, java.lang.String):void");
    }

    public static String a(String str, String str2) {
        String str3 = str + str2;
        byte[] bArr = new byte[0];
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    FileInputStream fileInputStream2 = new FileInputStream(str3);
                    try {
                        bArr = new byte[fileInputStream2.available()];
                        fileInputStream2.read(bArr);
                        fileInputStream2.close();
                    } catch (FileNotFoundException e) {
                        e = e;
                        fileInputStream = fileInputStream2;
                        com.tencent.qimei.k.a.a(e);
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        return new String(bArr);
                    } catch (IOException e2) {
                        e = e2;
                        fileInputStream = fileInputStream2;
                        com.tencent.qimei.k.a.a(e);
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        return new String(bArr);
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e3) {
                                com.tencent.qimei.k.a.a(e3);
                            }
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e4) {
                    e = e4;
                } catch (IOException e5) {
                    e = e5;
                }
            } catch (IOException e6) {
                com.tencent.qimei.k.a.a(e6);
            }
            return new String(bArr);
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
