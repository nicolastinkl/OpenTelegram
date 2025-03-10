package org.xbill.DNS.config;

import java.net.InetSocketAddress;
import java.util.List;
import org.xbill.DNS.Name;

/* loaded from: classes4.dex */
public interface ResolverConfigProvider {

    /* renamed from: org.xbill.DNS.config.ResolverConfigProvider$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static boolean $default$isEnabled(ResolverConfigProvider resolverConfigProvider) {
            return true;
        }

        public static int $default$ndots(ResolverConfigProvider resolverConfigProvider) {
            return 1;
        }
    }

    void initialize() throws InitializationException;

    boolean isEnabled();

    int ndots();

    List<Name> searchPaths();

    List<InetSocketAddress> servers();
}
