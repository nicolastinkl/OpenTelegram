package com.tencent.qmsp.sdk.d;

/* loaded from: classes.dex */
public class a {
    /* JADX WARN: Code restructure failed: missing block: B:44:0x006f, code lost:
    
        if (r2 == null) goto L47;
     */
    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0076: IF  (r2 I:??[int, boolean, OBJECT, ARRAY, byte, short, char]) == (0 ??[int, boolean, OBJECT, ARRAY, byte, short, char])  -> B:52:0x007b, block:B:51:0x0076 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(java.lang.String r9) {
        /*
            java.lang.String r0 = "MD5"
            r1 = 0
            java.security.MessageDigest r0 = java.security.MessageDigest.getInstance(r0)     // Catch: java.lang.Throwable -> L52 java.io.IOException -> L5a java.io.FileNotFoundException -> L62 java.security.NoSuchAlgorithmException -> L6a
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L52 java.io.IOException -> L5a java.io.FileNotFoundException -> L62 java.security.NoSuchAlgorithmException -> L6a
            r2.<init>(r9)     // Catch: java.lang.Throwable -> L52 java.io.IOException -> L5a java.io.FileNotFoundException -> L62 java.security.NoSuchAlgorithmException -> L6a
            java.io.File r3 = new java.io.File     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c java.io.FileNotFoundException -> L4e java.security.NoSuchAlgorithmException -> L50
            r3.<init>(r9)     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c java.io.FileNotFoundException -> L4e java.security.NoSuchAlgorithmException -> L50
            long r3 = r3.length()     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c java.io.FileNotFoundException -> L4e java.security.NoSuchAlgorithmException -> L50
            r5 = 10002432(0x98a000, double:4.941858E-317)
            int r9 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r9 <= 0) goto L1d
            r3 = r5
        L1d:
            r9 = 4096(0x1000, float:5.74E-42)
            byte[] r9 = new byte[r9]     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c java.io.FileNotFoundException -> L4e java.security.NoSuchAlgorithmException -> L50
        L21:
            r5 = 0
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 <= 0) goto L3b
            int r5 = r2.read(r9)     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c java.io.FileNotFoundException -> L4e java.security.NoSuchAlgorithmException -> L50
            r6 = -1
            if (r5 == r6) goto L3b
            long r6 = (long) r5     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c java.io.FileNotFoundException -> L4e java.security.NoSuchAlgorithmException -> L50
            int r8 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r8 <= 0) goto L34
            int r5 = (int) r3     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c java.io.FileNotFoundException -> L4e java.security.NoSuchAlgorithmException -> L50
        L34:
            long r6 = (long) r5     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c java.io.FileNotFoundException -> L4e java.security.NoSuchAlgorithmException -> L50
            long r3 = r3 - r6
            r6 = 0
            r0.update(r9, r6, r5)     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c java.io.FileNotFoundException -> L4e java.security.NoSuchAlgorithmException -> L50
            goto L21
        L3b:
            r2.close()     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c java.io.FileNotFoundException -> L4e java.security.NoSuchAlgorithmException -> L50
            byte[] r9 = r0.digest()     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c java.io.FileNotFoundException -> L4e java.security.NoSuchAlgorithmException -> L50
            java.lang.String r9 = com.tencent.qmsp.sdk.f.e.a(r9)     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4c java.io.FileNotFoundException -> L4e java.security.NoSuchAlgorithmException -> L50
            r2.close()     // Catch: java.lang.Exception -> L49
        L49:
            return r9
        L4a:
            r9 = move-exception
            goto L54
        L4c:
            r9 = move-exception
            goto L5c
        L4e:
            r9 = move-exception
            goto L64
        L50:
            r9 = move-exception
            goto L6c
        L52:
            r9 = move-exception
            r2 = r1
        L54:
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L75
            if (r2 == 0) goto L74
            goto L71
        L5a:
            r9 = move-exception
            r2 = r1
        L5c:
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L75
            if (r2 == 0) goto L74
            goto L71
        L62:
            r9 = move-exception
            r2 = r1
        L64:
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L75
            if (r2 == 0) goto L74
            goto L71
        L6a:
            r9 = move-exception
            r2 = r1
        L6c:
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L75
            if (r2 == 0) goto L74
        L71:
            r2.close()     // Catch: java.lang.Exception -> L74
        L74:
            return r1
        L75:
            r9 = move-exception
            if (r2 == 0) goto L7b
            r2.close()     // Catch: java.lang.Exception -> L7b
        L7b:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.d.a.a(java.lang.String):java.lang.String");
    }
}
