package org.xbill.DNS;

import j$.util.function.Supplier;
import java.util.HashMap;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.NotificationBadge;

/* loaded from: classes4.dex */
public final class Type {
    private static TypeMnemonic types;

    public static boolean isRR(int i) {
        if (i == 41) {
            return false;
        }
        switch (i) {
            case 249:
            case 250:
            case 251:
            case 252:
            case 253:
            case 254:
            case 255:
                return false;
            default:
                return true;
        }
    }

    private static class TypeMnemonic extends Mnemonic {
        private HashMap<Integer, Supplier<Record>> factories;

        public TypeMnemonic() {
            super("Type", 2);
            setPrefix("TYPE");
            setMaximum(65535);
            this.factories = new HashMap<>();
        }

        public void add(int i, String str, Supplier<Record> supplier) {
            super.add(i, str);
            this.factories.put(Integer.valueOf(i), supplier);
        }

        @Override // org.xbill.DNS.Mnemonic
        public void check(int i) {
            Type.check(i);
        }

        public Supplier<Record> getFactory(int i) {
            check(i);
            return this.factories.get(Integer.valueOf(i));
        }
    }

    static {
        TypeMnemonic typeMnemonic = new TypeMnemonic();
        types = typeMnemonic;
        typeMnemonic.add(1, "A", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda4
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new ARecord();
            }
        });
        types.add(2, "NS", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda37
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new NSRecord();
            }
        });
        types.add(3, "MD", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda25
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new MDRecord();
            }
        });
        types.add(4, "MF", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda26
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new MFRecord();
            }
        });
        types.add(5, NotificationBadge.AdwHomeBadger.CLASSNAME, new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda9
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new CNAMERecord();
            }
        });
        types.add(6, "SOA", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda49
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new SOARecord();
            }
        });
        types.add(7, "MB", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda24
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new MBRecord();
            }
        });
        types.add(8, "MG", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda27
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new MGRecord();
            }
        });
        types.add(9, "MR", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda29
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new MRRecord();
            }
        });
        types.add(10, "NULL", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda38
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new NULLRecord();
            }
        });
        types.add(11, "WKS", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda59
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new WKSRecord();
            }
        });
        types.add(12, "PTR", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda42
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new PTRRecord();
            }
        });
        types.add(13, "HINFO", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda16
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new HINFORecord();
            }
        });
        types.add(14, "MINFO", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda28
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new MINFORecord();
            }
        });
        types.add(15, "MX", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda30
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new MXRecord();
            }
        });
        types.add(16, "TXT", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda57
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new TXTRecord();
            }
        });
        types.add(17, "RP", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda44
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new RPRecord();
            }
        });
        types.add(18, "AFSDB", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda2
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new AFSDBRecord();
            }
        });
        types.add(19, "X25", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda60
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new X25Record();
            }
        });
        types.add(20, "ISDN", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda20
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new ISDNRecord();
            }
        });
        types.add(21, "RT", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda46
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new RTRecord();
            }
        });
        types.add(22, "NSAP", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda32
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new NSAPRecord();
            }
        });
        types.add(23, "NSAP-PTR", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda33
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new NSAP_PTRRecord();
            }
        });
        types.add(24, "SIG", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda47
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new SIGRecord();
            }
        });
        types.add(25, "KEY", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda21
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new KEYRecord();
            }
        });
        types.add(26, "PX", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda43
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new PXRecord();
            }
        });
        types.add(27, "GPOS", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda15
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new GPOSRecord();
            }
        });
        types.add(28, "AAAA", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda1
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new AAAARecord();
            }
        });
        types.add(29, "LOC", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda23
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new LOCRecord();
            }
        });
        types.add(30, "NXT", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda39
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new NXTRecord();
            }
        });
        types.add(31, "EID");
        types.add(32, "NIMLOC");
        types.add(33, "SRV", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda51
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new SRVRecord();
            }
        });
        types.add(34, "ATMA");
        types.add(35, "NAPTR", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda31
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new NAPTRRecord();
            }
        });
        types.add(36, "KX", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda22
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new KXRecord();
            }
        });
        types.add(37, "CERT", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda8
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new CERTRecord();
            }
        });
        types.add(38, "A6", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda0
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new A6Record();
            }
        });
        types.add(39, "DNAME", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda12
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new DNAMERecord();
            }
        });
        types.add(40, "SINK");
        types.add(41, "OPT", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda41
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new OPTRecord();
            }
        });
        types.add(42, "APL", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda3
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new APLRecord();
            }
        });
        types.add(43, "DS", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda14
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new DSRecord();
            }
        });
        types.add(44, "SSHFP", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda52
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new SSHFPRecord();
            }
        });
        types.add(45, "IPSECKEY", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda19
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new IPSECKEYRecord();
            }
        });
        types.add(46, "RRSIG", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda45
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new RRSIGRecord();
            }
        });
        types.add(47, "NSEC", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda36
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new NSECRecord();
            }
        });
        types.add(48, "DNSKEY", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda13
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new DNSKEYRecord();
            }
        });
        types.add(49, "DHCID", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda10
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new DHCIDRecord();
            }
        });
        types.add(50, "NSEC3", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda35
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new NSEC3Record();
            }
        });
        types.add(51, "NSEC3PARAM", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda34
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new NSEC3PARAMRecord();
            }
        });
        types.add(52, "TLSA", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda55
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new TLSARecord();
            }
        });
        types.add(53, "SMIMEA", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda48
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new SMIMEARecord();
            }
        });
        types.add(55, "HIP", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda17
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new HIPRecord();
            }
        });
        types.add(56, "NINFO");
        types.add(57, "RKEY");
        types.add(58, "TALINK");
        types.add(59, "CDS", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda7
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new CDSRecord();
            }
        });
        types.add(60, "CDNSKEY", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda6
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new CDNSKEYRecord();
            }
        });
        types.add(61, "OPENPGPKEY", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda40
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new OPENPGPKEYRecord();
            }
        });
        types.add(62, "CSYNC");
        types.add(63, "ZONEMD");
        types.add(64, "SVCB", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda53
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new SVCBRecord();
            }
        });
        types.add(65, "HTTPS", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda18
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new HTTPSRecord();
            }
        });
        types.add(99, "SPF", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda50
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new SPFRecord();
            }
        });
        types.add(100, "UINFO");
        types.add(101, "UID");
        types.add(102, "GID");
        types.add(103, "UNSPEC");
        types.add(104, "NID");
        types.add(105, "L32");
        types.add(106, "L64");
        types.add(107, "LP");
        types.add(108, "EUI48");
        types.add(109, "EUI64");
        types.add(249, "TKEY", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda54
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new TKEYRecord();
            }
        });
        types.add(250, "TSIG", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda56
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new TSIGRecord();
            }
        });
        types.add(251, "IXFR");
        types.add(252, "AXFR");
        types.add(253, "MAILB");
        types.add(254, "MAILA");
        types.add(255, "ANY");
        types.add(256, "URI", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda58
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new URIRecord();
            }
        });
        types.add(257, "CAA", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda5
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new CAARecord();
            }
        });
        types.add(258, "AVC");
        types.add(259, "DOA");
        types.add(260, "AMTRELAY");
        types.add(LiteMode.FLAG_CHAT_SCALE, "TA");
        types.add(32769, "DLV", new Supplier() { // from class: org.xbill.DNS.Type$$ExternalSyntheticLambda11
            @Override // j$.util.function.Supplier
            public final Object get() {
                return new DLVRecord();
            }
        });
    }

    public static void check(int i) {
        if (i < 0 || i > 65535) {
            throw new InvalidTypeException(i);
        }
    }

    public static String string(int i) {
        return types.getText(i);
    }

    static Supplier<Record> getFactory(int i) {
        return types.getFactory(i);
    }
}
