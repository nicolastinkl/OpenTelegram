package com.tencent.qcloud.core.http;

import com.tencent.cos.xml.crypto.Headers;
import com.tencent.qcloud.core.auth.QCloudSelfSigner;
import com.tencent.qcloud.core.auth.QCloudSignSourceProvider;
import com.tencent.qcloud.core.auth.QCloudSigner;
import com.tencent.qcloud.core.auth.STSCredentialScope;
import com.tencent.qcloud.core.auth.SignerFactory;
import com.tencent.qcloud.core.common.QCloudAuthenticationException;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.http.HttpRequest;
import com.tencent.qcloud.core.util.QCloudStringUtils;
import java.net.URL;

/* loaded from: classes.dex */
public class QCloudHttpRequest<T> extends HttpRequest<T> {
    private final STSCredentialScope[] credentialScope;
    private QCloudSelfSigner selfSigner;
    private final boolean signInUrl;
    private final QCloudSignSourceProvider signProvider;
    private final String signerType;

    public QCloudHttpRequest(Builder<T> builder) {
        super(builder);
        this.signerType = ((Builder) builder).signerType;
        this.signProvider = ((Builder) builder).signProvider;
        this.credentialScope = ((Builder) builder).credentialScope;
        this.signInUrl = ((Builder) builder).signInUrl;
        this.selfSigner = ((Builder) builder).selfSigner;
    }

    public QCloudSignSourceProvider getSignProvider() {
        return this.signProvider;
    }

    public STSCredentialScope[] getCredentialScope() {
        return this.credentialScope;
    }

    public boolean isSignInUrl() {
        return this.signInUrl;
    }

    @Override // com.tencent.qcloud.core.http.HttpRequest
    QCloudSigner getQCloudSigner() throws QCloudClientException {
        if (this.signerType == null || !shouldCalculateAuth()) {
            return null;
        }
        QCloudSigner signer = SignerFactory.getSigner(this.signerType);
        if (signer != null) {
            return signer;
        }
        throw new QCloudClientException(new QCloudAuthenticationException("can't get signer for type : " + this.signerType));
    }

    @Override // com.tencent.qcloud.core.http.HttpRequest
    QCloudSelfSigner getQCloudSelfSigner() throws QCloudClientException {
        return this.selfSigner;
    }

    private boolean shouldCalculateAuth() {
        return QCloudStringUtils.isEmpty(header(Headers.COS_AUTHORIZATION));
    }

    public static class Builder<T> extends HttpRequest.Builder<T> {
        private STSCredentialScope[] credentialScope;
        private QCloudSelfSigner selfSigner;
        private boolean signInUrl;
        private QCloudSignSourceProvider signProvider;
        private String signerType;

        public Builder<T> signer(String str, QCloudSignSourceProvider qCloudSignSourceProvider) {
            this.signerType = str;
            this.signProvider = qCloudSignSourceProvider;
            return this;
        }

        public Builder<T> selfSigner(QCloudSelfSigner qCloudSelfSigner) {
            this.selfSigner = qCloudSelfSigner;
            return this;
        }

        public Builder<T> credentialScope(STSCredentialScope[] sTSCredentialScopeArr) {
            this.credentialScope = sTSCredentialScopeArr;
            return this;
        }

        public Builder<T> signInUrl(boolean z) {
            this.signInUrl = z;
            return this;
        }

        @Override // com.tencent.qcloud.core.http.HttpRequest.Builder
        public Builder<T> url(URL url) {
            return (Builder) super.url(url);
        }

        @Override // com.tencent.qcloud.core.http.HttpRequest.Builder
        public Builder<T> scheme(String str) {
            return (Builder) super.scheme(str);
        }

        @Override // com.tencent.qcloud.core.http.HttpRequest.Builder
        public Builder<T> path(String str) {
            return (Builder) super.path(str);
        }

        @Override // com.tencent.qcloud.core.http.HttpRequest.Builder
        public Builder<T> host(String str) {
            return (Builder) super.host(str);
        }

        @Override // com.tencent.qcloud.core.http.HttpRequest.Builder
        public Builder<T> port(int i) {
            return (Builder) super.port(i);
        }

        @Override // com.tencent.qcloud.core.http.HttpRequest.Builder
        public Builder<T> method(String str) {
            return (Builder) super.method(str);
        }

        @Override // com.tencent.qcloud.core.http.HttpRequest.Builder
        public Builder<T> contentMD5() {
            return (Builder) super.contentMD5();
        }

        @Override // com.tencent.qcloud.core.http.HttpRequest.Builder
        public Builder<T> addHeader(String str, String str2) {
            return (Builder) super.addHeader(str, str2);
        }

        @Override // com.tencent.qcloud.core.http.HttpRequest.Builder
        public Builder<T> userAgent(String str) {
            return (Builder) super.userAgent(str);
        }

        @Override // com.tencent.qcloud.core.http.HttpRequest.Builder
        public Builder<T> body(RequestBodySerializer requestBodySerializer) {
            return (Builder) super.body(requestBodySerializer);
        }

        @Override // com.tencent.qcloud.core.http.HttpRequest.Builder
        public Builder<T> tag(Object obj) {
            return (Builder) super.tag(obj);
        }

        @Override // com.tencent.qcloud.core.http.HttpRequest.Builder
        public Builder<T> converter(ResponseBodyConverter<T> responseBodyConverter) {
            return (Builder) super.converter((ResponseBodyConverter) responseBodyConverter);
        }

        @Override // com.tencent.qcloud.core.http.HttpRequest.Builder
        public QCloudHttpRequest<T> build() {
            prepareBuild();
            return new QCloudHttpRequest<>(this);
        }
    }
}
