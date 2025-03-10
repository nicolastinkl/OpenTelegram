package com.shubao.xinstall.a.c;

import com.shubao.xinstall.a.c.a;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class e {
    /* JADX WARN: Removed duplicated region for block: B:18:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x008c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(java.io.File r5) {
        /*
            boolean r0 = r5.exists()
            r1 = 0
            if (r0 == 0) goto L9b
            boolean r0 = r5.isFile()
            if (r0 != 0) goto Lf
            goto L9b
        Lf:
            boolean r0 = r5.exists()
            if (r0 == 0) goto L89
            boolean r0 = r5.isFile()
            if (r0 != 0) goto L1c
            goto L89
        L1c:
            boolean r0 = r5.exists()
            if (r0 == 0) goto L5c
            boolean r0 = r5.isFile()
            if (r0 != 0) goto L29
            goto L5c
        L29:
            java.util.Map r0 = b(r5)
            java.io.PrintStream r2 = java.lang.System.out
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "getByteBufferValueById , destApk "
            r3.<init>(r4)
            java.lang.String r5 = r5.getAbsolutePath()
            r3.append(r5)
            java.lang.String r5 = " IdValueMap = "
            r3.append(r5)
            r3.append(r0)
            java.lang.String r5 = r3.toString()
            r2.println(r5)
            if (r0 == 0) goto L5c
            r5 = 6609066(0x64d8aa, float:9.261274E-39)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.Object r5 = r0.get(r5)
            java.nio.ByteBuffer r5 = (java.nio.ByteBuffer) r5
            goto L5d
        L5c:
            r5 = r1
        L5d:
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.String r2 = java.lang.String.valueOf(r5)
            java.lang.String r3 = "getByteValueById , id = 6609066 , value = "
            java.lang.String r2 = r3.concat(r2)
            r0.println(r2)
            if (r5 == 0) goto L89
            byte[] r0 = r5.array()
            int r2 = r5.arrayOffset()
            int r3 = r5.position()
            int r2 = r2 + r3
            int r3 = r5.arrayOffset()
            int r5 = r5.limit()
            int r3 = r3 + r5
            byte[] r5 = java.util.Arrays.copyOfRange(r0, r2, r3)
            goto L8a
        L89:
            r5 = r1
        L8a:
            if (r5 == 0) goto L9b
            int r0 = r5.length     // Catch: java.io.UnsupportedEncodingException -> L97
            if (r0 <= 0) goto L9b
            java.lang.String r0 = new java.lang.String     // Catch: java.io.UnsupportedEncodingException -> L97
            java.lang.String r2 = "UTF-8"
            r0.<init>(r5, r2)     // Catch: java.io.UnsupportedEncodingException -> L97
            return r0
        L97:
            r5 = move-exception
            r5.printStackTrace()
        L9b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shubao.xinstall.a.c.e.a(java.io.File):java.lang.String");
    }

    private static Map<Integer, ByteBuffer> b(File file) {
        if (file != null && file.exists() && file.isFile()) {
            try {
                ByteBuffer a = h.a(file);
                if (a.order() != ByteOrder.LITTLE_ENDIAN) {
                    throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
                }
                ByteBuffer a2 = a.a(a, a.capacity() - 24);
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                int i = 0;
                while (a2.hasRemaining()) {
                    i++;
                    if (a2.remaining() < 8) {
                        throw new a.C0011a("Insufficient data to read size of APK Signing Block entry #".concat(String.valueOf(i)));
                    }
                    long j = a2.getLong();
                    if (j < 4 || j > 2147483647L) {
                        throw new a.C0011a("APK Signing Block entry #" + i + " size out of range: " + j);
                    }
                    int i2 = (int) j;
                    int position = a2.position() + i2;
                    if (i2 > a2.remaining()) {
                        throw new a.C0011a("APK Signing Block entry #" + i + " size out of range: " + i2 + ", available: " + a2.remaining());
                    }
                    int i3 = a2.getInt();
                    linkedHashMap.put(Integer.valueOf(i3), a.b(a2, i2 - 4));
                    if (i3 == 1896449818) {
                        System.out.println("find V2 signature block Id : 1896449818");
                    }
                    a2.position(position);
                }
                if (linkedHashMap.isEmpty()) {
                    throw new a.C0011a("not have Id-Value Pair in APK Signing Block entry #".concat(String.valueOf(i)));
                }
                return linkedHashMap;
            } catch (a.C0011a unused) {
                System.out.println("APK : " + file.getAbsolutePath() + " not have apk signature block");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
