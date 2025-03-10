package org.xbill.DNS;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/* loaded from: classes4.dex */
public final class Address {
    private static byte[] parseV4(String str) {
        byte[] bArr = new byte[4];
        int length = str.length();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            char charAt = str.charAt(i4);
            if (charAt < '0' || charAt > '9') {
                if (charAt != '.' || i == 3 || i2 == 0) {
                    return null;
                }
                bArr[i] = (byte) i3;
                i++;
                i2 = 0;
                i3 = 0;
            } else {
                if (i2 == 3) {
                    return null;
                }
                if (i2 > 0 && i3 == 0) {
                    return null;
                }
                i2++;
                i3 = (i3 * 10) + (charAt - '0');
                if (i3 > 255) {
                    return null;
                }
            }
        }
        if (i != 3 || i2 == 0) {
            return null;
        }
        bArr[i] = (byte) i3;
        return bArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x00bb, code lost:
    
        if (r8 >= 16) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00bd, code lost:
    
        if (r3 >= 0) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00bf, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00c0, code lost:
    
        if (r3 < 0) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00c2, code lost:
    
        r12 = (16 - r8) + r3;
        java.lang.System.arraycopy(r1, r3, r1, r12, r8 - r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00c9, code lost:
    
        if (r3 >= r12) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00cb, code lost:
    
        r1[r3] = 0;
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00d0, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static byte[] parseV6(java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 209
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.xbill.DNS.Address.parseV6(java.lang.String):byte[]");
    }

    public static byte[] toByteArray(String str, int i) {
        if (i == 1) {
            return parseV4(str);
        }
        if (i == 2) {
            return parseV6(str);
        }
        throw new IllegalArgumentException("unknown address family");
    }

    public static String toDottedQuad(byte[] bArr) {
        return (bArr[0] & 255) + "." + (bArr[1] & 255) + "." + (bArr[2] & 255) + "." + (bArr[3] & 255);
    }

    public static int familyOf(InetAddress inetAddress) {
        if (inetAddress instanceof Inet4Address) {
            return 1;
        }
        if (inetAddress instanceof Inet6Address) {
            return 2;
        }
        throw new IllegalArgumentException("unknown address family");
    }

    public static int addressLength(int i) {
        if (i == 1) {
            return 4;
        }
        if (i == 2) {
            return 16;
        }
        throw new IllegalArgumentException("unknown address family");
    }

    public static InetAddress truncate(InetAddress inetAddress, int i) {
        int i2;
        int addressLength = addressLength(familyOf(inetAddress)) * 8;
        if (i < 0 || i > addressLength) {
            throw new IllegalArgumentException("invalid mask length");
        }
        if (i == addressLength) {
            return inetAddress;
        }
        byte[] address = inetAddress.getAddress();
        int i3 = i / 8;
        int i4 = i3 + 1;
        while (true) {
            if (i4 >= address.length) {
                break;
            }
            address[i4] = 0;
            i4++;
        }
        int i5 = 0;
        for (i2 = 0; i2 < i % 8; i2++) {
            i5 |= 1 << (7 - i2);
        }
        address[i3] = (byte) (address[i3] & i5);
        try {
            return InetAddress.getByAddress(address);
        } catch (UnknownHostException unused) {
            throw new IllegalArgumentException("invalid address");
        }
    }
}
