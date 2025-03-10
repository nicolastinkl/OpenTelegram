package io.openinstall.sdk;

import java.nio.ByteOrder;

/* loaded from: classes.dex */
public class eb {
    public static int a(byte b, byte b2, byte b3, byte b4) {
        return ((b & 255) << 24) | ((b2 & 255) << 16) | ((b3 & 255) << 8) | (b4 & 255);
    }

    public static int a(byte[] bArr, int i, ByteOrder byteOrder) {
        byte b;
        byte b2;
        byte b3;
        byte b4;
        if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            b = bArr[i + 3];
            b2 = bArr[i + 2];
            b3 = bArr[i + 1];
            b4 = bArr[i];
        } else {
            b = bArr[i];
            b2 = bArr[i + 1];
            b3 = bArr[i + 2];
            b4 = bArr[i + 3];
        }
        return a(b, b2, b3, b4);
    }

    public static long a(byte b, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
        return ((a(b, b2, b3, b4) & (-1)) << 32) | ((-1) & a(b5, b6, b7, b8));
    }

    public static short a(byte b, byte b2) {
        return (short) ((b << 8) | (b2 & 255));
    }

    public static short b(byte[] bArr, int i, ByteOrder byteOrder) {
        byte b;
        byte b2;
        if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            b = bArr[i + 1];
            b2 = bArr[i];
        } else {
            b = bArr[i];
            b2 = bArr[i + 1];
        }
        return a(b, b2);
    }

    public static long c(byte[] bArr, int i, ByteOrder byteOrder) {
        byte b;
        byte b2;
        byte b3;
        byte b4;
        byte b5;
        byte b6;
        byte b7;
        byte b8;
        if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            b = bArr[i + 7];
            b2 = bArr[i + 6];
            b3 = bArr[i + 5];
            b4 = bArr[i + 4];
            b5 = bArr[i + 3];
            b6 = bArr[i + 2];
            b7 = bArr[i + 1];
            b8 = bArr[i];
        } else {
            b = bArr[i];
            b2 = bArr[i + 1];
            b3 = bArr[i + 2];
            b4 = bArr[i + 3];
            b5 = bArr[i + 4];
            b6 = bArr[i + 5];
            b7 = bArr[i + 6];
            b8 = bArr[i + 7];
        }
        return a(b, b2, b3, b4, b5, b6, b7, b8);
    }
}
