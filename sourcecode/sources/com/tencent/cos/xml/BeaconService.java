package com.tencent.cos.xml;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.beacon.core.info.BeaconPubParams;
import com.tencent.beacon.event.open.BeaconReport;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.object.ObjectRequest;
import com.tencent.cos.xml.transfer.TransferTaskMetrics;
import com.tencent.qcloud.core.common.QCloudAuthenticationException;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.ConnectionRepository;
import com.tencent.qcloud.core.http.HttpRequest;
import com.tencent.qcloud.core.http.HttpTask;
import com.tencent.qcloud.core.http.HttpTaskMetrics;
import com.tencent.qcloud.core.track.TrackService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.InetAddress;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLHandshakeException;

/* loaded from: classes.dex */
public class BeaconService {
    private static final String APP_KEY = "0AND0VEVB24UBGDU";
    private static final String EVENT_CODE_BASE_SERVICE = "base_service";
    private static final String EVENT_CODE_COPY = "cos_copy";
    private static final String EVENT_CODE_DOWNLOAD = "cos_download";
    private static final String EVENT_CODE_ERROR = "cos_error";
    private static final String EVENT_CODE_UPLOAD = "cos_upload";
    private static final String EVENT_PARAMS_CLIENT = "Client";
    private static final String EVENT_PARAMS_FAILURE = "Failure";
    public static final String EVENT_PARAMS_NODE_GET = "GetObjectRequest";
    public static final String EVENT_PARAMS_NODE_HEAD = "HeadObjectRequest";
    private static final String EVENT_PARAMS_SERVER = "Server";
    private static final String EVENT_PARAMS_SUCCESS = "Success";
    private static final boolean IS_DEBUG = false;
    private static final String TAG = "BeaconProxy";
    private static BeaconService instance;
    private Context applicationContext;
    private String bridge;
    private boolean isCloseBeacon;

    private boolean isReport(CosXmlServiceException cosXmlServiceException) {
        return true;
    }

    private boolean isReport(CosXmlRequest cosXmlRequest) {
        return true;
    }

    private BeaconService(Context context) {
        this.applicationContext = context;
    }

    public static void init(Context context, boolean z, String str) {
        synchronized (BeaconService.class) {
            if (instance == null) {
                BeaconService beaconService = new BeaconService(context);
                instance = beaconService;
                beaconService.isCloseBeacon = z;
                beaconService.bridge = str;
                TrackService.init(context, APP_KEY, false, z);
            }
        }
    }

    public static BeaconService getInstance() {
        return instance;
    }

    public void reportRequestSuccess(CosXmlRequest cosXmlRequest) {
        reportRequestSuccess(parseEventCode(cosXmlRequest), cosXmlRequest, null);
    }

    public CosXmlClientException reportRequestClientException(CosXmlRequest cosXmlRequest, QCloudClientException qCloudClientException) {
        return reportClientException(parseEventCode(cosXmlRequest), cosXmlRequest, qCloudClientException, null);
    }

    public CosXmlServiceException reportRequestServiceException(CosXmlRequest cosXmlRequest, QCloudServiceException qCloudServiceException) {
        return reportServiceException(parseEventCode(cosXmlRequest), cosXmlRequest, qCloudServiceException, null);
    }

    public void reportUploadTaskSuccess(CosXmlRequest cosXmlRequest) {
        reportRequestSuccess(EVENT_CODE_UPLOAD, cosXmlRequest, Collections.singletonMap("name", "UploadTask"));
    }

    public void reportUploadTaskClientException(CosXmlRequest cosXmlRequest, QCloudClientException qCloudClientException) {
        reportClientException(EVENT_CODE_UPLOAD, cosXmlRequest, qCloudClientException, createTransferExtra("UploadTask", cosXmlRequest));
    }

    public void reportUploadTaskServiceException(CosXmlRequest cosXmlRequest, QCloudServiceException qCloudServiceException) {
        reportServiceException(EVENT_CODE_UPLOAD, cosXmlRequest, qCloudServiceException, createTransferExtra("UploadTask", cosXmlRequest));
    }

    public void reportDownloadTaskSuccess(CosXmlRequest cosXmlRequest) {
        reportRequestSuccess(EVENT_CODE_DOWNLOAD, cosXmlRequest, Collections.singletonMap("name", "DownloadTask"));
    }

    public void reportDownloadTaskClientException(CosXmlRequest cosXmlRequest, QCloudClientException qCloudClientException) {
        reportClientException(EVENT_CODE_DOWNLOAD, cosXmlRequest, qCloudClientException, createTransferExtra("DownloadTask", cosXmlRequest));
    }

    public void reportDownloadTaskServiceException(CosXmlRequest cosXmlRequest, QCloudServiceException qCloudServiceException) {
        reportServiceException(EVENT_CODE_DOWNLOAD, cosXmlRequest, qCloudServiceException, createTransferExtra("DownloadTask", cosXmlRequest));
    }

    public void reportCopyTaskSuccess(CosXmlRequest cosXmlRequest) {
        reportRequestSuccess(EVENT_CODE_COPY, cosXmlRequest, Collections.singletonMap("name", "CopyTask"));
    }

    public void reportCopyTaskClientException(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException) {
        reportClientException(EVENT_CODE_COPY, cosXmlRequest, cosXmlClientException, createTransferExtra("CopyTask", cosXmlRequest));
    }

    public void reportCopyTaskServiceException(CosXmlRequest cosXmlRequest, CosXmlServiceException cosXmlServiceException) {
        reportServiceException(EVENT_CODE_COPY, cosXmlRequest, cosXmlServiceException, createTransferExtra("CopyTask", cosXmlRequest));
    }

    private Map<String, String> createTransferExtra(String str, CosXmlRequest cosXmlRequest) {
        HashMap hashMap = new HashMap();
        hashMap.put("name", str);
        hashMap.put("error_node", cosXmlRequest != null ? cosXmlRequest.getClass().getSimpleName() : "null");
        return hashMap;
    }

    public void reportTransferSuccess(CosXmlRequest cosXmlRequest, TransferTaskMetrics transferTaskMetrics, boolean z) {
        if (isReport(cosXmlRequest)) {
            reportTransferTask(cosXmlRequest, transferTaskMetrics, z, true, null);
        }
    }

    public void reportTransferClientException(CosXmlRequest cosXmlRequest, TransferTaskMetrics transferTaskMetrics, CosXmlClientException cosXmlClientException, boolean z) {
        if (isReport(cosXmlClientException) && isReport(cosXmlRequest)) {
            reportTransferTask(cosXmlRequest, transferTaskMetrics, z, false, parseClientExceptionParams(cosXmlClientException));
        }
    }

    public void reportTransferServiceException(CosXmlRequest cosXmlRequest, TransferTaskMetrics transferTaskMetrics, CosXmlServiceException cosXmlServiceException, boolean z) {
        if (isReport(cosXmlServiceException) && isReport(cosXmlRequest)) {
            reportTransferTask(cosXmlRequest, transferTaskMetrics, z, false, parseServiceExceptionParams(cosXmlServiceException));
        }
    }

    private void reportTransferTask(CosXmlRequest cosXmlRequest, TransferTaskMetrics transferTaskMetrics, boolean z, boolean z2, Map<String, String> map) {
        Map<String, String> parseUrlParams = parseUrlParams(cosXmlRequest);
        parseUrlParams.put("name", cosXmlRequest.getClass().getSimpleName());
        parseUrlParams.putAll(getCommonParams());
        parseUrlParams.putAll(parseSimplePerfParams(transferTaskMetrics));
        parseUrlParams.put("encrypted", String.valueOf(z));
        parseUrlParams.put("result", z2 ? EVENT_PARAMS_SUCCESS : EVENT_PARAMS_FAILURE);
        if (map != null) {
            parseUrlParams.putAll(map);
        }
        report("cos_transfer", parseUrlParams);
    }

    private void reportRequestSuccess(String str, CosXmlRequest cosXmlRequest, Map<String, String> map) {
        if (isReport(cosXmlRequest)) {
            HttpTaskMetrics metrics = cosXmlRequest.getMetrics();
            Map<String, String> parseUrlParams = parseUrlParams(cosXmlRequest);
            parseUrlParams.putAll(getCommonParams());
            parseUrlParams.putAll(parseSimplePerfParams(metrics));
            if (map == null || !map.containsKey("name")) {
                parseUrlParams.put("name", cosXmlRequest.getClass().getSimpleName());
            }
            parseUrlParams.put("result", EVENT_PARAMS_SUCCESS);
            if (map != null) {
                parseUrlParams.putAll(map);
            }
            report(str, parseUrlParams);
        }
    }

    private CosXmlClientException reportClientException(String str, CosXmlRequest cosXmlRequest, QCloudClientException qCloudClientException, Map<String, String> map) {
        ReturnClientException clientExceptionParams = getClientExceptionParams(qCloudClientException);
        if (isReport(clientExceptionParams.exception) && isReport(cosXmlRequest)) {
            HttpTaskMetrics metrics = cosXmlRequest.getMetrics();
            Map<String, String> parseUrlParams = parseUrlParams(cosXmlRequest);
            parseUrlParams.putAll(getCommonParams());
            parseUrlParams.putAll(clientExceptionParams.params);
            parseUrlParams.putAll(parsePerfParams(metrics));
            parseUrlParams.putAll(parseDnsParams(cosXmlRequest));
            if (map == null || !map.containsKey("name")) {
                parseUrlParams.put("name", cosXmlRequest.getClass().getSimpleName());
            }
            parseUrlParams.put("result", EVENT_PARAMS_FAILURE);
            if (map != null) {
                parseUrlParams.putAll(map);
            }
            report(str, parseUrlParams);
        }
        return clientExceptionParams.exception;
    }

    private CosXmlServiceException reportServiceException(String str, CosXmlRequest cosXmlRequest, QCloudServiceException qCloudServiceException, Map<String, String> map) {
        ReturnServiceException serviceExceptionParams = getServiceExceptionParams(qCloudServiceException);
        if (cosXmlRequest instanceof ObjectRequest) {
            ((ObjectRequest) cosXmlRequest).getCosPath();
        }
        if (isReport(serviceExceptionParams.exception) && isReport(cosXmlRequest)) {
            Map<String, String> parseUrlParams = parseUrlParams(cosXmlRequest);
            parseUrlParams.putAll(getCommonParams());
            parseUrlParams.putAll(serviceExceptionParams.params);
            parseUrlParams.putAll(parsePerfParams(cosXmlRequest.getMetrics()));
            parseUrlParams.putAll(parseDnsParams(cosXmlRequest));
            if (map == null || !map.containsKey("name")) {
                parseUrlParams.put("name", cosXmlRequest.getClass().getSimpleName());
            }
            parseUrlParams.put("result", EVENT_PARAMS_FAILURE);
            if (map != null) {
                parseUrlParams.putAll(map);
            }
            report(str, parseUrlParams);
        }
        return serviceExceptionParams.exception;
    }

    private Map<String, String> parseDnsParams(CosXmlRequest cosXmlRequest) {
        HashMap hashMap = new HashMap();
        String parseHost = parseHost(cosXmlRequest);
        if (TextUtils.isEmpty(parseHost)) {
            return hashMap;
        }
        HttpTaskMetrics metrics = cosXmlRequest.getMetrics();
        List<InetAddress> list = null;
        try {
            list = ConnectionRepository.getInstance().getDnsRecord(parseHost);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        hashMap.put("ips", flatDns(metrics.getConnectAddress(), list));
        return hashMap;
    }

    private String flatDns(InetAddress inetAddress, List<InetAddress> list) {
        return (inetAddress == null || inetAddress.getHostAddress() == null) ? flatInetAddressList(list) : String.format("{%s}", inetAddress.getHostAddress());
    }

    private String parseHost(CosXmlRequest cosXmlRequest) {
        HttpRequest request;
        HttpTask httpTask = cosXmlRequest.getHttpTask();
        String host = (httpTask == null || (request = httpTask.request()) == null) ? null : request.host();
        return (host != null || cosXmlRequest.getMetrics() == null) ? host : cosXmlRequest.getMetrics().getDomainName();
    }

    private Map<String, String> parsePerfParams(HttpTaskMetrics httpTaskMetrics) {
        HashMap hashMap = new HashMap();
        if (httpTaskMetrics == null) {
            return hashMap;
        }
        hashMap.put("took_time", String.valueOf(httpTaskMetrics.httpTaskFullTime()));
        hashMap.put("http_dns", String.valueOf(httpTaskMetrics.dnsLookupTookTime()));
        hashMap.put("http_connect", String.valueOf(httpTaskMetrics.connectTookTime()));
        hashMap.put("http_secure_connect", String.valueOf(httpTaskMetrics.secureConnectTookTime()));
        hashMap.put("http_md5", String.valueOf(httpTaskMetrics.calculateMD5STookTime()));
        hashMap.put("http_sign", String.valueOf(httpTaskMetrics.signRequestTookTime()));
        hashMap.put("http_read_header", String.valueOf(httpTaskMetrics.readResponseHeaderTookTime()));
        hashMap.put("http_read_body", String.valueOf(httpTaskMetrics.readResponseBodyTookTime()));
        hashMap.put("http_write_header", String.valueOf(httpTaskMetrics.writeRequestHeaderTookTime()));
        hashMap.put("http_write_body", String.valueOf(httpTaskMetrics.writeRequestBodyTookTime()));
        hashMap.put("http_full", String.valueOf(httpTaskMetrics.fullTaskTookTime()));
        hashMap.put("size", String.valueOf(httpTaskMetrics.requestBodyByteCount() + httpTaskMetrics.responseBodyByteCount()));
        hashMap.put("retry_times", String.valueOf(httpTaskMetrics.getRetryCount()));
        return hashMap;
    }

    private Map<String, String> parseSimplePerfParams(HttpTaskMetrics httpTaskMetrics) {
        HashMap hashMap = new HashMap();
        if (httpTaskMetrics == null) {
            return hashMap;
        }
        hashMap.put("took_time", String.valueOf(httpTaskMetrics.httpTaskFullTime()));
        hashMap.put("size", String.valueOf(httpTaskMetrics.requestBodyByteCount() + httpTaskMetrics.responseBodyByteCount()));
        hashMap.put("retry_times", String.valueOf(httpTaskMetrics.getRetryCount()));
        return hashMap;
    }

    private Map<String, String> parseSimplePerfParams(TransferTaskMetrics transferTaskMetrics) {
        HashMap hashMap = new HashMap();
        if (transferTaskMetrics == null) {
            return hashMap;
        }
        hashMap.put("transfer_size", String.valueOf(transferTaskMetrics.getSize()));
        hashMap.put("ip", transferTaskMetrics.getConnectAddress() != null ? transferTaskMetrics.getConnectAddress().getHostAddress() : "");
        hashMap.put("took_time", String.valueOf(transferTaskMetrics.getTookTime()));
        hashMap.put("wait_took_time", String.valueOf(transferTaskMetrics.getWaitTookTime()));
        hashMap.put("first_progress_took_time", String.valueOf(transferTaskMetrics.getFirstProgressTookTime()));
        return hashMap;
    }

    private Map<String, String> parseUrlParams(CosXmlRequest cosXmlRequest) {
        HashMap hashMap = new HashMap();
        String parseHost = parseHost(cosXmlRequest);
        if (TextUtils.isEmpty(parseHost)) {
            return hashMap;
        }
        hashMap.put("host", parseHost);
        try {
            Matcher matcher = Pattern.compile(".*\\.cos\\.(.*)\\.myqcloud.com").matcher(parseHost);
            if (matcher.find()) {
                hashMap.put("region", matcher.group(1));
            }
        } catch (Exception unused) {
        }
        if (cosXmlRequest instanceof ObjectRequest) {
            ObjectRequest objectRequest = (ObjectRequest) cosXmlRequest;
            if (!TextUtils.isEmpty(objectRequest.getCosPath())) {
                hashMap.put("request_path", objectRequest.getCosPath());
            }
        }
        if (cosXmlRequest.getHttpTask() != null && cosXmlRequest.getHttpTask().request() != null) {
            String header = cosXmlRequest.getHttpTask().request().header(Headers.USER_AGENT);
            if (!TextUtils.isEmpty(header)) {
                hashMap.put("user_agent", header);
            }
        }
        return hashMap;
    }

    private String parseEventCode(CosXmlRequest cosXmlRequest) {
        return isUploadTaskRequest(cosXmlRequest) ? EVENT_CODE_UPLOAD : isDownloadTaskRequest(cosXmlRequest) ? EVENT_CODE_DOWNLOAD : EVENT_CODE_BASE_SERVICE;
    }

    private void report(String str, Map<String, String> map) {
        if (this.isCloseBeacon || !TrackService.isIncludeBeacon()) {
            return;
        }
        TrackService.getInstance().track(APP_KEY, str, map);
    }

    public void reportError(String str, Exception exc) {
        Map<String, String> commonParams = getCommonParams();
        commonParams.put("source", str);
        commonParams.put("name", exc.getClass().getSimpleName());
        commonParams.put("message", exc.getMessage());
        report(EVENT_CODE_ERROR, commonParams);
    }

    private String flatInetAddressList(List<InetAddress> list) {
        if (list == null) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        Iterator<InetAddress> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            sb.append(it.next().getHostAddress());
            i++;
            if (i == list.size()) {
                sb.append("}");
            } else {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private Map<String, String> getCommonParams() {
        if (this.isCloseBeacon || !TrackService.isIncludeBeacon()) {
            return new HashMap();
        }
        HashMap hashMap = new HashMap();
        BeaconPubParams commonParams = BeaconReport.getInstance().getCommonParams(this.applicationContext);
        hashMap.put("boundle_id", commonParams.getBoundleId());
        hashMap.put("network_type", commonParams.getNetworkType());
        hashMap.put("cossdk_version", "5.9.13");
        hashMap.put("cossdk_version_code", String.valueOf(50913));
        if (!TextUtils.isEmpty(this.bridge)) {
            hashMap.put("bridge", this.bridge);
        }
        return hashMap;
    }

    private ReturnServiceException getServiceExceptionParams(QCloudServiceException qCloudServiceException) {
        HashMap hashMap = new HashMap();
        CosXmlServiceException convertServerException = convertServerException(qCloudServiceException);
        hashMap.put("error_request_id", convertServerException.getRequestId());
        hashMap.put("error_message", convertServerException.getErrorMessage());
        hashMap.put("error_code", convertServerException.getErrorCode());
        hashMap.put("error_status_code", String.valueOf(convertServerException.getStatusCode()));
        hashMap.put("error_service_name", convertServerException.getServiceName());
        hashMap.put("error_type", "Server");
        return new ReturnServiceException(convertServerException, hashMap);
    }

    private static class ReturnServiceException {
        private final CosXmlServiceException exception;
        private final Map<String, String> params;

        public ReturnServiceException(CosXmlServiceException cosXmlServiceException, Map<String, String> map) {
            this.exception = cosXmlServiceException;
            this.params = map;
        }
    }

    private Map<String, String> parseClientExceptionParams(CosXmlClientException cosXmlClientException) {
        HashMap hashMap = new HashMap();
        hashMap.put("error_message", cosXmlClientException.getMessage());
        hashMap.put("error_code", String.valueOf(cosXmlClientException.errorCode));
        hashMap.put("error_type", EVENT_PARAMS_CLIENT);
        return hashMap;
    }

    private Map<String, String> parseServiceExceptionParams(CosXmlServiceException cosXmlServiceException) {
        HashMap hashMap = new HashMap();
        hashMap.put("error_message", cosXmlServiceException.getErrorMessage());
        hashMap.put("error_code", cosXmlServiceException.getErrorCode());
        hashMap.put("request_id", cosXmlServiceException.getRequestId());
        hashMap.put("error_type", "Server");
        return hashMap;
    }

    private ReturnClientException getClientExceptionParams(QCloudClientException qCloudClientException) {
        HashMap hashMap = new HashMap();
        CosXmlClientException convertClientException = convertClientException(qCloudClientException);
        String simpleName = (convertClientException.getCause() == null ? convertClientException.getClass() : convertClientException.getCause().getClass()).getSimpleName();
        String message = convertClientException.getCause() == null ? convertClientException.getMessage() : convertClientException.getCause().getMessage();
        hashMap.put("error_name", simpleName);
        hashMap.put("error_message", message);
        hashMap.put("error_code", String.valueOf(convertClientException.errorCode));
        hashMap.put("error_type", EVENT_PARAMS_CLIENT);
        return new ReturnClientException(convertClientException, hashMap);
    }

    private static class ReturnClientException {
        private final CosXmlClientException exception;
        private final Map<String, String> params;

        public ReturnClientException(CosXmlClientException cosXmlClientException, Map<String, String> map) {
            this.exception = cosXmlClientException;
            this.params = map;
        }
    }

    private CosXmlServiceException convertServerException(QCloudServiceException qCloudServiceException) {
        return qCloudServiceException instanceof CosXmlServiceException ? (CosXmlServiceException) qCloudServiceException : new CosXmlServiceException(qCloudServiceException);
    }

    private CosXmlClientException convertClientException(QCloudClientException qCloudClientException) {
        if (!TextUtils.isEmpty(qCloudClientException.getMessage()) && qCloudClientException.getMessage().contains("NetworkNotConnected")) {
            return new CosXmlClientException(ClientErrorCode.NETWORK_NOT_CONNECTED.getCode(), qCloudClientException);
        }
        if (qCloudClientException instanceof CosXmlClientException) {
            return qCloudClientException.getCause() instanceof IOException ? new CosXmlClientException(subdivisionIOException(qCloudClientException.getCause()), qCloudClientException) : (CosXmlClientException) qCloudClientException;
        }
        Throwable cause = qCloudClientException.getCause();
        if (cause instanceof IllegalArgumentException) {
            return new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), qCloudClientException);
        }
        if (cause instanceof QCloudAuthenticationException) {
            return new CosXmlClientException(ClientErrorCode.INVALID_CREDENTIALS.getCode(), qCloudClientException);
        }
        if (cause instanceof IOException) {
            return new CosXmlClientException(subdivisionIOException(cause), qCloudClientException);
        }
        return new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), qCloudClientException);
    }

    private int subdivisionIOException(Throwable th) {
        if (th instanceof FileNotFoundException) {
            return ClientErrorCode.SINK_SOURCE_NOT_FOUND.getCode();
        }
        if (th instanceof UnknownHostException) {
            return 200032;
        }
        if (th instanceof SocketTimeoutException) {
            return 200033;
        }
        if (th instanceof ConnectException) {
            return 200034;
        }
        if (th instanceof HttpRetryException) {
            return 200035;
        }
        if (th instanceof NoRouteToHostException) {
            return 200036;
        }
        if (!(th instanceof SSLHandshakeException) || (th.getCause() instanceof CertificateException)) {
            return ClientErrorCode.IO_ERROR.getCode();
        }
        return 200037;
    }

    private boolean isDownloadTaskRequest(CosXmlRequest cosXmlRequest) {
        String simpleName = cosXmlRequest.getClass().getSimpleName();
        return EVENT_PARAMS_NODE_HEAD.equals(simpleName) || EVENT_PARAMS_NODE_GET.equals(simpleName);
    }

    private boolean isUploadTaskRequest(CosXmlRequest cosXmlRequest) {
        String simpleName = cosXmlRequest.getClass().getSimpleName();
        return "PutObjectRequest".equals(simpleName) || "InitMultipartUploadRequest".equals(simpleName) || "ListPartsRequest".equals(simpleName) || "UploadPartRequest".equals(simpleName) || "CompleteMultiUploadRequest".equals(simpleName) || "AbortMultiUploadRequest".equals(simpleName);
    }

    private boolean isReport(CosXmlClientException cosXmlClientException) {
        return !(cosXmlClientException.getMessage() != null && cosXmlClientException.getMessage().toLowerCase(Locale.ROOT).contains("canceled"));
    }

    private static class PoorNetworkCode {
        private static final int ConnectException = 200034;
        private static final int HttpRetryException = 200035;
        private static final int NoRouteToHostException = 200036;
        private static final int SSLHandshakeException = 200037;
        private static final int SocketTimeoutException = 200033;
        private static final int UnknownHostException = 200032;

        private PoorNetworkCode() {
        }
    }
}
