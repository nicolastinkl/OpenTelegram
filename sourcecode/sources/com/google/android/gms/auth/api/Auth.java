package com.google.android.gms.auth.api;

import android.os.Bundle;
import com.google.android.gms.auth.api.proxy.ProxyApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.zbd;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.p000authapi.zbl;

/* compiled from: com.google.android.gms:play-services-auth@@20.4.0 */
/* loaded from: classes.dex */
public final class Auth {
    public static final Api<GoogleSignInOptions> GOOGLE_SIGN_IN_API;
    public static final Api.ClientKey zba;
    public static final Api.ClientKey zbb;
    private static final Api.AbstractClientBuilder zbc;
    private static final Api.AbstractClientBuilder zbd;

    /* compiled from: com.google.android.gms:play-services-auth@@20.4.0 */
    @Deprecated
    public static class AuthCredentialsOptions implements Api.ApiOptions {
        public static final AuthCredentialsOptions zba = new AuthCredentialsOptions(new Builder());
        private final String zbb = null;
        private final boolean zbc;
        private final String zbd;

        /* compiled from: com.google.android.gms:play-services-auth@@20.4.0 */
        @Deprecated
        public static class Builder {
            protected Boolean zba;
            protected String zbb;

            public Builder() {
                this.zba = Boolean.FALSE;
            }

            public final Builder zba(String str) {
                this.zbb = str;
                return this;
            }

            public Builder(AuthCredentialsOptions authCredentialsOptions) {
                this.zba = Boolean.FALSE;
                AuthCredentialsOptions.zbb(authCredentialsOptions);
                this.zba = Boolean.valueOf(authCredentialsOptions.zbc);
                this.zbb = authCredentialsOptions.zbd;
            }
        }

        public AuthCredentialsOptions(Builder builder) {
            this.zbc = builder.zba.booleanValue();
            this.zbd = builder.zbb;
        }

        static /* bridge */ /* synthetic */ String zbb(AuthCredentialsOptions authCredentialsOptions) {
            String str = authCredentialsOptions.zbb;
            return null;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AuthCredentialsOptions)) {
                return false;
            }
            AuthCredentialsOptions authCredentialsOptions = (AuthCredentialsOptions) obj;
            String str = authCredentialsOptions.zbb;
            return Objects.equal(null, null) && this.zbc == authCredentialsOptions.zbc && Objects.equal(this.zbd, authCredentialsOptions.zbd);
        }

        public int hashCode() {
            return Objects.hashCode(null, Boolean.valueOf(this.zbc), this.zbd);
        }

        public final Bundle zba() {
            Bundle bundle = new Bundle();
            bundle.putString("consumer_package", null);
            bundle.putBoolean("force_save_dialog", this.zbc);
            bundle.putString("log_session_id", this.zbd);
            return bundle;
        }
    }

    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zba = clientKey;
        Api.ClientKey clientKey2 = new Api.ClientKey();
        zbb = clientKey2;
        zba zbaVar = new zba();
        zbc = zbaVar;
        zbb zbbVar = new zbb();
        zbd = zbbVar;
        Api<AuthProxyOptions> api = AuthProxy.API;
        new Api("Auth.CREDENTIALS_API", zbaVar, clientKey);
        GOOGLE_SIGN_IN_API = new Api<>("Auth.GOOGLE_SIGN_IN_API", zbbVar, clientKey2);
        ProxyApi proxyApi = AuthProxy.ProxyApi;
        new zbl();
        new zbd();
    }
}
