package com.tencent.qcloud.core.util;

import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class QCloudStringUtils {
    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static byte[] getBytesUTF8(String str) {
        return str.getBytes(Charset.forName("UTF-8"));
    }

    public static String getExtension(String str) {
        int lastIndexOf;
        if (str != null && (lastIndexOf = str.lastIndexOf(".")) >= 0) {
            return str.substring(lastIndexOf + 1);
        }
        return null;
    }
}
