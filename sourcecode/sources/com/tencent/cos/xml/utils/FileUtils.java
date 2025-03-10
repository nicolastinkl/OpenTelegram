package com.tencent.cos.xml.utils;

import android.util.Log;
import com.tencent.cos.xml.CosXmlBaseService;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.internal.Util;
import org.telegram.messenger.CharacterCompat;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
public class FileUtils {
    public static void saveInputStreamToTmpFile(InputStream inputStream, File file, long j, long j2) throws IOException {
        FileOutputStream fileOutputStream;
        int read;
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr = new byte[LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM];
            long j3 = 0;
            if (j2 < 0) {
                j2 = Long.MAX_VALUE;
            }
            if (j > 0) {
                inputStream.skip(j);
            }
            while (j3 < j2 && (read = inputStream.read(bArr)) != -1) {
                long j4 = read;
                fileOutputStream.write(bArr, 0, (int) Math.min(j4, j2 - j3));
                j3 += j4;
            }
            fileOutputStream.flush();
            Util.closeQuietly(fileOutputStream);
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                Util.closeQuietly(fileOutputStream2);
            }
            throw th;
        }
    }

    public static String tempCache(InputStream inputStream) throws CosXmlClientException {
        FileOutputStream fileOutputStream = null;
        try {
            if (inputStream == null) {
                return null;
            }
            try {
                String str = CosXmlBaseService.appCachePath + File.separator + "temp.tmp";
                Log.d("UnitTest", str);
                File file = new File(str);
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT];
                    while (true) {
                        int read = inputStream.read(bArr, 0, CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT);
                        if (read > 0) {
                            fileOutputStream2.write(bArr, 0, read);
                        } else {
                            fileOutputStream2.flush();
                            CloseUtil.closeQuietly(fileOutputStream2);
                            CloseUtil.closeQuietly(inputStream);
                            return str;
                        }
                    }
                } catch (IOException e) {
                    e = e;
                    throw new CosXmlClientException(ClientErrorCode.IO_ERROR.getCode(), e);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    CloseUtil.closeQuietly(fileOutputStream);
                    CloseUtil.closeQuietly(inputStream);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void intercept(String str, long j, long j2) throws IOException {
        if (j2 <= 0) {
            clearFile(str);
        }
        File file = new File(str);
        File file2 = new File(str.concat("." + System.currentTimeMillis() + ".temp"));
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        FileInputStream fileInputStream = new FileInputStream(file);
        if (j > 0 && fileInputStream.skip(j) != j) {
            throw new IOException("skip size is not equal to offset");
        }
        byte[] bArr = new byte[CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT];
        long j3 = CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT;
        long min = Math.min(j3, j2);
        while (true) {
            int read = fileInputStream.read(bArr, 0, (int) min);
            if (read <= 0) {
                break;
            }
            fileOutputStream.write(bArr, 0, read);
            j2 -= read;
            min = Math.min(j3, j2);
        }
        deleteFileIfExist(str);
        if (file2.renameTo(file)) {
            return;
        }
        throw new IOException("rename to " + str + "failed");
    }

    public static boolean deleteFileIfExist(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static boolean clearFile(String str) throws IOException {
        if (deleteFileIfExist(str)) {
            return new File(str).createNewFile();
        }
        return false;
    }

    public static File[] listFile(File file) {
        if (file == null || !file.isDirectory()) {
            return null;
        }
        return file.listFiles();
    }
}
