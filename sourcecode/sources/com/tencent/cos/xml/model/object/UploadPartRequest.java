package com.tencent.cos.xml.model.object;

import android.content.Context;
import android.net.Uri;
import com.tencent.cos.xml.CosXmlBaseService;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.common.RequestMethod;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import com.tencent.qcloud.core.util.ContextHolder;
import com.tencent.qcloud.core.util.QCloudUtils;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

/* loaded from: classes.dex */
public final class UploadPartRequest extends BaseMultipartUploadRequest implements TransferRequest {
    private byte[] data;
    private long fileContentLength;
    private long fileOffset;
    private InputStream inputStream;
    private boolean lastPart;
    private int partNumber;
    private CosXmlProgressListener progressListener;
    private String srcPath;
    private String uploadId;
    private Uri uri;
    private URL url;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return RequestMethod.PUT;
    }

    private UploadPartRequest(String str, String str2) {
        super(str, str2);
        this.fileOffset = -1L;
        this.fileContentLength = -1L;
        setNeedMD5(true);
    }

    public UploadPartRequest(String str, String str2, int i, String str3, String str4) {
        this(str, str2);
        this.partNumber = i;
        this.srcPath = str3;
        this.uploadId = str4;
        this.fileOffset = -1L;
        this.fileContentLength = -1L;
    }

    public UploadPartRequest(String str, String str2, int i, Uri uri, String str3) {
        this(str, str2);
        this.partNumber = i;
        this.uri = uri;
        this.uploadId = str3;
        this.fileOffset = -1L;
        this.fileContentLength = -1L;
    }

    public UploadPartRequest(String str, String str2, int i, String str3, long j, long j2, String str4) {
        this(str, str2);
        this.partNumber = i;
        setSrcPath(str3, j, j2);
        this.uploadId = str4;
    }

    public UploadPartRequest(String str, String str2, int i, Uri uri, long j, long j2, String str3) {
        this(str, str2);
        this.partNumber = i;
        this.uri = uri;
        this.fileOffset = j;
        this.fileContentLength = j2;
        this.uploadId = str3;
    }

    public UploadPartRequest(String str, String str2, int i, byte[] bArr, String str3) {
        this(str, str2);
        this.partNumber = i;
        this.data = bArr;
        this.uploadId = str3;
        this.fileOffset = -1L;
        this.fileContentLength = -1L;
    }

    public UploadPartRequest(String str, String str2, int i, InputStream inputStream, String str3) throws CosXmlClientException {
        this(str, str2);
        this.partNumber = i;
        this.inputStream = inputStream;
        this.uploadId = str3;
        this.fileOffset = -1L;
        this.fileContentLength = -1L;
    }

    public UploadPartRequest(String str, String str2, int i, InputStream inputStream, long j, String str3) throws CosXmlClientException {
        this(str, str2);
        this.partNumber = i;
        this.inputStream = inputStream;
        this.uploadId = str3;
        this.fileOffset = 0L;
        this.fileContentLength = j;
    }

    public UploadPartRequest(String str, String str2, int i, URL url, String str3) {
        this(str, str2);
        this.partNumber = i;
        this.url = url;
        this.uploadId = str3;
        this.fileOffset = -1L;
        this.fileContentLength = -1L;
        setNeedMD5(false);
    }

    public UploadPartRequest(String str, String str2, int i, URL url, long j, long j2, String str3) {
        this(str, str2);
        this.partNumber = i;
        this.url = url;
        this.fileOffset = j;
        this.fileContentLength = j2;
        this.uploadId = str3;
        setNeedMD5(false);
    }

    public void setPartNumber(int i) {
        this.partNumber = i;
    }

    public int getPartNumber() {
        return this.partNumber;
    }

    public void setUploadId(String str) {
        this.uploadId = str;
    }

    public String getUploadId() {
        return this.uploadId;
    }

    public long getFileOffset() {
        return this.fileOffset;
    }

    public long getFileContentLength() {
        return this.fileContentLength;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setFileOffset(long j) {
        this.fileOffset = j;
    }

    public void setFileContentLength(long j) {
        this.fileContentLength = j;
    }

    public boolean isLastPart() {
        return this.lastPart;
    }

    public void setLastPart(boolean z) {
        this.lastPart = z;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("partNumber", String.valueOf(this.partNumber));
        this.queryParameters.put("uploadId", this.uploadId);
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        if (this.srcPath != null) {
            if (this.fileOffset != -1) {
                return RequestBodySerializer.file(getContentType(), new File(this.srcPath), this.fileOffset, this.fileContentLength);
            }
            return RequestBodySerializer.file(getContentType(), new File(this.srcPath));
        }
        if (this.data != null) {
            return RequestBodySerializer.bytes(getContentType(), this.data);
        }
        if (this.inputStream != null) {
            return RequestBodySerializer.stream(getContentType(), new File(CosXmlBaseService.appCachePath, String.valueOf(System.currentTimeMillis())), this.inputStream);
        }
        if (this.uri != null && ContextHolder.getAppContext() != null) {
            return RequestBodySerializer.uri(getContentType(), this.uri, ContextHolder.getAppContext(), this.fileOffset, this.fileContentLength);
        }
        if (this.url == null) {
            return null;
        }
        if (this.fileOffset != -1) {
            return RequestBodySerializer.url(getContentType(), this.url, this.fileOffset, this.fileContentLength);
        }
        return RequestBodySerializer.url(getContentType(), this.url);
    }

    @Override // com.tencent.cos.xml.model.object.ObjectRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        Context appContext;
        super.checkParameters();
        if (this.requestURL == null) {
            if (this.partNumber <= 0) {
                throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "partNumber must be >= 1");
            }
            if (this.uploadId == null) {
                throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "uploadID must not be null");
            }
        }
        String str = this.srcPath;
        if (str == null && this.data == null && this.inputStream == null && this.uri == null && this.url == null) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "Data Source must not be null");
        }
        if (str != null && !new File(this.srcPath).exists()) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "upload file does not exist");
        }
        if (this.uri != null && (appContext = ContextHolder.getAppContext()) != null && !QCloudUtils.doesUriFileExist(this.uri, appContext.getContentResolver())) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "upload file does not exist");
        }
    }

    public void setPriorityLow() {
        this.priority = 1;
    }

    public boolean isPriorityLow() {
        return this.priority == 1;
    }

    public void setSrcPath(String str) {
        this.srcPath = str;
    }

    public void setSrcPath(String str, long j, long j2) {
        this.srcPath = str;
        this.fileOffset = j;
        this.fileContentLength = j2;
    }

    public String getSrcPath() {
        return this.srcPath;
    }

    public Uri getUri() {
        return this.uri;
    }

    public void setData(byte[] bArr) {
        this.data = bArr;
    }

    public byte[] getData() {
        return this.data;
    }

    public long getFileLength() {
        if (this.data != null) {
            this.fileContentLength = r0.length;
        } else if (this.srcPath != null && this.fileContentLength == -1) {
            this.fileContentLength = new File(this.srcPath).length();
        }
        return this.fileContentLength;
    }

    public void setProgressListener(CosXmlProgressListener cosXmlProgressListener) {
        this.progressListener = cosXmlProgressListener;
    }

    public CosXmlProgressListener getProgressListener() {
        return this.progressListener;
    }

    @Override // com.tencent.cos.xml.model.object.TransferRequest
    public void setTrafficLimit(long j) {
        addHeader(Headers.COS_TRAFFIC_LIMIT, String.valueOf(j));
    }
}
