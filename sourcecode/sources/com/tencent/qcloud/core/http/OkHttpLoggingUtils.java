package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.http.HttpLoggingInterceptor;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/* loaded from: classes.dex */
public class OkHttpLoggingUtils {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static boolean isContentLengthTooLarge(long j) {
        return j > 2048;
    }

    public static void logResponse(Response response, long j, HttpLoggingInterceptor.Level level, HttpLoggingInterceptor.Logger logger) {
        boolean z = level == HttpLoggingInterceptor.Level.BODY;
        boolean z2 = z || level == HttpLoggingInterceptor.Level.HEADERS;
        ResponseBody body = response.body();
        boolean z3 = body != null;
        long contentLength = z3 ? body.contentLength() : 0L;
        String str = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        StringBuilder sb = new StringBuilder();
        sb.append("<-- ");
        sb.append(response.code());
        sb.append(' ');
        sb.append(response.message());
        sb.append(' ');
        sb.append(response.request().url());
        sb.append(" (");
        sb.append(j);
        sb.append("ms");
        sb.append(z2 ? "" : ", " + str + " body");
        sb.append(')');
        logger.logResponse(response, sb.toString());
        if (z2) {
            Headers headers = response.headers();
            int size = headers.size();
            for (int i = 0; i < size; i++) {
                logger.logResponse(response, headers.name(i) + ": " + headers.value(i));
            }
            if (!z || !HttpHeaders.hasBody(response) || !z3 || isContentLengthTooLarge(contentLength)) {
                logger.logResponse(response, "<-- END HTTP");
                return;
            }
            if (bodyEncoded(response.headers())) {
                logger.logResponse(response, "<-- END HTTP (encoded body omitted)");
                return;
            }
            try {
                BufferedSource source = body.source();
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.buffer();
                Charset charset = UTF8;
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(charset);
                    } catch (UnsupportedCharsetException unused) {
                        logger.logResponse(response, "");
                        logger.logResponse(response, "Couldn't decode the response body; charset is likely malformed.");
                        logger.logResponse(response, "<-- END HTTP");
                        return;
                    }
                }
                if (!isPlaintext(buffer)) {
                    logger.logResponse(response, "");
                    logger.logResponse(response, "<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                    return;
                }
                if (contentLength != 0) {
                    logger.logResponse(response, "");
                    logger.logResponse(response, buffer.clone().readString(charset));
                }
                logger.logResponse(response, "<-- END HTTP (" + buffer.size() + "-byte body)");
            } catch (Exception unused2) {
                logger.logResponse(response, "<-- END HTTP");
            }
        }
    }

    public static void logRequest(Request request, Protocol protocol, HttpLoggingInterceptor.Level level, HttpLoggingInterceptor.Logger logger) throws IOException {
        boolean z = level == HttpLoggingInterceptor.Level.BODY;
        boolean z2 = z || level == HttpLoggingInterceptor.Level.HEADERS;
        RequestBody body = request.body();
        boolean z3 = body != null;
        String str = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
        if (!z2 && z3) {
            str = str + " (" + body.contentLength() + "-byte body)";
        }
        logger.logRequest(str);
        if (z2) {
            if (z3) {
                if (body.contentType() != null) {
                    logger.logRequest("Content-Type: " + body.contentType());
                }
                if (body.contentLength() != -1) {
                    logger.logRequest("Content-Length: " + body.contentLength());
                }
            }
            Headers headers = request.headers();
            int size = headers.size();
            for (int i = 0; i < size; i++) {
                String name = headers.name(i);
                if (!com.tencent.cos.xml.crypto.Headers.CONTENT_TYPE.equalsIgnoreCase(name) && !com.tencent.cos.xml.crypto.Headers.CONTENT_LENGTH.equalsIgnoreCase(name)) {
                    logger.logRequest(name + ": " + headers.value(i));
                }
            }
            if (!z || !z3 || isContentLengthTooLarge(body.contentLength())) {
                logger.logRequest("--> END " + request.method());
                return;
            }
            if (bodyEncoded(request.headers())) {
                logger.logRequest("--> END " + request.method() + " (encoded body omitted)");
                return;
            }
            try {
                Buffer buffer = new Buffer();
                body.writeTo(buffer);
                Charset charset = UTF8;
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
                logger.logRequest("");
                if (isPlaintext(buffer)) {
                    logger.logRequest(buffer.readString(charset));
                    logger.logRequest("--> END " + request.method() + " (" + body.contentLength() + "-byte body)");
                    return;
                }
                logger.logRequest("--> END " + request.method() + " (binary " + body.contentLength() + "-byte body omitted)");
            } catch (Exception unused) {
                logger.logRequest("--> END " + request.method());
            }
        }
    }

    private static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer buffer2 = new Buffer();
            buffer.copyTo(buffer2, 0L, buffer.size() < 64 ? buffer.size() : 64L);
            for (int i = 0; i < 16; i++) {
                if (buffer2.exhausted()) {
                    return true;
                }
                int readUtf8CodePoint = buffer2.readUtf8CodePoint();
                if (Character.isISOControl(readUtf8CodePoint) && !Character.isWhitespace(readUtf8CodePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException unused) {
            return false;
        }
    }

    private static boolean bodyEncoded(Headers headers) {
        String str = headers.get("Content-Encoding");
        return (str == null || str.equalsIgnoreCase("identity")) ? false : true;
    }
}
