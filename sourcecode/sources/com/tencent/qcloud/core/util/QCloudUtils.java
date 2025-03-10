package com.tencent.qcloud.core.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
public class QCloudUtils {
    public static long getUriContentLength(Uri uri, ContentResolver contentResolver) {
        long j;
        String scheme = uri.getScheme();
        if ("content".equals(scheme)) {
            Cursor query = contentResolver.query(uri, null, null, null, null);
            if (query != null) {
                try {
                    j = query.moveToFirst() ? query.getLong(query.getColumnIndex("_size")) : -1L;
                } finally {
                    query.close();
                }
            } else {
                j = -1;
            }
            return j == -1 ? getUriContentLengthByRead(uri, contentResolver) : j;
        }
        if ("file".equals(scheme)) {
            return new File(uri.getPath()).length();
        }
        return -1L;
    }

    public static boolean doesUriFileExist(Uri uri, ContentResolver contentResolver) {
        try {
            contentResolver.openFileDescriptor(uri, "r").close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e2) {
            e2.printStackTrace();
            return true;
        }
    }

    private static long getUriContentLengthByRead(Uri uri, ContentResolver contentResolver) {
        InputStream inputStream = null;
        try {
            try {
                inputStream = contentResolver.openInputStream(uri);
                long j = 0;
                byte[] bArr = new byte[LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read != -1) {
                        j += read;
                    } else {
                        try {
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                inputStream.close();
                return j;
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            return -1L;
        }
    }

    public static byte[] toBytes(Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = null;
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            bArr = byteArrayOutputStream.toByteArray();
            objectOutputStream.close();
            byteArrayOutputStream.close();
            return bArr;
        } catch (IOException e) {
            e.printStackTrace();
            return bArr;
        }
    }

    public static Object toObject(byte[] bArr) {
        Object obj = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            obj = objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
            return obj;
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return obj;
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x0020 -> B:9:0x0023). Please report as a decompilation issue!!! */
    public static void writeToFile(String str, byte[] bArr) {
        FileOutputStream fileOutputStream = null;
        try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(str);
                try {
                    fileOutputStream2.write(bArr);
                    fileOutputStream2.close();
                } catch (IOException e2) {
                    e = e2;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static byte[] readBytesFromFile(String str) {
        byte[] bArr = null;
        try {
            File file = new File(str);
            bArr = new byte[(int) file.length()];
            new FileInputStream(file).read(bArr);
            return bArr;
        } catch (IOException e) {
            e.printStackTrace();
            return bArr;
        }
    }

    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager;
        Context appContext = ContextHolder.getAppContext();
        if (appContext == null || (connectivityManager = (ConnectivityManager) appContext.getSystemService("connectivity")) == null) {
            return true;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
