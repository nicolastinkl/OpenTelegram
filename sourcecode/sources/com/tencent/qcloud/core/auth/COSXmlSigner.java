package com.tencent.qcloud.core.auth;

import com.tencent.cos.xml.crypto.Headers;
import com.tencent.qcloud.core.common.QCloudAuthenticationException;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.http.QCloudHttpRequest;
import java.net.URL;
import java.util.Locale;

/* loaded from: classes.dex */
public class COSXmlSigner implements QCloudSigner {
    protected String getSessionTokenKey() {
        return Headers.SECURITY_TOKEN;
    }

    @Override // com.tencent.qcloud.core.auth.QCloudSigner
    public void sign(QCloudHttpRequest qCloudHttpRequest, QCloudCredentials qCloudCredentials) throws QCloudClientException {
        if (qCloudCredentials == null) {
            throw new QCloudClientException(new QCloudAuthenticationException("Credentials is null."));
        }
        COSXmlSignSourceProvider cOSXmlSignSourceProvider = (COSXmlSignSourceProvider) qCloudHttpRequest.getSignProvider();
        if (cOSXmlSignSourceProvider == null) {
            throw new QCloudClientException(new QCloudAuthenticationException("No sign provider for cos xml signer."));
        }
        StringBuilder sb = new StringBuilder();
        QCloudLifecycleCredentials qCloudLifecycleCredentials = (QCloudLifecycleCredentials) qCloudCredentials;
        String keyTime = qCloudHttpRequest.getKeyTime();
        if (keyTime == null) {
            keyTime = qCloudLifecycleCredentials.getKeyTime();
        }
        cOSXmlSignSourceProvider.setSignTime(keyTime);
        String signature = signature(cOSXmlSignSourceProvider.source(qCloudHttpRequest), qCloudLifecycleCredentials.getSignKey());
        sb.append("q-sign-algorithm");
        sb.append("=");
        sb.append("sha1");
        sb.append("&");
        sb.append("q-ak");
        sb.append("=");
        sb.append(qCloudCredentials.getSecretId());
        sb.append("&");
        sb.append("q-sign-time");
        sb.append("=");
        sb.append(keyTime);
        sb.append("&");
        sb.append("q-key-time");
        sb.append("=");
        sb.append(qCloudLifecycleCredentials.getKeyTime());
        sb.append("&");
        sb.append("q-header-list");
        sb.append("=");
        String realHeaderList = cOSXmlSignSourceProvider.getRealHeaderList();
        Locale locale = Locale.ROOT;
        sb.append(realHeaderList.toLowerCase(locale));
        sb.append("&");
        sb.append("q-url-param-list");
        sb.append("=");
        sb.append(cOSXmlSignSourceProvider.getRealParameterList().toLowerCase(locale));
        sb.append("&");
        sb.append("q-signature");
        sb.append("=");
        sb.append(signature);
        String sb2 = sb.toString();
        if (qCloudHttpRequest.isSignInUrl()) {
            addAuthInPara(qCloudHttpRequest, qCloudCredentials, sb2);
        } else {
            addAuthInHeader(qCloudHttpRequest, qCloudCredentials, sb2);
        }
        cOSXmlSignSourceProvider.onSignRequestSuccess(qCloudHttpRequest, qCloudCredentials, sb2);
    }

    private void addAuthInPara(QCloudHttpRequest qCloudHttpRequest, QCloudCredentials qCloudCredentials, String str) {
        String concat;
        URL url = qCloudHttpRequest.url();
        if (qCloudCredentials instanceof SessionQCloudCredentials) {
            str = str.concat("&token").concat("=").concat(((SessionQCloudCredentials) qCloudCredentials).getToken());
        }
        String query = url.getQuery();
        String url2 = url.toString();
        int indexOf = url2.indexOf(63);
        if (indexOf < 0) {
            concat = url2.concat("?").concat(str);
        } else {
            int length = indexOf + query.length() + 1;
            concat = url2.substring(0, length).concat("&").concat(str).concat(url2.substring(length));
        }
        qCloudHttpRequest.setUrl(concat);
    }

    private void addAuthInHeader(QCloudHttpRequest qCloudHttpRequest, QCloudCredentials qCloudCredentials, String str) {
        qCloudHttpRequest.removeHeader(Headers.COS_AUTHORIZATION);
        qCloudHttpRequest.addHeader(Headers.COS_AUTHORIZATION, str);
        if (qCloudCredentials instanceof SessionQCloudCredentials) {
            String sessionTokenKey = getSessionTokenKey();
            qCloudHttpRequest.removeHeader(sessionTokenKey);
            qCloudHttpRequest.addHeader(sessionTokenKey, ((SessionQCloudCredentials) qCloudCredentials).getToken());
        }
    }

    private String signature(String str, String str2) {
        byte[] hmacSha1 = Utils.hmacSha1(str, str2);
        return hmacSha1 != null ? new String(Utils.encodeHex(hmacSha1)) : "";
    }
}
