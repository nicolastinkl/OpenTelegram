package org.xbill.DNS.config;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class SunJvmResolverConfigProvider extends BaseResolverConfigProvider {
    @Override // org.xbill.DNS.config.ResolverConfigProvider
    public void initialize() throws InitializationException {
        reset();
        try {
            Class<?> cls = Class.forName("sun.net.dns.ResolverConfiguration");
            Object invoke = cls.getDeclaredMethod("open", new Class[0]).invoke(null, new Object[0]);
            Iterator it = ((List) cls.getMethod("nameservers", new Class[0]).invoke(invoke, new Object[0])).iterator();
            while (it.hasNext()) {
                addNameserver(new InetSocketAddress((String) it.next(), 53));
            }
            Iterator it2 = ((List) cls.getMethod("searchlist", new Class[0]).invoke(invoke, new Object[0])).iterator();
            while (it2.hasNext()) {
                addSearchPath((String) it2.next());
            }
        } catch (Exception e) {
            throw new InitializationException(e);
        }
    }

    @Override // org.xbill.DNS.config.BaseResolverConfigProvider, org.xbill.DNS.config.ResolverConfigProvider
    public boolean isEnabled() {
        return Boolean.getBoolean("dnsjava.configprovider.sunjvm.enabled");
    }
}
