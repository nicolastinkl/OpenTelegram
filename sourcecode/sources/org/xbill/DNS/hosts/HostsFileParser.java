package org.xbill.DNS.hosts;

import j$.time.Instant;
import j$.time.TimeConversions;
import j$.util.DesugarArrays;
import j$.util.Map;
import j$.util.Optional;
import j$.util.function.Function;
import j$.util.function.Predicate;
import j$.util.stream.Stream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.Address;
import org.xbill.DNS.Name;
import org.xbill.DNS.TextParseException;

/* loaded from: classes4.dex */
public final class HostsFileParser {

    @Generated
    private static final Logger log = LoggerFactory.getLogger((Class<?>) HostsFileParser.class);
    private final boolean clearCacheOnChange;
    private final Map<String, InetAddress> hostsCache;
    private boolean isEntireFileParsed;
    private Instant lastFileReadTime;
    private final Path path;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public HostsFileParser() {
        /*
            r2 = this;
            java.lang.String r0 = "os.name"
            java.lang.String r0 = java.lang.System.getProperty(r0)
            java.lang.String r1 = "Windows"
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L1f
            java.lang.String r0 = "SystemRoot"
            java.lang.String r0 = java.lang.System.getenv(r0)
            java.lang.String r1 = "\\System32\\drivers\\etc\\hosts"
            java.lang.String[] r1 = new java.lang.String[]{r1}
            java.nio.file.Path r0 = java.nio.file.Paths.get(r0, r1)
            goto L28
        L1f:
            r0 = 0
            java.lang.String[] r0 = new java.lang.String[r0]
            java.lang.String r1 = "/etc/hosts"
            java.nio.file.Path r0 = java.nio.file.Paths.get(r1, r0)
        L28:
            r1 = 1
            r2.<init>(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.xbill.DNS.hosts.HostsFileParser.<init>():void");
    }

    public HostsFileParser(Path path, boolean z) {
        this.hostsCache = new HashMap();
        this.lastFileReadTime = Instant.MIN;
        Objects.requireNonNull(path, "path is required");
        this.path = path;
        this.clearCacheOnChange = z;
        if (Files.isDirectory(path, new LinkOption[0])) {
            throw new IllegalArgumentException("path must be a file");
        }
    }

    public synchronized Optional<InetAddress> getAddressForHost(Name name, int i) throws IOException {
        Objects.requireNonNull(name, "name is required");
        if (i != 1 && i != 28) {
            throw new IllegalArgumentException("type can only be A or AAAA");
        }
        validateCache();
        InetAddress inetAddress = this.hostsCache.get(key(name, i));
        if (inetAddress != null) {
            return Optional.of(inetAddress);
        }
        if (!this.isEntireFileParsed && Files.exists(this.path, new LinkOption[0])) {
            if (Files.size(this.path) <= 16384) {
                parseEntireHostsFile();
            } else {
                searchHostsFileForEntry(name, i);
            }
            return Optional.ofNullable(this.hostsCache.get(key(name, i)));
        }
        return Optional.empty();
    }

    private void parseEntireHostsFile() throws IOException {
        BufferedReader newBufferedReader = Files.newBufferedReader(this.path, StandardCharsets.UTF_8);
        int i = 0;
        while (true) {
            try {
                String readLine = newBufferedReader.readLine();
                if (readLine != null) {
                    i++;
                    LineData parseLine = parseLine(i, readLine);
                    if (parseLine != null) {
                        for (Name name : parseLine.names) {
                            Map.EL.putIfAbsent(this.hostsCache, key(name, parseLine.type), InetAddress.getByAddress(name.toString(true), parseLine.address));
                        }
                    }
                } else {
                    newBufferedReader.close();
                    this.isEntireFileParsed = true;
                    return;
                }
            } finally {
            }
        }
    }

    private void searchHostsFileForEntry(Name name, int i) throws IOException {
        BufferedReader newBufferedReader = Files.newBufferedReader(this.path, StandardCharsets.UTF_8);
        int i2 = 0;
        while (true) {
            try {
                String readLine = newBufferedReader.readLine();
                if (readLine == null) {
                    newBufferedReader.close();
                    return;
                }
                i2++;
                LineData parseLine = parseLine(i2, readLine);
                if (parseLine != null) {
                    for (Name name2 : parseLine.names) {
                        if (name2.equals(name) && i == parseLine.type) {
                            Map.EL.putIfAbsent(this.hostsCache, key(name2, parseLine.type), InetAddress.getByAddress(name2.toString(true), parseLine.address));
                            newBufferedReader.close();
                            return;
                        }
                    }
                }
            } finally {
            }
        }
    }

    private static final class LineData {
        final byte[] address;
        final Iterable<? extends Name> names;
        final int type;

        @Generated
        public LineData(int i, byte[] bArr, Iterable<? extends Name> iterable) {
            this.type = i;
            this.address = bArr;
            this.names = iterable;
        }
    }

    private LineData parseLine(final int i, String str) {
        int i2;
        String[] lineTokens = getLineTokens(str);
        if (lineTokens.length < 2) {
            return null;
        }
        byte[] byteArray = Address.toByteArray(lineTokens[0], 1);
        if (byteArray == null) {
            byteArray = Address.toByteArray(lineTokens[0], 2);
            i2 = 28;
        } else {
            i2 = 1;
        }
        if (byteArray == null) {
            log.warn("Could not decode address {}, {}#L{}", lineTokens[0], this.path, Integer.valueOf(i));
            return null;
        }
        final Stream filter = DesugarArrays.stream(lineTokens).skip(1L).map(new Function() { // from class: org.xbill.DNS.hosts.HostsFileParser$$ExternalSyntheticLambda2
            @Override // j$.util.function.Function
            public /* synthetic */ Function andThen(Function function) {
                return Function.CC.$default$andThen(this, function);
            }

            @Override // j$.util.function.Function
            public final Object apply(Object obj) {
                Name lambda$parseLine$0;
                lambda$parseLine$0 = HostsFileParser.this.lambda$parseLine$0(i, (String) obj);
                return lambda$parseLine$0;
            }

            @Override // j$.util.function.Function
            public /* synthetic */ Function compose(Function function) {
                return Function.CC.$default$compose(this, function);
            }
        }).filter(new Predicate() { // from class: org.xbill.DNS.hosts.HostsFileParser$$ExternalSyntheticLambda3
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
                return HostsFileParser$$ExternalSyntheticBackport0.m((Name) obj);
            }
        });
        filter.getClass();
        return new LineData(i2, byteArray, new Iterable() { // from class: org.xbill.DNS.hosts.HostsFileParser$$ExternalSyntheticLambda1
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return Stream.this.iterator();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: safeName, reason: merged with bridge method [inline-methods] */
    public Name lambda$parseLine$0(String str, int i) {
        try {
            return Name.fromString(str, Name.root);
        } catch (TextParseException unused) {
            log.warn("Could not decode name {}, {}#L{}, skipping", str, this.path, Integer.valueOf(i));
            return null;
        }
    }

    private String[] getLineTokens(String str) {
        int indexOf = str.indexOf(35);
        if (indexOf == -1) {
            indexOf = str.length();
        }
        return str.substring(0, indexOf).trim().split("\\s+");
    }

    private void validateCache() throws IOException {
        if (this.clearCacheOnChange) {
            Instant convert = Files.exists(this.path, new LinkOption[0]) ? TimeConversions.convert(Files.getLastModifiedTime(this.path, new LinkOption[0]).toInstant()) : Instant.MAX;
            if (convert.isAfter(this.lastFileReadTime)) {
                if (!this.hostsCache.isEmpty()) {
                    log.info("Local hosts database has changed at {}, clearing cache", convert);
                    this.hostsCache.clear();
                }
                this.isEntireFileParsed = false;
                this.lastFileReadTime = convert;
            }
        }
    }

    private String key(Name name, int i) {
        return name.toString() + '\t' + i;
    }
}
