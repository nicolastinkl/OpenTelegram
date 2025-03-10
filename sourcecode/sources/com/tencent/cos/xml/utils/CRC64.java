package com.tencent.cos.xml.utils;

import java.util.zip.Checksum;

/* loaded from: classes.dex */
public class CRC64 implements Checksum {
    private static final int GF2_DIM = 64;
    private static final long POLY = -3932672073523589310L;
    private static final long[] table = new long[256];
    private long value;

    static {
        for (int i = 0; i < 256; i++) {
            long j = i;
            for (int i2 = 0; i2 < 8; i2++) {
                j = (j & 1) == 1 ? (j >>> 1) ^ POLY : j >>> 1;
            }
            table[i] = j;
        }
    }

    public CRC64() {
        this.value = 0L;
    }

    public CRC64(long j) {
        this.value = j;
    }

    public CRC64(byte[] bArr, int i) {
        this.value = 0L;
        update(bArr, i);
    }

    public static CRC64 fromBytes(byte[] bArr) {
        long j = 0;
        for (int i = 0; i < 4; i++) {
            j = (j << 8) ^ (bArr[i] & 255);
        }
        return new CRC64(j);
    }

    public byte[] getBytes() {
        byte[] bArr = new byte[8];
        for (int i = 0; i < 8; i++) {
            bArr[7 - i] = (byte) (this.value >>> (i * 8));
        }
        return bArr;
    }

    @Override // java.util.zip.Checksum
    public long getValue() {
        return this.value;
    }

    public void update(byte[] bArr, int i) {
        this.value = ~this.value;
        int i2 = 0;
        while (i > 0) {
            long[] jArr = table;
            long j = this.value;
            this.value = (j >>> 8) ^ jArr[((int) (bArr[i2] ^ j)) & 255];
            i2++;
            i--;
        }
        this.value = ~this.value;
    }

    public void update(byte b) {
        long j = ~this.value;
        this.value = j;
        long j2 = (j >>> 8) ^ table[((int) (b ^ j)) & 255];
        this.value = j2;
        this.value = ~j2;
    }

    @Override // java.util.zip.Checksum
    public void update(int i) {
        update((byte) (i & 255));
    }

    @Override // java.util.zip.Checksum
    public void update(byte[] bArr, int i, int i2) {
        while (i2 > 0) {
            update(bArr[i]);
            i2--;
            i++;
        }
    }

    @Override // java.util.zip.Checksum
    public void reset() {
        this.value = 0L;
    }

    private static long gf2MatrixTimes(long[] jArr, long j) {
        int i = 0;
        long j2 = 0;
        while (j != 0) {
            if ((j & 1) == 1) {
                j2 ^= jArr[i];
            }
            j >>>= 1;
            i++;
        }
        return j2;
    }

    private static void gf2MatrixSquare(long[] jArr, long[] jArr2) {
        for (int i = 0; i < 64; i++) {
            jArr[i] = gf2MatrixTimes(jArr2, jArr2[i]);
        }
    }

    public static CRC64 combine(CRC64 crc64, CRC64 crc642, long j) {
        if (j == 0) {
            return new CRC64(crc64.getValue());
        }
        long[] jArr = new long[64];
        long[] jArr2 = new long[64];
        jArr2[0] = -3932672073523589310L;
        long j2 = 1;
        for (int i = 1; i < 64; i++) {
            jArr2[i] = j2;
            j2 <<= 1;
        }
        gf2MatrixSquare(jArr, jArr2);
        gf2MatrixSquare(jArr2, jArr);
        long value = crc64.getValue();
        long value2 = crc642.getValue();
        do {
            gf2MatrixSquare(jArr, jArr2);
            if ((j & 1) == 1) {
                value = gf2MatrixTimes(jArr, value);
            }
            long j3 = j >>> 1;
            if (j3 == 0) {
                break;
            }
            gf2MatrixSquare(jArr2, jArr);
            if ((j3 & 1) == 1) {
                value = gf2MatrixTimes(jArr2, value);
            }
            j = j3 >>> 1;
        } while (j != 0);
        return new CRC64(value2 ^ value);
    }

    public static long combine(long j, long j2, long j3) {
        if (j3 == 0) {
            return j;
        }
        long[] jArr = new long[64];
        long[] jArr2 = new long[64];
        jArr2[0] = -3932672073523589310L;
        long j4 = 1;
        for (int i = 1; i < 64; i++) {
            jArr2[i] = j4;
            j4 <<= 1;
        }
        gf2MatrixSquare(jArr, jArr2);
        gf2MatrixSquare(jArr2, jArr);
        long j5 = j;
        long j6 = j3;
        do {
            gf2MatrixSquare(jArr, jArr2);
            if ((j6 & 1) == 1) {
                j5 = gf2MatrixTimes(jArr, j5);
            }
            long j7 = j6 >>> 1;
            if (j7 == 0) {
                break;
            }
            gf2MatrixSquare(jArr2, jArr);
            if ((j7 & 1) == 1) {
                j5 = gf2MatrixTimes(jArr2, j5);
            }
            j6 = j7 >>> 1;
        } while (j6 != 0);
        return j5 ^ j2;
    }
}
