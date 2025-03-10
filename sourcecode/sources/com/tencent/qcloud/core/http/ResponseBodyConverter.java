package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudServiceException;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public abstract class ResponseBodyConverter<T> {
    public abstract T convert(HttpResponse<T> httpResponse) throws QCloudClientException, QCloudServiceException;

    private static final class StringConverter extends ResponseBodyConverter<String> {
        private StringConverter() {
        }

        @Override // com.tencent.qcloud.core.http.ResponseBodyConverter
        public String convert(HttpResponse<String> httpResponse) throws QCloudClientException, QCloudServiceException {
            try {
                return httpResponse.string();
            } catch (IOException e) {
                throw new QCloudClientException(e);
            }
        }
    }

    private static final class BytesConverter extends ResponseBodyConverter<byte[]> {
        private BytesConverter() {
        }

        @Override // com.tencent.qcloud.core.http.ResponseBodyConverter
        public byte[] convert(HttpResponse<byte[]> httpResponse) throws QCloudClientException, QCloudServiceException {
            try {
                return httpResponse.bytes();
            } catch (IOException e) {
                throw new QCloudClientException(e);
            }
        }
    }

    private static final class InputStreamConverter extends ResponseBodyConverter<InputStream> {
        private InputStreamConverter() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.tencent.qcloud.core.http.ResponseBodyConverter
        public InputStream convert(HttpResponse<InputStream> httpResponse) throws QCloudClientException, QCloudServiceException {
            return httpResponse.byteStream();
        }
    }

    public static ResponseBodyConverter<Void> file(String str) {
        return file(str, -1L);
    }

    public static ResponseBodyConverter<Void> file(String str, long j) {
        return new ResponseFileConverter(str, j);
    }

    public static ResponseBodyConverter<String> string() {
        return new StringConverter();
    }

    public static ResponseBodyConverter<byte[]> bytes() {
        return new BytesConverter();
    }

    public static ResponseBodyConverter<InputStream> inputStream() {
        return new InputStreamConverter();
    }
}
