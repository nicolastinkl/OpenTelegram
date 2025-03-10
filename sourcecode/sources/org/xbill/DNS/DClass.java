package org.xbill.DNS;

/* loaded from: classes4.dex */
public final class DClass {
    private static Mnemonic classes;

    private static class DClassMnemonic extends Mnemonic {
        public DClassMnemonic() {
            super("DClass", 2);
            setPrefix("CLASS");
        }

        @Override // org.xbill.DNS.Mnemonic
        public void check(int i) {
            DClass.check(i);
        }
    }

    static {
        DClassMnemonic dClassMnemonic = new DClassMnemonic();
        classes = dClassMnemonic;
        dClassMnemonic.add(1, "IN");
        classes.add(3, "CH");
        classes.addAlias(3, "CHAOS");
        classes.add(4, "HS");
        classes.addAlias(4, "HESIOD");
        classes.add(254, "NONE");
        classes.add(255, "ANY");
    }

    public static void check(int i) {
        if (i < 0 || i > 65535) {
            throw new InvalidDClassException(i);
        }
    }

    public static String string(int i) {
        return classes.getText(i);
    }
}
