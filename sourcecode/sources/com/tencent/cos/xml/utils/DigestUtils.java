package com.tencent.cos.xml.utils;

import android.text.TextUtils;
import android.util.Base64;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.telegram.messenger.CharacterCompat;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
public class DigestUtils {
    public static String getMD5(String str) throws CosXmlClientException {
        Throwable th;
        NoSuchAlgorithmException e;
        IOException e2;
        FileNotFoundException e3;
        MessageDigest messageDigest;
        FileInputStream fileInputStream;
        if (str == null) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "file Path is null");
        }
        File file = new File(str);
        if (!file.exists()) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "file Path is not exist");
        }
        try {
            try {
                messageDigest = MessageDigest.getInstance("MD5");
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e4) {
                e3 = e4;
            } catch (IOException e5) {
                e2 = e5;
            } catch (NoSuchAlgorithmException e6) {
                e = e6;
            } catch (Throwable th2) {
                th = th2;
                CloseUtil.closeQuietly(null);
                throw th;
            }
            try {
                byte[] bArr = new byte[LiteMode.FLAG_CHAT_SCALE];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read != -1) {
                        messageDigest.update(bArr, 0, read);
                    } else {
                        String hexString = StringUtils.toHexString(messageDigest.digest());
                        CloseUtil.closeQuietly(fileInputStream);
                        return hexString;
                    }
                }
            } catch (FileNotFoundException e7) {
                e3 = e7;
                throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), e3);
            } catch (IOException e8) {
                e2 = e8;
                throw new CosXmlClientException(ClientErrorCode.IO_ERROR.getCode(), e2);
            } catch (NoSuchAlgorithmException e9) {
                e = e9;
                throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e);
            }
        } catch (Throwable th3) {
            th = th3;
            CloseUtil.closeQuietly(null);
            throw th;
        }
    }

    public static long getBigIntFromString(String str) {
        return new BigInteger(str).longValue();
    }

    public static String getBigIntToString(long j) {
        BigInteger valueOf = BigInteger.valueOf((j >> 1) & 4611686018427387904L);
        return valueOf.add(valueOf).add(BigInteger.valueOf(j & Long.MAX_VALUE)).toString();
    }

    public static String getCRC64String(InputStream inputStream) {
        return getBigIntToString(getCRC64(inputStream));
    }

    public static String getCRC64String(File file) {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (Exception unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            String cRC64String = getCRC64String(fileInputStream);
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return cRC64String;
        } catch (Exception unused2) {
            fileInputStream2 = fileInputStream;
            try {
                fileInputStream2.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return "";
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            try {
                fileInputStream2.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            throw th;
        }
    }

    public static long getCRC64(InputStream inputStream) {
        try {
            CRC64 crc64 = new CRC64();
            byte[] bArr = new byte[LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    crc64.update(bArr, read);
                } else {
                    return crc64.getValue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public static long getCRC64(InputStream inputStream, long j, long j2) {
        int read;
        try {
            if (inputStream.skip(j) != j) {
                return -1L;
            }
            CRC64 crc64 = new CRC64();
            byte[] bArr = new byte[LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM];
            if (j2 < 0) {
                j2 = Long.MAX_VALUE;
            }
            int min = (int) Math.min(j2, LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM);
            while (j2 > 0 && (read = inputStream.read(bArr, 0, min)) != -1) {
                crc64.update(bArr, read);
                j2 -= read;
            }
            return crc64.getValue();
        } catch (IOException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public static String getCOSMd5(InputStream inputStream, long j, long j2) throws IOException {
        int read;
        try {
            if (inputStream.skip(j) != j) {
                return "";
            }
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bArr = new byte[LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM];
            if (j2 < 0) {
                j2 = Long.MAX_VALUE;
            }
            int min = (int) Math.min(j2, LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM);
            while (j2 > 0 && (read = inputStream.read(bArr, 0, min)) != -1) {
                if (read < min) {
                    return "";
                }
                messageDigest.update(bArr, 0, read);
                j2 -= read;
            }
            return "\"" + StringUtils.toHexString(messageDigest.digest()) + "\"";
        } catch (IOException e) {
            throw e;
        } catch (NoSuchAlgorithmException e2) {
            throw new IOException("unSupport Md5 algorithm", e2);
        }
    }

    public static COSMd5AndReadData getCOSMd5AndReadData(InputStream inputStream, int i) throws IOException {
        try {
            byte[] bArr = new byte[i];
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            int read = inputStream.read(bArr, 0, i);
            if (read != -1) {
                if (read < i) {
                    return new COSMd5AndReadData("", subByte(bArr, 0, read));
                }
                messageDigest.update(bArr, 0, read);
            }
            return new COSMd5AndReadData("\"" + StringUtils.toHexString(messageDigest.digest()) + "\"", subByte(bArr, 0, read));
        } catch (IOException e) {
            throw e;
        } catch (NoSuchAlgorithmException e2) {
            throw new IOException("unSupport Md5 algorithm", e2);
        }
    }

    private static byte[] subByte(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    public static class COSMd5AndReadData {
        public String md5;
        public byte[] readData;

        public COSMd5AndReadData(String str, byte[] bArr) {
            this.md5 = str;
            this.readData = bArr;
        }
    }

    public static String getSha1(String str) throws CosXmlClientException {
        try {
            return StringUtils.toHexString(MessageDigest.getInstance("SHA-1").digest(str.getBytes(Charset.forName("UTF-8"))));
        } catch (NoSuchAlgorithmException e) {
            throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e);
        }
    }

    public static String getSHA1FromPath(String str) throws CosXmlClientException {
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(str);
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                    byte[] bArr = new byte[CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT];
                    while (true) {
                        int read = fileInputStream2.read(bArr, 0, CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT);
                        if (read != -1) {
                            messageDigest.update(bArr, 0, read);
                        } else {
                            String hexString = StringUtils.toHexString(messageDigest.digest());
                            CloseUtil.closeQuietly(fileInputStream2);
                            return hexString;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e = e;
                    throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), e);
                } catch (IOException e2) {
                    e = e2;
                    throw new CosXmlClientException(ClientErrorCode.IO_ERROR.getCode(), e);
                } catch (NoSuchAlgorithmException e3) {
                    e = e3;
                    throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e);
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    CloseUtil.closeQuietly(fileInputStream);
                    throw th;
                }
            } catch (FileNotFoundException e4) {
                e = e4;
            } catch (IOException e5) {
                e = e5;
            } catch (NoSuchAlgorithmException e6) {
                e = e6;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String getSHA1FromBytes(byte[] bArr, int i, int i2) throws CosXmlClientException {
        if (bArr == null || i2 <= 0 || i < 0) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "data == null | len <= 0 |offset < 0 |offset >= len");
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(bArr, i, i2);
            return StringUtils.toHexString(messageDigest.digest());
        } catch (OutOfMemoryError e) {
            throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e);
        } catch (NoSuchAlgorithmException e2) {
            throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e2);
        }
    }

    public static String getHmacSha1(String str, String str2) throws CosXmlClientException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(Charset.forName("UTF-8")), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            return StringUtils.toHexString(mac.doFinal(str.getBytes(Charset.forName("UTF-8"))));
        } catch (InvalidKeyException e) {
            throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e);
        } catch (NoSuchAlgorithmException e2) {
            throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e2);
        }
    }

    public static String getBase64(String str) throws CosXmlClientException {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            return Base64.encodeToString(str.getBytes("utf-8"), 2);
        } catch (UnsupportedEncodingException e) {
            throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e);
        }
    }

    public static String getSecurityBase64(String str) throws CosXmlClientException {
        String base64 = getBase64(str);
        return TextUtils.isEmpty(base64) ? base64 : base64.replace("+", "-").replace("/", "_");
    }
}
