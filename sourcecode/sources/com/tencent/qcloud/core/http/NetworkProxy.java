package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudProgressListener;
import com.tencent.qcloud.core.common.QCloudServiceException;
import okhttp3.Response;

/* loaded from: classes.dex */
public abstract class NetworkProxy<T> {
    protected String identifier;
    protected QCloudProgressListener mProgressListener;
    protected HttpTaskMetrics metrics;

    protected abstract void cancel();

    protected abstract HttpResult<T> convertResponse(HttpRequest<T> httpRequest, Response response) throws QCloudClientException, QCloudServiceException;

    protected abstract HttpResult<T> executeHttpRequest(HttpRequest<T> httpRequest) throws QCloudClientException, QCloudServiceException;
}
