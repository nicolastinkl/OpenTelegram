package okhttp3.internal.http;

import com.tencent.cos.xml.crypto.Headers;
import java.io.IOException;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.GzipSource;
import okio.Okio;

/* compiled from: BridgeInterceptor.kt */
/* loaded from: classes3.dex */
public final class BridgeInterceptor implements Interceptor {
    private final CookieJar cookieJar;

    public BridgeInterceptor(CookieJar cookieJar) {
        Intrinsics.checkNotNullParameter(cookieJar, "cookieJar");
        this.cookieJar = cookieJar;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        boolean equals;
        ResponseBody body;
        Intrinsics.checkNotNullParameter(chain, "chain");
        Request request = chain.request();
        Request.Builder newBuilder = request.newBuilder();
        RequestBody body2 = request.body();
        if (body2 != null) {
            MediaType contentType = body2.contentType();
            if (contentType != null) {
                newBuilder.header(Headers.CONTENT_TYPE, contentType.toString());
            }
            long contentLength = body2.contentLength();
            if (contentLength != -1) {
                newBuilder.header(Headers.CONTENT_LENGTH, String.valueOf(contentLength));
                newBuilder.removeHeader("Transfer-Encoding");
            } else {
                newBuilder.header("Transfer-Encoding", "chunked");
                newBuilder.removeHeader(Headers.CONTENT_LENGTH);
            }
        }
        boolean z = false;
        if (request.header(Headers.HOST) == null) {
            newBuilder.header(Headers.HOST, Util.toHostHeader$default(request.url(), false, 1, null));
        }
        if (request.header("Connection") == null) {
            newBuilder.header("Connection", "Keep-Alive");
        }
        if (request.header("Accept-Encoding") == null && request.header("Range") == null) {
            newBuilder.header("Accept-Encoding", "gzip");
            z = true;
        }
        List<Cookie> loadForRequest = this.cookieJar.loadForRequest(request.url());
        if (!loadForRequest.isEmpty()) {
            newBuilder.header("Cookie", cookieHeader(loadForRequest));
        }
        if (request.header(Headers.USER_AGENT) == null) {
            newBuilder.header(Headers.USER_AGENT, "okhttp/4.11.0");
        }
        Response proceed = chain.proceed(newBuilder.build());
        HttpHeaders.receiveHeaders(this.cookieJar, request.url(), proceed.headers());
        Response.Builder request2 = proceed.newBuilder().request(request);
        if (z) {
            equals = StringsKt__StringsJVMKt.equals("gzip", Response.header$default(proceed, "Content-Encoding", null, 2, null), true);
            if (equals && HttpHeaders.promisesBody(proceed) && (body = proceed.body()) != null) {
                GzipSource gzipSource = new GzipSource(body.source());
                request2.headers(proceed.headers().newBuilder().removeAll("Content-Encoding").removeAll(Headers.CONTENT_LENGTH).build());
                request2.body(new RealResponseBody(Response.header$default(proceed, Headers.CONTENT_TYPE, null, 2, null), -1L, Okio.buffer(gzipSource)));
            }
        }
        return request2.build();
    }

    private final String cookieHeader(List<Cookie> list) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            Cookie cookie = (Cookie) obj;
            if (i > 0) {
                sb.append("; ");
            }
            sb.append(cookie.name());
            sb.append('=');
            sb.append(cookie.value());
            i = i2;
        }
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }
}
