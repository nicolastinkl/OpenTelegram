package com.tencent.qmsp.sdk.b;

import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.qmsp.sdk.f.h;
import com.youth.banner.config.BannerConfig;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes.dex */
public class d {
    private static byte[][] a = {new byte[]{16, 99, -74, 82, 99, 26, 112, -8, 43, 100}, new byte[]{8, Byte.MAX_VALUE, -87, 73, 34, 55, 118, -78, 113, 62, -29, 0, 102, 56, 120, -16, 53, 113, -89, 73, 44, 55, 114, -90, 101, 93, Byte.MIN_VALUE, 105, AbstractJceStruct.STRUCT_END, 123, 34, -77, 117, 43, -13, 119, 39, 53, 115, -14, 50, 99, -13, 110, 26, 96, 55, -39, 44, 119, -106, 88, 58, 114}, new byte[]{20, 96, -3, 68, 33, 44, 121}};

    public static String a(int i) {
        return h.a(a[i]);
    }

    public static void a(b bVar) {
        HttpURLConnection httpURLConnection;
        String str = bVar.a;
        String str2 = bVar.d;
        String str3 = bVar.c;
        c cVar = new c(bVar);
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        } catch (Exception unused) {
            cVar.a = 1;
            com.tencent.qmsp.sdk.f.g.c(a(2), 0, "dwl:" + str + "  fail");
        }
        if (httpURLConnection instanceof HttpURLConnection) {
            httpURLConnection.setConnectTimeout(BannerConfig.LOOP_TIME);
            httpURLConnection.setRequestProperty(a(0), a(1));
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] a2 = a(inputStream);
            File file = new File(str3);
            if (!file.exists()) {
                file.mkdir();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(new File(file + File.separator + str2));
            fileOutputStream.write(a2);
            fileOutputStream.close();
            if (inputStream != null) {
                inputStream.close();
            }
            cVar.a = 0;
            String a3 = a(2);
            StringBuilder sb = new StringBuilder();
            sb.append("dwl:");
            sb.append(str);
            sb.append("  ok");
            com.tencent.qmsp.sdk.f.g.c(a3, 0, sb.toString());
            com.tencent.qmsp.sdk.c.f.i().d().a(cVar);
        }
    }

    public static byte[] a(InputStream inputStream) {
        byte[] bArr = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                byteArrayOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }
}
