package org.xbill.DNS.config;

import j$.util.Collection$EL;
import j$.util.function.Predicate;
import j$.util.stream.Collectors;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.Name;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.config.ResolverConfigProvider;

/* loaded from: classes4.dex */
public abstract class BaseResolverConfigProvider implements ResolverConfigProvider {
    private static final boolean ipv4only = Boolean.getBoolean("java.net.preferIPv4Stack");
    private static final boolean ipv6first = Boolean.getBoolean("java.net.preferIPv6Addresses");
    private final List<InetSocketAddress> nameservers = new ArrayList(3);
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final List<Name> searchlist = new ArrayList(1);

    @Override // org.xbill.DNS.config.ResolverConfigProvider
    public /* synthetic */ boolean isEnabled() {
        return ResolverConfigProvider.CC.$default$isEnabled(this);
    }

    @Override // org.xbill.DNS.config.ResolverConfigProvider
    public /* synthetic */ int ndots() {
        return ResolverConfigProvider.CC.$default$ndots(this);
    }

    protected final void reset() {
        this.nameservers.clear();
        this.searchlist.clear();
    }

    protected void parseSearchPathList(String str, String str2) {
        if (str != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, str2);
            while (stringTokenizer.hasMoreTokens()) {
                addSearchPath(stringTokenizer.nextToken());
            }
        }
    }

    protected void addSearchPath(String str) {
        if (str == null || str.isEmpty()) {
            return;
        }
        try {
            Name fromString = Name.fromString(str, Name.root);
            if (this.searchlist.contains(fromString)) {
                return;
            }
            this.searchlist.add(fromString);
            this.log.debug("Added {} to search paths", fromString);
        } catch (TextParseException unused) {
            this.log.warn("Could not parse search path {} as a dns name, ignoring", str);
        }
    }

    protected void addNameserver(InetSocketAddress inetSocketAddress) {
        if (this.nameservers.contains(inetSocketAddress)) {
            return;
        }
        this.nameservers.add(inetSocketAddress);
        this.log.debug("Added {} to nameservers", inetSocketAddress);
    }

    protected int parseNdots(String str) {
        if (str == null || str.isEmpty()) {
            return 1;
        }
        try {
            int parseInt = Integer.parseInt(str);
            if (parseInt < 0) {
                return 1;
            }
            if (parseInt > 15) {
                return 15;
            }
            return parseInt;
        } catch (NumberFormatException unused) {
            return 1;
        }
    }

    @Override // org.xbill.DNS.config.ResolverConfigProvider
    public final List<InetSocketAddress> servers() {
        if (ipv6first) {
            return (List) Collection$EL.stream(this.nameservers).sorted(new Comparator() { // from class: org.xbill.DNS.config.BaseResolverConfigProvider$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int lambda$servers$0;
                    lambda$servers$0 = BaseResolverConfigProvider.lambda$servers$0((InetSocketAddress) obj, (InetSocketAddress) obj2);
                    return lambda$servers$0;
                }
            }).collect(Collectors.toList());
        }
        if (ipv4only) {
            return (List) Collection$EL.stream(this.nameservers).filter(new Predicate() { // from class: org.xbill.DNS.config.BaseResolverConfigProvider$$ExternalSyntheticLambda1
                @Override // j$.util.function.Predicate
                public /* synthetic */ Predicate and(Predicate predicate) {
                    return Predicate.CC.$default$and(this, predicate);
                }

                @Override // j$.util.function.Predicate
                public /* synthetic */ Predicate negate() {
                    return Predicate.CC.$default$negate(this);
                }

                @Override // j$.util.function.Predicate
                public /* synthetic */ Predicate or(Predicate predicate) {
                    return Predicate.CC.$default$or(this, predicate);
                }

                @Override // j$.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$servers$1;
                    lambda$servers$1 = BaseResolverConfigProvider.lambda$servers$1((InetSocketAddress) obj);
                    return lambda$servers$1;
                }
            }).collect(Collectors.toList());
        }
        return Collections.unmodifiableList(this.nameservers);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$servers$0(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2) {
        return Integer.compare(inetSocketAddress2.getAddress().getAddress().length, inetSocketAddress.getAddress().getAddress().length);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$servers$1(InetSocketAddress inetSocketAddress) {
        return inetSocketAddress.getAddress() instanceof Inet4Address;
    }

    @Override // org.xbill.DNS.config.ResolverConfigProvider
    public final List<Name> searchPaths() {
        return Collections.unmodifiableList(this.searchlist);
    }
}
