package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudServiceException;
import java.lang.reflect.Field;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/* loaded from: classes.dex */
public class OkHttpProxy<T> extends NetworkProxy<T> {
    private Field eventListenerFiled;
    private Call httpCall;
    private OkHttpClient okHttpClient;

    public OkHttpProxy(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override // com.tencent.qcloud.core.http.NetworkProxy
    public void cancel() {
        Call call = this.httpCall;
        if (call != null) {
            call.cancel();
        }
    }

    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0099: MOVE (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:42:0x0099 */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004e A[Catch: IOException -> 0x0043, all -> 0x0098, TRY_LEAVE, TryCatch #1 {IOException -> 0x0043, blocks: (B:21:0x003d, B:9:0x0047, B:18:0x004e), top: B:20:0x003d }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0047 A[Catch: IOException -> 0x0043, all -> 0x0098, TryCatch #1 {IOException -> 0x0043, blocks: (B:21:0x003d, B:9:0x0047, B:18:0x004e), top: B:20:0x003d }] */
    @Override // com.tencent.qcloud.core.http.NetworkProxy
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected com.tencent.qcloud.core.http.HttpResult<T> executeHttpRequest(com.tencent.qcloud.core.http.HttpRequest<T> r6) throws com.tencent.qcloud.core.common.QCloudClientException, com.tencent.qcloud.core.common.QCloudServiceException {
        /*
            r5 = this;
            r6.getResponseBodyConverter()
            r0 = 0
            java.lang.String r1 = r5.identifier     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            r6.setOkHttpRequestTag(r1)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            okhttp3.Request r1 = r6.buildRealRequest()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            okhttp3.OkHttpClient r2 = r5.okHttpClient     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            okhttp3.Call r1 = r2.newCall(r1)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            r5.httpCall = r1     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            java.lang.reflect.Field r2 = r5.eventListenerFiled     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            if (r2 != 0) goto L34
            java.lang.Class r1 = r1.getClass()     // Catch: java.lang.Throwable -> L34 java.lang.Throwable -> L5c java.io.IOException -> L5e
            java.lang.String r2 = "eventListener"
            java.lang.reflect.Field r1 = r1.getDeclaredField(r2)     // Catch: java.lang.Throwable -> L34 java.lang.Throwable -> L5c java.io.IOException -> L5e
            r5.eventListenerFiled = r1     // Catch: java.lang.Throwable -> L34 java.lang.Throwable -> L5c java.io.IOException -> L5e
            r2 = 1
            r1.setAccessible(r2)     // Catch: java.lang.Throwable -> L34 java.lang.Throwable -> L5c java.io.IOException -> L5e
            java.lang.reflect.Field r1 = r5.eventListenerFiled     // Catch: java.lang.Throwable -> L34 java.lang.Throwable -> L5c java.io.IOException -> L5e
            okhttp3.Call r2 = r5.httpCall     // Catch: java.lang.Throwable -> L34 java.lang.Throwable -> L5c java.io.IOException -> L5e
            java.lang.Object r1 = r1.get(r2)     // Catch: java.lang.Throwable -> L34 java.lang.Throwable -> L5c java.io.IOException -> L5e
            com.tencent.qcloud.core.http.CallMetricsListener r1 = (com.tencent.qcloud.core.http.CallMetricsListener) r1     // Catch: java.lang.Throwable -> L34 java.lang.Throwable -> L5c java.io.IOException -> L5e
            goto L35
        L34:
            r1 = r0
        L35:
            okhttp3.Call r2 = r5.httpCall     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            okhttp3.Response r2 = r2.execute()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L5e
            if (r1 == 0) goto L45
            com.tencent.qcloud.core.http.HttpTaskMetrics r3 = r5.metrics     // Catch: java.io.IOException -> L43 java.lang.Throwable -> L98
            r1.dumpMetrics(r3)     // Catch: java.io.IOException -> L43 java.lang.Throwable -> L98
            goto L45
        L43:
            r6 = move-exception
            goto L60
        L45:
            if (r2 == 0) goto L4e
            com.tencent.qcloud.core.http.HttpResult r6 = r5.convertResponse(r6, r2)     // Catch: java.io.IOException -> L43 java.lang.Throwable -> L98
            r1 = r6
            r6 = r0
            goto L56
        L4e:
            com.tencent.qcloud.core.common.QCloudServiceException r6 = new com.tencent.qcloud.core.common.QCloudServiceException     // Catch: java.io.IOException -> L43 java.lang.Throwable -> L98
            java.lang.String r1 = "http response is null"
            r6.<init>(r1)     // Catch: java.io.IOException -> L43 java.lang.Throwable -> L98
            r1 = r0
        L56:
            if (r2 == 0) goto L91
            okhttp3.internal.Util.closeQuietly(r2)
            goto L91
        L5c:
            r6 = move-exception
            goto L9a
        L5e:
            r6 = move-exception
            r2 = r0
        L60:
            java.lang.Throwable r1 = r6.getCause()     // Catch: java.lang.Throwable -> L98
            boolean r1 = r1 instanceof com.tencent.qcloud.core.common.QCloudClientException     // Catch: java.lang.Throwable -> L98
            if (r1 == 0) goto L70
            java.lang.Throwable r6 = r6.getCause()     // Catch: java.lang.Throwable -> L98
            com.tencent.qcloud.core.common.QCloudClientException r6 = (com.tencent.qcloud.core.common.QCloudClientException) r6     // Catch: java.lang.Throwable -> L98
        L6e:
            r1 = r0
            goto L88
        L70:
            java.lang.Throwable r1 = r6.getCause()     // Catch: java.lang.Throwable -> L98
            boolean r1 = r1 instanceof com.tencent.qcloud.core.common.QCloudServiceException     // Catch: java.lang.Throwable -> L98
            if (r1 == 0) goto L81
            java.lang.Throwable r6 = r6.getCause()     // Catch: java.lang.Throwable -> L98
            com.tencent.qcloud.core.common.QCloudServiceException r6 = (com.tencent.qcloud.core.common.QCloudServiceException) r6     // Catch: java.lang.Throwable -> L98
            r1 = r6
            r6 = r0
            goto L88
        L81:
            com.tencent.qcloud.core.common.QCloudClientException r1 = new com.tencent.qcloud.core.common.QCloudClientException     // Catch: java.lang.Throwable -> L98
            r1.<init>(r6)     // Catch: java.lang.Throwable -> L98
            r6 = r1
            goto L6e
        L88:
            if (r2 == 0) goto L8d
            okhttp3.internal.Util.closeQuietly(r2)
        L8d:
            r4 = r0
            r0 = r6
            r6 = r1
            r1 = r4
        L91:
            if (r0 != 0) goto L97
            if (r6 != 0) goto L96
            return r1
        L96:
            throw r6
        L97:
            throw r0
        L98:
            r6 = move-exception
            r0 = r2
        L9a:
            if (r0 == 0) goto L9f
            okhttp3.internal.Util.closeQuietly(r0)
        L9f:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.core.http.OkHttpProxy.executeHttpRequest(com.tencent.qcloud.core.http.HttpRequest):com.tencent.qcloud.core.http.HttpResult");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.tencent.qcloud.core.http.NetworkProxy
    protected HttpResult<T> convertResponse(HttpRequest<T> httpRequest, Response response) throws QCloudClientException, QCloudServiceException {
        HttpResponse httpResponse = new HttpResponse(httpRequest, response);
        ResponseBodyConverter<T> responseBodyConverter = httpRequest.getResponseBodyConverter();
        if (responseBodyConverter instanceof ProgressBody) {
            ((ProgressBody) responseBodyConverter).setProgressListener(this.mProgressListener);
        }
        return new HttpResult<>(httpResponse, responseBodyConverter.convert(httpResponse));
    }
}
