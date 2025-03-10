package org.xbill.DNS;

/* loaded from: classes4.dex */
public final class Rcode {
    private static Mnemonic rcodes;

    static {
        Mnemonic mnemonic = new Mnemonic("DNS Rcode", 2);
        rcodes = mnemonic;
        mnemonic.setMaximum(4095);
        rcodes.setPrefix("RESERVED");
        rcodes.setNumericAllowed(true);
        rcodes.add(0, "NOERROR");
        rcodes.add(1, "FORMERR");
        rcodes.add(2, "SERVFAIL");
        rcodes.add(3, "NXDOMAIN");
        rcodes.add(4, "NOTIMP");
        rcodes.addAlias(4, "NOTIMPL");
        rcodes.add(5, "REFUSED");
        rcodes.add(6, "YXDOMAIN");
        rcodes.add(7, "YXRRSET");
        rcodes.add(8, "NXRRSET");
        rcodes.add(9, "NOTAUTH");
        rcodes.add(10, "NOTZONE");
        rcodes.add(16, "BADVERS");
        rcodes.add(17, "BADKEY");
        rcodes.add(18, "BADTIME");
        rcodes.add(19, "BADMODE");
        rcodes.add(20, "BADNAME");
        rcodes.add(21, "BADALG");
        rcodes.add(22, "BADTRUNC");
        rcodes.add(23, "BADCOOKIE");
    }

    public static String string(int i) {
        return rcodes.getText(i);
    }

    public static String TSIGstring(int i) {
        return i == 16 ? "BADSIG" : string(i);
    }
}
