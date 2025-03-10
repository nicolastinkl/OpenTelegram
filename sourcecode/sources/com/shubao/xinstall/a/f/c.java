package com.shubao.xinstall.a.f;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes.dex */
public final class c {
    public static String a = "AIYUAPP";

    public static String a(String str) {
        byte[] bArr;
        StringBuilder sb;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes());
            bArr = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            bArr = null;
        }
        String str2 = "";
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                sb = new StringBuilder();
                sb.append(str2);
                str2 = "0";
            } else {
                sb = new StringBuilder();
            }
            sb.append(str2);
            sb.append(hexString);
            str2 = sb.toString();
        }
        return str2;
    }

    public static String b(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.replace("\\/", "/").getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("Huh, MD5 should be supported?", e2);
        }
    }
}
