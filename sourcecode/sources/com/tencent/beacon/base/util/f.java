package com.tencent.beacon.base.util;

import android.text.TextUtils;
import java.nio.charset.Charset;

/* compiled from: StringUtil.java */
/* loaded from: classes.dex */
public class f {
    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            return Charset.forName("ISO-8859-1").newEncoder().canEncode(str);
        } catch (Exception e) {
            c.a(e);
            return false;
        }
    }
}
