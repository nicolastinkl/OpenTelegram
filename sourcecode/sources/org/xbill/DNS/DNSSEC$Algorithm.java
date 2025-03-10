package org.xbill.DNS;

import com.tencent.cos.xml.common.RequestMethod;

/* loaded from: classes4.dex */
public class DNSSEC$Algorithm {
    private static final Mnemonic algs;

    static {
        Mnemonic mnemonic = new Mnemonic("DNSSEC algorithm", 2);
        algs = mnemonic;
        mnemonic.setMaximum(255);
        mnemonic.setNumericAllowed(true);
        mnemonic.add(0, RequestMethod.DELETE);
        mnemonic.add(1, "RSAMD5");
        mnemonic.add(2, "DH");
        mnemonic.add(3, "DSA");
        mnemonic.add(5, "RSASHA1");
        mnemonic.add(6, "DSA-NSEC3-SHA1");
        mnemonic.add(7, "RSA-NSEC3-SHA1");
        mnemonic.add(8, "RSASHA256");
        mnemonic.add(10, "RSASHA512");
        mnemonic.add(12, "ECC-GOST");
        mnemonic.add(13, "ECDSAP256SHA256");
        mnemonic.add(14, "ECDSAP384SHA384");
        mnemonic.add(15, "ED25519");
        mnemonic.add(16, "ED448");
        mnemonic.add(252, "INDIRECT");
        mnemonic.add(253, "PRIVATEDNS");
        mnemonic.add(254, "PRIVATEOID");
    }

    public static String string(int i) {
        return algs.getText(i);
    }
}
