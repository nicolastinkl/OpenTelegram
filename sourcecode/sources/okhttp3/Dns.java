package okhttp3;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Dns.kt */
/* loaded from: classes3.dex */
public interface Dns {
    public static final Dns SYSTEM;

    List<InetAddress> lookup(String str) throws UnknownHostException;

    /* compiled from: Dns.kt */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        /* compiled from: Dns.kt */
        private static final class DnsSystem implements Dns {
            @Override // okhttp3.Dns
            public List<InetAddress> lookup(String hostname) {
                List<InetAddress> list;
                Intrinsics.checkNotNullParameter(hostname, "hostname");
                try {
                    InetAddress[] allByName = InetAddress.getAllByName(hostname);
                    Intrinsics.checkNotNullExpressionValue(allByName, "getAllByName(hostname)");
                    list = ArraysKt___ArraysKt.toList(allByName);
                    return list;
                } catch (NullPointerException e) {
                    UnknownHostException unknownHostException = new UnknownHostException(Intrinsics.stringPlus("Broken system behaviour for dns lookup of ", hostname));
                    unknownHostException.initCause(e);
                    throw unknownHostException;
                }
            }
        }
    }

    static {
        Companion companion = Companion.$$INSTANCE;
        SYSTEM = new Companion.DnsSystem();
    }
}
