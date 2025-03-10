package org.xbill.DNS;

/* loaded from: classes4.dex */
public final class Flags {
    private static Mnemonic flags;

    static {
        Mnemonic mnemonic = new Mnemonic("DNS Header Flag", 3);
        flags = mnemonic;
        mnemonic.setMaximum(15);
        flags.setPrefix("FLAG");
        flags.setNumericAllowed(true);
        flags.add(0, "qr");
        flags.add(5, "aa");
        flags.add(6, "tc");
        flags.add(7, "rd");
        flags.add(8, "ra");
        flags.add(10, "ad");
        flags.add(11, "cd");
    }

    public static String string(int i) {
        return flags.getText(i);
    }

    public static boolean isFlag(int i) {
        flags.check(i);
        return (i < 1 || i > 4) && i < 12;
    }
}
