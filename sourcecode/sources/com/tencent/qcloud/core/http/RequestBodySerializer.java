package com.tencent.qcloud.core.http;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.tencent.qcloud.core.util.QCloudStringUtils;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/* loaded from: classes.dex */
public abstract class RequestBodySerializer {
    public abstract RequestBody body();

    private static final class BaseRequestBodyWrapper extends RequestBodySerializer {
        private final RequestBody body;

        public BaseRequestBodyWrapper(RequestBody requestBody) {
            this.body = requestBody;
        }

        @Override // com.tencent.qcloud.core.http.RequestBodySerializer
        public RequestBody body() {
            return this.body;
        }
    }

    private static MediaType parseType(String str) {
        if (str != null) {
            return MediaType.parse(str);
        }
        return null;
    }

    public static RequestBodySerializer bytes(String str, byte[] bArr) {
        return bytes(str, bArr, 0L, -1L);
    }

    public static RequestBodySerializer bytes(String str, byte[] bArr, long j, long j2) {
        long length = bArr.length - j;
        if (j2 >= 0) {
            length = Math.min(j2, length);
        }
        if (length < 204800) {
            return new BaseRequestBodyWrapper(RequestBody.create(parseType(str), bArr));
        }
        return new BaseRequestBodyWrapper(StreamingRequestBody.bytes(bArr, str, j, j2));
    }

    public static RequestBodySerializer file(String str, File file) {
        return file(str, file, 0L, -1L);
    }

    public static RequestBodySerializer file(String str, File file, long j, long j2) {
        if (TextUtils.isEmpty(str)) {
            str = MimeTypeMap.getSingleton().getMimeTypeFromExtension(QCloudStringUtils.getExtension(file.getPath()));
        }
        return new BaseRequestBodyWrapper(StreamingRequestBody.file(file, str, j, j2));
    }

    public static RequestBodySerializer url(String str, URL url) {
        return url(str, url, 0L, -1L);
    }

    public static RequestBodySerializer url(String str, URL url, long j, long j2) {
        if (TextUtils.isEmpty(str)) {
            str = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url.toString()));
        }
        return new BaseRequestBodyWrapper(StreamingRequestBody.url(url, str, j, j2));
    }

    public static RequestBodySerializer uri(String str, Uri uri, Context context) {
        return uri(str, uri, context, 0L, -1L);
    }

    public static RequestBodySerializer uri(String str, Uri uri, Context context, long j, long j2) {
        ContentResolver contentResolver = context.getContentResolver();
        if (TextUtils.isEmpty(str)) {
            str = contentResolver.getType(uri);
        }
        return new BaseRequestBodyWrapper(StreamingRequestBody.uri(uri, contentResolver, str, j, j2));
    }

    public static RequestBodySerializer stream(String str, File file, InputStream inputStream) {
        return stream(str, file, inputStream, 0L, -1L);
    }

    public static RequestBodySerializer stream(String str, File file, InputStream inputStream, long j, long j2) {
        return new BaseRequestBodyWrapper(StreamingRequestBody.steam(inputStream, file, str, j, j2));
    }

    public static RequestBodySerializer multiPart(MultipartStreamRequestBody multipartStreamRequestBody) {
        return new BaseRequestBodyWrapper(multipartStreamRequestBody);
    }
}
