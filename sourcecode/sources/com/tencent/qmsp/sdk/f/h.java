package com.tencent.qmsp.sdk.f;

import java.util.HashMap;

/* loaded from: classes.dex */
public class h {
    public static byte[] a;

    static {
        new HashMap();
        a = new byte[]{6, 120, -74, 67, 37, 123, 99, -11, 32, 48, -93, 65, 60, 58, 122, -8, 49, 117, -95, 83, 111};
    }

    public static String a(byte[] bArr) {
        byte[] bArr2 = {69, 16, -45, 32, 78, 91, 23, -99, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] bArr3 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr3[i] = (byte) (bArr[i] ^ bArr2[i % 8]);
        }
        return new String(bArr3);
    }
}
