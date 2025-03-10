package com.tencent.qmsp.sdk.c;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class m {
    private static final byte[] a = {96, 110, -109, 82, 109, 2, 36, -77, 108, 59, -80, 10, 112, 122, 56, -67};

    public String a(byte[] bArr) {
        return com.tencent.qmsp.sdk.f.h.a(bArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(java.lang.String r4, byte[] r5, java.lang.String r6, int r7) {
        /*
            r3 = this;
            r0 = 0
            r1 = 0
            if (r6 != 0) goto La
            byte[] r6 = com.tencent.qmsp.sdk.c.m.a     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            java.lang.String r6 = r3.a(r6)     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
        La:
            com.tencent.qmsp.sdk.f.b r2 = new com.tencent.qmsp.sdk.f.b     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            r2.<init>()     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            byte[] r6 = r6.getBytes()     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            byte[] r5 = r2.a(r5, r6)     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            if (r5 != 0) goto L1a
            return r0
        L1a:
            java.io.DataOutputStream r6 = new java.io.DataOutputStream     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            r2.<init>(r4)     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            r6.<init>(r2)     // Catch: java.lang.Throwable -> L43 java.io.IOException -> L45
            r4 = 305419896(0x12345678, float:5.6904566E-28)
            r6.writeInt(r4)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L40
            r6.writeInt(r7)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L40
            r4 = 1
            r6.writeInt(r4)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L40
            r6.write(r5)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L40
            r6.close()     // Catch: java.io.IOException -> L38
            goto L3c
        L38:
            r5 = move-exception
            r5.printStackTrace()
        L3c:
            return r4
        L3d:
            r4 = move-exception
            r1 = r6
            goto L54
        L40:
            r4 = move-exception
            r1 = r6
            goto L46
        L43:
            r4 = move-exception
            goto L54
        L45:
            r4 = move-exception
        L46:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L43
            if (r1 == 0) goto L53
            r1.close()     // Catch: java.io.IOException -> L4f
            goto L53
        L4f:
            r4 = move-exception
            r4.printStackTrace()
        L53:
            return r0
        L54:
            if (r1 == 0) goto L5e
            r1.close()     // Catch: java.io.IOException -> L5a
            goto L5e
        L5a:
            r5 = move-exception
            r5.printStackTrace()
        L5e:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.c.m.a(java.lang.String, byte[], java.lang.String, int):boolean");
    }

    /* JADX WARN: Type inference failed for: r6v1, types: [boolean] */
    public byte[] a(String str, String str2, int i) {
        Throwable th;
        IOException e;
        DataInputStream dataInputStream;
        File file = new File(str);
        ?? exists = file.exists();
        DataInputStream dataInputStream2 = null;
        try {
            if (exists == 0) {
                return null;
            }
            try {
                dataInputStream = new DataInputStream(new FileInputStream(file));
            } catch (IOException e2) {
                e = e2;
                dataInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                if (dataInputStream2 != null) {
                    try {
                        dataInputStream2.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                throw th;
            }
            try {
                int available = dataInputStream.available();
                if (available > 12 && available <= 102400) {
                    if (dataInputStream.readInt() != 305419896) {
                        try {
                            dataInputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                        return null;
                    }
                    int readInt = dataInputStream.readInt();
                    if (readInt > 0 && readInt <= i) {
                        if (dataInputStream.readInt() != 1) {
                            try {
                                dataInputStream.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                            return null;
                        }
                        int i2 = available - 12;
                        byte[] bArr = new byte[i2];
                        if (i2 != dataInputStream.read(bArr)) {
                            try {
                                dataInputStream.close();
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                            return null;
                        }
                        if (str2 == null) {
                            str2 = a(a);
                        }
                        byte[] a2 = new com.tencent.qmsp.sdk.f.b().a(bArr, 0, i2, str2.getBytes());
                        if (a2 == null) {
                            try {
                                dataInputStream.close();
                            } catch (IOException e7) {
                                e7.printStackTrace();
                            }
                            return null;
                        }
                        try {
                            dataInputStream.close();
                        } catch (IOException e8) {
                            e8.printStackTrace();
                        }
                        return a2;
                    }
                    try {
                        dataInputStream.close();
                    } catch (IOException e9) {
                        e9.printStackTrace();
                    }
                    return null;
                }
                try {
                    dataInputStream.close();
                } catch (IOException e10) {
                    e10.printStackTrace();
                }
                return null;
            } catch (IOException e11) {
                e = e11;
                e.printStackTrace();
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e12) {
                        e12.printStackTrace();
                    }
                }
                return null;
            }
        } catch (Throwable th3) {
            dataInputStream2 = exists;
            th = th3;
        }
    }
}
