package okhttp3;

import java.nio.charset.Charset;
import kotlin.jvm.internal.Intrinsics;
import okio.ByteString;

/* compiled from: Credentials.kt */
/* loaded from: classes3.dex */
public final class Credentials {
    static {
        new Credentials();
    }

    private Credentials() {
    }

    public static final String basic(String username, String password, Charset charset) {
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(password, "password");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return Intrinsics.stringPlus("Basic ", ByteString.Companion.encodeString(username + ':' + password, charset).base64());
    }
}
