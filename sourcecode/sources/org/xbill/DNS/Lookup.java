package org.xbill.DNS;

import j$.util.Optional;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.hosts.HostsFileParser;

/* loaded from: classes4.dex */
public final class Lookup {
    private static Map<Integer, Cache> defaultCaches;
    private static HostsFileParser defaultHostsFileParser;
    private static int defaultNdots;
    private static Resolver defaultResolver;
    private static List<Name> defaultSearchPath;

    @Generated
    private static final Logger log = LoggerFactory.getLogger((Class<?>) Lookup.class);
    private List<Name> aliases;
    private Record[] answers;
    private boolean badresponse;
    private String badresponse_error;
    private Cache cache;
    private int credibility;
    private boolean cycleResults;
    private int dclass;
    private boolean done;
    private boolean doneCurrent;
    private boolean foundAlias;
    private HostsFileParser hostsFileParser;
    private int iterations;
    private int maxIterations;
    private Name name;
    private boolean nametoolong;
    private int ndots;
    private boolean networkerror;
    private boolean nxdomain;
    private boolean referral;
    private Resolver resolver;
    private int result;
    private List<Name> searchPath;
    private boolean temporary_cache;
    private boolean timedout;
    private int type;

    static {
        refreshDefault();
    }

    public static synchronized void refreshDefault() {
        synchronized (Lookup.class) {
            defaultResolver = new ExtendedResolver();
            defaultSearchPath = ResolverConfig.getCurrentConfig().searchPath();
            defaultCaches = new HashMap();
            defaultNdots = ResolverConfig.getCurrentConfig().ndots();
            defaultHostsFileParser = new HostsFileParser();
        }
    }

    public static synchronized Resolver getDefaultResolver() {
        Resolver resolver;
        synchronized (Lookup.class) {
            resolver = defaultResolver;
        }
        return resolver;
    }

    public static synchronized Cache getDefaultCache(int i) {
        Cache cache;
        synchronized (Lookup.class) {
            DClass.check(i);
            cache = defaultCaches.get(Integer.valueOf(i));
            if (cache == null) {
                cache = new Cache(i);
                defaultCaches.put(Integer.valueOf(i), cache);
            }
        }
        return cache;
    }

    public static synchronized List<Name> getDefaultSearchPath() {
        List<Name> list;
        synchronized (Lookup.class) {
            list = defaultSearchPath;
        }
        return list;
    }

    public static synchronized HostsFileParser getDefaultHostsFileParser() {
        HostsFileParser hostsFileParser;
        synchronized (Lookup.class) {
            hostsFileParser = defaultHostsFileParser;
        }
        return hostsFileParser;
    }

    private void reset() {
        this.iterations = 0;
        this.foundAlias = false;
        this.done = false;
        this.doneCurrent = false;
        this.aliases = null;
        this.answers = null;
        this.result = -1;
        this.nxdomain = false;
        this.badresponse = false;
        this.badresponse_error = null;
        this.networkerror = false;
        this.timedout = false;
        this.nametoolong = false;
        this.referral = false;
        if (this.temporary_cache) {
            this.cache.clearCache();
        }
    }

    public Lookup(Name name, int i, int i2) {
        this.cycleResults = true;
        Type.check(i);
        DClass.check(i2);
        if (!Type.isRR(i) && i != 255) {
            throw new IllegalArgumentException("Cannot query for meta-types other than ANY");
        }
        this.name = name;
        this.type = i;
        this.dclass = i2;
        synchronized (Lookup.class) {
            this.resolver = getDefaultResolver();
            this.searchPath = getDefaultSearchPath();
            this.cache = getDefaultCache(i2);
        }
        this.ndots = defaultNdots;
        this.credibility = 3;
        this.result = -1;
        this.maxIterations = Integer.parseInt(System.getProperty("dnsjava.lookup.max_iterations", "16"));
        if (Boolean.parseBoolean(System.getProperty("dnsjava.lookup.use_hosts_file", "true"))) {
            this.hostsFileParser = getDefaultHostsFileParser();
        }
    }

    public Lookup(String str, int i) throws TextParseException {
        this(Name.fromString(str), i, 1);
    }

    public void setResolver(Resolver resolver) {
        this.resolver = resolver;
    }

    private void follow(Name name, Name name2) {
        this.foundAlias = true;
        this.badresponse = false;
        this.networkerror = false;
        this.timedout = false;
        this.nxdomain = false;
        this.referral = false;
        int i = this.iterations + 1;
        this.iterations = i;
        if (i >= this.maxIterations || name.equals(name2)) {
            this.result = 1;
            this.done = true;
        } else {
            if (this.aliases == null) {
                this.aliases = new ArrayList();
            }
            this.aliases.add(name2);
            lookup(name);
        }
    }

    private void processResponse(Name name, SetResponse setResponse) {
        if (setResponse.isSuccessful()) {
            List<RRset> answers = setResponse.answers();
            ArrayList arrayList = new ArrayList();
            Iterator<RRset> it = answers.iterator();
            while (it.hasNext()) {
                arrayList.addAll(it.next().rrs(this.cycleResults));
            }
            this.result = 0;
            this.answers = (Record[]) arrayList.toArray(new Record[0]);
            this.done = true;
            return;
        }
        if (setResponse.isNXDOMAIN()) {
            this.nxdomain = true;
            this.doneCurrent = true;
            if (this.iterations > 0) {
                this.result = 3;
                this.done = true;
                return;
            }
            return;
        }
        if (setResponse.isNXRRSET()) {
            this.result = 4;
            this.answers = null;
            this.done = true;
        } else {
            if (setResponse.isCNAME()) {
                follow(setResponse.getCNAME().getTarget(), name);
                return;
            }
            if (setResponse.isDNAME()) {
                try {
                    follow(name.fromDNAME(setResponse.getDNAME()), name);
                    return;
                } catch (NameTooLongException unused) {
                    this.result = 1;
                    this.done = true;
                    return;
                }
            }
            if (setResponse.isDelegation()) {
                this.referral = true;
            }
        }
    }

    private void lookup(Name name) {
        if (lookupFromHostsFile(name)) {
            return;
        }
        SetResponse lookupRecords = this.cache.lookupRecords(name, this.type, this.credibility);
        Logger logger = log;
        logger.debug("Lookup for {}/{}, cache answer: {}", name, Type.string(this.type), lookupRecords);
        processResponse(name, lookupRecords);
        if (this.done || this.doneCurrent) {
            return;
        }
        Message newQuery = Message.newQuery(Record.newRecord(name, this.type, this.dclass));
        try {
            Message send = this.resolver.send(newQuery);
            int rcode = send.getHeader().getRcode();
            if (rcode != 0 && rcode != 3) {
                this.badresponse = true;
                this.badresponse_error = Rcode.string(rcode);
            } else {
                if (!newQuery.getQuestion().equals(send.getQuestion())) {
                    this.badresponse = true;
                    this.badresponse_error = "response does not match query";
                    return;
                }
                SetResponse addMessage = this.cache.addMessage(send);
                if (addMessage == null) {
                    addMessage = this.cache.lookupRecords(name, this.type, this.credibility);
                }
                logger.debug("Queried {}/{}, id={}: {}", name, Type.string(this.type), Integer.valueOf(send.getHeader().getID()), addMessage);
                processResponse(name, addMessage);
            }
        } catch (IOException e) {
            log.debug("Lookup for {}/{}, id={} failed using resolver {}", name, Type.string(newQuery.getQuestion().getType()), Integer.valueOf(newQuery.getHeader().getID()), this.resolver, e);
            if (e instanceof InterruptedIOException) {
                this.timedout = true;
            } else {
                this.networkerror = true;
            }
        }
    }

    private boolean lookupFromHostsFile(Name name) {
        int i;
        HostsFileParser hostsFileParser = this.hostsFileParser;
        if (hostsFileParser != null && ((i = this.type) == 1 || i == 28)) {
            try {
                Optional<InetAddress> addressForHost = hostsFileParser.getAddressForHost(name, i);
                if (addressForHost.isPresent()) {
                    this.result = 0;
                    this.done = true;
                    if (this.type == 1) {
                        this.answers = new ARecord[]{new ARecord(name, this.dclass, 0L, addressForHost.get())};
                    } else {
                        this.answers = new AAAARecord[]{new AAAARecord(name, this.dclass, 0L, addressForHost.get())};
                    }
                    return true;
                }
            } catch (IOException e) {
                log.debug("Local hosts database parsing failed, ignoring and using resolver", (Throwable) e);
            }
        }
        return false;
    }

    private void resolve(Name name, Name name2) {
        this.doneCurrent = false;
        if (name2 != null) {
            try {
                name = Name.concatenate(name, name2);
            } catch (NameTooLongException unused) {
                this.nametoolong = true;
                return;
            }
        }
        lookup(name);
    }

    public Record[] run() {
        if (this.done) {
            reset();
        }
        if (this.name.isAbsolute()) {
            resolve(this.name, null);
        } else if (this.searchPath == null) {
            resolve(this.name, Name.root);
        } else {
            if (this.name.labels() > this.ndots) {
                resolve(this.name, Name.root);
            }
            if (this.done) {
                return this.answers;
            }
            Iterator<Name> it = this.searchPath.iterator();
            while (it.hasNext()) {
                resolve(this.name, it.next());
                if (this.done) {
                    return this.answers;
                }
                if (this.foundAlias) {
                    break;
                }
            }
            resolve(this.name, Name.root);
        }
        if (!this.done) {
            if (this.badresponse) {
                this.result = 2;
                this.done = true;
            } else if (this.timedout) {
                this.result = 2;
                this.done = true;
            } else if (this.networkerror) {
                this.result = 2;
                this.done = true;
            } else if (this.nxdomain) {
                this.result = 3;
                this.done = true;
            } else if (this.referral) {
                this.result = 1;
                this.done = true;
            } else if (this.nametoolong) {
                this.result = 1;
                this.done = true;
            }
        }
        return this.answers;
    }

    private void checkDone() {
        if (!this.done || this.result == -1) {
            StringBuilder sb = new StringBuilder("Lookup of " + this.name + " ");
            int i = this.dclass;
            if (i != 1) {
                sb.append(DClass.string(i));
                sb.append(" ");
            }
            sb.append(Type.string(this.type));
            sb.append(" isn't done");
            throw new IllegalStateException(sb.toString());
        }
    }

    public Record[] getAnswers() {
        checkDone();
        return this.answers;
    }

    public int getResult() {
        checkDone();
        return this.result;
    }
}
