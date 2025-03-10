package okhttp3;

import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CookieJar.kt */
/* loaded from: classes3.dex */
public interface CookieJar {
    public static final CookieJar NO_COOKIES;

    List<Cookie> loadForRequest(HttpUrl httpUrl);

    void saveFromResponse(HttpUrl httpUrl, List<Cookie> list);

    /* compiled from: CookieJar.kt */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        /* compiled from: CookieJar.kt */
        private static final class NoCookies implements CookieJar {
            @Override // okhttp3.CookieJar
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                Intrinsics.checkNotNullParameter(url, "url");
                Intrinsics.checkNotNullParameter(cookies, "cookies");
            }

            @Override // okhttp3.CookieJar
            public List<Cookie> loadForRequest(HttpUrl url) {
                Intrinsics.checkNotNullParameter(url, "url");
                return CollectionsKt__CollectionsKt.emptyList();
            }
        }
    }

    static {
        Companion companion = Companion.$$INSTANCE;
        NO_COOKIES = new Companion.NoCookies();
    }
}
