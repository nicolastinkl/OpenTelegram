package com.tencent.cos.xml.utils;

import android.text.TextUtils;
import com.tencent.qcloud.core.util.QCloudHttpUtils;
import java.util.Map;

/* loaded from: classes.dex */
public class StringUtils {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String toHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            char[] cArr = HEX_DIGITS;
            sb.append(cArr[(b & 240) >>> 4]);
            sb.append(cArr[b & 15]);
        }
        return sb.toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String flat(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!z) {
                sb.append("&");
            }
            z = false;
            sb.append(key);
            if (!TextUtils.isEmpty(value)) {
                sb.append("=");
                sb.append(QCloudHttpUtils.urlEncodeString(value));
            }
        }
        return sb.toString();
    }

    public static String extractFileName(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(47);
        if (lastIndexOf < 0) {
            return str;
        }
        if (lastIndexOf == str.length() - 1) {
            lastIndexOf = str.substring(0, str.length() - 1).lastIndexOf(47);
        }
        return str.substring(lastIndexOf + 1);
    }

    public static String extractNameNoSuffix(String str) {
        String extractFileName = extractFileName(str);
        if (TextUtils.isEmpty(extractFileName)) {
            return "";
        }
        int lastIndexOf = extractFileName.lastIndexOf(46);
        return lastIndexOf > 0 ? extractFileName.substring(0, lastIndexOf) : extractFileName;
    }

    public static String extractSuffix(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.endsWith("/")) {
            return "/";
        }
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf > 0 ? str.substring(lastIndexOf) : "";
    }
}
