package io.openinstall.sdk;

import io.openinstall.sdk.dr;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class fv {
    public static String a(String str) {
        try {
            return new String(dr.c().a(str), ds.c);
        } catch (Exception unused) {
            return str;
        }
    }

    public static String b(String str) {
        try {
            return new String(dr.d().a(str), ds.c);
        } catch (Exception unused) {
            return str;
        }
    }

    public static String c(String str) {
        try {
            dr.b a = dr.a();
            Charset charset = ds.c;
            return new String(a.a(str.getBytes(charset)), charset);
        } catch (Exception unused) {
            return str;
        }
    }
}
