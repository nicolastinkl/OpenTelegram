package com.shubao.xinstall.a.f;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes.dex */
public final class e {
    private static final char[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static String b = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_=";

    private static int a(char c) {
        int i;
        if (c >= 'A' && c <= 'Z') {
            return c - 'A';
        }
        if (c >= 'a' && c <= 'z') {
            i = c - 'a';
        } else {
            if (c < '0' || c > '9') {
                if (c == '+') {
                    return 62;
                }
                if (c == '/') {
                    return 63;
                }
                if (c == '=') {
                    return 0;
                }
                throw new RuntimeException("unexpected code: ".concat(String.valueOf(c)));
            }
            i = (c - '0') + 26;
        }
        return i + 26;
    }

    public static String a(byte[] bArr) {
        String str;
        int length = bArr.length;
        StringBuffer stringBuffer = new StringBuffer((bArr.length * 3) / 2);
        int i = length - 3;
        int i2 = 0;
        loop0: while (true) {
            int i3 = 0;
            while (i2 <= i) {
                int i4 = ((bArr[i2] & 255) << 16) | ((bArr[i2 + 1] & 255) << 8) | (bArr[i2 + 2] & 255);
                char[] cArr = a;
                stringBuffer.append(cArr[(i4 >> 18) & 63]);
                stringBuffer.append(cArr[(i4 >> 12) & 63]);
                stringBuffer.append(cArr[(i4 >> 6) & 63]);
                stringBuffer.append(cArr[i4 & 63]);
                i2 += 3;
                int i5 = i3 + 1;
                if (i3 >= 14) {
                    break;
                }
                i3 = i5;
            }
            stringBuffer.append(" ");
        }
        int i6 = length + 0;
        if (i2 != i6 - 2) {
            if (i2 == i6 - 1) {
                int i7 = (bArr[i2] & 255) << 16;
                char[] cArr2 = a;
                stringBuffer.append(cArr2[(i7 >> 18) & 63]);
                stringBuffer.append(cArr2[(i7 >> 12) & 63]);
                str = "==";
            }
            return stringBuffer.toString();
        }
        int i8 = ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2] & 255) << 16);
        char[] cArr3 = a;
        stringBuffer.append(cArr3[(i8 >> 18) & 63]);
        stringBuffer.append(cArr3[(i8 >> 12) & 63]);
        stringBuffer.append(cArr3[(i8 >> 6) & 63]);
        str = "=";
        stringBuffer.append(str);
        return stringBuffer.toString();
    }

    private static void a(String str, OutputStream outputStream) {
        int length = str.length();
        int i = 0;
        while (true) {
            if (i < length && str.charAt(i) <= ' ') {
                i++;
            } else {
                if (i == length) {
                    return;
                }
                int i2 = i + 2;
                int i3 = i + 3;
                int a2 = (a(str.charAt(i)) << 18) + (a(str.charAt(i + 1)) << 12) + (a(str.charAt(i2)) << 6) + a(str.charAt(i3));
                outputStream.write((a2 >> 16) & 255);
                if (str.charAt(i2) == '=') {
                    return;
                }
                outputStream.write((a2 >> 8) & 255);
                if (str.charAt(i3) == '=') {
                    return;
                }
                outputStream.write(a2 & 255);
                i += 4;
            }
        }
    }

    public static byte[] a(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            a(str, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
                return byteArray;
            } catch (IOException e) {
                System.err.println("Error while decoding BASE64: " + e.toString());
                return null;
            }
        } catch (IOException unused) {
            throw new RuntimeException();
        }
    }
}
