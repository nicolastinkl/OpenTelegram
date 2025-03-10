package io.openinstall.sdk;

import android.util.Pair;
import com.tencent.beacon.pack.AbstractJceStruct;

/* loaded from: classes.dex */
public class fm {
    private static final byte[] a = {-96, 45, -21, -15, -5, 123, 124, -63, -29, -45, -85, -1, -119, 62, 73, 31, 37, -22, 122, -4, 57, 79, 56, 77, 57, -119, -75, 30, 40, 104, 52, AbstractJceStruct.STRUCT_END};

    static String a(String str) {
        if (str == null) {
            return "";
        }
        try {
            byte[] a2 = dr.d().a(str.replaceAll("\"|\n", ""));
            for (int i = 0; i < a2.length; i++) {
                byte b = a2[i];
                byte[] bArr = a;
                a2[i] = (byte) (b ^ bArr[i % bArr.length]);
            }
            return new String(a2);
        } catch (Exception unused) {
            return "";
        }
    }

    static Pair<String, String> b(String str) {
        String a2 = a(str);
        String str2 = null;
        if (a2 == null || a2.length() == 0) {
            return Pair.create(null, null);
        }
        int length = a2.length();
        String str3 = null;
        int i = 0;
        while (i < length) {
            while (i < length && (a2.charAt(i) == ' ' || a2.charAt(i) == '\"' || a2.charAt(i) == '\n')) {
                i++;
            }
            if (i >= length) {
                break;
            }
            int i2 = i;
            while (i2 < length && a2.charAt(i2) != '=') {
                i2++;
            }
            String trim = a2.substring(i, i2).trim();
            if (i2 >= length) {
                break;
            }
            int i3 = i2 + 1;
            while (i2 < length && a2.charAt(i2) != ' ' && a2.charAt(i2) != '\"' && a2.charAt(i2) != '\n') {
                i2++;
            }
            String trim2 = a2.substring(i3, i2).trim();
            if ("api".equalsIgnoreCase(trim)) {
                str2 = trim2;
            } else if ("stat".equalsIgnoreCase(trim)) {
                str3 = trim2;
            }
            i = i2;
        }
        return Pair.create(str2, str3);
    }
}
