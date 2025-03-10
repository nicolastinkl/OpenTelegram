package com.tencent.qcloud.core.http;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.qcloud.core.common.QCloudDigistListener;
import com.tencent.qcloud.core.common.QCloudProgressListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/* loaded from: classes.dex */
public class MultipartStreamRequestBody extends RequestBody implements ProgressBody, QCloudDigistListener, ReactiveBody {
    private Map<String, String> bodyParameters = new LinkedHashMap();
    private String fileName;
    MultipartBody multipartBody;
    private String name;
    StreamingRequestBody streamingRequestBody;

    @Override // com.tencent.qcloud.core.http.ReactiveBody
    public <T> void end(HttpResult<T> httpResult) throws IOException {
    }

    public void setBodyParameters(Map<String, String> map) {
        if (map != null) {
            this.bodyParameters.putAll(map);
        }
    }

    public void setContent(String str, String str2, String str3, File file, long j, long j2) {
        if (str2 != null) {
            this.name = str2;
        }
        this.fileName = str3;
        if (TextUtils.isEmpty(str)) {
            str = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(file.getPath()));
        }
        this.streamingRequestBody = ExStreamingRequestBody.file(file, str, j, j2);
    }

    public void setContent(String str, String str2, String str3, byte[] bArr, long j, long j2) {
        if (str2 != null) {
            this.name = str2;
        }
        this.fileName = str3;
        this.streamingRequestBody = ExStreamingRequestBody.bytes(bArr, str, j, j2);
    }

    public void setContent(String str, String str2, String str3, File file, InputStream inputStream, long j, long j2) throws IOException {
        if (str2 != null) {
            this.name = str2;
        }
        this.fileName = str3;
        this.streamingRequestBody = ExStreamingRequestBody.steam(inputStream, file, str, j, j2);
    }

    public void setSign(String str) {
        if (str != null) {
            this.bodyParameters.put("Signature", str);
        }
    }

    public void addMd5() throws IOException {
        try {
            this.bodyParameters.put(Headers.CONTENT_MD5, onGetMd5());
        } catch (IOException e) {
            throw e;
        }
    }

    @Override // com.tencent.qcloud.core.http.ReactiveBody
    public void prepare() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MediaType.parse("multipart/form-data"));
        for (Map.Entry<String, String> entry : this.bodyParameters.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        builder.addFormDataPart(this.name, this.fileName, this.streamingRequestBody);
        this.multipartBody = builder.build();
    }

    @Override // com.tencent.qcloud.core.http.ProgressBody
    public void setProgressListener(QCloudProgressListener qCloudProgressListener) {
        StreamingRequestBody streamingRequestBody = this.streamingRequestBody;
        if (streamingRequestBody != null) {
            streamingRequestBody.setProgressListener(qCloudProgressListener);
        }
    }

    @Override // com.tencent.qcloud.core.http.ProgressBody
    public long getBytesTransferred() {
        StreamingRequestBody streamingRequestBody = this.streamingRequestBody;
        if (streamingRequestBody != null) {
            return streamingRequestBody.getBytesTransferred();
        }
        return 0L;
    }

    @Override // okhttp3.RequestBody
    public MediaType contentType() {
        return this.multipartBody.contentType();
    }

    @Override // okhttp3.RequestBody
    public long contentLength() throws IOException {
        return this.multipartBody.contentLength();
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        try {
            this.multipartBody.writeTo(bufferedSink);
        } finally {
            CountingSink countingSink = this.streamingRequestBody.countingSink;
            if (countingSink != null) {
                Util.closeQuietly(countingSink);
            }
        }
    }

    @Override // com.tencent.qcloud.core.common.QCloudDigistListener
    public String onGetMd5() throws IOException {
        StreamingRequestBody streamingRequestBody = this.streamingRequestBody;
        if (streamingRequestBody == null) {
            return null;
        }
        String onGetMd5 = streamingRequestBody.onGetMd5();
        this.bodyParameters.put(Headers.CONTENT_MD5, onGetMd5);
        return onGetMd5;
    }

    private static class ExStreamingRequestBody extends StreamingRequestBody {
        protected ExStreamingRequestBody() {
        }

        static StreamingRequestBody file(File file, String str, long j, long j2) {
            ExStreamingRequestBody exStreamingRequestBody = new ExStreamingRequestBody();
            exStreamingRequestBody.file = file;
            exStreamingRequestBody.contentType = str;
            if (j < 0) {
                j = 0;
            }
            exStreamingRequestBody.offset = j;
            exStreamingRequestBody.requiredLength = j2;
            return exStreamingRequestBody;
        }

        static StreamingRequestBody bytes(byte[] bArr, String str, long j, long j2) {
            ExStreamingRequestBody exStreamingRequestBody = new ExStreamingRequestBody();
            exStreamingRequestBody.bytes = bArr;
            exStreamingRequestBody.contentType = str;
            if (j < 0) {
                j = 0;
            }
            exStreamingRequestBody.offset = j;
            exStreamingRequestBody.requiredLength = j2;
            return exStreamingRequestBody;
        }

        static StreamingRequestBody steam(InputStream inputStream, File file, String str, long j, long j2) {
            ExStreamingRequestBody exStreamingRequestBody = new ExStreamingRequestBody();
            exStreamingRequestBody.stream = inputStream;
            exStreamingRequestBody.contentType = str;
            exStreamingRequestBody.file = file;
            if (j < 0) {
                j = 0;
            }
            exStreamingRequestBody.offset = j;
            exStreamingRequestBody.requiredLength = j2;
            return exStreamingRequestBody;
        }

        @Override // com.tencent.qcloud.core.http.StreamingRequestBody, okhttp3.RequestBody
        public void writeTo(BufferedSink bufferedSink) throws IOException {
            BufferedSource bufferedSource;
            InputStream inputStream = null;
            r0 = null;
            BufferedSource bufferedSource2 = null;
            try {
                InputStream stream = getStream();
                if (stream != null) {
                    try {
                        bufferedSource2 = Okio.buffer(Okio.source(stream));
                        long contentLength = contentLength();
                        CountingSink countingSink = new CountingSink(bufferedSink, contentLength, this.progressListener);
                        this.countingSink = countingSink;
                        BufferedSink buffer = Okio.buffer(countingSink);
                        if (contentLength > 0) {
                            buffer.write(bufferedSource2, contentLength);
                        } else {
                            buffer.writeAll(bufferedSource2);
                        }
                        buffer.flush();
                    } catch (Throwable th) {
                        th = th;
                        bufferedSource = bufferedSource2;
                        inputStream = stream;
                        if (inputStream != null) {
                            Util.closeQuietly(inputStream);
                        }
                        if (bufferedSource != null) {
                            Util.closeQuietly(bufferedSource);
                        }
                        throw th;
                    }
                }
                if (stream != null) {
                    Util.closeQuietly(stream);
                }
                if (bufferedSource2 != null) {
                    Util.closeQuietly(bufferedSource2);
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedSource = null;
            }
        }
    }
}
