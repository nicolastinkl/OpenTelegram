package org.xbill.DNS.config;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.ptr.IntByReference;
import java.net.InetSocketAddress;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.Name;
import org.xbill.DNS.config.IPHlpAPI;
import org.xbill.DNS.config.ResolverConfigProvider;

/* loaded from: classes4.dex */
public class WindowsResolverConfigProvider implements ResolverConfigProvider {

    @Generated
    private static final Logger log = LoggerFactory.getLogger((Class<?>) WindowsResolverConfigProvider.class);
    private InnerWindowsResolverConfigProvider inner;

    @Override // org.xbill.DNS.config.ResolverConfigProvider
    public /* synthetic */ int ndots() {
        return ResolverConfigProvider.CC.$default$ndots(this);
    }

    public WindowsResolverConfigProvider() {
        if (System.getProperty("os.name").contains("Windows")) {
            try {
                this.inner = new InnerWindowsResolverConfigProvider();
            } catch (NoClassDefFoundError unused) {
                log.debug("JNA not available");
            }
        }
    }

    private static final class InnerWindowsResolverConfigProvider extends BaseResolverConfigProvider {

        @Generated
        private static final Logger log;

        static {
            Logger logger = LoggerFactory.getLogger((Class<?>) InnerWindowsResolverConfigProvider.class);
            log = logger;
            logger.debug("Checking for JNA classes: {} and {}", Memory.class.getName(), Win32Exception.class.getName());
        }

        private InnerWindowsResolverConfigProvider() {
        }

        @Override // org.xbill.DNS.config.ResolverConfigProvider
        public void initialize() throws InitializationException {
            reset();
            Memory memory = new Memory(15360L);
            IntByReference intByReference = new IntByReference(0);
            IPHlpAPI iPHlpAPI = IPHlpAPI.INSTANCE;
            if (iPHlpAPI.GetAdaptersAddresses(0, 39, Pointer.NULL, memory, intByReference) == 111) {
                memory = new Memory(intByReference.getValue());
                int GetAdaptersAddresses = iPHlpAPI.GetAdaptersAddresses(0, 39, Pointer.NULL, memory, intByReference);
                if (GetAdaptersAddresses != 0) {
                    throw new InitializationException((Exception) new Win32Exception(GetAdaptersAddresses));
                }
            }
            IPHlpAPI.IP_ADAPTER_ADDRESSES_LH ip_adapter_addresses_lh = new IPHlpAPI.IP_ADAPTER_ADDRESSES_LH(memory);
            if (ip_adapter_addresses_lh.OperStatus == 1) {
                addSearchPath(ip_adapter_addresses_lh.DnsSuffix.toString());
            }
        }
    }

    @Override // org.xbill.DNS.config.ResolverConfigProvider
    public void initialize() throws InitializationException {
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
