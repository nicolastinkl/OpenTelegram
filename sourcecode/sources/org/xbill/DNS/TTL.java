package org.xbill.DNS;

/* loaded from: classes4.dex */
public final class TTL {
    static void check(long j) {
        if (j < 0 || j > 2147483647L) {
            throw new InvalidTTLException(j);
        }
    }

    public static String format(long j) {
        check(j);
        StringBuilder sb = new StringBuilder();
        long j2 = j % 60;
        long j3 = j / 60;
        long j4 = j3 % 60;
        long j5 = j3 / 60;
        long j6 = j5 % 24;
        long j7 = j5 / 24;
        long j8 = j7 % 7;
        long j9 = j7 / 7;
        if (j9 > 0) {
            sb.append(j9);
            sb.append("W");
        }
        if (j8 > 0) {
            sb.append(j8);
            sb.append("D");
        }
        if (j6 > 0) {
            sb.append(j6);
            sb.append("H");
        }
        if (j4 > 0) {
            sb.append(j4);
            sb.append("M");
        }
        if (j2 > 0 || (j9 == 0 && j8 == 0 && j6 == 0 && j4 == 0)) {
            sb.append(j2);
            sb.append("S");
        }
        return sb.toString();
    }
}
