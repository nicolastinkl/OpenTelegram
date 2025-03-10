package org.xbill.DNS;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.config.AndroidResolverConfigProvider;
import org.xbill.DNS.config.FallbackPropertyResolverConfigProvider;
import org.xbill.DNS.config.InitializationException;
import org.xbill.DNS.config.JndiContextResolverConfigProvider;
import org.xbill.DNS.config.PropertyResolverConfigProvider;
import org.xbill.DNS.config.ResolvConfResolverConfigProvider;
import org.xbill.DNS.config.ResolverConfigProvider;
import org.xbill.DNS.config.SunJvmResolverConfigProvider;
import org.xbill.DNS.config.WindowsResolverConfigProvider;

/* loaded from: classes4.dex */
public final class ResolverConfig {
    private static List<ResolverConfigProvider> configProviders;
    private static ResolverConfig currentConfig;

    @Generated
    private static final Logger log = LoggerFactory.getLogger((Class<?>) ResolverConfig.class);
    private int ndots;
    private final List<InetSocketAddress> servers = new ArrayList(2);
    private final List<Name> searchlist = new ArrayList(0);

    private static void checkInitialized() {
        if (currentConfig == null || configProviders == null) {
            refresh();
        }
    }

    public static synchronized ResolverConfig getCurrentConfig() {
        ResolverConfig resolverConfig;
        synchronized (ResolverConfig.class) {
            checkInitialized();
            resolverConfig = currentConfig;
        }
        return resolverConfig;
    }

    public static void refresh() {
        ResolverConfig resolverConfig = new ResolverConfig();
        synchronized (ResolverConfig.class) {
            currentConfig = resolverConfig;
        }
    }

    public ResolverConfig() {
        this.ndots = 1;
        synchronized (ResolverConfig.class) {
            if (configProviders == null) {
                configProviders = new ArrayList(8);
                if (!Boolean.getBoolean("dnsjava.configprovider.skipinit")) {
                    configProviders.add(new PropertyResolverConfigProvider());
                    configProviders.add(new ResolvConfResolverConfigProvider());
                    configProviders.add(new WindowsResolverConfigProvider());
                    configProviders.add(new AndroidResolverConfigProvider());
                    configProviders.add(new JndiContextResolverConfigProvider());
                    configProviders.add(new SunJvmResolverConfigProvider());
                    configProviders.add(new FallbackPropertyResolverConfigProvider());
                }
            }
        }
        for (ResolverConfigProvider resolverConfigProvider : configProviders) {
            if (resolverConfigProvider.isEnabled()) {
                try {
                    resolverConfigProvider.initialize();
                    if (this.servers.isEmpty()) {
                        this.servers.addAll(resolverConfigProvider.servers());
                    }
                    if (this.searchlist.isEmpty()) {
                        List<Name> searchPaths = resolverConfigProvider.searchPaths();
                        if (!searchPaths.isEmpty()) {
                            this.searchlist.addAll(searchPaths);
                            this.ndots = resolverConfigProvider.ndots();
                        }
                    }
                    if (!this.servers.isEmpty() && !this.searchlist.isEmpty()) {
                        return;
                    }
                } catch (InitializationException e) {
                    log.warn("Failed to initialize provider", (Throwable) e);
                }
            }
        }
        if (this.servers.isEmpty()) {
            this.servers.add(new InetSocketAddress(InetAddress.getLoopbackAddress(), 53));
        }
    }

    public List<InetSocketAddress> servers() {
        return this.servers;
    }

    public InetSocketAddress server() {
        return this.servers.get(0);
    }

    public List<Name> searchPath() {
        return this.searchlist;
    }

    public int ndots() {
        return this.ndots;
    }
}
