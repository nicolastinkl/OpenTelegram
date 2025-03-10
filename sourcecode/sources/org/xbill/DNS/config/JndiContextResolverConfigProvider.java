package org.xbill.DNS.config;

import java.net.InetSocketAddress;
import java.util.List;
import javax.naming.directory.DirContext;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.Name;
import org.xbill.DNS.config.ResolverConfigProvider;

/* loaded from: classes4.dex */
public class JndiContextResolverConfigProvider implements ResolverConfigProvider {

    @Generated
    private static final Logger log = LoggerFactory.getLogger((Class<?>) JndiContextResolverConfigProvider.class);
    private InnerJndiContextResolverConfigProvider inner;

    @Override // org.xbill.DNS.config.ResolverConfigProvider
    public /* synthetic */ int ndots() {
        return ResolverConfigProvider.CC.$default$ndots(this);
    }

    public JndiContextResolverConfigProvider() {
        if (System.getProperty("java.vendor").contains("Android")) {
            return;
        }
        try {
            this.inner = new InnerJndiContextResolverConfigProvider();
        } catch (NoClassDefFoundError unused) {
            log.debug("JNDI DNS not available");
        }
    }

    private static final class InnerJndiContextResolverConfigProvider extends BaseResolverConfigProvider {

        @Generated
        private static final Logger log;

        static {
            Logger logger = LoggerFactory.getLogger((Class<?>) InnerJndiContextResolverConfigProvider.class);
            log = logger;
            logger.debug("JNDI class: {}", DirContext.class.getName());
        }

        private InnerJndiContextResolverConfigProvider() {
        }

        /* JADX WARN: Removed duplicated region for block: B:36:0x006c A[ORIG_RETURN, RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:7:0x002e  */
        @Override // org.xbill.DNS.config.ResolverConfigProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void initialize() {
            /*
                r5 = this;
                r5.reset()
                java.util.Hashtable r0 = new java.util.Hashtable
                r0.<init>()
                java.lang.String r1 = "java.naming.factory.initial"
                java.lang.String r2 = "com.sun.jndi.dns.DnsContextFactory"
                r0.put(r1, r2)
                java.lang.String r1 = "java.naming.provider.url"
                java.lang.String r2 = "dns://"
                r0.put(r1, r2)
                r2 = 0
                javax.naming.directory.InitialDirContext r3 = new javax.naming.directory.InitialDirContext     // Catch: javax.naming.NamingException -> L2b
                r3.<init>(r0)     // Catch: javax.naming.NamingException -> L2b
                java.util.Hashtable r0 = r3.getEnvironment()     // Catch: javax.naming.NamingException -> L2b
                java.lang.Object r0 = r0.get(r1)     // Catch: javax.naming.NamingException -> L2b
                java.lang.String r0 = (java.lang.String) r0     // Catch: javax.naming.NamingException -> L2b
                r3.close()     // Catch: javax.naming.NamingException -> L2a
                goto L2c
            L2a:
                r2 = r0
            L2b:
                r0 = r2
            L2c:
                if (r0 == 0) goto L6c
                java.util.StringTokenizer r1 = new java.util.StringTokenizer
                java.lang.String r2 = " "
                r1.<init>(r0, r2)
            L35:
                boolean r0 = r1.hasMoreTokens()
                if (r0 == 0) goto L6c
                java.lang.String r0 = r1.nextToken()
                java.net.URI r2 = new java.net.URI     // Catch: java.net.URISyntaxException -> L63
                r2.<init>(r0)     // Catch: java.net.URISyntaxException -> L63
                java.lang.String r3 = r2.getHost()     // Catch: java.net.URISyntaxException -> L63
                if (r3 == 0) goto L35
                boolean r4 = r3.isEmpty()     // Catch: java.net.URISyntaxException -> L63
                if (r4 == 0) goto L51
                goto L35
            L51:
                int r2 = r2.getPort()     // Catch: java.net.URISyntaxException -> L63
                r4 = -1
                if (r2 != r4) goto L5a
                r2 = 53
            L5a:
                java.net.InetSocketAddress r4 = new java.net.InetSocketAddress     // Catch: java.net.URISyntaxException -> L63
                r4.<init>(r3, r2)     // Catch: java.net.URISyntaxException -> L63
                r5.addNameserver(r4)     // Catch: java.net.URISyntaxException -> L63
                goto L35
            L63:
                r2 = move-exception
                org.slf4j.Logger r3 = org.xbill.DNS.config.JndiContextResolverConfigProvider.InnerJndiContextResolverConfigProvider.log
                java.lang.String r4 = "Could not parse {} as a dns server, ignoring"
                r3.debug(r4, r0, r2)
                goto L35
            L6c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.xbill.DNS.config.JndiContextResolverConfigProvider.InnerJndiContextResolverConfigProvider.initialize():void");
        }
    }

    @Override // org.xbill.DNS.config.ResolverConfigProvider
    public void initialize() {
        this.inner.initialize();
    }

    @Override // org.xbill.DNS.config.ResolverConfigProvider
    public List<InetSocketAddress> servers() {
        return this.inner.servers();
    }

    @Override // org.xbill.DNS.config.ResolverConfigProvider
    public List<Name> searchPaths() {
        return this.inner.searchPaths();
    }

    @Override // org.xbill.DNS.config.ResolverConfigProvider
    public boolean isEnabled() {
        return this.inner != null;
    }
}
