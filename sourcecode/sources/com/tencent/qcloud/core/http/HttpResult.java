package com.tencent.qcloud.core.http;

import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class HttpResult<T> {
    private final int code;
    private final T content;
    private final Map<String, List<String>> headers;

    public HttpResult(HttpResponse<T> httpResponse, T t) {
        this.code = httpResponse.code();
        httpResponse.message();
        this.headers = httpResponse.response.headers().toMultimap();
        this.content = t;
    }

    public T content() {
        return this.content;
    }

    public Map<String, List<String>> headers() {
        return this.headers;
    }

    public final boolean isSuccessful() {
        int i = this.code;
        return i >= 200 && i < 300;
    }

    public String header(String str) {
        List<String> list = this.headers.get(str);
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }
}
