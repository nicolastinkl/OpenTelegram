package io.openinstall.sdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/* loaded from: classes.dex */
public class ea {
    private static int a(FileChannel fileChannel, long j, ByteBuffer byteBuffer) throws IOException {
        int read;
        int i = 0;
        while (byteBuffer.hasRemaining() && (read = fileChannel.read(byteBuffer, j)) != -1) {
            j += read;
            i += read;
        }
        return i;
    }

    private static int a(FileChannel fileChannel, long j, byte[] bArr, int i, int i2) throws IOException {
        ByteBuffer wrap = ByteBuffer.wrap(bArr, i, i2);
        int i3 = 0;
        while (i3 < i2) {
            int read = fileChannel.read(wrap, i3 + j);
            if (read == -1) {
                break;
            }
            i3 += read;
        }
        return i3;
    }

    public static dz a(FileChannel fileChannel) throws IOException {
        ee b = b(fileChannel);
        if (b == null) {
            return null;
        }
        long j = b.f;
        if (j < 32) {
            return new dz(b);
        }
        byte[] bArr = new byte[24];
        a(fileChannel, j - 24, bArr, 0, 24);
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        long c = eb.c(bArr, 0, byteOrder);
        long c2 = eb.c(bArr, 8, byteOrder);
        long c3 = eb.c(bArr, 16, byteOrder);
        if (c2 != 2334950737559900225L || c3 != 3617552046287187010L) {
            return new dz(b);
        }
        int i = (int) (8 + c);
        long j2 = i;
        long j3 = b.f - j2;
        if (i < 32 || j3 < 0) {
            return new dz(b);
        }
        if (j2 > 20971520) {
            return new dz(b);
        }
        ByteBuffer allocate = ByteBuffer.allocate(i - 24);
        allocate.order(byteOrder);
        if (a(fileChannel, j3, allocate) != allocate.capacity() || ((ByteBuffer) allocate.flip()).getLong() != c) {
            return new dz(b);
        }
        ed edVar = new ed(j3);
        while (allocate.remaining() >= 12) {
            long j4 = allocate.getLong();
            int i2 = allocate.getInt();
            int i3 = (int) (j4 - 4);
            if (i3 < 0 || i3 > allocate.remaining()) {
                break;
            }
            byte[] bArr2 = new byte[i3];
            allocate.get(bArr2, 0, i3);
            edVar.a(i2, bArr2);
        }
        return new dz(edVar, b);
    }

    private static void a(FileChannel fileChannel, FileChannel fileChannel2, long j, long j2) throws IOException {
        while (j2 > 0) {
            long transferTo = fileChannel.transferTo(j, j2, fileChannel2);
            j += transferTo;
            j2 -= transferTo;
        }
    }

    public static void a(byte[] bArr, File file, File file2) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        try {
            FileChannel channel = fileInputStream.getChannel();
            FileChannel channel2 = fileOutputStream.getChannel();
            dz a = a(channel);
            channel.position(0L);
            if (a == null) {
                a(channel, channel2, 0L, channel.size());
                return;
            }
            a.a(bArr);
            ed b = a.b();
            ee a2 = a.a();
            if (b != null) {
                a(channel, channel2, 0L, b.b());
                for (ByteBuffer byteBuffer : b.e()) {
                    while (byteBuffer.hasRemaining()) {
                        channel2.write(byteBuffer);
                    }
                }
                long j = a2.f;
                a(channel, channel2, j, a2.h - j);
            } else {
                a(channel, channel2, 0L, a2.h);
            }
            ByteBuffer a3 = a2.a(b != null ? b.a() : a2.f);
            while (a3.hasRemaining()) {
                channel2.write(a3);
            }
        } finally {
            fileInputStream.close();
            fileOutputStream.close();
        }
    }

    private static ee b(FileChannel fileChannel) throws IOException {
        long j;
        byte[] bArr = new byte[128];
        long size = fileChannel.size();
        ee eeVar = null;
        long j2 = 22;
        if (size < 22) {
            return null;
        }
        long j3 = 0;
        long j4 = 106;
        long max = Math.max(0L, (size > 65557 ? size - 65557 : 0L) - j4);
        long j5 = size - 128;
        while (j5 >= max) {
            int i = 0;
            if (j5 < j3) {
                int i2 = (int) (-j5);
                Arrays.fill(bArr, 0, i2, (byte) 0);
                i = i2;
            }
            long j6 = j5;
            long j7 = j4;
            a(fileChannel, j5 < j3 ? 0L : j5, bArr, i, 128 - i);
            int i3 = 106;
            while (i3 >= 0) {
                if (bArr[i3 + 0] == 80 && bArr[i3 + 1] == 75 && bArr[i3 + 2] == 5 && bArr[i3 + 3] == 6) {
                    ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
                    int b = eb.b(bArr, i3 + 20, byteOrder) & 65535;
                    long j8 = j6 + i3;
                    if (j8 + j2 + b == size) {
                        ee eeVar2 = new ee();
                        eeVar2.h = j8;
                        eeVar2.a = eb.b(bArr, i3 + 4, byteOrder) & 65535;
                        eeVar2.b = eb.b(bArr, i3 + 6, byteOrder) & 65535;
                        eeVar2.c = eb.b(bArr, i3 + 8, byteOrder) & 65535;
                        eeVar2.d = 65535 & eb.b(bArr, i3 + 10, byteOrder);
                        eeVar2.e = eb.a(bArr, i3 + 12, byteOrder) & 4294967295L;
                        eeVar2.f = eb.a(bArr, i3 + 16, byteOrder) & 4294967295L;
                        if (b > 0) {
                            byte[] bArr2 = new byte[b];
                            eeVar2.g = bArr2;
                            a(fileChannel, eeVar2.h + 22, bArr2, 0, b);
                        }
                        return eeVar2;
                    }
                    j = 22;
                } else {
                    j = j2;
                }
                i3--;
                j2 = j;
            }
            j5 = j6 - j7;
            j4 = j7;
            eeVar = null;
            j3 = 0;
        }
        return eeVar;
    }
}
