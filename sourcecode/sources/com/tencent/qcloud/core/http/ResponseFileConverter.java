package com.tencent.qcloud.core.http;

import android.content.ContentResolver;
import android.net.Uri;
import android.text.TextUtils;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudProgressListener;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.util.QCloudHttpUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import okhttp3.internal.Util;
import okio.Buffer;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
public class ResponseFileConverter<T> extends ResponseBodyConverter<T> implements ProgressBody {
    private ContentResolver contentResolver;
    private Uri contentUri;
    private CountingSink countingSink;
    private String filePath;
    private InputStream inputStream;
    protected boolean isQuic = false;
    private long offset;
    protected QCloudProgressListener progressListener;

    public ResponseFileConverter(String str, long j) {
        this.filePath = str;
        this.offset = j;
    }

    public ResponseFileConverter(Uri uri, ContentResolver contentResolver, long j) {
        this.contentUri = uri;
        this.contentResolver = contentResolver;
        this.offset = j;
    }

    public ResponseFileConverter() {
    }

    @Override // com.tencent.qcloud.core.http.ProgressBody
    public void setProgressListener(QCloudProgressListener qCloudProgressListener) {
        this.progressListener = qCloudProgressListener;
    }

    public void enableQuic(boolean z) {
        this.isQuic = z;
    }

    public QCloudProgressListener getProgressListener() {
        return this.progressListener;
    }

    @Override // com.tencent.qcloud.core.http.ResponseBodyConverter
    public T convert(HttpResponse<T> httpResponse) throws QCloudClientException, QCloudServiceException {
        long contentLength;
        if (this.isQuic) {
            return null;
        }
        HttpResponse.checkResponseSuccessful(httpResponse);
        long[] parseContentRange = QCloudHttpUtils.parseContentRange(httpResponse.header(Headers.CONTENT_RANGE));
        if (parseContentRange != null) {
            contentLength = (parseContentRange[1] - parseContentRange[0]) + 1;
        } else {
            contentLength = httpResponse.contentLength();
        }
        if (!TextUtils.isEmpty(this.filePath)) {
            return downloadToAbsolutePath(httpResponse, contentLength);
        }
        if (this.contentUri != null) {
            return pipeToContentUri(httpResponse, contentLength);
        }
        throw new QCloudClientException(new IllegalArgumentException("filePath or ContentUri are both null"));
    }

    private T pipeToContentUri(HttpResponse<T> httpResponse, long j) throws QCloudClientException, QCloudServiceException {
        OutputStream outputStream = getOutputStream();
        InputStream byteStream = httpResponse.byteStream();
        byte[] bArr = new byte[LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM];
        this.countingSink = new CountingSink(new Buffer(), j, this.progressListener);
        while (true) {
            try {
                try {
                    int read = byteStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(bArr, 0, read);
                    this.countingSink.writeBytesInternal(read);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new QCloudClientException("write local uri error for " + e.toString(), e);
                }
            } finally {
                if (outputStream != null) {
                    Util.closeQuietly(outputStream);
                }
            }
        }
        return null;
    }

    private T downloadToAbsolutePath(HttpResponse<T> httpResponse, long j) throws QCloudClientException, QCloudServiceException {
        File file = new File(this.filePath);
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
            throw new QCloudClientException(new IOException("local file directory can not create."));
        }
        if (httpResponse.response.body() == null) {
            throw new QCloudServiceException("response body is empty !");
        }
        try {
            writeRandomAccessFile(file, httpResponse.byteStream(), j);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new QCloudClientException("write local file error for " + e.toString(), e);
        }
    }

    private void writeRandomAccessFile(File file, InputStream inputStream, long j) throws IOException, QCloudClientException {
        RandomAccessFile randomAccessFile;
        if (inputStream == null) {
            throw new QCloudClientException(new IOException("response body stream is null"));
        }
        RandomAccessFile randomAccessFile2 = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rws");
        } catch (Throwable th) {
            th = th;
        }
        try {
            long bytesTransferred = getBytesTransferred();
            long j2 = this.offset;
            if (j2 + bytesTransferred > 0) {
                randomAccessFile.seek(j2 + bytesTransferred);
            }
            byte[] bArr = new byte[LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM];
            this.countingSink = new CountingSink(new Buffer(), j, bytesTransferred, this.progressListener);
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    randomAccessFile.write(bArr, 0, read);
                    this.countingSink.writeBytesInternal(read);
                } else {
                    Util.closeQuietly(randomAccessFile);
                    return;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            randomAccessFile2 = randomAccessFile;
            if (randomAccessFile2 != null) {
                Util.closeQuietly(randomAccessFile2);
            }
            throw th;
        }
    }

    public OutputStream getOutputStream() throws QCloudClientException {
        if (!TextUtils.isEmpty(this.filePath)) {
            File file = new File(this.filePath);
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
                throw new QCloudClientException(new IOException("local file directory can not create."));
            }
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                throw new QCloudClientException(e);
            }
        }
        Uri uri = this.contentUri;
        if (uri != null) {
            try {
                return this.contentResolver.openOutputStream(uri);
            } catch (FileNotFoundException e2) {
                throw new QCloudClientException(e2);
            }
        }
        throw new QCloudClientException(new IllegalArgumentException("filePath or ContentUri are both null"));
    }

    @Override // com.tencent.qcloud.core.http.ProgressBody
    public long getBytesTransferred() {
        CountingSink countingSink = this.countingSink;
        if (countingSink != null) {
            return countingSink.getTotalTransferred();
        }
        return 0L;
    }

    public boolean isFilePathConverter() {
        return !TextUtils.isEmpty(this.filePath);
    }
}
