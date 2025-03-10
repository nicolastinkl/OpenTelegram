package org.xbill.DNS;

/* loaded from: classes4.dex */
public final class Serial {
    public static int compare(long j, long j2) {
        if (j < 0 || j > 4294967295L) {
            throw new IllegalArgumentException(j + " out of range");
        }
        if (j2 < 0 || j2 > 4294967295L) {
            throw new IllegalArgumentException(j2 + " out of range");
        }
        long j3 = j - j2;
        if (j3 >= 4294967295L) {
            j3 -= 4294967296L;
        } else if (j3 < -4294967295L) {
            j3 += 4294967296L;
        }
        return (int) j3;
    }
}
