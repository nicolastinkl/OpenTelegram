package okhttp3.internal.http1;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Headers;
import okio.BufferedSource;

/* compiled from: HeadersReader.kt */
/* loaded from: classes3.dex */
public final class HeadersReader {
    private long headerLimit;
    private final BufferedSource source;

    static {
        new Companion(null);
    }

    public HeadersReader(BufferedSource source) {
        Intrinsics.checkNotNullParameter(source, "source");
        this.source = source;
        this.headerLimit = 262144L;
    }

    public final String readLine() {
        String readUtf8LineStrict = this.source.readUtf8LineStrict(this.headerLimit);
        this.headerLimit -= readUtf8LineStrict.length();
        return readUtf8LineStrict;
    }

    public final Headers readHeaders() {
        Headers.Builder builder = new Headers.Builder();
        while (true) {
            String readLine = readLine();
            if (!(readLine.length() == 0)) {
                builder.addLenient$okhttp(readLine);
            } else {
                return builder.build();
            }
        }
    }

    /* compiled from: HeadersReader.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
