package com.shubao.xinstall.a.c;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public final class g {
    public static String a(File file) {
        RandomAccessFile randomAccessFile;
        RandomAccessFile randomAccessFile2 = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (Throwable th) {
            th = th;
        }
        try {
            long length = randomAccessFile.length();
            byte[] bArr = new byte[b.a.length];
            long length2 = length - r6.length;
            randomAccessFile.seek(length2);
            randomAccessFile.readFully(bArr);
            if (!a(bArr)) {
                throw new Exception("zip v1 magic not found");
            }
            long j = length2 - 2;
            randomAccessFile.seek(j);
            byte[] bArr2 = new byte[2];
            randomAccessFile.readFully(bArr2);
            int i = ByteBuffer.wrap(bArr2).order(ByteOrder.LITTLE_ENDIAN).getShort(0);
            if (i <= 0) {
                throw new Exception("zip channel info not found");
            }
            randomAccessFile.seek(j - i);
            byte[] bArr3 = new byte[i];
            randomAccessFile.readFully(bArr3);
            String str = new String(bArr3, "UTF-8");
            randomAccessFile.close();
            return str;
        } catch (Throwable th2) {
            th = th2;
            randomAccessFile2 = randomAccessFile;
            if (randomAccessFile2 != null) {
                randomAccessFile2.close();
            }
            throw th;
        }
    }

    private static boolean a(byte[] bArr) {
        if (bArr.length != b.a.length) {
            return false;
        }
        int i = 0;
        while (true) {
            byte[] bArr2 = b.a;
            if (i >= bArr2.length) {
                return true;
            }
            if (bArr[i] != bArr2[i]) {
                return false;
            }
            i++;
        }
    }
}
