package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import com.google.android.gms.common.internal.Preconditions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-basement@@18.1.0 */
/* loaded from: classes.dex */
public class ProcessUtils {
    private static String zza;
    private static int zzb;

    public static String getMyProcessName() {
        BufferedReader bufferedReader;
        String str;
        StrictMode.ThreadPolicy allowThreadDiskReads;
        if (zza == null) {
            int i = zzb;
            if (i == 0) {
                i = Process.myPid();
                zzb = i;
            }
            String str2 = null;
            str2 = null;
            str2 = null;
            BufferedReader bufferedReader2 = null;
            if (i > 0) {
                try {
                    str = "/proc/" + i + "/cmdline";
                    allowThreadDiskReads = StrictMode.allowThreadDiskReads();
                } catch (IOException unused) {
                    bufferedReader = null;
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    bufferedReader = new BufferedReader(new FileReader(str));
                    try {
                        String readLine = bufferedReader.readLine();
                        Preconditions.checkNotNull(readLine);
                        str2 = readLine.trim();
                    } catch (IOException unused2) {
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedReader2 = bufferedReader;
                        IOUtils.closeQuietly(bufferedReader2);
                        throw th;
                    }
                    IOUtils.closeQuietly(bufferedReader);
                } finally {
                    StrictMode.setThreadPolicy(allowThreadDiskReads);
                }
            }
            zza = str2;
        }
        return zza;
    }
}
