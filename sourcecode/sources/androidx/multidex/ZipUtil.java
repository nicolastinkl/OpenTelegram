package androidx.multidex;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.CRC32;
import java.util.zip.ZipException;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
final class ZipUtil {

    static class CentralDirectory {
        long offset;
        long size;

        CentralDirectory() {
        }
    }

    static long getZipCrc(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        try {
            return computeCrcOfCentralDir(randomAccessFile, findCentralDirectory(randomAccessFile));
        } finally {
            randomAccessFile.close();
        }
    }

    static CentralDirectory findCentralDirectory(RandomAccessFile randomAccessFile) throws IOException, ZipException {
        long length = randomAccessFile.length() - 22;
        if (length < 0) {
            throw new ZipException("File too short to be a zip file: " + randomAccessFile.length());
        }
        long j = length - 65536;
        long j2 = j >= 0 ? j : 0L;
        int reverseBytes = Integer.reverseBytes(101010256);
        do {
            randomAccessFile.seek(length);
            if (randomAccessFile.readInt() == reverseBytes) {
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                CentralDirectory centralDirectory = new CentralDirectory();
                centralDirectory.size = Integer.reverseBytes(randomAccessFile.readInt()) & 4294967295L;
                centralDirectory.offset = Integer.reverseBytes(randomAccessFile.readInt()) & 4294967295L;
                return centralDirectory;
            }
            length--;
        } while (length >= j2);
        throw new ZipException("End Of Central Directory signature not found");
    }

    static long computeCrcOfCentralDir(RandomAccessFile randomAccessFile, CentralDirectory centralDirectory) throws IOException {
        CRC32 crc32 = new CRC32();
        long j = centralDirectory.size;
        randomAccessFile.seek(centralDirectory.offset);
        int min = (int) Math.min(16384L, j);
        byte[] bArr = new byte[LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM];
        int read = randomAccessFile.read(bArr, 0, min);
        while (read != -1) {
            crc32.update(bArr, 0, read);
            j -= read;
            if (j == 0) {
                break;
            }
            read = randomAccessFile.read(bArr, 0, (int) Math.min(16384L, j));
        }
        return crc32.getValue();
    }
}
