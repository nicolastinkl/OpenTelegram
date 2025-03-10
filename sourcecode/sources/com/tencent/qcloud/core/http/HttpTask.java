package com.tencent.qcloud.core.http;

import bolts.CancellationTokenSource;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.QCloudCredentials;
import com.tencent.qcloud.core.auth.QCloudSigner;
import com.tencent.qcloud.core.auth.ScopeLimitCredentialProvider;
import com.tencent.qcloud.core.common.QCloudAuthenticationException;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudDigistListener;
import com.tencent.qcloud.core.common.QCloudProgressListener;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.task.QCloudTask;
import com.tencent.qcloud.core.task.TaskExecutors;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/* loaded from: classes.dex */
public final class HttpTask<T> extends QCloudTask<HttpResult<T>> {
    private static AtomicInteger increments = new AtomicInteger(1);
    protected final QCloudCredentialProvider credentialProvider;
    protected final HttpRequest<T> httpRequest;
    protected HttpResult<T> httpResult;
    private QCloudProgressListener mProgressListener;
    protected HttpTaskMetrics metrics;
    private NetworkProxy<T> networkProxy;

    HttpTask(HttpRequest<T> httpRequest, QCloudCredentialProvider qCloudCredentialProvider, NetworkClient networkClient) {
        super("HttpTask-" + httpRequest.tag() + "-" + increments.getAndIncrement(), httpRequest.tag());
        this.mProgressListener = new QCloudProgressListener() { // from class: com.tencent.qcloud.core.http.HttpTask.1
            @Override // com.tencent.qcloud.core.common.QCloudProgressListener
            public void onProgress(long j, long j2) {
                HttpTask.this.onProgress(j, j2);
            }
        };
        this.httpRequest = httpRequest;
        this.credentialProvider = qCloudCredentialProvider;
        NetworkProxy<T> networkProxy = networkClient.getNetworkProxy();
        this.networkProxy = networkProxy;
        networkProxy.identifier = getIdentifier();
        this.networkProxy.mProgressListener = this.mProgressListener;
    }

    public HttpTask<T> scheduleOn(Executor executor) {
        scheduleOn(executor, 2);
        return this;
    }

    public HttpTask<T> scheduleOn(Executor executor, int i) {
        scheduleOn(executor, new CancellationTokenSource(), i);
        return this;
    }

    public HttpTask<T> schedule() {
        schedule(2);
        return this;
    }

    public HttpTask<T> schedule(int i) {
        if (this.httpRequest.getRequestBody() instanceof ProgressBody) {
            scheduleOn(TaskExecutors.UPLOAD_EXECUTOR, i);
        } else if (this.httpRequest.getResponseBodyConverter() instanceof ProgressBody) {
            scheduleOn(TaskExecutors.DOWNLOAD_EXECUTOR, i);
        } else {
            scheduleOn(TaskExecutors.COMMAND_EXECUTOR, i);
        }
        return this;
    }

    @Override // com.tencent.qcloud.core.task.QCloudTask
    public HttpResult<T> getResult() {
        return this.httpResult;
    }

    public HttpTask<T> attachMetric(HttpTaskMetrics httpTaskMetrics) {
        this.metrics = httpTaskMetrics;
        return this;
    }

    public HttpTaskMetrics metrics() {
        return this.metrics;
    }

    public boolean isUploadTask() {
        if (this.httpRequest.getRequestBody() instanceof StreamingRequestBody) {
            return ((StreamingRequestBody) this.httpRequest.getRequestBody()).isLargeData();
        }
        return false;
    }

    public boolean isDownloadTask() {
        return this.httpRequest.getResponseBodyConverter() instanceof ProgressBody;
    }

    public boolean isResponseFilePathConverter() {
        return (this.httpRequest.getResponseBodyConverter() instanceof ResponseFileConverter) && ((ResponseFileConverter) this.httpRequest.getResponseBodyConverter()).isFilePathConverter();
    }

    public HttpRequest<T> request() {
        return this.httpRequest;
    }

    public long getTransferBodySize() {
        ProgressBody progressBody;
        if (this.httpRequest.getRequestBody() instanceof ProgressBody) {
            progressBody = (ProgressBody) this.httpRequest.getRequestBody();
        } else {
            progressBody = this.httpRequest.getResponseBodyConverter() instanceof ProgressBody ? (ProgressBody) this.httpRequest.getResponseBodyConverter() : null;
        }
        if (progressBody != null) {
            return progressBody.getBytesTransferred();
        }
        return 0L;
    }

    @Override // com.tencent.qcloud.core.task.QCloudTask
    public void cancel() {
        this.networkProxy.cancel();
        super.cancel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00db, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0138, code lost:
    
        if ((r3.httpRequest.getRequestBody() instanceof com.tencent.qcloud.core.http.StreamingRequestBody) != false) goto L33;
     */
    @Override // com.tencent.qcloud.core.task.QCloudTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.tencent.qcloud.core.http.HttpResult<T> execute() throws com.tencent.qcloud.core.common.QCloudClientException, com.tencent.qcloud.core.common.QCloudServiceException {
        /*
            Method dump skipped, instructions count: 371
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.core.http.HttpTask.execute():com.tencent.qcloud.core.http.HttpResult");
    }

    private boolean isClockSkewedError(QCloudServiceException qCloudServiceException) {
        return QCloudServiceException.ERR0R_REQUEST_IS_EXPIRED.equals(qCloudServiceException.getErrorCode()) || QCloudServiceException.ERR0R_REQUEST_TIME_TOO_SKEWED.equals(qCloudServiceException.getErrorCode());
    }

    private void signRequest(QCloudSigner qCloudSigner, QCloudHttpRequest qCloudHttpRequest) throws QCloudClientException {
        QCloudCredentials credentials;
        QCloudCredentialProvider qCloudCredentialProvider = this.credentialProvider;
        if (qCloudCredentialProvider == null) {
            throw new QCloudClientException(new QCloudAuthenticationException("no credentials provider"));
        }
        if (qCloudCredentialProvider instanceof ScopeLimitCredentialProvider) {
            credentials = ((ScopeLimitCredentialProvider) qCloudCredentialProvider).getCredentials(qCloudHttpRequest.getCredentialScope());
        } else {
            credentials = qCloudCredentialProvider.getCredentials();
        }
        qCloudSigner.sign(qCloudHttpRequest, credentials);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void calculateContentMD5() throws QCloudClientException {
        RequestBody requestBody = this.httpRequest.getRequestBody();
        if (requestBody == 0) {
            throw new QCloudClientException(new IllegalArgumentException("get md5 canceled, request body is null."));
        }
        if (requestBody instanceof QCloudDigistListener) {
            try {
                if (this.httpRequest.getRequestBody() instanceof MultipartStreamRequestBody) {
                    ((MultipartStreamRequestBody) this.httpRequest.getRequestBody()).addMd5();
                } else {
                    this.httpRequest.addHeader(Headers.CONTENT_MD5, ((QCloudDigistListener) requestBody).onGetMd5());
                }
                return;
            } catch (IOException e) {
                throw new QCloudClientException("calculate md5 error: " + e.getMessage(), e);
            }
        }
        Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
            this.httpRequest.addHeader(Headers.CONTENT_MD5, buffer.md5().base64());
            buffer.close();
        } catch (IOException e2) {
            throw new QCloudClientException("calculate md5 error" + e2.getMessage(), e2);
        }
    }

    public void convertResponse(Response response) throws QCloudClientException, QCloudServiceException {
        this.httpResult = this.networkProxy.convertResponse(this.httpRequest, response);
    }
}
