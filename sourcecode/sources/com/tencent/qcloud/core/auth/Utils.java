package com.tencent.qcloud.core.auth;

import com.tencent.qcloud.core.util.QCloudStringUtils;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public class Utils {
    private static final char[] DIGITS_LOWER;
    private static final char[] DIGITS_UPPER;
    private static Calendar calendar;

    static {
        Calendar calendar2 = Calendar.getInstance();
        calendar = calendar2;
        calendar2.set(2010, 0, 1);
        DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    static long handleTimeAccuracy(long j) {
        return j > calendar.getTime().getTime() ? j / 1000 : j;
    }

    public static char[] encodeHex(byte[] bArr) {
        return encodeHex(bArr, true);
    }

    public static char[] encodeHex(byte[] bArr, boolean z) {
        return encodeHex(bArr, z ? DIGITS_LOWER : DIGITS_UPPER);
    }

    public static String encodeHexString(byte[] bArr) {
        return new String(encodeHex(bArr));
    }

    public static byte[] sha1(String str) {
        try {
            return MessageDigest.getInstance("SHA-1").digest(QCloudStringUtils.getBytesUTF8(str));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static byte[] hmacSha1(String str, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(QCloudStringUtils.getBytesUTF8(str2), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            return mac.doFinal(QCloudStringUtils.getBytesUTF8(str));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    static char[] encodeHex(byte[] bArr, char[] cArr) {
        int length = bArr.length;
        char[] cArr2 = new char[length << 1];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr2[i] = cArr[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr2[i3] = cArr[bArr[i2] & 15];
        }
        return cArr2;
    }

    static long[] parseKeyTimes(String str) {
        String[] split = str.split(";");
        return new long[]{Long.parseLong(split[0]), Long.parseLong(split[1])};
    }
}
