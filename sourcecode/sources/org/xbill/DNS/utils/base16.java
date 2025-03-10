package org.xbill.DNS.utils;

import java.io.ByteArrayOutputStream;

/* loaded from: classes4.dex */
public class base16 {
    public static String toString(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (byte b : bArr) {
            short s = (short) (b & 255);
            byteArrayOutputStream.write("0123456789ABCDEF".charAt((byte) (s >> 4)));
            byteArrayOutputStream.write("0123456789ABCDEF".charAt((byte) (s & 15)));
        }
        return new String(byteArrayOutputStream.toByteArray());
    }
}
