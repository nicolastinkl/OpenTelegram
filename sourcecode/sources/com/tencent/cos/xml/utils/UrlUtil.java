package com.tencent.cos.xml.utils;

import android.text.TextUtils;
import android.util.Patterns;
import android.webkit.URLUtil;
import com.tencent.cos.xml.common.Range;
import com.tencent.cos.xml.common.RequestMethod;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.cos.xml.model.tag.UrlUploadPolicy;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.HttpRequest;
import com.tencent.qcloud.core.http.HttpResult;
import com.tencent.qcloud.core.http.QCloudHttpClient;
import com.tencent.qcloud.core.util.QCloudHttpUtils;
import java.net.MalformedURLException;
import java.net.URL;

/* loaded from: classes.dex */
public class UrlUtil {
    public static boolean isUrl(String str) {
        if (!TextUtils.isEmpty(str) && Patterns.WEB_URL.matcher(str).matches()) {
            return URLUtil.isHttpsUrl(str) || URLUtil.isHttpUrl(str);
        }
        return false;
    }

    public static UrlUploadPolicy getUrlUploadPolicy(String str) {
        if (!isUrl(str)) {
            return new UrlUploadPolicy(UrlUploadPolicy.Type.NOTSUPPORT, 0L);
        }
        try {
            URL url = new URL(str);
            try {
                HttpResult executeNow = QCloudHttpClient.getDefault().resolveRequest(new HttpRequest.Builder().url(url).method(RequestMethod.GET).addHeader("Range", new Range(0L, 1L).getRange()).build()).executeNow();
                if (executeNow != null && executeNow.isSuccessful() && executeNow.headers() != null && executeNow.headers().size() > 0) {
                    String header = executeNow.header("Accept-Ranges");
                    String header2 = executeNow.header(Headers.CONTENT_RANGE);
                    if ("bytes".equals(header) && !TextUtils.isEmpty(header2)) {
                        long[] parseContentRange = QCloudHttpUtils.parseContentRange(header2);
                        if (parseContentRange != null) {
                            return new UrlUploadPolicy(UrlUploadPolicy.Type.RANGE, parseContentRange[2]);
                        }
                    } else {
                        String header3 = executeNow.header(Headers.CONTENT_LENGTH);
                        if (!TextUtils.isEmpty(header3)) {
                            return new UrlUploadPolicy(UrlUploadPolicy.Type.ENTIRETY, Long.parseLong(header3));
                        }
                    }
                }
                return new UrlUploadPolicy(UrlUploadPolicy.Type.NOTSUPPORT, 0L);
            } catch (QCloudClientException | QCloudServiceException unused) {
                return new UrlUploadPolicy(UrlUploadPolicy.Type.NOTSUPPORT, 0L);
            }
        } catch (MalformedURLException unused2) {
            return new UrlUploadPolicy(UrlUploadPolicy.Type.NOTSUPPORT, 0L);
        }
    }
}
