package io.openinstall.sdk;

/* loaded from: classes.dex */
public class dd {
    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            short s = (short) (b & 255);
            sb.append("0123456789ABCDEF".charAt((byte) (s >> 4)));
            sb.append("0123456789ABCDEF".charAt((byte) (s & 15)));
        }
        return sb.toString();
    }
}
