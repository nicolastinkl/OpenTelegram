package com.shubao.xinstall.a.f;

import android.util.Base64;
import com.tencent.cos.xml.crypto.JceEncryptionConstants;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public final class b {
    public static String a(String str, String str2, String str3) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), JceEncryptionConstants.SYMMETRIC_KEY_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(str3.getBytes());
        Cipher cipher = Cipher.getInstance(JceEncryptionConstants.SYMMETRIC_CIPHER_METHOD);
        cipher.init(1, secretKeySpec, ivParameterSpec);
        return Base64.encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)), 0).replace("\n", "");
    }
}
