package org.xbill.DNS.config;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.StringTokenizer;

/* loaded from: classes4.dex */
public class PropertyResolverConfigProvider extends BaseResolverConfigProvider {
    private int ndots;

    @Override // org.xbill.DNS.config.ResolverConfigProvider
    public void initialize() {
        initialize("dns.server", "dns.search", "dns.ndots");
    }

    protected void initialize(String str, String str2, String str3) {
        reset();
        String property = System.getProperty(str);
        if (property != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(property, ",");
            while (stringTokenizer.hasMoreTokens()) {
                String nextToken = stringTokenizer.nextToken();
                try {
                    URI uri = new URI("dns://" + nextToken);
                    int i = 53;
                    if (uri.getHost() == null) {
                        addNameserver(new InetSocketAddress(nextToken, 53));
                    } else {
                        int port = uri.getPort();
                        if (port != -1) {
                            i = port;
                        }
                        addNameserver(new InetSocketAddress(uri.getHost(), i));
                    }
                } catch (URISyntaxException unused) {
                    this.log.warn("Ignored invalid server {}", nextToken);
                }
            }
        }
        parseSearchPathList(System.getProperty(str2), ",");
        this.ndots = parseNdots(System.getProperty(str3));
    }

    @Override // org.xbill.DNS.config.BaseResolverConfigProvider, org.xbill.DNS.config.ResolverConfigProvider
    public int ndots() {
        return this.ndots;
    }
}
