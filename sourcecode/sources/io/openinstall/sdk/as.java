package io.openinstall.sdk;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/* loaded from: classes.dex */
public class as extends ao {
    public as(Context context) {
        super(context);
    }

    private String a(File file) throws IOException {
        RandomAccessFile randomAccessFile;
        RandomAccessFile randomAccessFile2 = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr = new byte[(int) randomAccessFile.length()];
            randomAccessFile.readFully(bArr);
            String str = new String(bArr);
            try {
                randomAccessFile.close();
            } catch (IOException unused) {
            }
            return str;
        } catch (Throwable th2) {
            th = th2;
            randomAccessFile2 = randomAccessFile;
            if (randomAccessFile2 != null) {
                try {
                    randomAccessFile2.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    private void a(File file, String str) throws IOException {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (Throwable th) {
            th = th;
        }
        try {
            fileOutputStream.write(str.getBytes());
            try {
                fileOutputStream.close();
            } catch (IOException unused) {
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    @Override // io.openinstall.sdk.ao
    public String b(String str) {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return null;
        }
        try {
            return a(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Installation", str));
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // io.openinstall.sdk.ao
    public boolean b(String str, String str2) {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return false;
        }
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Installation", str);
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                parentFile.mkdirs();
            }
            a(file, str2);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }
}
