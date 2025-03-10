package com.tencent.qmsp.sdk.d;

import android.util.Base64;
import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.qmsp.sdk.f.h;
import java.io.DataInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
public class e {
    private static final byte[] a = {8, 89, -108, 70, 3, 26, 39, -38, 6, 67, -94, 103, 29, 18, 117, -82, 1, 65, -106, 98, 15, 10, 66, -36, 4, 36, -108, 110, 15, 31, 84, -33, 44, 65, -104, 98, 41, 10, 83, -48, 63, 32, -108, 73, 5, 42, 66, -52, 43, 96, -67, 83, AbstractJceStruct.SIMPLE_LIST, 51, 34, -18, 52, 73, -65, 118, 10, 54, 92, -12, 116, 105, -71, 105, AbstractJceStruct.SIMPLE_LIST, 21, 124, -10, 3, 114, -87, 97, 43, 15, 118, -54, 35, 63, -93, 101, 0, 48, 66, -55, 17, 33, -22, 108, AbstractJceStruct.STRUCT_END, 99, 81, -54, 43, 36, -21, 99, 120, 104, 66, -46, 6, 88, -126, 85, 27, 61, 33, -86, 52, 105, -76, 99, 8, 14, 86, -19, 18, 37, -8, 18, 126, AbstractJceStruct.ZERO_TAG, 83, -88, 22, 113, -80, 98, 40, 63, 109, -16, 48, 118, -68, 72, 45, 24, 86, -25, 35, 88, -28, 106, 123, 33, 114, -45, 117, 81, -94, 111, 3, 60, 120, -86, 110, 123, -28, 105, 101, 52, 94, -19, 31, 97, -67, 104, 3, 41, 117, -84, 51, 82, -65, 86, 6, 57, 60, -86, 124, 115, -89, 23, 30, AbstractJceStruct.STRUCT_END, 60, -39, 52, 67, -125, 66, 36, 41, 60, -7, 20, 69, -80, 111, 120, 41, 89, -10, 47, 103, -102, 100, 15, 10, 86, -33};
    private static final byte[] b = {8, 84, -26, 87, 39, 47, Byte.MAX_VALUE, -49, 22, 81};
    private static final byte[] c = {23, 67, -110};

    private static class b {
        public int a;
        public int b;
        public int c;
        public byte[] d;

        private b() {
        }
    }

    public static b a(DataInputStream dataInputStream) {
        b bVar = new b();
        try {
            bVar.a = dataInputStream.readInt();
            bVar.b = dataInputStream.readInt();
            int readInt = dataInputStream.readInt();
            bVar.c = readInt;
            if (readInt > 0 && readInt <= 256) {
                byte[] bArr = new byte[readInt];
                bVar.d = bArr;
                dataInputStream.read(bArr);
                return bVar;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static PublicKey a(String str) {
        if (str == null) {
            str = h.a(a);
        }
        try {
            return KeyFactory.getInstance(h.a(c)).generatePublic(new X509EncodedKeySpec(Base64.decode(str, 0)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean a(DataInputStream dataInputStream, int i, byte[] bArr, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance(h.a(b));
            signature.initVerify(publicKey);
            byte[] bArr2 = new byte[LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM];
            while (true) {
                int read = i < 8192 ? dataInputStream.read(bArr2, 0, i) : dataInputStream.read(bArr2);
                if (read == 0 || read == -1) {
                    break;
                }
                signature.update(bArr2, 0, read);
                i -= read;
            }
            return signature.verify(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean a(byte[] bArr, byte[] bArr2, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance(h.a(b));
            signature.initVerify(publicKey);
            signature.update(bArr);
            return signature.verify(bArr2);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x0083, code lost:
    
        if (r0 == null) goto L62;
     */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0092 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] a(java.io.File r5, java.lang.String r6) {
        /*
            boolean r0 = r5.exists()
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            java.io.DataInputStream r0 = new java.io.DataInputStream     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            r0.<init>(r2)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            int r5 = r0.available()     // Catch: java.io.IOException -> L7a java.lang.Throwable -> L8e
            r2 = 8
            if (r5 < r2) goto L71
            r3 = 5242880(0x500000, float:7.34684E-39)
            if (r5 <= r3) goto L1f
            goto L71
        L1f:
            int r3 = r0.readInt()     // Catch: java.io.IOException -> L7a java.lang.Throwable -> L8e
            r4 = 1364419939(0x51536563, float:5.6746193E10)
            if (r3 == r4) goto L31
            r0.close()     // Catch: java.io.IOException -> L2c
            goto L30
        L2c:
            r5 = move-exception
            r5.printStackTrace()
        L30:
            return r1
        L31:
            int r3 = r0.readInt()     // Catch: java.io.IOException -> L7a java.lang.Throwable -> L8e
            if (r3 <= 0) goto L68
            r4 = 256(0x100, float:3.59E-43)
            if (r3 > r4) goto L68
            int r5 = r5 - r2
            if (r3 < r5) goto L3f
            goto L68
        L3f:
            java.security.PublicKey r6 = a(r6)     // Catch: java.io.IOException -> L7a java.lang.Throwable -> L8e
            if (r6 != 0) goto L4e
            r0.close()     // Catch: java.io.IOException -> L49
            goto L4d
        L49:
            r5 = move-exception
            r5.printStackTrace()
        L4d:
            return r1
        L4e:
            byte[] r2 = new byte[r3]     // Catch: java.io.IOException -> L7a java.lang.Throwable -> L8e
            int r5 = r5 - r3
            byte[] r5 = new byte[r5]     // Catch: java.io.IOException -> L7a java.lang.Throwable -> L8e
            r0.read(r2)     // Catch: java.io.IOException -> L7a java.lang.Throwable -> L8e
            r0.read(r5)     // Catch: java.io.IOException -> L7a java.lang.Throwable -> L8e
            boolean r6 = a(r5, r2, r6)     // Catch: java.io.IOException -> L7a java.lang.Throwable -> L8e
            if (r6 == 0) goto L85
            r0.close()     // Catch: java.io.IOException -> L63
            goto L67
        L63:
            r6 = move-exception
            r6.printStackTrace()
        L67:
            return r5
        L68:
            r0.close()     // Catch: java.io.IOException -> L6c
            goto L70
        L6c:
            r5 = move-exception
            r5.printStackTrace()
        L70:
            return r1
        L71:
            r0.close()     // Catch: java.io.IOException -> L75
            goto L79
        L75:
            r5 = move-exception
            r5.printStackTrace()
        L79:
            return r1
        L7a:
            r5 = move-exception
            goto L80
        L7c:
            r5 = move-exception
            goto L90
        L7e:
            r5 = move-exception
            r0 = r1
        L80:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L8e
            if (r0 == 0) goto L8d
        L85:
            r0.close()     // Catch: java.io.IOException -> L89
            goto L8d
        L89:
            r5 = move-exception
            r5.printStackTrace()
        L8d:
            return r1
        L8e:
            r5 = move-exception
            r1 = r0
        L90:
            if (r1 == 0) goto L9a
            r1.close()     // Catch: java.io.IOException -> L96
            goto L9a
        L96:
            r6 = move-exception
            r6.printStackTrace()
        L9a:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.d.e.a(java.io.File, java.lang.String):byte[]");
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x00ce A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00c4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean b(java.io.File r7, java.lang.String r8) {
        /*
            Method dump skipped, instructions count: 215
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.d.e.b(java.io.File, java.lang.String):boolean");
    }
}
