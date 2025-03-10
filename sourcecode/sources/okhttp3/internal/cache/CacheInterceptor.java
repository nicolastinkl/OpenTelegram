package okhttp3.internal.cache;

import java.io.IOException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.connection.RealCall;

/* compiled from: CacheInterceptor.kt */
/* loaded from: classes3.dex */
public final class CacheInterceptor implements Interceptor {
    public static final Companion Companion = new Companion(null);
    private final Cache cache;

    public CacheInterceptor(Cache cache) {
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Intrinsics.checkNotNullParameter(chain, "chain");
        Call call = chain.call();
        CacheStrategy compute = new CacheStrategy.Factory(System.currentTimeMillis(), chain.request(), null).compute();
        Request networkRequest = compute.getNetworkRequest();
        Response cacheResponse = compute.getCacheResponse();
        RealCall realCall = call instanceof RealCall ? (RealCall) call : null;
        EventListener eventListener$okhttp = realCall == null ? null : realCall.getEventListener$okhttp();
        if (eventListener$okhttp == null) {
            eventListener$okhttp = EventListener.NONE;
        }
        if (networkRequest == null && cacheResponse == null) {
            Response build = new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).receivedResponseAtMillis(System.currentTimeMillis()).build();
            eventListener$okhttp.satisfactionFailure(call, build);
            return build;
        }
        if (networkRequest == null) {
            Intrinsics.checkNotNull(cacheResponse);
            Response build2 = cacheResponse.newBuilder().cacheResponse(Companion.stripBody(cacheResponse)).build();
            eventListener$okhttp.cacheHit(call, build2);
            return build2;
        }
        if (cacheResponse != null) {
            eventListener$okhttp.cacheConditionalHit(call, cacheResponse);
        }
        Response proceed = chain.proceed(networkRequest);
        if (cacheResponse != null) {
            boolean z = false;
            if (proceed != null && proceed.code() == 304) {
                z = true;
            }
            if (z) {
                Response.Builder newBuilder = cacheResponse.newBuilder();
                Companion companion = Companion;
                newBuilder.headers(companion.combine(cacheResponse.headers(), proceed.headers())).sentRequestAtMillis(proceed.sentRequestAtMillis()).receivedResponseAtMillis(proceed.receivedResponseAtMillis()).cacheResponse(companion.stripBody(cacheResponse)).networkResponse(companion.stripBody(proceed)).build();
                ResponseBody body = proceed.body();
                Intrinsics.checkNotNull(body);
                body.close();
                Intrinsics.checkNotNull(this.cache);
                throw null;
            }
            ResponseBody body2 = cacheResponse.body();
            if (body2 != null) {
                Util.closeQuietly(body2);
            }
        }
        Intrinsics.checkNotNull(proceed);
        Response.Builder newBuilder2 = proceed.newBuilder();
        Companion companion2 = Companion;
        return newBuilder2.cacheResponse(companion2.stripBody(cacheResponse)).networkResponse(companion2.stripBody(proceed)).build();
    }

    /* compiled from: CacheInterceptor.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Response stripBody(Response response) {
            return (response == null ? null : response.body()) != null ? response.newBuilder().body(null).build() : response;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Headers combine(Headers headers, Headers headers2) {
            boolean equals;
            Headers.Builder builder = new Headers.Builder();
            int size = headers.size();
            int i = 0;
            int i2 = 0;
            while (i2 < size) {
                int i3 = i2 + 1;
                String name = headers.name(i2);
                String value = headers.value(i2);
                equals = StringsKt__StringsJVMKt.equals("Warning", name, true);
                if ((!equals || !StringsKt__StringsJVMKt.startsWith$default(value, "1", false, 2, null)) && (isContentSpecificHeader(name) || !isEndToEnd(name) || headers2.get(name) == null)) {
                    builder.addLenient$okhttp(name, value);
                }
                i2 = i3;
            }
            int size2 = headers2.size();
            while (i < size2) {
                int i4 = i + 1;
                String name2 = headers2.name(i);
                if (!isContentSpecificHeader(name2) && isEndToEnd(name2)) {
                    builder.addLenient$okhttp(name2, headers2.value(i));
                }
                i = i4;
            }
            return builder.build();
        }

        private final boolean isEndToEnd(String str) {
            boolean equals;
            boolean equals2;
            boolean equals3;
            boolean equals4;
            boolean equals5;
            boolean equals6;
            boolean equals7;
            boolean equals8;
            equals = StringsKt__StringsJVMKt.equals("Connection", str, true);
            if (!equals) {
                equals2 = StringsKt__StringsJVMKt.equals("Keep-Alive", str, true);
                if (!equals2) {
                    equals3 = StringsKt__StringsJVMKt.equals("Proxy-Authenticate", str, true);
                    if (!equals3) {
                        equals4 = StringsKt__StringsJVMKt.equals("Proxy-Authorization", str, true);
                        if (!equals4) {
                            equals5 = StringsKt__StringsJVMKt.equals("TE", str, true);
                            if (!equals5) {
                                equals6 = StringsKt__StringsJVMKt.equals("Trailers", str, true);
                                if (!equals6) {
                                    equals7 = StringsKt__StringsJVMKt.equals("Transfer-Encoding", str, true);
                                    if (!equals7) {
                                        equals8 = StringsKt__StringsJVMKt.equals("Upgrade", str, true);
                                        if (!equals8) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }

        private final boolean isContentSpecificHeader(String str) {
            boolean equals;
            boolean equals2;
            boolean equals3;
            equals = StringsKt__StringsJVMKt.equals(com.tencent.cos.xml.crypto.Headers.CONTENT_LENGTH, str, true);
            if (equals) {
                return true;
            }
            equals2 = StringsKt__StringsJVMKt.equals("Content-Encoding", str, true);
            if (equals2) {
                return true;
            }
            equals3 = StringsKt__StringsJVMKt.equals(com.tencent.cos.xml.crypto.Headers.CONTENT_TYPE, str, true);
            return equals3;
        }
    }
}
