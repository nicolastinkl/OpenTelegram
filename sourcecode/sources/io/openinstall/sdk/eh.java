package io.openinstall.sdk;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/* loaded from: classes.dex */
public class eh {
    private String a(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, ds.c));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                String sb2 = sb.toString();
                bufferedReader.close();
                return sb2;
            }
            sb.append(readLine);
            sb.append("\n");
        }
    }

    private void a(HttpURLConnection httpURLConnection, byte[] bArr) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
        bufferedOutputStream.write(bArr);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    public String a(el elVar, int i) throws Exception {
        String b = elVar.b();
        if (elVar.c() != null) {
            b = b + "?" + elVar.c();
        }
        if (i < 1000) {
            i = 1000;
        }
        if (i > 10000) {
            i = 10000;
        }
        boolean z = elVar.a() == ek.POST && elVar.d() != null;
        HttpURLConnection httpURLConnection = null;
        try {
            try {
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(b).openConnection();
                try {
                    httpURLConnection2.setRequestMethod(elVar.a().name());
                    httpURLConnection2.setDoInput(true);
                    httpURLConnection2.setUseCaches(false);
                    httpURLConnection2.setConnectTimeout(i);
                    httpURLConnection2.setReadTimeout(i);
                    if (z) {
                        httpURLConnection2.setDoOutput(true);
                        httpURLConnection2.setFixedLengthStreamingMode(elVar.d().length);
                    }
                    if (elVar.e() != null) {
                        for (Map.Entry<String, String> entry : elVar.e().entrySet()) {
                            httpURLConnection2.setRequestProperty(entry.getKey(), entry.getValue());
                        }
                    }
                    httpURLConnection2.setRequestProperty("Connection", "close");
                    System.currentTimeMillis();
                    httpURLConnection2.connect();
                    if (z) {
                        a(httpURLConnection2, elVar.d());
                    }
                    if (httpURLConnection2.getResponseCode() != 200) {
                        throw new ei(httpURLConnection2.getResponseCode(), httpURLConnection2.getResponseMessage());
                    }
                    String a = a(httpURLConnection2.getInputStream());
                    httpURLConnection2.disconnect();
                    return a;
                } catch (Exception e) {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    httpURLConnection = httpURLConnection2;
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
