package com.tencent.qcloud.core.http;

import com.tencent.cos.xml.crypto.Headers;
import com.tencent.qcloud.core.auth.QCloudSelfSigner;
import com.tencent.qcloud.core.auth.QCloudSigner;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.util.QCloudStringUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/* loaded from: classes.dex */
public class HttpRequest<T> {
    protected final boolean calculateContentMD5;
    protected final Map<String, List<String>> headers;
    protected final String keyTime;
    protected final String method;
    protected final Set<String> noSignHeaders;
    protected final RequestBody requestBody;
    protected final Request.Builder requestBuilder;
    protected final ResponseBodyConverter<T> responseBodyConverter;
    protected final Object tag;
    protected final URL url;

    QCloudSelfSigner getQCloudSelfSigner() throws QCloudClientException {
        return null;
    }

    QCloudSigner getQCloudSigner() throws QCloudClientException {
        return null;
    }

    HttpRequest(Builder<T> builder) {
        Request.Builder builder2 = builder.requestBuilder;
        this.requestBuilder = builder2;
        this.responseBodyConverter = builder.responseBodyConverter;
        this.headers = builder.headers;
        this.noSignHeaders = builder.noSignHeaderKeys;
        this.keyTime = builder.keyTime;
        this.method = builder.method;
        this.calculateContentMD5 = builder.calculateContentMD5;
        Object obj = builder.tag;
        if (obj == null) {
            this.tag = toString();
        } else {
            this.tag = obj;
        }
        this.url = builder.httpUrlBuilder.build().url();
        RequestBodySerializer requestBodySerializer = builder.requestBodySerializer;
        if (requestBodySerializer != null) {
            this.requestBody = requestBodySerializer.body();
        } else {
            this.requestBody = null;
        }
        builder2.method(builder.method, this.requestBody);
    }

    public Map<String, List<String>> headers() {
        return this.headers;
    }

    public Set<String> getNoSignHeaders() {
        return this.noSignHeaders;
    }

    public String getKeyTime() {
        return this.keyTime;
    }

    public String header(String str) {
        List<String> list = this.headers.get(str);
        if (list != null) {
            return list.get(0);
        }
        return null;
    }

    public Object tag() {
        return this.tag;
    }

    public void setOkHttpRequestTag(String str) {
        this.requestBuilder.tag(str);
    }

    public void addHeader(String str, String str2) {
        List<String> list = this.headers.get(str);
        if (list == null || list.size() < 1) {
            this.requestBuilder.addHeader(str, str2);
            addHeaderNameValue(this.headers, str, str2);
        }
    }

    public void setUrl(String str) {
        this.requestBuilder.url(str);
    }

    public void removeHeader(String str) {
        this.requestBuilder.removeHeader(str);
        this.headers.remove(str);
    }

    public boolean shouldCalculateContentMD5() {
        return this.calculateContentMD5 && QCloudStringUtils.isEmpty(header(Headers.CONTENT_MD5));
    }

    public String method() {
        return this.method;
    }

    public String host() {
        return this.url.getHost();
    }

    public String contentType() {
        MediaType contentType;
        RequestBody requestBody = this.requestBody;
        if (requestBody == null || (contentType = requestBody.contentType()) == null) {
            return null;
        }
        return contentType.toString();
    }

    public long contentLength() throws IOException {
        RequestBody requestBody = this.requestBody;
        if (requestBody == null) {
            return -1L;
        }
        return requestBody.contentLength();
    }

    public URL url() {
        return this.url;
    }

    public ResponseBodyConverter<T> getResponseBodyConverter() {
        return this.responseBodyConverter;
    }

    public RequestBody getRequestBody() {
        return this.requestBody;
    }

    public Request buildRealRequest() {
        return this.requestBuilder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void addHeaderNameValue(Map<String, List<String>> map, String str, String str2) {
        List<String> list = map.get(str);
        if (list == null) {
            list = new ArrayList<>(2);
            map.put(str, list);
        }
        list.add(str2.trim());
    }

    public static class Builder<T> {
        boolean calculateContentMD5;
        String keyTime;
        String method;
        RequestBodySerializer requestBodySerializer;
        ResponseBodyConverter<T> responseBodyConverter;
        Object tag;
        Map<String, List<String>> headers = new HashMap(10);
        Map<String, String> queries = new HashMap(10);
        Set<String> noSignHeaderKeys = new HashSet();
        Set<String> noSignParamsKeys = new HashSet();
        boolean isCacheEnabled = true;
        HttpUrl.Builder httpUrlBuilder = new HttpUrl.Builder();
        Request.Builder requestBuilder = new Request.Builder();

        public Builder<T> url(URL url) {
            HttpUrl httpUrl = HttpUrl.get(url);
            if (httpUrl == null) {
                throw new IllegalArgumentException("url is not legal : " + url);
            }
            this.httpUrlBuilder = httpUrl.newBuilder();
            return this;
        }

        public Builder<T> scheme(String str) {
            this.httpUrlBuilder.scheme(str);
            return this;
        }

        public Builder<T> tag(Object obj) {
            this.tag = obj;
            return this;
        }

        public Builder<T> host(String str) {
            this.httpUrlBuilder.host(str);
            return this;
        }

        public Builder<T> port(int i) {
            this.httpUrlBuilder.port(i);
            return this;
        }

        public Builder<T> path(String str) {
            if (str.startsWith("/")) {
                str = str.substring(1);
            }
            if (str.length() > 0) {
                this.httpUrlBuilder.addPathSegments(str);
            }
            return this;
        }

        public Builder<T> method(String str) {
            this.method = str;
            return this;
        }

        public Builder<T> encodedQuery(String str) {
            this.httpUrlBuilder.encodedQuery(str);
            return this;
        }

        public Builder<T> query(Map<String, String> map) {
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    if (key != null) {
                        this.queries.put(key, entry.getValue());
                        this.httpUrlBuilder.addQueryParameter(key, entry.getValue());
                    }
                }
            }
            return this;
        }

        public Builder<T> contentMD5() {
            this.calculateContentMD5 = true;
            return this;
        }

        public Builder<T> addHeader(String str, String str2) {
            if (str != null && str2 != null) {
                this.requestBuilder.addHeader(str, str2);
                HttpRequest.addHeaderNameValue(this.headers, str, str2);
            }
            return this;
        }

        public Builder<T> addHeaders(Map<String, List<String>> map) {
            if (map != null) {
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    String key = entry.getKey();
                    for (String str : entry.getValue()) {
                        if (key != null && str != null) {
                            this.requestBuilder.addHeader(key, str);
                            HttpRequest.addHeaderNameValue(this.headers, key, str);
                        }
                    }
                }
            }
            return this;
        }

        public Builder<T> addHeadersUnsafeNonAscii(Map<String, List<String>> map) {
            if (map != null) {
                Headers.Builder builder = new Headers.Builder();
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    String key = entry.getKey();
                    for (String str : entry.getValue()) {
                        if (key != null && str != null) {
                            builder.addUnsafeNonAscii(key, str);
                            HttpRequest.addHeaderNameValue(this.headers, key, str);
                        }
                    }
                }
                this.requestBuilder.headers(builder.build());
            }
            return this;
        }

        public Builder<T> addNoSignHeaderKeys(Set<String> set) {
            this.noSignHeaderKeys.addAll(set);
            return this;
        }

        public Builder<T> addNoSignParamKeys(Set<String> set) {
            this.noSignParamsKeys.addAll(set);
            return this;
        }

        public Builder<T> setKeyTime(String str) {
            this.keyTime = str;
            return this;
        }

        public Builder<T> userAgent(String str) {
            this.requestBuilder.addHeader(com.tencent.cos.xml.crypto.Headers.USER_AGENT, str);
            HttpRequest.addHeaderNameValue(this.headers, com.tencent.cos.xml.crypto.Headers.USER_AGENT, str);
            return this;
        }

        public Builder<T> body(RequestBodySerializer requestBodySerializer) {
            this.requestBodySerializer = requestBodySerializer;
            return this;
        }

        public Builder<T> converter(ResponseBodyConverter<T> responseBodyConverter) {
            this.responseBodyConverter = responseBodyConverter;
            return this;
        }

        protected void prepareBuild() {
            this.requestBuilder.url(this.httpUrlBuilder.build());
            if (!this.isCacheEnabled) {
                this.requestBuilder.cacheControl(CacheControl.FORCE_NETWORK);
            }
            if (this.responseBodyConverter == null) {
                this.responseBodyConverter = (ResponseBodyConverter<T>) ResponseBodyConverter.string();
            }
        }

        public HttpRequest<T> build() {
            prepareBuild();
            return new HttpRequest<>(this);
        }
    }
}
