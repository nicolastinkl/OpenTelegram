package com.tencent.qmsp.sdk.f;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* loaded from: classes.dex */
public class j {
    public static int a(String str, File file) {
        ZipInputStream zipInputStream;
        byte[] bArr = new byte[1024];
        int i = -1;
        try {
            try {
                zipInputStream = new ZipInputStream(new FileInputStream(str));
                loop0: while (true) {
                    try {
                        ZipEntry nextEntry = zipInputStream.getNextEntry();
                        while (nextEntry != null) {
                            String name = nextEntry.getName();
                            if (name != null && !name.contains("../")) {
                                File a = a(file, nextEntry);
                                if (!nextEntry.isDirectory()) {
                                    File parentFile = a.getParentFile();
                                    if (!parentFile.isDirectory() && !parentFile.mkdirs()) {
                                        throw new IOException("Failed to create directory " + parentFile);
                                    }
                                    FileOutputStream fileOutputStream = new FileOutputStream(a);
                                    while (true) {
                                        int read = zipInputStream.read(bArr);
                                        if (read <= 0) {
                                            break;
                                        }
                                        fileOutputStream.write(bArr, 0, read);
                                    }
                                    fileOutputStream.close();
                                } else if (!a.isDirectory() && !a.mkdirs()) {
                                    throw new IOException("Failed to create directory " + a);
                                }
                            }
                        }
                        zipInputStream.closeEntry();
                        zipInputStream.close();
                        zipInputStream.close();
                        i = 0;
                        break loop0;
                    } catch (Throwable th) {
                        th = th;
                        try {
                            th.printStackTrace();
                            if (zipInputStream != null) {
                                zipInputStream.close();
                            }
                            return i;
                        } catch (Throwable th2) {
                            if (zipInputStream != null) {
                                try {
                                    zipInputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            throw th2;
                        }
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                zipInputStream = null;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return i;
        }
    }

    public static File a(File file, ZipEntry zipEntry) {
        File file2;
        String canonicalPath;
        try {
            file2 = new File(file, zipEntry.getName());
        } catch (Throwable th) {
            th = th;
            file2 = null;
        }
        try {
            canonicalPath = file.getCanonicalPath();
        } catch (Throwable th2) {
            th = th2;
            th.printStackTrace();
            return file2;
        }
        if (file2.getCanonicalPath().startsWith(canonicalPath + File.separator)) {
            return file2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Entry is outside of the target dir: ");
        sb.append(zipEntry.getName());
        throw new IOException(sb.toString());
    }
}
