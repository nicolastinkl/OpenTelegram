package okhttp3;

import java.io.IOException;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.authenticator.JavaNetAuthenticator;

/* compiled from: Authenticator.kt */
/* loaded from: classes3.dex */
public interface Authenticator {
    public static final Authenticator NONE;

    Request authenticate(Route route, Response response) throws IOException;

    /* compiled from: Authenticator.kt */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        /* compiled from: Authenticator.kt */
        private static final class AuthenticatorNone implements Authenticator {
            @Override // okhttp3.Authenticator
            public Request authenticate(Route route, Response response) {
                Intrinsics.checkNotNullParameter(response, "response");
                return null;
            }
        }

        private Companion() {
        }
    }

    static {
        Companion companion = Companion.$$INSTANCE;
        NONE = new Companion.AuthenticatorNone();
        new JavaNetAuthenticator(null, 1, null);
    }
}
