package com.tencent.qmsp.sdk.f;

/* loaded from: classes.dex */
public class e {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String a(byte[] bArr) {
        if (bArr != null && bArr.length != 0) {
            char[] cArr = new char[bArr.length * 2];
            for (int i = 0; i < bArr.length; i++) {
                try {
                    byte b = bArr[i];
                    int i2 = i * 2;
                    char[] cArr2 = a;
                    cArr[i2 + 1] = cArr2[b & 15];
                    cArr[i2 + 0] = cArr2[((byte) (b >>> 4)) & 15];
                } catch (Exception e) {
                    g.a("Qp.b2h", 1, " === bytes2HexStr error === " + e.toString());
                }
            }
            return new String(cArr);
        }
        return null;
    }
}
