package org.xbill.DNS.config;

/* loaded from: classes4.dex */
public class FallbackPropertyResolverConfigProvider extends PropertyResolverConfigProvider {
    @Override // org.xbill.DNS.config.PropertyResolverConfigProvider, org.xbill.DNS.config.ResolverConfigProvider
    public void initialize() {
        initialize("dns.fallback.server", "dns.fallback.search", "dns.fallback.ndots");
    }
}
