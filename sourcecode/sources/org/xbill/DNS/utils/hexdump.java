package org.xbill.DNS.utils;

import com.tencent.qimei.n.b;

/* loaded from: classes4.dex */
public class hexdump {
    private static final char[] hex = "0123456789ABCDEF".toCharArray();

    public static String dump(String str, byte[] bArr, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(i2);
        sb.append(b.a);
        if (str != null) {
            sb.append(" (");
            sb.append(str);
            sb.append(")");
        }
        sb.append(':');
        int length = (sb.toString().length() + 8) & (-8);
        sb.append('\t');
        int i3 = (80 - length) / 3;
        for (int i4 = 0; i4 < i2; i4++) {
            if (i4 != 0 && i4 % i3 == 0) {
                sb.append('\n');
                for (int i5 = 0; i5 < length / 8; i5++) {
                    sb.append('\t');
                }
            }
            int i6 = bArr[i4 + i] & 255;
            char[] cArr = hex;
            sb.append(cArr[i6 >> 4]);
            sb.append(cArr[i6 & 15]);
            sb.append(' ');
        }
        sb.append('\n');
        return sb.toString();
    }

    public static String dump(String str, byte[] bArr) {
        return dump(str, bArr, 0, bArr.length);
    }
}
