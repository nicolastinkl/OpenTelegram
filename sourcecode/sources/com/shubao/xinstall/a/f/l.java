package com.shubao.xinstall.a.f;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/* loaded from: classes.dex */
public final class l {
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0051, code lost:
    
        if (r5 != null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.json.JSONArray a(java.io.File r5) {
        /*
            org.json.JSONArray r0 = new org.json.JSONArray
            r0.<init>()
            boolean r1 = r5.exists()
            if (r1 != 0) goto Lc
            return r0
        Lc:
            r1 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L4b
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L4b
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L37 java.io.IOException -> L3b
            r5.<init>(r2)     // Catch: java.lang.Throwable -> L37 java.io.IOException -> L3b
        L17:
            java.lang.String r3 = r5.readLine()     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L3c
            if (r3 == 0) goto L2e
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch: org.json.JSONException -> L23 java.lang.Throwable -> L35 java.io.IOException -> L3c
            r4.<init>(r3)     // Catch: org.json.JSONException -> L23 java.lang.Throwable -> L35 java.io.IOException -> L3c
            goto L28
        L23:
            r3 = move-exception
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L3c
            r4 = r1
        L28:
            if (r4 == 0) goto L17
            r0.put(r4)     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L3c
            goto L17
        L2e:
            r2.close()     // Catch: java.io.IOException -> L54
        L31:
            r5.close()     // Catch: java.io.IOException -> L54
            goto L54
        L35:
            r0 = move-exception
            goto L39
        L37:
            r0 = move-exception
            r5 = r1
        L39:
            r1 = r2
            goto L40
        L3b:
            r5 = r1
        L3c:
            r1 = r2
            goto L4c
        L3e:
            r0 = move-exception
            r5 = r1
        L40:
            if (r1 == 0) goto L45
            r1.close()     // Catch: java.io.IOException -> L4a
        L45:
            if (r5 == 0) goto L4a
            r5.close()     // Catch: java.io.IOException -> L4a
        L4a:
            throw r0
        L4b:
            r5 = r1
        L4c:
            if (r1 == 0) goto L51
            r1.close()     // Catch: java.io.IOException -> L54
        L51:
            if (r5 == 0) goto L54
            goto L31
        L54:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shubao.xinstall.a.f.l.a(java.io.File):org.json.JSONArray");
    }

    public static boolean a(File file, com.shubao.xinstall.a.b.c cVar) {
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        BufferedWriter bufferedWriter2 = null;
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            if (file.length() >= 1048576) {
                file.delete();
                file.createNewFile();
            }
            fileWriter = new FileWriter(file, true);
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
            bufferedWriter.write(cVar.a().toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException unused3) {
            }
            return true;
        } catch (IOException unused4) {
            bufferedWriter2 = bufferedWriter;
            if (bufferedWriter2 != null) {
                try {
                    bufferedWriter2.close();
                } catch (IOException unused5) {
                    return false;
                }
            }
            if (fileWriter == null) {
                return false;
            }
            fileWriter.close();
            return false;
        } catch (Throwable th3) {
            bufferedWriter2 = bufferedWriter;
            th = th3;
            if (bufferedWriter2 != null) {
                try {
                    bufferedWriter2.close();
                } catch (IOException unused6) {
                    throw th;
                }
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
            throw th;
        }
    }
}
