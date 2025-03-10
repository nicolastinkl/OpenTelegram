package com.shubao.xinstall.a.f;

import android.util.Base64;
import com.tencent.cos.xml.crypto.JceEncryptionConstants;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class m {
    public static String a(InputStream inputStream) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    return sb.toString();
                }
                sb.append(readLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String a(InputStream inputStream, String str) {
        String str2;
        String a = a(inputStream);
        if (a != null && !a.startsWith("{")) {
            try {
                String a2 = c.a("qMNyC9JYhVeRpw8c_ehmgNTw7QRw8Kug8YF");
                if (a2.length() > 16) {
                    a2 = a2.substring(0, 16);
                }
                byte[] bytes = a2.getBytes();
                String a3 = c.a(str);
                if (a3.length() > 16) {
                    a3 = a3.substring(0, 16);
                }
                byte[] bytes2 = a3.getBytes();
                SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, JceEncryptionConstants.SYMMETRIC_KEY_ALGORITHM);
                IvParameterSpec ivParameterSpec = new IvParameterSpec(bytes2);
                Cipher cipher = Cipher.getInstance(JceEncryptionConstants.SYMMETRIC_CIPHER_METHOD);
                cipher.init(2, secretKeySpec, ivParameterSpec);
                str2 = new String(cipher.doFinal(Base64.decode(a, 0)));
            } catch (Exception e) {
                e.printStackTrace();
                str2 = null;
            }
            if (str2 != null) {
                a = str2;
            }
        }
        return a == null ? "" : a;
    }

    private static String a(Map<Object, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            sb.append((String) entry.getKey());
            sb.append("=");
            sb.append(entry.getValue() == null ? "" : (String) entry.getValue());
            sb.append("&");
        }
        String sb2 = sb.toString();
        return sb2.length() > 0 ? sb2.substring(0, sb2.length() - 1) : sb2;
    }

    public static String a(Map<Object, Object> map, String str) {
        String a = a(map);
        String a2 = c.a("qMNyC9JYhVeRpw8c_ehmgNTw7QRw8Kug8YF");
        if (a2.length() > 16) {
            a2 = a2.substring(0, 16);
        }
        String a3 = c.a(str);
        if (a3.length() > 16) {
            a3 = a3.substring(0, 16);
        }
        String a4 = b.a(a, a2, a3);
        return "__d=" + URLEncoder.encode(a4, "UTF-8") + "&__t=" + URLEncoder.encode("android", "UTF-8") + "&__tt=" + URLEncoder.encode(str, "UTF-8") + "&__s=" + URLEncoder.encode(c.a(c.b("__d=" + a4 + "&__t=android&__tt=" + str) + "mV6ehmgNTw7QRw8Kug8YF"), "UTF-8");
    }

    public static String a(Map<String, String> map, List<String> list) {
        JSONObject jSONObject = new JSONObject();
        for (String str : list) {
            try {
                jSONObject.put(str, map.get(str));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject.toString().replace("\\/", "/");
    }
}
