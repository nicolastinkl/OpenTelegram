package com.tencent.qcloud.core.http;

import android.content.ContentResolver;
import android.net.Uri;
import com.tencent.qcloud.core.common.QCloudDigistListener;
import com.tencent.qcloud.core.common.QCloudProgressListener;
import com.tencent.qcloud.core.logger.QCloudLogger;
import com.tencent.qcloud.core.util.Base64Utils;
import com.tencent.qcloud.core.util.QCloudUtils;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
public class StreamingRequestBody extends RequestBody implements ProgressBody, QCloudDigistListener {
    protected byte[] bytes;
    protected ContentResolver contentResolver;
    protected String contentType;
    protected CountingSink countingSink;
    protected File file;
    protected QCloudProgressListener progressListener;
    protected InputStream stream;
    protected Uri uri;
    protected URL url;
    protected long offset = 0;
    protected long requiredLength = -1;
    protected long contentRawLength = -1;
    private boolean deleteFileWhenComplete = false;

    @Override // com.tencent.qcloud.core.http.ProgressBody
    public void setProgressListener(QCloudProgressListener qCloudProgressListener) {
        this.progressListener = qCloudProgressListener;
    }

    protected StreamingRequestBody() {
    }

    @Override // com.tencent.qcloud.core.http.ProgressBody
    public long getBytesTransferred() {
        CountingSink countingSink = this.countingSink;
        if (countingSink != null) {
            return countingSink.getTotalTransferred();
        }
        return 0L;
    }

    static StreamingRequestBody file(File file, String str, long j, long j2) {
        StreamingRequestBody streamingRequestBody = new StreamingRequestBody();
        streamingRequestBody.file = file;
        streamingRequestBody.contentType = str;
        if (j < 0) {
            j = 0;
        }
        streamingRequestBody.offset = j;
        streamingRequestBody.requiredLength = j2;
        return streamingRequestBody;
    }

    static StreamingRequestBody bytes(byte[] bArr, String str, long j, long j2) {
        StreamingRequestBody streamingRequestBody = new StreamingRequestBody();
        streamingRequestBody.bytes = bArr;
        streamingRequestBody.contentType = str;
        if (j < 0) {
            j = 0;
        }
        streamingRequestBody.offset = j;
        streamingRequestBody.requiredLength = j2;
        return streamingRequestBody;
    }

    static StreamingRequestBody steam(InputStream inputStream, File file, String str, long j, long j2) {
        StreamingRequestBody streamingRequestBody = new StreamingRequestBody();
        streamingRequestBody.stream = inputStream;
        streamingRequestBody.contentType = str;
        streamingRequestBody.file = file;
        if (j < 0) {
            j = 0;
        }
        streamingRequestBody.offset = j;
        streamingRequestBody.requiredLength = j2;
        streamingRequestBody.deleteFileWhenComplete = true;
        return streamingRequestBody;
    }

    static StreamingRequestBody url(URL url, String str, long j, long j2) {
        StreamingRequestBody streamingRequestBody = new StreamingRequestBody();
        streamingRequestBody.url = url;
        streamingRequestBody.contentType = str;
        if (j < 0) {
            j = 0;
        }
        streamingRequestBody.offset = j;
        streamingRequestBody.requiredLength = j2;
        return streamingRequestBody;
    }

    static StreamingRequestBody uri(Uri uri, ContentResolver contentResolver, String str, long j, long j2) {
        StreamingRequestBody streamingRequestBody = new StreamingRequestBody();
        streamingRequestBody.uri = uri;
        streamingRequestBody.contentResolver = contentResolver;
        streamingRequestBody.contentType = str;
        if (j < 0) {
            j = 0;
        }
        streamingRequestBody.offset = j;
        streamingRequestBody.requiredLength = j2;
        return streamingRequestBody;
    }

    boolean isLargeData() {
        return (this.file == null && this.stream == null) ? false : true;
    }

    @Override // okhttp3.RequestBody
    public MediaType contentType() {
        String str = this.contentType;
        if (str != null) {
            return MediaType.parse(str);
        }
        return null;
    }

    @Override // okhttp3.RequestBody
    public long contentLength() throws IOException {
        long contentRawLength = getContentRawLength();
        if (contentRawLength <= 0) {
            return Math.max(this.requiredLength, -1L);
        }
        long j = this.requiredLength;
        if (j <= 0) {
            return Math.max(contentRawLength - this.offset, -1L);
        }
        return Math.min(contentRawLength - this.offset, j);
    }

    protected long getContentRawLength() throws IOException {
        if (this.contentRawLength < 0) {
            if (this.stream != null) {
                this.contentRawLength = r0.available();
            } else {
                File file = this.file;
                if (file != null) {
                    this.contentRawLength = file.length();
                } else {
                    if (this.bytes != null) {
                        this.contentRawLength = r0.length;
                    } else {
                        Uri uri = this.uri;
                        if (uri != null) {
                            this.contentRawLength = QCloudUtils.getUriContentLength(uri, this.contentResolver);
                        }
                    }
                }
            }
        }
        return this.contentRawLength;
    }

    /* JADX WARN: Finally extract failed */
    public InputStream getStream() throws IOException {
        InputStream inputStream = null;
        if (this.bytes != null) {
            inputStream = new ByteArrayInputStream(this.bytes);
        } else {
            InputStream inputStream2 = this.stream;
            if (inputStream2 != null) {
                try {
                    saveInputStreamToTmpFile(inputStream2, this.file);
                    InputStream inputStream3 = this.stream;
                    if (inputStream3 != null) {
                        Util.closeQuietly(inputStream3);
                    }
                    this.stream = null;
                    this.offset = 0L;
                    inputStream = new FileInputStream(this.file);
                } catch (Throwable th) {
                    InputStream inputStream4 = this.stream;
                    if (inputStream4 != null) {
                        Util.closeQuietly(inputStream4);
                    }
                    this.stream = null;
                    this.offset = 0L;
                    throw th;
                }
            } else if (this.file != null) {
                inputStream = new FileInputStream(this.file);
            } else {
                URL url = this.url;
                if (url != null) {
                    URLConnection openConnection = url.openConnection();
                    if (this.offset > 0) {
                        openConnection.setRequestProperty("Range", "bytes=" + this.offset + "-" + this.offset + this.requiredLength);
                    }
                    inputStream = this.url.openStream();
                } else {
                    Uri uri = this.uri;
                    if (uri != null) {
                        inputStream = this.contentResolver.openInputStream(uri);
                    }
                }
            }
        }
        if (this.url == null && inputStream != null) {
            long j = this.offset;
            if (j > 0) {
                long skip = inputStream.skip(j);
                if (skip < this.offset) {
                    QCloudLogger.w("QCloudHttp", "skip  %d is small than offset %d", Long.valueOf(skip), Long.valueOf(this.offset));
                }
            }
        }
        return inputStream;
    }

    protected void saveInputStreamToTmpFile(InputStream inputStream, File file) throws IOException {
        FileOutputStream fileOutputStream;
        int read;
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr = new byte[LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM];
            long contentLength = contentLength();
            long j = 0;
            if (contentLength < 0) {
                contentLength = Long.MAX_VALUE;
            }
            long j2 = this.offset;
            if (j2 > 0) {
                inputStream.skip(j2);
            }
            while (j < contentLength && (read = inputStream.read(bArr)) != -1) {
                long j3 = read;
                fileOutputStream.write(bArr, 0, (int) Math.min(j3, contentLength - j));
                j += j3;
            }
            fileOutputStream.flush();
            Util.closeQuietly(fileOutputStream);
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                Util.closeQuietly(fileOutputStream2);
            }
            throw th;
        }
    }

    @Override // okhttp3.RequestBody
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
                    CountingSink countingSink2 = this.countingSink;
                    if (countingSink2 != null) {
                        Util.closeQuietly(countingSink2);
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
            CountingSink countingSink3 = this.countingSink;
            if (countingSink3 != null) {
                Util.closeQuietly(countingSink3);
            }
        } catch (Throwable th2) {
            th = th2;
            bufferedSource = null;
        }
    }

    @Override // com.tencent.qcloud.core.common.QCloudDigistListener
    public String onGetMd5() throws IOException {
        try {
            try {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                    byte[] bArr = this.bytes;
                    if (bArr != null) {
                        messageDigest.update(bArr, (int) this.offset, (int) contentLength());
                        return Base64Utils.encode(messageDigest.digest());
                    }
                    InputStream stream = getStream();
                    byte[] bArr2 = new byte[LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM];
                    long contentLength = contentLength();
                    while (contentLength > 0) {
                        int read = stream.read(bArr2, 0, ((long) LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM) > contentLength ? (int) contentLength : LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM);
                        if (read == -1) {
                            break;
                        }
                        messageDigest.update(bArr2, 0, read);
                        contentLength -= read;
                    }
                    String encode = Base64Utils.encode(messageDigest.digest());
                    if (stream != null) {
                        Util.closeQuietly(stream);
                    }
                    return encode;
                } catch (NoSuchAlgorithmException e) {
                    throw new IOException("unSupport Md5 algorithm", e);
                }
            } catch (IOException e2) {
                throw e2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                Util.closeQuietly((Closeable) null);
            }
            throw th;
        }
    }

    public void release() {
        File file;
        if (!this.deleteFileWhenComplete || (file = this.file) == null) {
            return;
        }
        file.delete();
    }
}
