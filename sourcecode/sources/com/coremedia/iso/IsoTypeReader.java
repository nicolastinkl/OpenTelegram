package com.coremedia.iso;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class IsoTypeReader {
    public static int byte2int(byte b) {
        return b < 0 ? b + 256 : b;
    }

    public static long readUInt32(ByteBuffer byteBuffer) {
        long j = byteBuffer.getInt();
        return j < 0 ? j + 4294967296L : j;
    }

    public static int readUInt24(ByteBuffer byteBuffer) {
        return (readUInt16(byteBuffer) << 8) + 0 + byte2int(byteBuffer.get());
    }

    public static int readUInt16(ByteBuffer byteBuffer) {
        return (byte2int(byteBuffer.get()) << 8) + 0 + byte2int(byteBuffer.get());
    }

    public static int readUInt8(ByteBuffer byteBuffer) {
        return byte2int(byteBuffer.get());
    }

    public static String readString(ByteBuffer byteBuffer, int i) {
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        return Utf8.convert(bArr);
    }

    public static long readUInt64(ByteBuffer byteBuffer) {
        long readUInt32 = (readUInt32(byteBuffer) << 32) + 0;
        if (readUInt32 < 0) {
            throw new RuntimeException("I don't know how to deal with UInt64! long is not sufficient and I don't want to use BigInt");
        }
        return readUInt32 + readUInt32(byteBuffer);
    }

    public static double readFixedPoint1616(ByteBuffer byteBuffer) {
        byteBuffer.get(new byte[4]);
        return ((((0 | ((r0[0] << 24) & (-16777216))) | ((r0[1] << 16) & 16711680)) | ((r0[2] << 8) & 65280)) | (r0[3] & 255)) / 65536.0d;
    }

    public static double readFixedPoint0230(ByteBuffer byteBuffer) {
        byteBuffer.get(new byte[4]);
        return ((((0 | ((r0[0] << 24) & (-16777216))) | ((r0[1] << 16) & 16711680)) | ((r0[2] << 8) & 65280)) | (r0[3] & 255)) / 1.073741824E9d;
    }

    public static float readFixedPoint88(ByteBuffer byteBuffer) {
        byteBuffer.get(new byte[2]);
        return ((short) (((short) (0 | ((r0[0] << 8) & 65280))) | (r0[1] & 255))) / 256.0f;
    }

    public static String readIso639(ByteBuffer byteBuffer) {
        int readUInt16 = readUInt16(byteBuffer);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append((char) (((readUInt16 >> ((2 - i) * 5)) & 31) + 96));
        }
        return sb.toString();
    }

    public static String read4cc(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[4];
        byteBuffer.get(bArr);
        try {
            return new String(bArr, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
