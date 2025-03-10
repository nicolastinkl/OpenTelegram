package io.openinstall.sdk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/* loaded from: classes.dex */
public class dn {
    public static void a(File file) {
        try {
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                parentFile.mkdirs();
            }
            file.createNewFile();
        } catch (Exception unused) {
        }
    }

    public static void a(File file, String str, boolean z) {
        a(file, str, false, z);
    }

    public static void a(File file, String str, boolean z, boolean z2) {
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        a(file);
        BufferedWriter bufferedWriter2 = null;
        try {
            try {
                fileWriter = new FileWriter(file, z2);
                try {
                    bufferedWriter = new BufferedWriter(fileWriter);
                } catch (IOException unused) {
                } catch (Throwable th) {
                    th = th;
                }
            } catch (IOException unused2) {
                fileWriter = null;
            } catch (Throwable th2) {
                th = th2;
                fileWriter = null;
            }
            try {
                bufferedWriter.write(str);
                if (z) {
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException unused3) {
                bufferedWriter2 = bufferedWriter;
                if (bufferedWriter2 != null) {
                    bufferedWriter2.close();
                }
                if (fileWriter == null) {
                    return;
                }
                fileWriter.close();
            } catch (Throwable th3) {
                th = th3;
                bufferedWriter2 = bufferedWriter;
                if (bufferedWriter2 != null) {
                    try {
                        bufferedWriter2.close();
                    } catch (IOException unused4) {
                        throw th;
                    }
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
                throw th;
            }
            fileWriter.close();
        } catch (IOException unused5) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x003d, code lost:
    
        if (r3 != null) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String b(java.io.File r3) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L37
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L2a java.io.IOException -> L37
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L27
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L27
        L10:
            java.lang.String r1 = r3.readLine()     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L28
            if (r1 == 0) goto L1a
            r0.append(r1)     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L28
            goto L10
        L1a:
            r2.close()     // Catch: java.io.IOException -> L40
        L1d:
            r3.close()     // Catch: java.io.IOException -> L40
            goto L40
        L21:
            r0 = move-exception
            goto L25
        L23:
            r0 = move-exception
            r3 = r1
        L25:
            r1 = r2
            goto L2c
        L27:
            r3 = r1
        L28:
            r1 = r2
            goto L38
        L2a:
            r0 = move-exception
            r3 = r1
        L2c:
            if (r1 == 0) goto L31
            r1.close()     // Catch: java.io.IOException -> L36
        L31:
            if (r3 == 0) goto L36
            r3.close()     // Catch: java.io.IOException -> L36
        L36:
            throw r0
        L37:
            r3 = r1
        L38:
            if (r1 == 0) goto L3d
            r1.close()     // Catch: java.io.IOException -> L40
        L3d:
            if (r3 == 0) goto L40
            goto L1d
        L40:
            java.lang.String r3 = r0.toString()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.openinstall.sdk.dn.b(java.io.File):java.lang.String");
    }
}
