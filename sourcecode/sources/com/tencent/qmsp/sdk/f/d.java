package com.tencent.qmsp.sdk.f;

import java.io.File;

/* loaded from: classes.dex */
public class d {
    public static void a(String str, boolean z) {
        if (str == null) {
            return;
        }
        File file = new File(str);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                return;
            }
            for (File file2 : listFiles) {
                a(file2.getAbsolutePath(), z);
            }
            if (z) {
                return;
            }
            file.delete();
        }
    }
}
